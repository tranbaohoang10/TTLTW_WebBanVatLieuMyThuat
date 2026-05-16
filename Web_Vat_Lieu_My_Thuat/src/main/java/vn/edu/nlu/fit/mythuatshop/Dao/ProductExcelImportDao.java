package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.Excel.ProductExcelRow;
import vn.edu.nlu.fit.mythuatshop.Model.Excel.SpecificationExcelRow;

import java.sql.Timestamp;

public class ProductExcelImportDao {
    private final Jdbi jdbi;

    public ProductExcelImportDao() {
        this.jdbi = JDBIConnector.getJdbi();
    }

    public Jdbi getJdbi() {
        return jdbi;
    }

    public int insertProduct(Handle handle, ProductExcelRow row) {
        String sql = """
                INSERT INTO products
                (name, price, discountDefault, categoryID, thumbnail,
                 quantityStock, soldQuantity, status, createAt, brand, isActive)
                VALUES
                (:name, :price, :discountDefault, :categoryID, :thumbnail,
                 :quantityStock, :soldQuantity, :status, :createAt, :brand, :isActive)
                """;

        return handle.createUpdate(sql)
                .bind("name", row.getName())
                .bind("price", row.getPrice())
                .bind("discountDefault", row.getDiscountDefault())
                .bind("categoryID", row.getCategoryId())
                .bind("thumbnail", row.getThumbnail())
                .bind("quantityStock", row.getQuantityStock())
                .bind("soldQuantity", 0)
                .bind("status", row.getQuantityStock() > 0 ? "Còn hàng" : "Hết hàng")
                .bind("createAt", new Timestamp(System.currentTimeMillis()))
                .bind("brand", row.getBrand())
                .bind("isActive", row.getIsActive())
                .executeAndReturnGeneratedKeys("ID")
                .mapTo(Integer.class)
                .one();
    }

    public void insertSubImage(Handle handle, int productId, String image) {
        String sql = """
                INSERT INTO subimages(productID, image)
                VALUES (:productID, :image)
                """;

        handle.createUpdate(sql)
                .bind("productID", productId)
                .bind("image", image)
                .execute();
    }

    public void upsertSpecification(Handle handle, int productId, SpecificationExcelRow row) {
        String sql = """
                INSERT INTO specifications(productID, Size, Standard, MadeIn, Warning)
                VALUES (:productID, :size, :standard, :madeIn, :warning)
                ON DUPLICATE KEY UPDATE
                    Size = VALUES(Size),
                    Standard = VALUES(Standard),
                    MadeIn = VALUES(MadeIn),
                    Warning = VALUES(Warning)
                """;

        handle.createUpdate(sql)
                .bind("productID", productId)
                .bind("size", row.getSize())
                .bind("standard", row.getStandard())
                .bind("madeIn", row.getMadeIn())
                .bind("warning", row.getWarning())
                .execute();
    }

    public void recordInitialStock(Handle handle,
                                   int productId,
                                   int quantity,
                                   Integer adminId) {
        if (productId < 0 || quantity <= 0) {
            return;
        }

        String sql = """
                INSERT INTO inventory_transactions
                (productID, type, quantity, beforeStock, afterStock, note, orderID, createdBy)
                VALUES
                (:productID, 'IMPORT', :quantity, 0, :quantity, :note, NULL, :createdBy)
                """;

        handle.createUpdate(sql)
                .bind("productID", productId)
                .bind("quantity", quantity)
                .bind("note", "Tồn kho ban đầu khi import Excel")
                .bind("createdBy", adminId)
                .execute();
    }
}