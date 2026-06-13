package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.ProductRecommendationService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.IOException;

@WebServlet(name = "RecommendationImportController", value = "/admin/recommendations/import")
@MultipartConfig
public class RecommendationImportController extends HttpServlet {
    private ProductRecommendationService recommendationService;

    @Override
    public void init() throws ServletException {
        recommendationService = new ProductRecommendationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Part filePart = request.getPart("recommendationFile");

        if (filePart == null || filePart.getSize() == 0) {
            response.sendRedirect(request.getContextPath() + "/admin/recommendations?import=empty");
            return;
        }

        String folderPath = getServletContext().getRealPath("/WEB-INF/ml/recommendation");

        Path folder = Paths.get(folderPath);
        Files.createDirectories(folder);

        Path filePath = folder.resolve("recommendation_results.csv");

        Files.copy(filePart.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        int count = recommendationService.importFromCsv(filePath.toString());

        response.sendRedirect(request.getContextPath() + "/admin/recommendations?import=success&count=" + count);
    }
}