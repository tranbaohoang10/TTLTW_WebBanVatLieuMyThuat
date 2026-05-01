package vn.edu.nlu.fit.mythuatshop.Service;

import vn.edu.nlu.fit.mythuatshop.Dao.InventoryDao;
import vn.edu.nlu.fit.mythuatshop.Model.InventoryTransaction;
import vn.edu.nlu.fit.mythuatshop.Model.Product;

import java.util.List;

public class InventoryService {
    private final InventoryDao inventoryDao;

    public InventoryService() {
        this.inventoryDao = new InventoryDao();
    }

    public List<Product> getInventoryProducts() {
        return inventoryDao.findAllProductsForInventory();
    }

    public List<InventoryTransaction> getHistory() {
        return inventoryDao.findAllHistory();
    }

    public boolean importStock(int productId, int quantity, String note, Integer adminId) {
        return inventoryDao.importStock(productId, quantity, note, adminId);
    }

    public boolean adjustStock(int productId, int newStock, String note, Integer adminId) {
        return inventoryDao.adjustStock(productId, newStock, note, adminId);
    }
}