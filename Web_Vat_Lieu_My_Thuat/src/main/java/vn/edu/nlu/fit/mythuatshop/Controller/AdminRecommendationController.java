package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.mythuatshop.Service.ProductInteractionService;
import vn.edu.nlu.fit.mythuatshop.Service.ProductRecommendationService;

import java.io.IOException;

@WebServlet(name = "AdminRecommendationController", value = "/admin/recommendations")
public class AdminRecommendationController extends HttpServlet {
    private ProductInteractionService interactionService = new ProductInteractionService();
    private ProductRecommendationService recommendationService= new ProductRecommendationService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int totalInteractions = interactionService.countInteractions();

        int totalUsers = interactionService.countUsersWithInteractions();

        int totalRecommendations = recommendationService.countRecommendations();

        String lastUpdatedTime = recommendationService.getLastUpdatedTime();

        request.setAttribute("totalInteractions", totalInteractions);

        request.setAttribute("totalUsers", totalUsers);

        request.setAttribute("totalRecommendations", totalRecommendations);

        request.setAttribute("lastUpdatedTime", lastUpdatedTime);
        request.getRequestDispatcher("/admin/Recommendation.jsp")
                .forward(request, response);
    }
}