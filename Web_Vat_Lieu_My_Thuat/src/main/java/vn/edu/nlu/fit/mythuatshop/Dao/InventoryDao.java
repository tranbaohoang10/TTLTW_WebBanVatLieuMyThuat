package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.InventoryTransaction;
import vn.edu.nlu.fit.mythuatshop.Model.Product;

import java.util.List;

public class InventoryDao {
    private final Jdbi jdbi;

    public InventoryDao() {
        this.jdbi = JDBIConnector.getJdbi();
    }
    public List<Product> findAllProductsForInventory() {
        String sql = """
                SELECT ID AS id,
                       name,
                       price,
                       discountDefault,
                       categoryID AS categoryId,
                       thumbnail,
                       quantityStock,
                       soldQuantity,
                       status,
                       createAt,
                       brand,
                       isActive
                FROM Products
                ORDER BY quantityStock ASC, ID DESC
                """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(Product.class)
                        .list()
        );
    }

    public List<InventoryTransaction> findAllHistory() {
        String sql = """
                SELECT it.ID AS id,
                       it.productID AS productId,
                       p.name AS productName,
                       it.type AS type,
                       it.quantity AS quantity,
                       it.beforeStock AS beforeStock,
                       it.afterStock AS afterStock,
                       it.note AS note,
                       it.orderID AS orderId,
                       it.createdBy AS createdBy,
                       it.createAt AS createAt
                FROM Inventory_Transactions it
                JOIN Products p ON p.ID = it.productID
                ORDER BY it.createAt DESC, it.ID DESC
                LIMIT 200
                """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(InventoryTransaction.class)
                        .list()
        );
    }

    public boolean importStock(int productId, int quantity, String note, Integer adminId) {
        if (productId <= 0 || quantity <= 0) {
            return false;
        }

        return jdbi.inTransaction(handle -> {
            int beforeStock = getCurrentStockForUpdate(handle, productId);
            int afterStock = beforeStock + quantity;

            int updated = updateStockOnly(handle, productId, afterStock);
            if (updated != 1) {
                return false;
            }

            insertWithHandle(
                    handle,
                    productId,
                    "IMPORT",
                    quantity,
                    beforeStock,
                    afterStock,
                    note,
                    null,
                    adminId
            );

            return true;
        });
    }
    public boolean adjustStock(int productId, int newStock, String note, Integer adminId) {
        if (productId <= 0 || newStock < 0) {
            return false;
        }

        return jdbi.inTransaction(handle -> {
            int beforeStock = getCurrentStockForUpdate(handle, productId);
            int changedQuantity = newStock - beforeStock;

            int updated = updateStockOnly(handle, productId, newStock);
            if (updated != 1) {
                return false;
            }

            insertWithHandle(
                    handle,
                    productId,
                    "ADJUST",
                    changedQuantity,
                    beforeStock,
                    newStock,
                    note,
                    null,
                    adminId
            );

            return true;
        });
    }

    public int getCurrentStockForUpdate(Handle handle, int productId) {
        return handle.createQuery("""
                        SELECT quantityStock
                        FROM Products
                        WHERE ID = :productId
                        FOR UPDATE
                        """)
                .bind("productId", productId)
                .mapTo(Integer.class)
                .findOne()
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm ID = " + productId));
    }

    public int updateStockOnly(Handle handle, int productId, int newStock) {
        String sql = """
                UPDATE Products
                SET quantityStock = :newStock,
                    status = CASE
                        WHEN :newStock > 0 THEN 'Còn hàng'
                        ELSE 'Hết hàng'
                    END
                WHERE ID = :productId
                """;

        return handle.createUpdate(sql)
                .bind("productId", productId)
                .bind("newStock", newStock)
                .execute();
    }

    public void insertWithHandle(Handle handle,
                                 int productId,
                                 String type,
                                 int quantity,
                                 int beforeStock,
                                 int afterStock,
                                 String note,
                                 Integer orderId,
                                 Integer createdBy) {
        String sql = """
                INSERT INTO Inventory_Transactions
                (productID, type, quantity, beforeStock, afterStock, note, orderID, createdBy)
                VALUES
                (:productID, :type, :quantity, :beforeStock, :afterStock, :note, :orderID, :createdBy)
                """;

        handle.createUpdate(sql)
                .bind("productID", productId)
                .bind("type", type)
                .bind("quantity", quantity)
                .bind("beforeStock", beforeStock)
                .bind("afterStock", afterStock)
                .bind("note", note)
                .bind("orderID", orderId)
                .bind("createdBy", createdBy)
                .execute();
    }
}
