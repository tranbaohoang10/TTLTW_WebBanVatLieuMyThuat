package vn.edu.nlu.fit.mythuatshop.Controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.mythuatshop.Model.Log;
import vn.edu.nlu.fit.mythuatshop.Service.LogService;
import vn.edu.nlu.fit.mythuatshop.Util.FormatDataLog;

import java.io.IOException;


@WebServlet(name = "AdminLogDetailController", value = "/admin/log-detail")
public class AdminLogDetailController extends HttpServlet {
    private final LogService logService = new LogService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idRaw = request.getParameter("id");

        if (idRaw == null || idRaw.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/admin/logs");
            return;
        }

        int id;

        try {
            id = Integer.parseInt(idRaw);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/logs");
            return;
        }

        Log log = logService.getById(id);

        if (log == null) {
            response.sendRedirect(request.getContextPath() + "/admin/logs");
            return;
        }

        request.setAttribute("log", log);
        request.setAttribute("beforeData", FormatDataLog.format(log.getBeforeData()));
        request.setAttribute("afterData", FormatDataLog.format(log.getAfterData()));
        request.getRequestDispatcher("/admin/LogDetail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}