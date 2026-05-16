package vn.edu.nlu.fit.mythuatshop.Dao;

import vn.edu.nlu.fit.mythuatshop.Model.Excel.ProductExcelImportData;
import vn.edu.nlu.fit.mythuatshop.Model.Excel.ProductExcelRow;
import vn.edu.nlu.fit.mythuatshop.Model.Excel.SpecificationExcelRow;
import vn.edu.nlu.fit.mythuatshop.Model.Excel.SubImageExcelRow;
import vn.edu.nlu.fit.mythuatshop.Model.Product;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class ProductExcelImportDao {
    private final ProductDao productDao;
    private final SubImagesDao subImagesDao;
    private final SpecificationsDao specificationsDao;
    private final InventoryDao inventoryDao;

    public ProductExcelImportDao() {
        this.productDao = new ProductDao();
        this.subImagesDao = new SubImagesDao();
        this.specificationsDao = new SpecificationsDao();
        this.inventoryDao = new InventoryDao();
    }

    public int saveImportData(ProductExcelImportData data, Integer adminId) {
        Map<String, Integer> productCodeToId = new HashMap<>();
        int importedCount = 0;
        for (ProductExcelRow row : data.getProducts().values()) {
            Product product = new Product();

            product.setName(row.getName());
            product.setPrice(row.getPrice());
            product.setDiscountDefault(row.getDiscountDefault());
            product.setCategoryId(row.getCategoryId());
            product.setThumbnail(row.getThumbnail());
            product.setQuantityStock(row.getQuantityStock());
            product.setSoldQuantity(0);
            product.setStatus(row.getQuantityStock() > 0 ? "Còn hàng" : "Hết hàng");
            product.setCreateAt(new Timestamp(System.currentTimeMillis()));
            product.setBrand(row.getBrand());
            product.setIsActive(row.getIsActive());

            int productId = productDao.insertReturnId(product);

            productCodeToId.put(row.getProductCode(), productId);
            if (row.getQuantityStock() > 0) {
                inventoryDao.recordInitialStock(
                        productId,
                        row.getQuantityStock(),
                        "Tồn kho ban đầu khi import Excel",
                        adminId
                );
            }

            importedCount++;
        }
        for (SubImageExcelRow row : data.getSubImages()) {
            Integer productId = productCodeToId.get(row.getProductCode());

            if (productId != null) {
                subImagesDao.insert(productId, row.getImage());
            }
        }
        for (Map.Entry<String, SpecificationExcelRow> entry : data.getSpecifications().entrySet()) {
            String productCode = entry.getKey();
            SpecificationExcelRow row = entry.getValue();

            Integer productId = productCodeToId.get(productCode);

            if (productId != null) {
                specificationsDao.upsert(
                        productId,
                        row.getSize(),
                        row.getStandard(),
                        row.getMadeIn(),
                        row.getWarning()
                );
            }
        }

        return importedCount;
    }
}