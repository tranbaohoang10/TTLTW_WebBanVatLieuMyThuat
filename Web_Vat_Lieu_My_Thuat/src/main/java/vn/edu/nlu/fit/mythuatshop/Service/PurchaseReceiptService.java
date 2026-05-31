package vn.edu.nlu.fit.mythuatshop.Service;

import jakarta.servlet.http.HttpServletRequest;
import vn.edu.nlu.fit.mythuatshop.Dao.PurchaseReceiptDao;
import vn.edu.nlu.fit.mythuatshop.Model.Product;
import vn.edu.nlu.fit.mythuatshop.Model.Supplier;

import java.lang.reflect.Method;
import java.util.List;

public class PurchaseReceiptService {
    private final PurchaseReceiptDao purchaseReceiptDao;

    public PurchaseReceiptService() {
        this.purchaseReceiptDao = new PurchaseReceiptDao();
    }

    public List<Supplier> getActiveSuppliersForCreateForm() {
        return purchaseReceiptDao.findActiveSuppliersForCreateForm();
    }
    public List<Product> getActiveProductsForCreateForm() {
        return purchaseReceiptDao.findActiveProductsForCreateForm();
    }
    public String getCurrentImporterName(HttpServletRequest request) {
        Object currentUser = request.getSession().getAttribute("currentUser");

        if (currentUser == null) {
            return "Admin";
        }

        String[] getterNames = {
                "getFullName",
                "getFull_name",
                "getName",
                "getUsername",
                "getEmail"
        };

        for (String getterName : getterNames) {
            try {
                Method method = currentUser.getClass().getMethod(getterName);
                Object value = method.invoke(currentUser);

                if (value != null && !value.toString().isBlank()) {
                    return value.toString();
                }
            } catch (Exception ignored) {
            }
        }

        return "Admin";
    }

}