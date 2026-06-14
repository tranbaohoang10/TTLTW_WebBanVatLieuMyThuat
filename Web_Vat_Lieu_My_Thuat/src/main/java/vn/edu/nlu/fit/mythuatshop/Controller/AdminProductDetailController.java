package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.mythuatshop.Model.Product;
import vn.edu.nlu.fit.mythuatshop.Service.ProductService;

import java.io.IOException;

@WebServlet(name = "AdminProductDetailController", value = "/admin/products/detail")
public class AdminProductDetailController extends HttpServlet {

    private ProductService productService;

    @Override
    public void init() {
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/products?status=invalid-id");
            return;
        }
        int productId;
        try {
            productId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect(
                    request.getContextPath() + "/admin/products?status=invalid-id");
            return;
        }

        if (productId <= 0) {
            response.sendRedirect(request.getContextPath() + "/admin/products?status=invalid-id");
            return;
        }
        Product product = productService.getProductDetail(productId);

        if (product == null) {
            response.sendRedirect(request.getContextPath() + "/admin/products?status=not-found");
            return;
        }
        request.setAttribute("product", product);
        request.getRequestDispatcher("/admin/ProductDetail.jsp")
                .forward(request, response);
    }
}