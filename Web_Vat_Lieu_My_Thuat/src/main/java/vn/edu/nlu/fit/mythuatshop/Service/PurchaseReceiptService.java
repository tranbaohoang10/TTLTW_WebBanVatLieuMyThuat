package vn.edu.nlu.fit.mythuatshop.Service;

import jakarta.servlet.http.HttpServletRequest;
import vn.edu.nlu.fit.mythuatshop.Dao.PurchaseReceiptDao;
import vn.edu.nlu.fit.mythuatshop.Model.Product;
import vn.edu.nlu.fit.mythuatshop.Model.Supplier;
import vn.edu.nlu.fit.mythuatshop.Model.PurchaseReceipt;
import vn.edu.nlu.fit.mythuatshop.Model.PurchaseReceiptDetail;

import java.util.HashSet;
import java.util.Set;

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
    public int createPurchaseReceipt(PurchaseReceipt receipt,
                                     List<PurchaseReceiptDetail> details) {
        validateReceipt(receipt, details);

        double totalAmount = 0;

        for (PurchaseReceiptDetail detail : details) {
            double lineTotal = detail.getQuantity() * detail.getImportPrice();
            detail.setLineTotal(lineTotal);
            totalAmount += lineTotal;
        }

        receipt.setTotalAmount(totalAmount);

        if (receipt.getStatus() == null || receipt.getStatus().isBlank()) {
            receipt.setStatus("COMPLETED");
        }

        return purchaseReceiptDao.createPurchaseReceipt(receipt, details);
    }

    private void validateReceipt(PurchaseReceipt receipt,
                                 List<PurchaseReceiptDetail> details) {
        if (receipt.getSupplierId() <= 0) {
            throw new IllegalArgumentException("Vui lòng chọn nhà cung cấp.");
        }

        if (!purchaseReceiptDao.existsActiveSupplier(receipt.getSupplierId())) {
            throw new IllegalArgumentException("Nhà cung cấp không tồn tại hoặc đã bị khóa.");
        }

        if (receipt.getImportDate() == null) {
            throw new IllegalArgumentException("Vui lòng chọn ngày nhập hàng.");
        }

        if (details == null || details.isEmpty()) {
            throw new IllegalArgumentException("Phiếu nhập phải có ít nhất một sản phẩm.");
        }

        Set<Integer> productIds = new HashSet<>();

        for (PurchaseReceiptDetail detail : details) {
            if (detail.getProductId() <= 0) {
                throw new IllegalArgumentException("Vui lòng chọn đầy đủ sản phẩm.");
            }

            if (!purchaseReceiptDao.existsActiveProduct(detail.getProductId())) {
                throw new IllegalArgumentException("Sản phẩm không tồn tại hoặc đã bị khóa.");
            }

            if (productIds.contains(detail.getProductId())) {
                throw new IllegalArgumentException("Không được chọn trùng sản phẩm trong cùng một phiếu nhập.");
            }

            productIds.add(detail.getProductId());

            if (detail.getQuantity() <= 0) {
                throw new IllegalArgumentException("Số lượng nhập phải lớn hơn 0.");
            }

            if (detail.getImportPrice() < 0) {
                throw new IllegalArgumentException("Giá nhập không được âm.");
            }
        }
    }
}