package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.BestSellerChartPoint;
import vn.edu.nlu.fit.mythuatshop.Model.BestSellerRow;
import vn.edu.nlu.fit.mythuatshop.Model.NoSaleRow;
import vn.edu.nlu.fit.mythuatshop.Model.RevenueMonth;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class StatisticDAO {
    private final Jdbi jdbi;

    public StatisticDAO() {
        this.jdbi = JDBIConnector.getJdbi();
    }

    // Lấy thời điểm bắt đầu của khoảng N tháng, tính từ tháng hiện tại
    public static LocalDateTime getStartOfMonthRange(int numberOfMonths) {
        LocalDate firstDayOfCurrentMonth = LocalDate.now().withDayOfMonth(1);
        return firstDayOfCurrentMonth.minusMonths(numberOfMonths - 1L).atStartOfDay();
    }

    // Lấy thời điểm bắt đầu của tháng sau
    public static LocalDateTime getEndOfMonthRange() {
        LocalDate firstDayOfNextMonth = LocalDate.now().withDayOfMonth(1).plusMonths(1);
        return firstDayOfNextMonth.atStartOfDay();
    }

    // Tổng doanh thu của năm hiện tại
    public BigDecimal getTotalRevenueOfCurrentYear() {
        String sql = """
            SELECT COALESCE(SUM(totalPrice), 0)
            FROM Orders
            WHERE orderStatusID = 3
              AND YEAR(createAt) = YEAR(CURDATE())
        """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapTo(BigDecimal.class)
                        .one()
        );
    }

    // Doanh thu theo từng tháng của năm hiện tại
    public List<RevenueMonth> getRevenueByMonthOfCurrentYear() {
        String sql = """
            SELECT
                monthNumbers.monthValue AS month,
                COALESCE(revenueTable.revenue, 0) AS revenue
            FROM (
                SELECT 1 AS monthValue
                UNION ALL SELECT 2
                UNION ALL SELECT 3
                UNION ALL SELECT 4
                UNION ALL SELECT 5
                UNION ALL SELECT 6
                UNION ALL SELECT 7
                UNION ALL SELECT 8
                UNION ALL SELECT 9
                UNION ALL SELECT 10
                UNION ALL SELECT 11
                UNION ALL SELECT 12
            ) AS monthNumbers
            LEFT JOIN (
                SELECT
                    MONTH(createAt) AS monthValue,
                    SUM(totalPrice) AS revenue
                FROM Orders
                WHERE orderStatusID = 3
                  AND YEAR(createAt) = YEAR(CURDATE())
                GROUP BY MONTH(createAt)
            ) AS revenueTable
            ON monthNumbers.monthValue = revenueTable.monthValue
            ORDER BY monthNumbers.monthValue
        """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .map((resultSet, context) -> new RevenueMonth(
                                resultSet.getInt("month"),
                                resultSet.getBigDecimal("revenue")
                        ))
                        .list()
        );
    }

    // Danh sách sản phẩm không bán được trong khoảng thời gian truyền vào
    public List<NoSaleRow> getProductsWithNoSales(LocalDateTime startTime, LocalDateTime endTime) {
        String sql = """
            SELECT
                product.ID AS productId,
                product.name AS productName,
                category.categoryName AS categoryName,
                product.price AS price,
                product.createAt AS createAt,
                0 AS soldQuantity
            FROM Products product
            JOIN Categories category
                ON category.ID = product.categoryID
            WHERE NOT EXISTS (
                SELECT 1
                FROM Order_Details orderDetail
                JOIN Orders orderTable
                    ON orderTable.ID = orderDetail.orderID
                WHERE orderDetail.productID = product.ID
                  AND orderTable.orderStatusID = 3
                  AND orderTable.createAt >= :startTime
                  AND orderTable.createAt < :endTime
            )
            ORDER BY product.createAt DESC
        """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("startTime", Timestamp.valueOf(startTime))
                        .bind("endTime", Timestamp.valueOf(endTime))
                        .mapToBean(NoSaleRow.class)
                        .list()
        );
    }

    // Danh sách sản phẩm bán chạy nhất
    public List<BestSellerRow> getBestSellingProductsOfAllTime() {
        String sql = """
            SELECT
                product.ID AS productId,
                product.name AS productName,
                category.categoryName AS categoryName,
                product.price AS price,
                product.createAt AS createAt,
                COALESCE((
                    SELECT SUM(orderDetail.quantity)
                    FROM Order_Details orderDetail
                    JOIN Orders orderTable
                        ON orderTable.ID = orderDetail.orderID
                    WHERE orderDetail.productID = product.ID
                      AND orderTable.orderStatusID = 3
                ), 0) AS soldQty
            FROM Products product
            JOIN Categories category
                ON category.ID = product.categoryID
            ORDER BY soldQty DESC
        """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(BestSellerRow.class)
                        .list()
        );
    }

    // Top 5 sản phẩm bán chạy nhất để vẽ biểu đồ
    public List<BestSellerChartPoint> getTop5BestSellingProductsForChart() {
        String sql = """
            SELECT
                product.ID AS productId,
                product.name AS productName,
                COALESCE((
                    SELECT SUM(orderDetail.quantity)
                    FROM Order_Details orderDetail
                    JOIN Orders orderTable
                        ON orderTable.ID = orderDetail.orderID
                    WHERE orderDetail.productID = product.ID
                      AND orderTable.orderStatusID = 3
                ), 0) AS soldQty
            FROM Products product
            ORDER BY soldQty DESC
            LIMIT 5
        """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(BestSellerChartPoint.class)
                        .list()
        );
    }
}