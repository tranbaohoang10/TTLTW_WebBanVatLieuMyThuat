package vn.edu.nlu.fit.mythuatshop.Service;

import vn.edu.nlu.fit.mythuatshop.Dao.StatisticDAO;
import vn.edu.nlu.fit.mythuatshop.Model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

public class StatisticService {
    private final StatisticDAO dao = new StatisticDAO();

    public BigDecimal getTotalRevenueThisYear() {
        return dao.getTotalRevenueOfCurrentYear();
    }

    public List<RevenueMonth> getRevenueByMonthThisYear() {
        return dao.getRevenueByMonthOfCurrentYear();
    }


    public List<NoSaleRow> getNoSaleProducts(int months) {
        LocalDateTime from = StatisticDAO.getStartOfMonthRange(months);
        LocalDateTime to = StatisticDAO.getEndOfMonthRange();
        return dao.getProductsWithNoSales(from, to);
    }


    public List<BestSellerRow> getBestSellersAllTime() {
        return dao.getBestSellingProductsOfAllTime();
    }


    public List<BestSellerChartPoint> getBestSellerTop5ChartAllTime() {
        return dao.getTop5BestSellingProductsForChart();
    }
    public BigDecimal getTotalImportCostThisYear() {
        return dao.getTotalImportCostOfCurrentYear();
    }

    public List<ImportCostMonth> getImportCostByMonthThisYear() {
        return dao.getImportCostByMonthOfCurrentYear();
    }
    public ProfitSummary getProfitSummaryThisYear() {
        ProfitSummary summary = dao.getProfitSummaryOfCurrentYear();

        BigDecimal revenue = summary.getRevenue() != null
                ? summary.getRevenue()
                : BigDecimal.ZERO;

        BigDecimal costOfGoodsSold = summary.getCostOfGoodsSold() != null
                ? summary.getCostOfGoodsSold()
                : BigDecimal.ZERO;

        BigDecimal profit = revenue.subtract(costOfGoodsSold);

        BigDecimal profitMargin = BigDecimal.ZERO;
        if (revenue.compareTo(BigDecimal.ZERO) > 0) {
            profitMargin = profit
                    .divide(revenue, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }

        summary.setRevenue(revenue);
        summary.setCostOfGoodsSold(costOfGoodsSold);
        summary.setProfit(profit);
        summary.setProfitMargin(profitMargin);

        return summary;
    }
}
