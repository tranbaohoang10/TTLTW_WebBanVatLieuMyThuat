package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.mythuatshop.Dao.ProductDao;

import vn.edu.nlu.fit.mythuatshop.Model.Product;
import vn.edu.nlu.fit.mythuatshop.Model.Product_Review;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.Product_ReviewService;

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

    try{
        String url = request.getParameter("id");
        String orderIdRaw = request.getParameter("orderID");
        if (url == null || url.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing product id");
            return;
        }
        int productID = Integer.parseInt(url);
        int orderID = 0;
        if(orderIdRaw != null && !orderIdRaw.trim().isEmpty()){
            orderID = Integer.parseInt(orderIdRaw);
        }

        Product product = productDao.findByProductId(productID);
            if (product == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                return;
            }
            List<Product_Review> reviews = reviewService.getReviewsByProductId(productID);
            double avgRating = reviewService.averageRating(productID);

            avgRating = Math.round(avgRating * 10.0) / 10.0;
            int reviewCount = reviews.size();
            HttpSession session = request.getSession(false);
            Users currentUser = (session != null) ? (Users) session.getAttribute("currentUser") : null;

            boolean canReview = false;
            boolean hasReviewed = false;

            if (currentUser != null) {
                if (orderID > 0) {
                    canReview = reviewService.canReviewOrderProduct(currentUser.getId(), productID, orderID);
                    hasReviewed = reviewService.hasReviewOrderProduct(currentUser.getId(), productID, orderID);}
                else {
                    canReview = reviewService.canReviewProduct(currentUser.getId(), productID);
                    hasReviewed = reviewService.hasReviewProduct(currentUser.getId(), productID);
                }
            }

            request.setAttribute("canReview", canReview);
            request.setAttribute("hasReviewed", hasReviewed);
            request.setAttribute("orderID", orderID);

            request.setAttribute("product", product);
            request.setAttribute("reviews", reviews);
            request.setAttribute("avgRating", avgRating);
            request.setAttribute("reviewCount", reviewCount);
            request.getRequestDispatcher("ProductReviews.jsp").forward(request, response);
        }catch (NumberFormatException e){
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product id or order id");
    }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Users currentUser = (session != null) ? (Users) session.getAttribute("currentUser") : null;

        if (currentUser == null) {

            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {

            String productIdStr = request.getParameter("productID");
            String orderIdStr = request.getParameter("orderID");
            String ratingStr = request.getParameter("rating");
            String comment = request.getParameter("comment");

            if (productIdStr == null || ratingStr == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing productID or rating");
                return;
            }

            int productID = Integer.parseInt(productIdStr);
            int rating    = Integer.parseInt(ratingStr);
            int orderID=0;
            if(orderIdStr != null && !orderIdStr.trim().isEmpty()){
                orderID = Integer.parseInt(orderIdStr);
            }


            if (rating < 1 || rating > 5) {
                rating = 5;
            }


            Product_Review review = new Product_Review();
            review.setProductID(productID);
            review.setUserID(currentUser.getId());
            review.setOrderID(orderID);
            review.setRating(rating);
            review.setComment(comment != null ? comment.trim() : "");

            boolean canReview ;
            if(orderID > 0) {
                canReview = reviewService.canReviewOrderProduct(currentUser.getId(), productID, orderID);
            }
            else{
                canReview = reviewService.canReviewProduct(currentUser.getId(), productID);
            }
            if(!canReview){
                response.sendError(HttpServletResponse.SC_FORBIDDEN,"Chỉ được đánh giá khi đã mua sản phẩm này");
                return;
            }
            boolean hasReview;
            if(orderID > 0) {
                hasReview = reviewService.hasReviewOrderProduct(currentUser.getId(), productID, orderID);
            }
            else {
                hasReview = reviewService.hasReviewProduct(currentUser.getId(), productID);
            }

            if (hasReview) {
                if (orderID > 0) {
                    reviewService.updateReviewByOrderProduct(review);
                } else {
                    reviewService.updateReview(review);
                }
            }
            else { reviewService.addReview(review);
            }
            String redirectUrl = request.getContextPath() + "/Product_ReviewsController?id=" + productID;
            if(orderID>0){
                redirectUrl += "&orderID=" + orderID;
            }
            response.sendRedirect(redirectUrl);

        } catch (NumberFormatException e) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid productID, orderID or rating");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error when saving review");
        }
    }
}