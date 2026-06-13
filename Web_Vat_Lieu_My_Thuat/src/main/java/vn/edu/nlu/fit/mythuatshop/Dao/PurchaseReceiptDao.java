package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.Product;
import vn.edu.nlu.fit.mythuatshop.Model.PurchaseReceipt;
import vn.edu.nlu.fit.mythuatshop.Model.PurchaseReceiptDetail;
import vn.edu.nlu.fit.mythuatshop.Model.Supplier;

import java.util.List;

public class PurchaseReceiptDao {
    private final Jdbi jdbi;

    public PurchaseReceiptDao() {
        this.jdbi = JDBIConnector.getJdbi();
    }

    public List<Supplier> findActiveSuppliersForCreateForm() {
        String sql = """
                SELECT ID AS id,
                       supplierCode AS supplierCode,
                       name,
                       phone,
                       email,
                       address,
                       note,
                       isActive,
                       createAt
                FROM suppliers
                WHERE isActive = 1
                ORDER BY name ASC
                """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(Supplier.class)
                        .list()
        );
    }
    public List<Product> findActiveProductsForCreateForm() {
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
            ORDER BY name ASC
            """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(Product.class)
                        .list()
        );
    }
    public boolean existsActiveSupplier(int supplierId) {
        String sql = """
            SELECT COUNT(*)
            FROM suppliers
            WHERE ID = :supplierId
              AND isActive = 1
            """;

        Integer count = jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("supplierId", supplierId)
                        .mapTo(Integer.class)
                        .one()
        );

        return count != null && count > 0;
    }
    public boolean existsActiveProduct(int productId) {
        String sql = """
            SELECT COUNT(*)
            FROM products
            WHERE ID = :productId
              AND isActive = 1
            """;

        Integer count = jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("productId", productId)
                        .mapTo(Integer.class)
                        .one()
        );

        return count != null && count > 0;
    }
    private int insertPurchaseReceipt(Handle handle, PurchaseReceipt receipt) {
        String sql = """
            INSERT INTO purchase_receipts
            (supplierID, importDate, createdBy, supplierDocumentCode,
             attachmentPath, totalAmount, note, status)
            VALUES
            (:supplierID, :importDate, :createdBy, :supplierDocumentCode,
             :attachmentPath, :totalAmount, :note, :status)
            """;

        return handle.createUpdate(sql)
                .bind("supplierID", receipt.getSupplierId())
                .bind("importDate", receipt.getImportDate())
                .bind("createdBy", receipt.getCreatedBy())
                .bind("supplierDocumentCode", receipt.getSupplierDocumentCode())
                .bind("attachmentPath", receipt.getAttachmentPath())
                .bind("totalAmount", receipt.getTotalAmount())
                .bind("note", receipt.getNote())
                .bind("status", receipt.getStatus())
                .executeAndReturnGeneratedKeys("ID")
                .mapTo(Integer.class)
                .one();
    }
    public int createPurchaseReceipt(PurchaseReceipt receipt,
                                     List<PurchaseReceiptDetail> details) {
        return jdbi.inTransaction(handle -> {
            int receiptId = insertPurchaseReceipt(handle, receipt);

            for (PurchaseReceiptDetail detail : details) {
                detail.setReceiptId(receiptId);

                insertPurchaseReceiptDetail(handle, detail);

                int beforeStock = getCurrentStockForUpdate(handle, detail.getProductId());
                int afterStock = beforeStock + detail.getQuantity();

                int updated = increaseProductStock(
                        handle,
                        detail.getProductId(),
                        detail.getQuantity()
                );

                if (updated != 1) {
                    throw new IllegalStateException(
                            "Không thể cập nhật tồn kho cho sản phẩm ID: " + detail.getProductId()
                    );
                }

                insertInventoryTransactionForImport(
                        handle,
                        detail.getProductId(),
                        detail.getQuantity(),
                        beforeStock,
                        afterStock,
                        receiptId,
                        receipt.getCreatedBy()
                );
            }

            return receiptId;
        });
    }
    private int getCurrentStockForUpdate(Handle handle, int productId) {
        String sql = """
            SELECT quantityStock
            FROM products
            WHERE ID = :productID
            FOR UPDATE
            """;

        return handle.createQuery(sql)
                .bind("productID", productId)
                .mapTo(Integer.class)
                .findOne()
                .orElseThrow(() -> new IllegalStateException(
                        "Không tìm thấy sản phẩm ID: " + productId
                ));
    }

    private int increaseProductStock(Handle handle, int productId, int quantity) {
        String sql = """
            UPDATE products
            SET quantityStock = quantityStock + :quantity,
                status = 'Còn hàng'
            WHERE ID = :productID
            """;

        return handle.createUpdate(sql)
                .bind("productID", productId)
                .bind("quantity", quantity)
                .execute();
    }

    private void insertInventoryTransactionForImport(Handle handle,
                                                     int productId,
                                                     int quantity,
                                                     int beforeStock,
                                                     int afterStock,
                                                     int purchaseReceiptId,
                                                     Integer createdBy) {
        String sql = """
            INSERT INTO inventory_transactions
            (productID, type, quantity, beforeStock, afterStock,
             note, orderID, createdBy, purchaseReceiptID)
            VALUES
            (:productID, 'IMPORT', :quantity, :beforeStock, :afterStock,
             :note, NULL, :createdBy, :purchaseReceiptID)
            """;

        handle.createUpdate(sql)
                .bind("productID", productId)
                .bind("quantity", quantity)
                .bind("beforeStock", beforeStock)
                .bind("afterStock", afterStock)
                .bind("note", "Nhập hàng từ phiếu nhập #" + purchaseReceiptId)
                .bind("createdBy", createdBy)
                .bind("purchaseReceiptID", purchaseReceiptId)
                .execute();
    }
    private void insertPurchaseReceiptDetail(Handle handle,
                                             PurchaseReceiptDetail detail) {
        String sql = """
            INSERT INTO purchase_receipt_details
            (receiptID, productID, quantity, importPrice, lineTotal)
            VALUES
            (:receiptID, :productID, :quantity, :importPrice, :lineTotal)
            """;

        handle.createUpdate(sql)
                .bind("receiptID", detail.getReceiptId())
                .bind("productID", detail.getProductId())
                .bind("quantity", detail.getQuantity())
                .bind("importPrice", detail.getImportPrice())
                .bind("lineTotal", detail.getLineTotal())
                .execute();
    }
    public List<PurchaseReceipt> findAllPurchaseReceipts() {
        String sql = """
            SELECT pr.ID AS id,
                   pr.supplierID AS supplierId,
                   s.name AS supplierName,
                   pr.importDate AS importDate,
                   pr.createdBy AS createdBy,
                   COALESCE(u.fullName, CONCAT('Admin #', pr.createdBy), 'Admin') AS createdByName,
                   pr.supplierDocumentCode AS supplierDocumentCode,
                   pr.attachmentPath AS attachmentPath,
                   pr.totalAmount AS totalAmount,
                   pr.note AS note,
                   pr.status AS status,
                   pr.createAt AS createAt
            FROM purchase_receipts pr
            JOIN suppliers s ON s.ID = pr.supplierID
            LEFT JOIN users u ON u.id = pr.createdBy
            ORDER BY pr.ID DESC
            """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(PurchaseReceipt.class)
                        .list()
        );
    }
    public PurchaseReceipt findPurchaseReceiptById(int receiptId) {
        String sql = """
            SELECT pr.ID AS id,
                   pr.supplierID AS supplierId,
                   s.name AS supplierName,
                   pr.importDate AS importDate,
                   pr.createdBy AS createdBy,
                   COALESCE(u.fullName, CONCAT('Admin #', pr.createdBy), 'Admin') AS createdByName,
                   pr.supplierDocumentCode AS supplierDocumentCode,
                   pr.attachmentPath AS attachmentPath,
                   pr.totalAmount AS totalAmount,
                   pr.note AS note,
                   pr.status AS status,
                   pr.createAt AS createAt
            FROM purchase_receipts pr
            JOIN suppliers s ON s.ID = pr.supplierID
            LEFT JOIN users u ON u.id = pr.createdBy
            WHERE pr.ID = :receiptId
            """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("receiptId", receiptId)
                        .mapToBean(PurchaseReceipt.class)
                        .findOne()
                        .orElse(null)
        );
    }
    public List<PurchaseReceiptDetail> findPurchaseReceiptDetailsByReceiptId(int receiptId) {
        String sql = """
            SELECT prd.ID AS id,
                   prd.receiptID AS receiptId,
                   prd.productID AS productId,
                   p.name AS productName,
                   prd.quantity AS quantity,
                   prd.importPrice AS importPrice,
                   prd.lineTotal AS lineTotal
            FROM purchase_receipt_details prd
            JOIN products p ON p.ID = prd.productID
            WHERE prd.receiptID = :receiptId
            ORDER BY prd.ID ASC
            """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("receiptId", receiptId)
                        .mapToBean(PurchaseReceiptDetail.class)
                        .list()
        );
    }
}