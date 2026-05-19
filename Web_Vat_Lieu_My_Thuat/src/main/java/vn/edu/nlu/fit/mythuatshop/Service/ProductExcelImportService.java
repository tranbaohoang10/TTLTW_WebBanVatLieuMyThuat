package vn.edu.nlu.fit.mythuatshop.Service;

import org.apache.poi.ss.usermodel.*;
import vn.edu.nlu.fit.mythuatshop.Dao.CategoryDao;
import vn.edu.nlu.fit.mythuatshop.Dao.ProductExcelImportDao;
import vn.edu.nlu.fit.mythuatshop.Model.Category;
import vn.edu.nlu.fit.mythuatshop.Model.Excel.ProductExcelImportData;
import vn.edu.nlu.fit.mythuatshop.Model.Excel.ProductExcelImportResult;
import vn.edu.nlu.fit.mythuatshop.Model.Excel.ProductExcelRow;
import vn.edu.nlu.fit.mythuatshop.Model.Excel.SpecificationExcelRow;
import vn.edu.nlu.fit.mythuatshop.Model.Excel.SubImageExcelRow;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ProductExcelImportService {
    private final CategoryDao categoryDao;
    private final ProductExcelImportDao productExcelImportDao;

    public ProductExcelImportService() {
        this.categoryDao = new CategoryDao();
        this.productExcelImportDao = new ProductExcelImportDao();
    }

    public ProductExcelImportResult importExcel(InputStream inputStream, Integer adminId) throws IOException {
        ProductExcelImportResult result = new ProductExcelImportResult();

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

            ProductExcelImportData importData = new ProductExcelImportData();

            Map<String, ProductExcelRow> productRows =
                    readProductSheet(productSheet, categoryMap, result);

            importData.setProducts(productRows);

            if (productRows.isEmpty() && !result.hasErrors()) {
                result.addError("Sheet Products không có sản phẩm nào để import.");
                return result;
            }

            List<SubImageExcelRow> subImageRows =
                    readSubImageSheet(subImageSheet, productRows.keySet(), result);

            importData.setSubImages(subImageRows);

            Map<String, SpecificationExcelRow> specificationRows =
                    readSpecificationSheet(specificationSheet, productRows.keySet(), result);

            importData.setSpecifications(specificationRows);
            if (result.hasErrors()) {
                return result;
            }

            int importedCount = saveImportData(importData, adminId);
            result.setImportedCount(importedCount);

            return result;
        }
    }

    private Map<String, ProductExcelRow> readProductSheet(Sheet sheet,
                                                          Map<String, Integer> categoryMap,
                                                          ProductExcelImportResult result) {
        Map<String, ProductExcelRow> rows = new LinkedHashMap<>();
        DataFormatter formatter = new DataFormatter();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row excelRow = sheet.getRow(i);

            if (isEmptyRow(excelRow)) {
                continue;
            }

            int excelLine = i + 1;
            boolean rowHasError = false;
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
                rowHasError = true;
            } else if (rows.containsKey(productCode)) {
                result.addError("Dòng " + excelLine + " sheet Products: productCode bị trùng: " + productCode);
                rowHasError = true;
            }

            if (name.isEmpty()) {
                result.addError("Dòng " + excelLine + " sheet Products: tên sản phẩm không được rỗng.");
                rowHasError = true;
            }

            if (categoryName.isEmpty()) {
                result.addError("Dòng " + excelLine + " sheet Products: danh mục không được rỗng.");
                rowHasError = true;
            }

            Integer categoryId = categoryMap.get(normalizeKey(categoryName));
            if (categoryId == null) {
                result.addError("Dòng " + excelLine + " sheet Products: danh mục không tồn tại: " + categoryName);
                rowHasError = true;
            }

            if (price < 0) {
                result.addError("Dòng " + excelLine + " sheet Products: giá sản phẩm không hợp lệ.");
                rowHasError = true;
            }

            if (discountDefault < 0 || discountDefault > 100) {
                result.addError("Dòng " + excelLine + " sheet Products: giảm giá phải từ 0 đến 100.");
                rowHasError = true;
            }

            if (quantityStock < 0) {
                result.addError("Dòng " + excelLine + " sheet Products: số lượng tồn kho không hợp lệ.");
                rowHasError = true;
            }

            if (thumbnail.isEmpty()) {
                result.addError("Dòng " + excelLine + " sheet Products: ảnh chính không được rỗng.");
                rowHasError = true;
            }

            if (isActive != 0 && isActive != 1) {
                result.addError("Dòng " + excelLine + " sheet Products: isActive chỉ được nhập 0 hoặc 1.");
                rowHasError = true;
            }

            if (rowHasError) {
                continue;
            }

            ProductExcelRow row = new ProductExcelRow();
            row.setProductCode(productCode);
            row.setName(name);
            row.setCategoryId(categoryId);
            row.setPrice(price);
            row.setDiscountDefault(discountDefault);
            row.setQuantityStock(quantityStock);
            row.setBrand(brand);
            row.setThumbnail(thumbnail);
            row.setIsActive(isActive);

            rows.put(productCode, row);
        }

        return rows;
    }

    private List<SubImageExcelRow> readSubImageSheet(Sheet sheet,
                                                     Set<String> productCodes,
                                                     ProductExcelImportResult result) {
        List<SubImageExcelRow> rows = new ArrayList<>();
        Map<String, Integer> imageCountByProductCode = new HashMap<>();
        DataFormatter formatter = new DataFormatter();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row excelRow = sheet.getRow(i);

            if (isEmptyRow(excelRow)) {
                continue;
            }

            int excelLine = i + 1;
            boolean rowHasError = false;
            String productCode = normalizeCode(getString(excelRow, 0, formatter));
            String image = normalizeImagePath(getString(excelRow, 1, formatter));

            if (productCode.isEmpty()) {
                result.addError("Dòng " + excelLine + " sheet Subimages: productCode không được rỗng.");
                rowHasError = true;
            } else if (!productCodes.contains(productCode)) {
                result.addError("Dòng " + excelLine + " sheet Subimages: productCode không tồn tại trong sheet Products: " + productCode);
                rowHasError = true;
            }

            if (image.isEmpty()) {
                result.addError("Dòng " + excelLine + " sheet Subimages: image không được rỗng.");
                rowHasError = true;
            }

            int currentCount = imageCountByProductCode.getOrDefault(productCode, 0);
            if (currentCount >= 3) {
                result.addError("Dòng " + excelLine + " sheet Subimages: mỗi sản phẩm chỉ được tối đa 3 ảnh phụ.");
                rowHasError = true;
            }

            if (rowHasError) {
                continue;
            }

            imageCountByProductCode.put(productCode, currentCount + 1);

            SubImageExcelRow row = new SubImageExcelRow();
            row.setProductCode(productCode);
            row.setImage(image);

            rows.add(row);
        }

        return rows;
    }

    private Map<String, SpecificationExcelRow> readSpecificationSheet(Sheet sheet,
                                                                      Set<String> productCodes,
                                                                      ProductExcelImportResult result) {
        Map<String, SpecificationExcelRow> rows = new LinkedHashMap<>();
        DataFormatter formatter = new DataFormatter();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row excelRow = sheet.getRow(i);

            if (isEmptyRow(excelRow)) {
                continue;
            }

            int excelLine = i + 1;
            boolean rowHasError = false;
            String productCode = normalizeCode(getString(excelRow, 0, formatter));
            String size = getString(excelRow, 1, formatter);
            String standard = getString(excelRow, 2, formatter);
            String madeIn = getString(excelRow, 3, formatter);
            String warning = getString(excelRow, 4, formatter);

            if (productCode.isEmpty()) {
                result.addError("Dòng " + excelLine + " sheet Specifications: productCode không được rỗng.");
                rowHasError = true;
            } else if (!productCodes.contains(productCode)) {
                result.addError("Dòng " + excelLine + " sheet Specifications: productCode không tồn tại trong sheet Products: " + productCode);
                rowHasError = true;
            } else if (rows.containsKey(productCode)) {
                result.addError("Dòng " + excelLine + " sheet Specifications: mỗi sản phẩm chỉ được có 1 dòng thông số kỹ thuật.");
                rowHasError = true;
            }
            if (rowHasError) {
                continue;
            }

            SpecificationExcelRow row = new SpecificationExcelRow();
            row.setProductCode(productCode);
            row.setSize(size);
            row.setStandard(standard);
            row.setMadeIn(madeIn);
            row.setWarning(warning);

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
    private int saveImportData(ProductExcelImportData data, Integer adminId) {
        return productExcelImportDao.getJdbi().inTransaction(handle -> {
            Map<String, Integer> productCodeToId = new HashMap<>();
            int importedCount = 0;


            for (ProductExcelRow row : data.getProducts().values()) {
                Integer productId = productExcelImportDao.findProductIdByCode(
                        handle,
                        row.getProductCode()
                );

                if (productId == null) {
                    // productCode chưa tồn tại -> thêm sản phẩm mới
                    productId = productExcelImportDao.insertProduct(handle, row);

                    if (row.getQuantityStock() > 0) {
                        productExcelImportDao.recordInitialStock(
                                handle,
                                productId,
                                row.getQuantityStock(),
                                adminId
                        );
                    }
                } else {
                    // productCode đã tồn tại -> cập nhật sản phẩm cũ
                    productExcelImportDao.updateProductByCode(handle, row);
                }
                productCodeToId.put(row.getProductCode(), productId);
                importedCount++;
            }
            Set<Integer> productIdsNeedRefreshSubImages = new HashSet<>();

            for (SubImageExcelRow row : data.getSubImages()) {
                Integer productId = productCodeToId.get(row.getProductCode());

                if (productId != null) {
                    productIdsNeedRefreshSubImages.add(productId);
                }
            }

            for (Integer productId : productIdsNeedRefreshSubImages) {
                productExcelImportDao.deleteSubImagesByProductId(handle, productId);
            }
            for (SubImageExcelRow row : data.getSubImages()) {
                Integer productId = productCodeToId.get(row.getProductCode());

                if (productId != null) {
                    productExcelImportDao.insertSubImage(
                            handle,
                            productId,
                            row.getImage()
                    );
                }
            }
            for (Map.Entry<String, SpecificationExcelRow> entry : data.getSpecifications().entrySet()) {
                String productCode = entry.getKey();
                SpecificationExcelRow row = entry.getValue();

                Integer productId = productCodeToId.get(productCode);

                if (productId != null) {
                    productExcelImportDao.upsertSpecification(
                            handle,
                            productId,
                            row
                    );
                }
            }

            return importedCount;
        });
    }
}