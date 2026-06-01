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
}