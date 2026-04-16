package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.Voucher;

import java.util.List;

public class VoucherDao {
    private final Jdbi jdbi;

    public VoucherDao() {
        this.jdbi = JDBIConnector.getJdbi();
    }

    public Voucher findByCode(String code) {
        String sql = "SELECT ID, code, name, description,  voucherType, voucherCash, voucherPercent, maxDiscount, minOrderValue, " +
                "startDate, endDate, quantity, quantityUsed, isActive " +
                "FROM Vouchers " +
                "WHERE code = :code LIMIT 1";

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("code", code)
                        .mapToBean(Voucher.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public boolean increaseUsed(int voucherId) {
        String sql = "UPDATE Vouchers " +
                "SET quantityUsed = quantityUsed + 1 " +
                "WHERE ID = :id " +
                "  AND isActive = 1 " +
                "  AND quantityUsed < quantity " +
                "  AND NOW() BETWEEN startDate AND endDate";

        int updated = jdbi.withHandle(h ->
                h.createUpdate(sql).bind("id", voucherId).execute()
        );
        return updated > 0;
    }
    public boolean decreaseUsed(int voucherId) {
        String sql = """
        UPDATE Vouchers
        SET quantityUsed = CASE
            WHEN quantityUsed > 0 THEN quantityUsed - 1
            ELSE 0
        END
        WHERE ID = :id
        """;

        int updated = jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind("id", voucherId)
                        .execute()
        );

        return updated > 0;
    }
    public List<Voucher> findAll() {
        String sql = "SELECT ID, code, name,  voucherType, voucherCash, voucherPercent, maxDiscount, voucherCash, minOrderValue, " +
                "startDate, endDate, quantity, quantityUsed, isActive " +
                "FROM Vouchers " +
                "ORDER BY ID DESC";

        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .mapToBean(Voucher.class)
                        .list()
        );
    }

    public Voucher findById(int id) {
        String sql = """
                SELECT ID, code, name, description,  voucherType, voucherCash, voucherPercent, maxDiscount, minOrderValue,
                       startDate, endDate, quantity, quantityUsed, isActive
                FROM Vouchers
                WHERE ID = :id
                """;

        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("id", id)
                        .mapToBean(Voucher.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public int insert(Voucher v) {
        String sql = """
                INSERT INTO Vouchers
                    (code, name, description,  voucherType, voucherCash, voucherPercent, maxDiscount, minOrderValue, startDate, endDate, quantity, quantityUsed, isActive)
                    VALUES (:code, :name, :description, :voucherType, :voucherCash, :voucherPercent,:maxDiscount, :minOrderValue, :startDate, :endDate, :quantity, :quantityUsed, :isActive)
                """;

        return jdbi.withHandle(h ->
                h.createUpdate(sql)
                        .bindBean(v)
                        .execute()
        );
    }

    public int update(Voucher v) {
        String sql = """
                UPDATE Vouchers SET
                                code = :code,
                                name = :name,
                                description = :description,
                                voucherType = :voucherType,                                               
                                voucherCash = :voucherCash,                                           
                                voucherPercent = :voucherPercent,
                                maxDiscount = :maxDiscount,
                                minOrderValue = :minOrderValue,
                                startDate = :startDate,
                                endDate = :endDate,
                                quantity = :quantity,
                                quantityUsed = :quantityUsed,
                                isActive = :isActive
                              WHERE ID = :id
                """;

        return jdbi.withHandle(h ->
                h.createUpdate(sql)
                        .bindBean(v)
                        .execute()
        );
    }

    public int delete(int id) {
        String sql = "DELETE FROM Vouchers WHERE ID = :id";
        return jdbi.withHandle(h ->
                h.createUpdate(sql)
                        .bind("id", id)
                        .execute()
        );
    }
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM Vouchers";
        return jdbi.withHandle(h -> h.createQuery(sql).mapTo(Integer.class).one());
    }

    public List<Voucher> findPage(int offset, int limit) {
        String sql = """
                SELECT ID, code, name, description,  voucherType, voucherCash, voucherPercent, maxDiscount, minOrderValue,
                       startDate, endDate, quantity, quantityUsed, isActive
                FROM Vouchers
                ORDER BY ID DESC
                LIMIT :limit OFFSET :offset
                """;

        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("limit", limit)
                        .bind("offset", offset)
                        .mapToBean(Voucher.class)
                        .list()
        );
    }

    public int countByKeyword(String keyword) {
        String sql = """
                SELECT COUNT(*)
                FROM Vouchers
                WHERE code LIKE :kw OR name LIKE :kw
                """;
        String pattern = "%" + keyword + "%";
        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("kw", pattern)
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public List<Voucher> searchPage(String keyword, int offset, int limit) {
        String sql = """
                SELECT ID, code, name, description,  voucherType, voucherCash, voucherPercent, maxDiscount, minOrderValue,
                       startDate, endDate, quantity, quantityUsed, isActive
                FROM Vouchers
                WHERE code LIKE :kw OR name LIKE :kw
                ORDER BY ID DESC
                LIMIT :limit OFFSET :offset
                """;

        String pattern = "%" + keyword + "%";
        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("kw", pattern)
                        .bind("limit", limit)
                        .bind("offset", offset)
                        .mapToBean(Voucher.class)
                        .list()
        );
    }
}


