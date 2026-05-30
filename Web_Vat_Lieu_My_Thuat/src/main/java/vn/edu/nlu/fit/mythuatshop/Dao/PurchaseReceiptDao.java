package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;
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
}