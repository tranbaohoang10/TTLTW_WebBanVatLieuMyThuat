package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.nlu.fit.mythuatshop.Service.OrderService;
import vn.edu.nlu.fit.mythuatshop.Util.PermissionUtil;

import java.io.IOException;

@WebServlet(name = "AdminOrderEditController", urlPatterns = "/admin/orders/edit")
public class AdminOrderEditController extends HttpServlet {
    private OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        if (!PermissionUtil.hasPermission(req, "ORDER_UPDATE_INFO")) {
            PermissionUtil.show404(req, resp);
            return;
        }

        int orderId = Integer.parseInt(req.getParameter("orderId"));
        String fullName = req.getParameter("fullName");
        String phoneNumber = req.getParameter("phoneNumber");
        String address = req.getParameter("address");

        boolean ok = orderService.adminUpdateOrderInfo(orderId, fullName, phoneNumber, address);

        HttpSession session = req.getSession();
        session.setAttribute("toast",
                ok ? "Cập nhật đơn hàng thành công!" : "Chỉ được sửa khi đơn ở trạng thái 'Đang xử lý'!");

        resp.sendRedirect(req.getContextPath() + "/admin/orders");
    }
}