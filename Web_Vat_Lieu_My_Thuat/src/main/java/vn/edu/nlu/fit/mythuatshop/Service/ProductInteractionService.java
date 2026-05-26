package vn.edu.nlu.fit.mythuatshop.Service;

import vn.edu.nlu.fit.mythuatshop.Dao.ProductInteractionDao;
import vn.edu.nlu.fit.mythuatshop.Dao.ProductInteractionDao;
import vn.edu.nlu.fit.mythuatshop.Model.ProductTrainData;

import java.util.List;

public class ProductInteractionService {
    private final ProductInteractionDao interactionDao;

    public ProductInteractionService() {
        this.interactionDao = new ProductInteractionDao();
    }

    public void saveViewDetail(int userId, int productId) {
        interactionDao.insert(userId, productId, "VIEW_DETAIL", 1);
    }

    public void saveAddToCart(int userId, int productId) {
        interactionDao.insert(userId, productId, "ADD_TO_CART", 3);
    }

    public void savePurchase(int userId, int productId) {
        interactionDao.insert(userId, productId, "PURCHASE", 10);
    }
    public List<ProductTrainData> getTrainingData() {
        return interactionDao.getTrainingData();
    }
}