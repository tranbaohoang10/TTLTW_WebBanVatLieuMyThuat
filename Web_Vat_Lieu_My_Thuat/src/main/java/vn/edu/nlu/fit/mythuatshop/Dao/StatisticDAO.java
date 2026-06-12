package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.*;

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

    public static LocalDateTime getStartOfMonthRange(int numberOfMonths) {
        LocalDate firstDayOfCurrentMonth = LocalDate.now().withDayOfMonth(1);
        return firstDayOfCurrentMonth.minusMonths(numberOfMonths - 1L).atStartOfDay();
    }

    public static LocalDateTime getEndOfMonthRange() {
        LocalDate firstDayOfNextMonth = LocalDate.now().withDayOfMonth(1).plusMonths(1);
        return firstDayOfNextMonth.atStartOfDay();
    }

    public BigDecimal getTotalRevenueOfCurrentYear() {
        String sql = """
            SELECT COALESCE(SUM(totalPrice), 0)
            FROM orders
            WHERE orderStatusID = 3
              AND YEAR(createAt) = YEAR(CURDATE())
        """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapTo(BigDecimal.class)
                        .one()
        );
    }

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
                FROM orders
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
    public BigDecimal getTotalImportCostOfCurrentYear() {
        String sql = """
        SELECT COALESCE(SUM(totalAmount), 0)
        FROM purchase_receipts
        WHERE status = 'COMPLETED'
          AND YEAR(importDate) = YEAR(CURDATE())
    """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapTo(BigDecimal.class)
                        .one()
        );
    }
    public List<ImportCostMonth> getImportCostByMonthOfCurrentYear() {
        String sql = """
        SELECT
            monthNumbers.monthValue AS month,
            COALESCE(importCostTable.importCost, 0) AS importCost
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
                MONTH(importDate) AS monthValue,
                SUM(totalAmount) AS importCost
            FROM purchase_receipts
            WHERE status = 'COMPLETED'
              AND YEAR(importDate) = YEAR(CURDATE())
            GROUP BY MONTH(importDate)
        ) AS importCostTable
        ON monthNumbers.monthValue = importCostTable.monthValue
        ORDER BY monthNumbers.monthValue
    """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .map((resultSet, context) -> new ImportCostMonth(
                                resultSet.getInt("month"),
                                resultSet.getBigDecimal("importCost")
                        ))
                        .list()
        );
    }
    public ProfitSummary getProfitSummaryOfCurrentYear() {
        String sql = """
        SELECT
            COALESCE((
                SELECT SUM(o.totalPrice)
                FROM orders o
                WHERE o.orderStatusID = 3
                  AND YEAR(o.createAt) = YEAR(CURDATE())
            ), 0) AS revenue,

            COALESCE((
                SELECT SUM(od.quantity * COALESCE(avg_import.avgImportPrice, 0))
                FROM orders o
                JOIN order_details od
                    ON od.orderID = o.ID
                LEFT JOIN (
                    SELECT
                        prd.productID,
                        SUM(prd.quantity * prd.importPrice) / SUM(prd.quantity) AS avgImportPrice
                    FROM purchase_receipt_details prd
                    JOIN purchase_receipts pr
                        ON pr.ID =  prd.receiptID
                    WHERE pr.status = 'COMPLETED'
                    GROUP BY prd.productID
                ) avg_import
                    ON avg_import.productID = od.productID
                WHERE o.orderStatusID = 3
                  AND YEAR(o.createAt) = YEAR(CURDATE())
            ), 0) AS costOfGoodsSold
    """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .map((rs, ctx) -> new ProfitSummary(
                                rs.getBigDecimal("revenue"),
                                rs.getBigDecimal("costOfGoodsSold")
                        ))
                        .one()
        );
    }
    public List<NoSaleRow> getProductsWithNoSales(LocalDateTime startTime, LocalDateTime endTime) {
        String sql = """
            SELECT
                product.ID AS productId,
                product.name AS productName,
                category.categoryName AS categoryName,
                product.price AS price,
                product.createAt AS createAt,
                0 AS soldQuantity
            FROM products product
            JOIN categories category
                ON category.ID = product.categoryID
            WHERE NOT EXISTS (
                SELECT 1
                FROM order_details orderDetail
                JOIN orders orderTable
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
                    FROM order_details orderDetail
                    JOIN orders orderTable
                        ON orderTable.ID = orderDetail.orderID
                    WHERE orderDetail.productID = product.ID
                      AND orderTable.orderStatusID = 3
                ), 0) AS soldQty
            FROM products product
            JOIN categories category
                ON category.ID = product.categoryID
            ORDER BY soldQty DESC
        """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(BestSellerRow.class)
                        .list()
        );
    }

    public List<BestSellerChartPoint> getTop5BestSellingProductsForChart() {
        String sql = """
            SELECT
                product.ID AS productId,
                product.name AS productName,
                COALESCE((
                    SELECT SUM(orderDetail.quantity)
                    FROM order_details orderDetail
                    JOIN orders orderTable
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
    public List<ProductProfitRow> getProductProfitRowsOfCurrentYear() {
        String sql = """
        SELECT
            product.ID AS productId,
            product.name AS productName,

            COALESCE(importTable.importedQuantity, 0) AS importedQuantity,
            COALESCE(importTable.totalImportCost, 0) AS totalImportCost,
            COALESCE(importTable.averageImportPrice, 0) AS averageImportPrice,

            COALESCE(saleTable.soldQuantity, 0) AS soldQuantity,
            COALESCE(saleTable.revenue, 0) AS revenue,

            COALESCE(saleTable.soldQuantity, 0) 
                * COALESCE(importTable.averageImportPrice, 0) AS costOfGoodsSold,

            COALESCE(saleTable.revenue, 0)
                - (
                    COALESCE(saleTable.soldQuantity, 0) 
                    * COALESCE(importTable.averageImportPrice, 0)
                  ) AS profit,

            CASE
                WHEN COALESCE(saleTable.revenue, 0) > 0 THEN
                    (
                        COALESCE(saleTable.revenue, 0)
                        - (
                            COALESCE(saleTable.soldQuantity, 0) 
                            * COALESCE(importTable.averageImportPrice, 0)
                          )
                    ) / COALESCE(saleTable.revenue, 0) * 100
                ELSE 0
            END AS profitMargin

        FROM products product

        LEFT JOIN (
            SELECT
                prd.productID,
                SUM(prd.quantity) AS importedQuantity,
                SUM(prd.quantity * prd.importPrice) AS totalImportCost,
                SUM(prd.quantity * prd.importPrice) / SUM(prd.quantity) AS averageImportPrice
            FROM purchase_receipt_details prd
            JOIN purchase_receipts pr
                ON pr.ID = prd.receiptID
            WHERE pr.status = 'COMPLETED'
            GROUP BY prd.productID
        ) importTable
            ON importTable.productID = product.ID

        LEFT JOIN (
            SELECT
                od.productID,
                SUM(od.quantity) AS soldQuantity,
                SUM(od.quantity * od.price) AS revenue
            FROM order_details od
            JOIN orders o
                ON o.ID = od.orderID
            WHERE o.orderStatusID = 3
              AND YEAR(o.createAt) = YEAR(CURDATE())
            GROUP BY od.productID
        ) saleTable
            ON saleTable.productID = product.ID

        WHERE COALESCE(importTable.importedQuantity, 0) > 0
           OR COALESCE(saleTable.soldQuantity, 0) > 0

        ORDER BY profit DESC
    """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(ProductProfitRow.class)
                        .list()
        );
    }
}