package vn.edu.nlu.fit.mythuatshop.Service;

import vn.edu.nlu.fit.mythuatshop.Dao.CategoryDao;
import vn.edu.nlu.fit.mythuatshop.Model.Category;

import java.util.List;

public class CategoryService {
    private final CategoryDao categoryDao;
    private static List<Category> categoryCache = null;
    private static long categoryCacheTime = 0;
    private static final long CACHE_TIME = 5*60*1000;

    public CategoryService() {
        this.categoryDao = new CategoryDao();
    }
    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    // Admin
    public List<Category> getAllcategories() {
        return categoryDao.findAll();
    }

    // Client
    public List<Category> getAllcategoriesActive() {
        return getAllCategoriesByCache();
    }

    public List<Category> getAllCategoriesByCache() {
        long now = System.currentTimeMillis();
        if (categoryCache != null && now - categoryCacheTime < CACHE_TIME) {
            return categoryCache;
        }
        categoryCache = categoryDao.findAllActive();
        categoryCacheTime = now;

        return categoryCache;
    }

    public void clearCategoryCache() {
        categoryCache = null;
        categoryCacheTime = 0;
    }



    public Category getCategoryById(int id) {
        return categoryDao.findById(id);
    }

    public Category getCategoryByIdActive(int id) {
        return categoryDao.findByIdActive(id);
    }

    public int create(Category c) {
        int id = categoryDao.insertReturnId(c);
        clearCategoryCache();
        return id;
    }

    public int update(Category c) {
        int result = categoryDao.update(c);
        clearCategoryCache();
        return result;
    }

    public int toggleActive(int id, int currentIsActive) {
        int newValue = (currentIsActive == 1) ? 0 : 1;
        int result = categoryDao.updateActive(id, newValue);
        clearCategoryCache();
        return result;
    }
    public List<Category> getAllActiveCategories() {
        return getAllCategoriesByCache();
    }

}
