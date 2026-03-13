package vn.edu.nlu.fit.mythuatshop.Service;

import vn.edu.nlu.fit.mythuatshop.Dao.ProductDao;
import vn.edu.nlu.fit.mythuatshop.Model.Product;

import java.util.List;

public class ProductService {
    private final ProductDao productDao;

    public ProductService() {
        this.productDao = new ProductDao();
    }

    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    public List<Product> getAllProductsByCategoryId(int categoryId) {
        return productDao.findByCategoryId(categoryId);
    }

    public List<Product> getProductsByCategoryId(int categoryId) {
        return productDao.findByCategoryId(categoryId);
    }

    public List<Product> getProductsByCategoryWithFilter(int categoryId, Double minPrice, Double maxPrice,
            String sort) {
        return productDao.findByCategoryWithFilter(categoryId, minPrice, maxPrice, sort);
    }

    public List<Product> getProductsByCategoryWithFilter(int categoryId, Double minPrice, Double maxPrice,
            String sort, int offset, int limit) {
        return productDao.findByCategoryWithFilter(categoryId, minPrice, maxPrice, sort, offset, limit);
    }

    public int countProductsByCategory(int categoryId, Double minPrice, Double maxPrice) {
        return productDao.countProductsByCategory(categoryId, minPrice, maxPrice);
    }

    public List<Product> getProductSearch(String productName) {
        if (productName == null) {
            productName = "";
        }
        return productDao.getProductSearch(productName);
    }

    public List<Product> getProductSearch(String productName, String sort, int offset, int limit) {
        if (productName == null) {
            productName = "";
        }
        if (sort == null || sort.trim().isEmpty()) {
            sort = "";
        }
        return productDao.getProductSearch(productName, sort, offset, limit);
    }


    public int countProductSearch(String productName) {
        return productDao.countProductSearch(productName);
    }
    public Product getProductById(int productId) {
        return productDao.findByProductId(productId);
    }
    public int create(Product p) {
        return productDao.insertReturnId(p);
    }

    public int update(Product p) {
        return productDao.update(p);
    }
    public int updateActive(int id, int isActive) {
        return productDao.updateActive(id, isActive);
    }public Product getProductByIdActive(int productId) {
        return productDao.findByProductIdActive(productId);
    }

    public int delete(int id) {
        return productDao.deleteById(id);
    }

}
