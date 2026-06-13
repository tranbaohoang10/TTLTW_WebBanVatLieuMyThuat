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
        String fileName = filePart.getSubmittedFileName();
        if (fileName == null || !fileName.toLowerCase().endsWith(".csv")) {
            response.sendRedirect(request.getContextPath() + "/admin/recommendations?status=format");
            return;
        }
        String folderPath = getServletContext().getRealPath("/WEB-INF/ml/recommendation");

        Path folder = Paths.get(folderPath);
        Files.createDirectories(folder);

        Path tempFile = folder.resolve("recommendation_results.csv");
        Path resultFile = folder.resolve( "recommendation_results.csv" );
        Files.copy(filePart.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
        try {
            int count = recommendationService.importFromCsv(tempFile.toString());
            Files.move( tempFile, resultFile, StandardCopyOption.REPLACE_EXISTING );
            response.sendRedirect( request.getContextPath() + "/admin/recommendations" + "?status=success&count=" + count );
        } catch (IllegalArgumentException e) {
            Files.deleteIfExists(tempFile);
            response.sendRedirect( request.getContextPath() + "/admin/recommendations?status=invalid" );
        } catch (Exception e) {
            e.printStackTrace(); Files.deleteIfExists(tempFile);
            response.sendRedirect( request.getContextPath() + "/admin/recommendations?status=error" );
        }
    }

}