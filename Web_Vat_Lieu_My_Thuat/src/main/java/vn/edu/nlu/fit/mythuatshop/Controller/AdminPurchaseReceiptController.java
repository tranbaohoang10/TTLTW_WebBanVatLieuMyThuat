package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.mythuatshop.Service.PurchaseReceiptService;

import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet(name = "AdminPurchaseReceiptController", value = "/admin/purchase-receipts")
public class AdminPurchaseReceiptController extends HttpServlet {
    private PurchaseReceiptService purchaseReceiptService;

    @Override
    public void init() {
        purchaseReceiptService = new PurchaseReceiptService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        request.setAttribute(
                "activeSuppliers",
                purchaseReceiptService.getActiveSuppliersForCreateForm()
        );
        request.setAttribute(
                "activeProducts",
                purchaseReceiptService.getActiveProductsForCreateForm()
        );

        request.setAttribute(
                "currentImporterName",
                getCurrentImporterName(request)
        );

        request.getRequestDispatcher("/admin/PurchaseReceipt.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        response.sendRedirect(request.getContextPath() + "/admin/purchase-receipts");
    }

    private String getCurrentImporterName(HttpServletRequest request) {
        Object currentUser = request.getSession().getAttribute("currentUser");

        if (currentUser == null) {
            return "Admin";
        }

        String[] getterNames = {
                "getFullName",
                "getFull_name",
                "getName",
                "getUsername",
                "getEmail"
        };

        for (String getterName : getterNames) {
            try {
                Method method = currentUser.getClass().getMethod(getterName);
                Object value = method.invoke(currentUser);

                if (value != null && !value.toString().isBlank()) {
                    return value.toString();
                }
            } catch (Exception ignored) {
            }
        }

        return "Admin";
    }
}