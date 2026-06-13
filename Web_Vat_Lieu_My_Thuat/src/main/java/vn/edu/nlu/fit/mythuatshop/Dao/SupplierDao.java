package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.Supplier;

import java.util.List;

public class SupplierDao {
    private final Jdbi jdbi;

    public SupplierDao() {
        this.jdbi = JDBIConnector.getJdbi();
    }

    public List<Supplier> findAll() {
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
                ORDER BY ID DESC
                """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(Supplier.class)
                        .list()
        );
    }

    public List<Supplier> findActiveSuppliers() {
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

    public Supplier findById(int id) {
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
                WHERE ID = :id
                """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("id", id)
                        .mapToBean(Supplier.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public boolean existsBySupplierCode(String supplierCode, Integer excludeId) {
        String sql = """
                SELECT COUNT(*)
                FROM suppliers
                WHERE supplierCode = :supplierCode
                  AND (:excludeId IS NULL OR ID <> :excludeId)
                """;

        Integer count = jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("supplierCode", supplierCode)
                        .bind("excludeId", excludeId)
                        .mapTo(Integer.class)
                        .one()
        );

        return count != null && count > 0;
    }

    public int insert(Supplier supplier) {
        String sql = """
                INSERT INTO suppliers
                (supplierCode, name, phone, email, address, note, isActive)
                VALUES
                (:supplierCode, :name, :phone, :email, :address, :note, :isActive)
                """;

        return jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind("supplierCode", supplier.getSupplierCode())
                        .bind("name", supplier.getName())
                        .bind("phone", supplier.getPhone())
                        .bind("email", supplier.getEmail())
                        .bind("address", supplier.getAddress())
                        .bind("note", supplier.getNote())
                        .bind("isActive", supplier.getIsActive())
                        .execute()
        );
    }

    public int update(Supplier supplier) {
        String sql = """
                UPDATE suppliers
                SET supplierCode = :supplierCode,
                    name = :name,
                    phone = :phone,
                    email = :email,
                    address = :address,
                    note = :note
                WHERE ID = :id
                """;

        return jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind("id", supplier.getId())
                        .bind("supplierCode", supplier.getSupplierCode())
                        .bind("name", supplier.getName())
                        .bind("phone", supplier.getPhone())
                        .bind("email", supplier.getEmail())
                        .bind("address", supplier.getAddress())
                        .bind("note", supplier.getNote())
                        .execute()
        );
    }

    public int updateStatus(int id, int isActive) {
        String sql = """
                UPDATE suppliers
                SET isActive = :isActive
                WHERE ID = :id
                """;

        return jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind("id", id)
                        .bind("isActive", isActive)
                        .execute()
        );
    }
}