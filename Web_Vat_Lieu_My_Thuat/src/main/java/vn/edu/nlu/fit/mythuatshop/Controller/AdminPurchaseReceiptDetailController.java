package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.mythuatshop.Model.PurchaseReceipt;
import vn.edu.nlu.fit.mythuatshop.Model.PurchaseReceiptDetail;
import vn.edu.nlu.fit.mythuatshop.Service.PurchaseReceiptService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminPurchaseReceiptDetailController", value = "/admin/purchase-receipts/detail")
public class AdminPurchaseReceiptDetailController extends HttpServlet {
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

        int receiptId = parseInt(request.getParameter("id"), 0);

        try {
            PurchaseReceipt receipt =
                    purchaseReceiptService.getPurchaseReceiptDetailForAdmin(receiptId);

            List<PurchaseReceiptDetail> details =
                    purchaseReceiptService.getPurchaseReceiptItemsForAdmin(receiptId);

            request.setAttribute("purchaseReceipt", receipt);
            request.setAttribute("purchaseReceiptDetails", details);

            request.getRequestDispatcher("/admin/PurchaseReceiptDetail.jsp")
                    .forward(request, response);

        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("purchaseReceiptError", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/purchase-receipts");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute(
                    "purchaseReceiptError",
                    "Có lỗi xảy ra khi xem chi tiết phiếu nhập."
            );
            response.sendRedirect(request.getContextPath() + "/admin/purchase-receipts");
        }
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}