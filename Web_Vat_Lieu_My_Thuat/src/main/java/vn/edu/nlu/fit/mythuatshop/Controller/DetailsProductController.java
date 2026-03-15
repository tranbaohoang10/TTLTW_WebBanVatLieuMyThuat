package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.mythuatshop.Model.Product;
import vn.edu.nlu.fit.mythuatshop.Model.ProductCard;
import vn.edu.nlu.fit.mythuatshop.Model.Specification;
import vn.edu.nlu.fit.mythuatshop.Model.Subimages;
import vn.edu.nlu.fit.mythuatshop.Service.DetailsProductService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "DetailsProductController", value = "/DetailsProductController")
public class DetailsProductController extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idUrl = request.getParameter("id");
        // kiem tra hop le
        if(idUrl == null || idUrl.isEmpty()){
            response.sendRedirect(request.getContextPath()+"/home");
            return;
        }
        int id = Integer.parseInt(idUrl);
        int productId = Integer.parseInt(idUrl);
        DetailsProductService detailsProductService = new DetailsProductService();
        Product product = detailsProductService.getProductActive(productId);
        if(product==null){
           response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
           return;
       }
        List<Specification> specifications = detailsProductService.getSpecifications(productId);
        List<Subimages> subimages = detailsProductService.getSubImages(productId);
        List<ProductCard> relatedProducts = detailsProductService.getRelatedProductCards(product);
        request.setAttribute("product", product);
        request.setAttribute("outOfStock", product.getQuantityStock() <= 0);
        request.setAttribute("specificationsList", specifications);
        request.setAttribute("subimagesList", subimages);
        request.setAttribute("relatedProducts", relatedProducts);
        request.getRequestDispatcher("ProductDetails.jsp").forward(request, response);

    }
}