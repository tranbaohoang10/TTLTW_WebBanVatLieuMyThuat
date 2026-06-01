package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.ProductRecommendationService;

import java.io.IOException;

@WebServlet(name = "RecommendationImportController", value = "/import-recommendation-results")
public class RecommendationImportController extends HttpServlet {
    private ProductRecommendationService recommendationService;

    @Override
    public void init() throws ServletException {
        recommendationService = new ProductRecommendationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("currentUser");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String filePath = getServletContext().getRealPath("/WEB-INF/ml/recommendation/recommendation_results.csv");
        int count = recommendationService.importFromCsv(filePath);

        response.setContentType("text/plain; charset=UTF-8");
        response.getWriter().write("Đã import " + count + " dòng gợi ý sản phẩm vào database.");
    }
}