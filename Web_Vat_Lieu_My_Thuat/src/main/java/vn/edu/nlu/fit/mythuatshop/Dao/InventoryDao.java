package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.InventoryReportRow;
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
                FROM products
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
                FROM inventory_transactions it
                JOIN products p ON p.ID = it.productID
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
        if (productId < 0 || quantity <= 0) {
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
        if (productId < 0 || newStock < 0) {
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
                        FROM products
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
                UPDATE products
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
                INSERT INTO inventory_transactions
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
    public void recordInitialStock(int productId, int quantity, String note, Integer adminId) {
        if (productId < 0 || quantity <= 0) {
            return;
        }

        jdbi.useHandle(handle ->
                insertWithHandle(
                        handle,
                        productId,
                        "IMPORT",
                        quantity,
                        0,
                        quantity,
                        note,
                        null,
                        adminId
                )
        );
    }
    public int countLowStockProducts(int threshold) {
        String sql = """
            SELECT COUNT(*)
            FROM products
            WHERE isActive = 1
              AND quantityStock > 0
              AND quantityStock <= :threshold
            """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("threshold", threshold)
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public int countOutOfStockProducts() {
        String sql = """
            SELECT COUNT(*)
            FROM products
            WHERE isActive = 1
              AND quantityStock <= 0
            """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapTo(Integer.class)
                        .one()
        );
    }
    public List<Product> findLowStockProducts(int threshold) {
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
            FROM products
            WHERE isActive = 1
              AND quantityStock > 0
              AND quantityStock <= :threshold
            ORDER BY quantityStock ASC, ID ASC
            """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("threshold", threshold)
                        .mapToBean(Product.class)
                        .list()
        );
    }

    public List<Product> findOutOfStockProducts() {
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
            FROM products
            WHERE isActive = 1
              AND quantityStock <= 0
            ORDER BY ID ASC
            """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(Product.class)
                        .list()
        );
    }
    public List<InventoryReportRow> findInventoryReportRows() {
        String sql = """
            SELECT p.ID AS productId,
                   p.name AS productName,
                   c.categoryName AS categoryName,
                   p.brand AS brand,
                   p.price AS price,
                   p.discountDefault AS discountDefault,
                   p.quantityStock AS quantityStock,
                   p.soldQuantity AS soldQuantity,
                   p.isActive AS isActive,

                   COALESCE(SUM(CASE
                       WHEN it.type = 'IMPORT' THEN it.quantity
                       ELSE 0
                   END), 0) AS totalImported,

                   COALESCE(SUM(CASE
                       WHEN it.type = 'SALE' THEN ABS(it.quantity)
                       ELSE 0
                   END), 0) AS totalSale,

                   COALESCE(SUM(CASE
                       WHEN it.type = 'CANCEL' THEN it.quantity
                       ELSE 0
                   END), 0) AS totalCancel,

                   COALESCE(SUM(CASE
                       WHEN it.type = 'ADJUST' THEN it.quantity
                       ELSE 0
                   END), 0) AS totalAdjust,

                   MAX(it.createAt) AS lastTransactionAt
            FROM products p
            LEFT JOIN categories c ON c.id = p.categoryID
            LEFT JOIN inventory_transactions it ON it.productID = p.ID
            GROUP BY p.ID,
                     p.ID,
                     p.name,
                     c.categoryName,
                     p.brand,
                     p.price,
                     p.discountDefault,
                     p.quantityStock,
                     p.soldQuantity,
                     p.isActive
            ORDER BY p.quantityStock ASC, p.ID DESC
            """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(InventoryReportRow.class)
                        .list()
        );
    }
}
