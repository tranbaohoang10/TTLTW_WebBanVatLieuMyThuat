package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.ProductInteractionService;

import java.io.IOException;

@WebServlet(name = "TrainDataExportController", value = "/admin/recommendations/export")
public class TrainDataExportController extends HttpServlet {
    private ProductInteractionService interactionService = new ProductInteractionService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("currentUser");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String csvData = interactionService.exportTrainingDataCsv();

        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"recommendation_train_data.csv\"");

        response.getWriter().write(csvData);
    }
}