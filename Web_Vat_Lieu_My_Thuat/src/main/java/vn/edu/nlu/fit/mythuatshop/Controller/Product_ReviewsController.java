package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.mythuatshop.Dao.CategoryDao;
import vn.edu.nlu.fit.mythuatshop.Dao.ProductDao;
import vn.edu.nlu.fit.mythuatshop.Dao.Product_ReviewsDao;
import vn.edu.nlu.fit.mythuatshop.Model.Category;
import vn.edu.nlu.fit.mythuatshop.Model.Product;
import vn.edu.nlu.fit.mythuatshop.Model.Product_Review;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.Product_ReviewService;
import vn.edu.nlu.fit.mythuatshop.Service.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Product_ReviewsController", value = "/Product_ReviewsController")
public class Product_ReviewsController extends HttpServlet {
    private Product_ReviewService reviewService;
    private ProductDao productDao;
    @Override
    public void init() throws ServletException {
        reviewService = new Product_ReviewService();
        productDao = new ProductDao();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String url = request.getParameter("id");
        if (url == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing product id");
            return;
        }
    int productID = Integer.parseInt(url);
    Product product = productDao.findByProductId(productID);
        if (product == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
            return;
        }
        List<Product_Review> reviews = reviewService.getReviewsByProductId(productID);
        double avgRating = reviewService.averageRating(productID);
        // làm tròn 1 chữ số thập phân
        avgRating = Math.round(avgRating * 10.0) / 10.0;
        int reviewCount = reviews.size();


        request.setAttribute("product", product);
        request.setAttribute("reviews", reviews);
        request.setAttribute("avgRating", avgRating);
        request.setAttribute("reviewCount", reviewCount);
        request.getRequestDispatcher("ProductReviews.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Users currentUser = (session != null) ? (Users) session.getAttribute("currentUser") : null;

        if (currentUser == null) {
            // Chưa đăng nhập -> bắt đăng nhập trước khi đánh giá
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // Lấy dữ liệu từ form
            String productIdStr = request.getParameter("productID");
            String ratingStr = request.getParameter("rating");
            String comment = request.getParameter("comment");

            if (productIdStr == null || ratingStr == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing productID or rating");
                return;
            }

            int productID = Integer.parseInt(productIdStr);
            int rating    = Integer.parseInt(ratingStr);


            if (rating < 1 || rating > 5) {
                rating = 5;
            }

            //Tạo đối tượng Product_Review
            Product_Review review = new Product_Review();
            review.setProductID(productID);
            review.setUserID(currentUser.getId()); // lấy id từ user đang đăng nhập
            review.setRating(rating);
            review.setComment(comment != null ? comment.trim() : "");

            boolean canReview = reviewService.canReviewProduct(currentUser.getId(),productID);
            if(!canReview){
                response.sendError(HttpServletResponse.SC_FORBIDDEN,"Chỉ được đánh giá khi đã mua sản phẩm này");
                return;
            }
            boolean hasReview = reviewService.hasReviewProduct(currentUser.getId(),productID);
            if(hasReview){
                response.sendError(HttpServletResponse.SC_FORBIDDEN,"Bạn đã đánh giá sản phẩm này rồi");
                return;
            }
            reviewService.addReview(review);


            response.sendRedirect(
                    request.getContextPath() + "/Product_ReviewsController?id=" + productID
            );

        } catch (NumberFormatException e) {
            // Sai định dạng số
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid productID or rating");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error when saving review");
        }
    }
}