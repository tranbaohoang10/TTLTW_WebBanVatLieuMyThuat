package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.edu.nlu.fit.mythuatshop.Dao.ProductDao;
import vn.edu.nlu.fit.mythuatshop.Dao.SpecificationsDao;
import vn.edu.nlu.fit.mythuatshop.Dao.SubImagesDao;
import vn.edu.nlu.fit.mythuatshop.Model.Product;
import vn.edu.nlu.fit.mythuatshop.Model.Specification;
import vn.edu.nlu.fit.mythuatshop.Model.Subimages;
import vn.edu.nlu.fit.mythuatshop.Service.CategoryService;
import vn.edu.nlu.fit.mythuatshop.Service.ProductService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "AdminProductController", value = "/admin/products")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class AdminProductController extends HttpServlet {

    private ProductService productService;
    private CategoryService categoryService;
    private ProductDao productDao;
    private SubImagesDao subImagesDao;
    private SpecificationsDao specificationsDao;

    @Override
    public void init() {
        productService = new ProductService();
        categoryService = new CategoryService();
        productDao = new ProductDao();
        subImagesDao = new SubImagesDao();
        specificationsDao = new SpecificationsDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        List<Product> products = productService.getAllProducts();
        request.setAttribute("products", products);
        request.setAttribute("categories", categoryService.getAllcategories());

        Map<Integer, String> subImagesMap = new HashMap<>();
        Map<Integer, Specification> specificationMap = new HashMap<>();

        for (Product product : products) {
            List<Subimages> subimagesList = subImagesDao.findByProductId(product.getId());
            String subImages = subimagesList.stream()
                    .map(Subimages::getImage)
                    .collect(Collectors.joining(","));
            subImagesMap.put(product.getId(), subImages);

            List<Specification> specificationList = specificationsDao.findByProductId(product.getId());
            if (specificationList != null && !specificationList.isEmpty()) {
                specificationMap.put(product.getId(), specificationList.get(0));
            }
        }

        request.setAttribute("subImagesMap", subImagesMap);
        request.setAttribute("specMap", specificationMap);

        request.getRequestDispatcher("/admin/Product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "create":
                    createProduct(request);
                    break;
                case "update":
                    updateProduct(request);
                    break;
                case "toggleActive":
                    toggleProductActive(request);
                    break;
                default:
                    break;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/admin/products");
    }

    private void createProduct(HttpServletRequest request) throws Exception {
        int categoryId = parseInt(request.getParameter("categoryId"), 0);
        String name = request.getParameter("name");
        double price = parseDouble(request.getParameter("price"), 0);
        int discountDefault = parseInt(request.getParameter("discountDefault"), 0);
        int quantityStock = parseInt(request.getParameter("quantityStock"), 0);
        String brand = request.getParameter("brand");

        String size = request.getParameter("size");
        String standard = request.getParameter("standard");
        String madeIn = request.getParameter("madeIn");
        String warning = request.getParameter("warning");

        if (standard == null) {
            standard = "";
        }

        Part thumbnailMainPart = request.getPart("thumbnailMain");
        String thumbnailMainUrl = saveUploadAndReturnUrl(request, thumbnailMainPart, "uploads/products");

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDiscountDefault(discountDefault);
        product.setCategoryId(categoryId);
        product.setThumbnail(thumbnailMainUrl);
        product.setQuantityStock(quantityStock);
        product.setSoldQuantity(0);
        product.setStatus(quantityStock > 0 ? "Còn hàng" : "Hết hàng");
        product.setCreateAt(new Timestamp(System.currentTimeMillis()));
        product.setBrand(brand);
        product.setIsActive(1);

        int productId = productDao.insertReturnId(product);

        for (Part part : request.getParts()) {
            if ("thumbnailSubs".equals(part.getName()) && part.getSize() > 0) {
                String subImageUrl = saveUploadAndReturnUrl(request, part, "uploads/subimages");
                subImagesDao.insert(productId, subImageUrl);
            }
        }

        specificationsDao.upsert(productId, size, standard, madeIn, warning);
    }

    private void updateProduct(HttpServletRequest request) throws Exception {
        int id = parseInt(request.getParameter("id"), 0);
        if (id <= 0) {
            return;
        }

        int categoryId = parseInt(request.getParameter("categoryId"), 0);
        String name = request.getParameter("name");
        double price = parseDouble(request.getParameter("price"), 0);
        int discountDefault = parseInt(request.getParameter("discountDefault"), 0);
        int quantityStock = parseInt(request.getParameter("quantityStock"), 0);
        String brand = request.getParameter("brand");

        String size = request.getParameter("size");
        String standard = request.getParameter("standard");
        String madeIn = request.getParameter("madeIn");
        String warning = request.getParameter("warning");

        if (standard == null) {
            standard = "";
        }

        Product oldProduct = productService.getProductById(id);
        String oldThumbnail = oldProduct != null ? oldProduct.getThumbnail() : null;

        String existingThumbnail = trimToNull(request.getParameter("existingThumbnail"));
        Part thumbnailMainPart = request.getPart("thumbnailMain");

        String uploadedThumbnail = null;
        if (thumbnailMainPart != null && thumbnailMainPart.getSize() > 0) {
            uploadedThumbnail = saveUploadAndReturnUrl(request, thumbnailMainPart, "uploads/products");
        }

        String finalThumbnail;
        if (uploadedThumbnail != null) {
            finalThumbnail = uploadedThumbnail;
        } else if (existingThumbnail != null) {
            finalThumbnail = existingThumbnail;
        } else {
            finalThumbnail = oldThumbnail;
        }

        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setDiscountDefault(discountDefault);
        product.setCategoryId(categoryId);
        product.setQuantityStock(quantityStock);
        product.setBrand(brand);
        product.setThumbnail(finalThumbnail);

        productDao.update(product);

        String existingSubImagesCsv = trimToNull(request.getParameter("existingSubImages"));
        List<String> oldSubImages = new ArrayList<>();

        if (existingSubImagesCsv != null) {
            for (String image : existingSubImagesCsv.split(",")) {
                String trimmedImage = trimToNull(image);
                if (trimmedImage != null) {
                    oldSubImages.add(trimmedImage);
                }
            }
        }

        List<String> newSubImages = new ArrayList<>();
        for (Part part : request.getParts()) {
            if ("thumbnailSubs".equals(part.getName()) && part.getSize() > 0) {
                String imageUrl = saveUploadAndReturnUrl(request, part, "uploads/subimages");
                if (imageUrl != null) {
                    newSubImages.add(imageUrl);
                }
            }
        }

        subImagesDao.deleteByProductId(id);

        for (String image : oldSubImages) {
            subImagesDao.insert(id, image);
        }

        for (String image : newSubImages) {
            subImagesDao.insert(id, image);
        }

        specificationsDao.upsert(id, size, standard, madeIn, warning);
    }

    private void toggleProductActive(HttpServletRequest request) {
        int id = parseInt(request.getParameter("id"), 0);
        int isActive = parseInt(request.getParameter("isActive"), 1);

        if (id <= 0) {
            return;
        }

        productService.updateActive(id, isActive);
    }

    private String saveUploadAndReturnUrl(HttpServletRequest request, Part part, String folder) throws IOException {
        if (part == null || part.getSize() == 0) {
            return null;
        }

        String originalFileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        String extension = "";

        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex >= 0) {
            extension = originalFileName.substring(dotIndex);
        }

        String savedFileName = UUID.randomUUID().toString().replace("-", "") + extension;

        String realPath = request.getServletContext().getRealPath("/");
        File uploadFolder = new File(realPath, folder);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        File savedFile = new File(uploadFolder, savedFileName);
        part.write(savedFile.getAbsolutePath());

        return "/" + folder + "/" + savedFileName;
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }

        value = value.trim();
        return value.isEmpty() ? null : value;
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Exception exception) {
            return defaultValue;
        }
    }

    private double parseDouble(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (Exception exception) {
            return defaultValue;
        }
    }
}