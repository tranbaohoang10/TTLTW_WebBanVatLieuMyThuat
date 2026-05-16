package vn.edu.nlu.fit.mythuatshop.Service;

import org.apache.poi.ss.usermodel.*;
import vn.edu.nlu.fit.mythuatshop.Dao.CategoryDao;
import vn.edu.nlu.fit.mythuatshop.Dao.InventoryDao;
import vn.edu.nlu.fit.mythuatshop.Dao.ProductDao;
import vn.edu.nlu.fit.mythuatshop.Dao.SpecificationsDao;
import vn.edu.nlu.fit.mythuatshop.Dao.SubImagesDao;
import vn.edu.nlu.fit.mythuatshop.Model.Category;
import vn.edu.nlu.fit.mythuatshop.Model.Product;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.*;

public class ProductExcelImportService {
    private final ProductDao productDao;
    private final CategoryDao categoryDao;
    private final SubImagesDao subImagesDao;
    private final SpecificationsDao specificationsDao;
    private final InventoryDao inventoryDao;

    public ProductExcelImportService() {
        this.productDao = new ProductDao();
        this.categoryDao = new CategoryDao();
        this.subImagesDao = new SubImagesDao();
        this.specificationsDao = new SpecificationsDao();
        this.inventoryDao = new InventoryDao();
    }

    public ImportResult importExcel(InputStream inputStream, Integer adminId) throws IOException {
        ImportResult result = new ImportResult();

        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet productSheet = getSheetIgnoreCase(workbook, "Products");
            Sheet subImageSheet = getSheetIgnoreCase(workbook, "Subimages");
            Sheet specificationSheet = getSheetIgnoreCase(workbook, "Specifications");

            if (productSheet == null) {
                result.addError("Không tìm thấy sheet Products.");
                return result;
            }

            if (subImageSheet == null) {
                result.addError("Không tìm thấy sheet Subimages.");
                return result;
            }

            if (specificationSheet == null) {
                result.addError("Không tìm thấy sheet Specifications.");
                return result;
            }

            Map<String, Integer> categoryMap = buildCategoryMap();
            Map<String, ProductRow> productRows = readProductSheet(productSheet, categoryMap, result);

            if (!result.isSuccess()) {
                return result;
            }

            List<SubImageRow> subImageRows = readSubImageSheet(subImageSheet, productRows.keySet(), result);
            Map<String, SpecRow> specRows = readSpecificationSheet(specificationSheet, productRows.keySet(), result);

            if (!result.isSuccess()) {
                return result;
            }

            Map<String, Integer> productCodeToId = new HashMap<>();

            for (ProductRow row : productRows.values()) {
                Product product = new Product();
                product.setName(row.name);
                product.setPrice(row.price);
                product.setDiscountDefault(row.discountDefault);
                product.setCategoryId(row.categoryId);
                product.setThumbnail(row.thumbnail);
                product.setQuantityStock(row.quantityStock);
                product.setSoldQuantity(0);
                product.setStatus(row.quantityStock > 0 ? "Còn hàng" : "Hết hàng");
                product.setCreateAt(new Timestamp(System.currentTimeMillis()));
                product.setBrand(row.brand);
                product.setIsActive(row.isActive);

                int productId = productDao.insertReturnId(product);
                productCodeToId.put(row.productCode, productId);

                if (row.quantityStock > 0) {
                    inventoryDao.recordInitialStock(
                            productId,
                            row.quantityStock,
                            "Tồn kho ban đầu khi import Excel",
                            adminId
                    );
                }

                result.increaseImportedCount();
            }

            for (SubImageRow row : subImageRows) {
                Integer productId = productCodeToId.get(row.productCode);
                if (productId != null) {
                    subImagesDao.insert(productId, row.image);
                }
            }

            for (Map.Entry<String, SpecRow> entry : specRows.entrySet()) {
                Integer productId = productCodeToId.get(entry.getKey());
                SpecRow row = entry.getValue();

                if (productId != null) {
                    specificationsDao.upsert(
                            productId,
                            row.size,
                            row.standard,
                            row.madeIn,
                            row.warning
                    );
                }
            }

            return result;
        }
    }

    private Map<String, ProductRow> readProductSheet(Sheet sheet,
                                                     Map<String, Integer> categoryMap,
                                                     ImportResult result) {
        Map<String, ProductRow> rows = new LinkedHashMap<>();
        DataFormatter formatter = new DataFormatter();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row excelRow = sheet.getRow(i);

            if (isEmptyRow(excelRow)) {
                continue;
            }

            int excelLine = i + 1;

            String productCode = normalizeCode(getString(excelRow, 0, formatter));
            String name = getString(excelRow, 1, formatter);
            String categoryName = getString(excelRow, 2, formatter);
            double price = getDouble(excelRow, 3, formatter, -1);
            int discountDefault = getInt(excelRow, 4, formatter, 0);
            int quantityStock = getInt(excelRow, 5, formatter, -1);
            String brand = getString(excelRow, 6, formatter);
            String thumbnail = normalizeImagePath(getString(excelRow, 7, formatter));
            int isActive = getInt(excelRow, 8, formatter, 1);

            if (productCode.isEmpty()) {
                result.addError("Dòng " + excelLine + " sheet Products: productCode không được rỗng.");
                continue;
            }

            if (rows.containsKey(productCode)) {
                result.addError("Dòng " + excelLine + " sheet Products: productCode bị trùng: " + productCode);
                continue;
            }

            if (name.isEmpty()) {
                result.addError("Dòng " + excelLine + " sheet Products: tên sản phẩm không được rỗng.");
            }

            if (categoryName.isEmpty()) {
                result.addError("Dòng " + excelLine + " sheet Products: danh mục không được rỗng.");
            }

            Integer categoryId = categoryMap.get(normalizeKey(categoryName));
            if (categoryId == null) {
                result.addError("Dòng " + excelLine + " sheet Products: danh mục không tồn tại: " + categoryName);
            }

            if (price < 0) {
                result.addError("Dòng " + excelLine + " sheet Products: giá sản phẩm không hợp lệ.");
            }

            if (discountDefault < 0 || discountDefault > 100) {
                result.addError("Dòng " + excelLine + " sheet Products: giảm giá phải từ 0 đến 100.");
            }

            if (quantityStock < 0) {
                result.addError("Dòng " + excelLine + " sheet Products: số lượng tồn kho không hợp lệ.");
            }

            if (thumbnail.isEmpty()) {
                result.addError("Dòng " + excelLine + " sheet Products: ảnh chính không được rỗng.");
            }

            if (isActive != 0 && isActive != 1) {
                result.addError("Dòng " + excelLine + " sheet Products: isActive chỉ được nhập 0 hoặc 1.");
            }

            if (result.hasErrors()) {
                continue;
            }

            ProductRow row = new ProductRow();
            row.productCode = productCode;
            row.name = name;
            row.categoryId = categoryId;
            row.price = price;
            row.discountDefault = discountDefault;
            row.quantityStock = quantityStock;
            row.brand = brand;
            row.thumbnail = thumbnail;
            row.isActive = isActive;

            rows.put(productCode, row);
        }

        if (rows.isEmpty() && !result.hasErrors()) {
            result.addError("Sheet Products không có sản phẩm nào để import.");
        }

        return rows;
    }

    private List<SubImageRow> readSubImageSheet(Sheet sheet,
                                                Set<String> productCodes,
                                                ImportResult result) {
        List<SubImageRow> rows = new ArrayList<>();
        Map<String, Integer> imageCountByProductCode = new HashMap<>();
        DataFormatter formatter = new DataFormatter();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row excelRow = sheet.getRow(i);

            if (isEmptyRow(excelRow)) {
                continue;
            }

            int excelLine = i + 1;

            String productCode = normalizeCode(getString(excelRow, 0, formatter));
            String image = normalizeImagePath(getString(excelRow, 1, formatter));

            if (productCode.isEmpty()) {
                result.addError("Dòng " + excelLine + " sheet Subimages: productCode không được rỗng.");
                continue;
            }

            if (!productCodes.contains(productCode)) {
                result.addError("Dòng " + excelLine + " sheet Subimages: productCode không tồn tại trong sheet Products: " + productCode);
                continue;
            }

            if (image.isEmpty()) {
                result.addError("Dòng " + excelLine + " sheet Subimages: image không được rỗng.");
                continue;
            }

            int currentCount = imageCountByProductCode.getOrDefault(productCode, 0);
            if (currentCount >= 3) {
                result.addError("Dòng " + excelLine + " sheet Subimages: mỗi sản phẩm chỉ nên có tối đa 3 ảnh phụ.");
                continue;
            }

            imageCountByProductCode.put(productCode, currentCount + 1);

            SubImageRow row = new SubImageRow();
            row.productCode = productCode;
            row.image = image;
            rows.add(row);
        }

        return rows;
    }

    private Map<String, SpecRow> readSpecificationSheet(Sheet sheet,
                                                        Set<String> productCodes,
                                                        ImportResult result) {
        Map<String, SpecRow> rows = new HashMap<>();
        DataFormatter formatter = new DataFormatter();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row excelRow = sheet.getRow(i);

            if (isEmptyRow(excelRow)) {
                continue;
            }

            int excelLine = i + 1;

            String productCode = normalizeCode(getString(excelRow, 0, formatter));
            String size = getString(excelRow, 1, formatter);
            String standard = getString(excelRow, 2, formatter);
            String madeIn = getString(excelRow, 3, formatter);
            String warning = getString(excelRow, 4, formatter);

            if (productCode.isEmpty()) {
                result.addError("Dòng " + excelLine + " sheet Specifications: productCode không được rỗng.");
                continue;
            }

            if (!productCodes.contains(productCode)) {
                result.addError("Dòng " + excelLine + " sheet Specifications: productCode không tồn tại trong sheet Products: " + productCode);
                continue;
            }

            if (rows.containsKey(productCode)) {
                result.addError("Dòng " + excelLine + " sheet Specifications: mỗi sản phẩm chỉ nên có 1 dòng thông số kỹ thuật.");
                continue;
            }

            SpecRow row = new SpecRow();
            row.productCode = productCode;
            row.size = size;
            row.standard = standard;
            row.madeIn = madeIn;
            row.warning = warning;

            rows.put(productCode, row);
        }

        return rows;
    }

    private Map<String, Integer> buildCategoryMap() {
        List<Category> categories = categoryDao.findAll();
        Map<String, Integer> map = new HashMap<>();

        if (categories != null) {
            for (Category category : categories) {
                map.put(normalizeKey(category.getCategoryName()), category.getId());
            }
        }

        return map;
    }

    private Sheet getSheetIgnoreCase(Workbook workbook, String sheetName) {
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet.getSheetName().equalsIgnoreCase(sheetName)) {
                return sheet;
            }
        }
        return null;
    }

    private boolean isEmptyRow(Row row) {
        if (row == null) {
            return true;
        }

        DataFormatter formatter = new DataFormatter();

        for (int i = 0; i < 10; i++) {
            Cell cell = row.getCell(i);
            if (cell != null && !formatter.formatCellValue(cell).trim().isEmpty()) {
                return false;
            }
        }

        return true;
    }

    private String getString(Row row, int index, DataFormatter formatter) {
        if (row == null) {
            return "";
        }

        Cell cell = row.getCell(index);
        if (cell == null) {
            return "";
        }

        return formatter.formatCellValue(cell).trim();
    }

    private double getDouble(Row row, int index, DataFormatter formatter, double defaultValue) {
        String value = getString(row, index, formatter);

        if (value.isEmpty()) {
            return defaultValue;
        }

        try {
            value = value.replace(",", "");
            return Double.parseDouble(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private int getInt(Row row, int index, DataFormatter formatter, int defaultValue) {
        String value = getString(row, index, formatter);

        if (value.isEmpty()) {
            return defaultValue;
        }

        try {
            value = value.replace(",", "");
            return (int) Math.round(Double.parseDouble(value));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private String normalizeCode(String value) {
        if (value == null) {
            return "";
        }

        return value.trim().toUpperCase(Locale.ROOT);
    }

    private String normalizeKey(String value) {
        if (value == null) {
            return "";
        }

        return value.trim().toLowerCase(Locale.ROOT);
    }

    private String normalizeImagePath(String value) {
        if (value == null) {
            return "";
        }

        value = value.trim();

        if (value.isEmpty()) {
            return "";
        }

        if (value.startsWith("http://") || value.startsWith("https://")) {
            return value;
        }

        if (!value.startsWith("/")) {
            value = "/" + value;
        }

        return value;
    }

    private static class ProductRow {
        String productCode;
        String name;
        int categoryId;
        double price;
        int discountDefault;
        int quantityStock;
        String brand;
        String thumbnail;
        int isActive;
    }

    private static class SubImageRow {
        String productCode;
        String image;
    }

    private static class SpecRow {
        String productCode;
        String size;
        String standard;
        String madeIn;
        String warning;
    }

    public static class ImportResult {
        private final List<String> errors = new ArrayList<>();
        private int importedCount;

        public boolean isSuccess() {
            return errors.isEmpty();
        }

        public boolean hasErrors() {
            return !errors.isEmpty();
        }

        public List<String> getErrors() {
            return errors;
        }

        public int getImportedCount() {
            return importedCount;
        }

        public void addError(String error) {
            errors.add(error);
        }

        public void increaseImportedCount() {
            importedCount++;
        }
    }
}