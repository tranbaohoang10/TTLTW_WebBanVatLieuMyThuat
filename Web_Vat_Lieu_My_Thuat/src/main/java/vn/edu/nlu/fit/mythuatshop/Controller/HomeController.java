package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.nlu.fit.mythuatshop.Model.Category;
import vn.edu.nlu.fit.mythuatshop.Model.ProductCard;
import vn.edu.nlu.fit.mythuatshop.Model.SliderShow;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.CategoryService;
import vn.edu.nlu.fit.mythuatshop.Service.ProductCardService;
import vn.edu.nlu.fit.mythuatshop.Service.ProductRecommendationService;
import vn.edu.nlu.fit.mythuatshop.Service.SliderShowService;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "HomeController", value = "/home")
public class HomeController extends HttpServlet {
    private ProductRecommendationService recommendationService = new ProductRecommendationService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CategoryService categoryService = new CategoryService();
        ProductCardService cardService = new ProductCardService();
        SliderShowService sliderShowService = new SliderShowService();

        List<Category> categories = categoryService.getAllActiveCategories();
        List<SliderShow> sliderShows = sliderShowService.getSliderShow();

        // dung LinkedHashMap de luu dung vi tri
        Map<Category, List<ProductCard>> productsByCategory = new LinkedHashMap<>();
        for (Category c : categories) {
            List<ProductCard> top = cardService.topByCategory(c.getId(), 10);
            // chi hien thi danh muc khong rong
            if (top != null && !top.isEmpty()) {
                productsByCategory.put(c, top);
            }
        }
        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("currentUser");

        List<ProductCard> recommendedProducts;

        if (currentUser != null) {
            recommendedProducts = recommendationService.getRecommendProducts(currentUser.getId());
        } else {
            recommendedProducts = recommendationService.getDefaultRecommendProducts();
        }

        request.setAttribute("recommendedProducts", recommendedProducts);

        request.setAttribute("sliders", sliderShows);
        request.setAttribute("categories", categories); // vẫn dùng cho CategoryMenu.jsp
        request.setAttribute("productsByCategory", productsByCategory);

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    
    }
}
