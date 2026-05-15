package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.OverviewOrderRow;
import vn.edu.nlu.fit.mythuatshop.Model.OverviewTopProductRow;

import java.math.BigDecimal;
import java.util.List;

public class OverviewDao {
    private final Jdbi jdbi;

    public OverviewDao() {
        this.jdbi = JDBIConnector.getJdbi();
    }

    public int countUsers() {
        return jdbi.withHandle(h ->
                h.createQuery("SELECT COUNT(*) FROM users")
                        .mapTo(int.class)
                        .one()
        );
    }

    public int countProducts() {
        return jdbi.withHandle(h ->
                h.createQuery("SELECT COUNT(*) FROM products")
                        .mapTo(int.class)
                        .one()
        );
    }

    public BigDecimal totalRevenues() {
        return jdbi.withHandle(h ->
                h.createQuery("""
                    SELECT COALESCE(SUM(totalPrice - discount), 0)
                    FROM orders
                    WHERE orderStatusID = 3
                """)
                        .mapTo(BigDecimal.class)
                        .one()
        );
    }

    public int totalOrders() {
        return jdbi.withHandle(h ->
                h.createQuery("""
                    SELECT COUNT(*)
                    FROM orders
                    WHERE orderStatusID = 3
                """)
                        .mapTo(int.class)
                        .one()
        );
    }

    public List<OverviewOrderRow> latestOrders(int limit) {
        String sql = """
            SELECT
                o.ID            AS id,
                o.userID        AS userId,
                o.fullName      AS fullName,
                o.email         AS email,
                o.phoneNumber   AS phoneNumber,
                o.address       AS address,
                o.totalPrice    AS totalPrice,
                o.paymentID     AS paymentId,
                o.orderStatusID AS orderStatusId,
                o.voucherID     AS voucherId,
                o.discount      AS discount,
                o.createAt      AS createAt,
                o.note          AS note,

                os.statusName   AS statusName,
                COALESCE(GROUP_CONCAT(p.name ORDER BY p.name SEPARATOR ', '), '') AS productNames
            FROM orders o
            LEFT JOIN order_statuses os ON os.ID = o.orderStatusID
            LEFT JOIN order_details od  ON od.orderID = o.ID
            LEFT JOIN products p        ON p.ID = od.productID
            GROUP BY
                o.ID, o.userID, o.fullName, o.email, o.phoneNumber, o.address,
                o.totalPrice, o.paymentID, o.orderStatusID, o.voucherID, o.discount,
                o.createAt, o.note, os.statusName
            ORDER BY o.createAt DESC
            LIMIT :limit
        """;

        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("limit", limit)
                        .mapToBean(OverviewOrderRow.class)
                        .list()
        );
    }

    public List<OverviewTopProductRow> topProductsThisMonth(int limit) {
        String sql = """
            SELECT
                p.ID            AS id,
                p.name          AS name,
                c.categoryName  AS categoryName,
                p.price         AS price,
                p.createAt      AS createAt,
                COALESCE(SUM(od.quantity), 0) AS soldQty
            FROM order_details od
            JOIN orders o      ON o.ID = od.orderID
            JOIN products p    ON p.ID = od.productID
            LEFT JOIN categories c ON c.ID = p.categoryID
            WHERE o.orderStatusID = 3
              AND YEAR(o.createAt) = YEAR(CURDATE())
              AND MONTH(o.createAt) = MONTH(CURDATE())
            GROUP BY p.ID, p.name, c.categoryName, p.price, p.createAt
            ORDER BY soldQty DESC
            LIMIT :limit
        """;
        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("limit", limit)
                        .mapToBean(OverviewTopProductRow.class)
                        .list()
        );
    }
}
