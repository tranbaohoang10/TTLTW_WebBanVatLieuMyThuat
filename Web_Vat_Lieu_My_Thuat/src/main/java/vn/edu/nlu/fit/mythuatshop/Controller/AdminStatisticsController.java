package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.nlu.fit.mythuatshop.Service.StatisticService;

import java.io.IOException;

@WebServlet(name = "AdminStatisticsController", value = "/admin/statistics")
public class AdminStatisticsController extends HttpServlet {
    private StatisticService service;

    @Override
    public void init() {
        service = new StatisticService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int noSaleMonths = clampMonths(parseInt(req.getParameter("noSaleMonths"), 1));

        var totalYear = service.getTotalRevenueThisYear();
        var revYear = service.getRevenueByMonthThisYear();


        var bestTable = service.getBestSellersAllTime();
        var bestChart = service.getBestSellerTop5ChartAllTime();


        var noSaleTable = service.getNoSaleProducts(noSaleMonths);

        req.setAttribute("noSaleMonths", noSaleMonths);
        req.setAttribute("totalYear", totalYear);
        req.setAttribute("revYear", revYear);
        req.setAttribute("bestTable", bestTable);
        req.setAttribute("bestChart", bestChart);
        req.setAttribute("noSaleTable", noSaleTable);

        req.getRequestDispatcher("/admin/Statistic.jsp").forward(req, resp);
    }

    private int parseInt(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) { return def; }
    }

    private int clampMonths(int m) {
        return Math.max(1, Math.min(12, m));
    }
}
