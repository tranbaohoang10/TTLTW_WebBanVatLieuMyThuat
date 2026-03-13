package vn.edu.nlu.fit.mythuatshop.Service;

import vn.edu.nlu.fit.mythuatshop.Dao.StatisticDAO;
import vn.edu.nlu.fit.mythuatshop.Model.BestSellerChartPoint;
import vn.edu.nlu.fit.mythuatshop.Model.BestSellerRow;
import vn.edu.nlu.fit.mythuatshop.Model.NoSaleRow;
import vn.edu.nlu.fit.mythuatshop.Model.RevenueMonth;

import java.math.BigDecimal;
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
}
