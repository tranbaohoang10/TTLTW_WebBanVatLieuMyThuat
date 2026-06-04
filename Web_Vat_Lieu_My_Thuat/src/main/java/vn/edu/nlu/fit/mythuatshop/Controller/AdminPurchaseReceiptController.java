package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.mythuatshop.Service.PurchaseReceiptService;
import vn.edu.nlu.fit.mythuatshop.Model.PurchaseReceipt;
import vn.edu.nlu.fit.mythuatshop.Model.PurchaseReceiptDetail;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet(name = "AdminPurchaseReceiptController", value = "/admin/purchase-receipts")
public class AdminPurchaseReceiptController extends HttpServlet {
    private PurchaseReceiptService purchaseReceiptService;

    @Override
    public void init() {
        purchaseReceiptService = new PurchaseReceiptService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        request.setAttribute(
                "activeSuppliers",
                purchaseReceiptService.getActiveSuppliersForCreateForm()
        );
        request.setAttribute(
                "activeProducts",
                purchaseReceiptService.getActiveProductsForCreateForm()
        );
        request.setAttribute(
                "purchaseReceipts",
                purchaseReceiptService.getAllPurchaseReceipts()
        );

        request.setAttribute(
                "currentImporterName",
                purchaseReceiptService.getCurrentImporterName(request)
        );

        request.getRequestDispatcher("/admin/PurchaseReceipt.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            PurchaseReceipt receipt = buildReceiptFromRequest(request);
            List<PurchaseReceiptDetail> details = buildDetailsFromRequest(request);

            int receiptId = purchaseReceiptService.createPurchaseReceipt(receipt, details);

            request.getSession().setAttribute(
                    "purchaseReceiptMessage",
                    "Lưu phiếu nhập hàng thành công. Đã cộng tồn kho và ghi lịch sử nhập kho cho phiếu #" + receiptId
            );

        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("purchaseReceiptError", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute(
                    "purchaseReceiptError",
                    "Có lỗi xảy ra khi lưu phiếu nhập hàng."
            );
        }

        response.sendRedirect(request.getContextPath() + "/admin/purchase-receipts");
    }

    private String getCurrentImporterName(HttpServletRequest request) {
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
    private PurchaseReceipt buildReceiptFromRequest(HttpServletRequest request) {
        PurchaseReceipt receipt = new PurchaseReceipt();

        receipt.setSupplierId(parseInt(request.getParameter("supplierID"), 0));
        receipt.setImportDate(parseImportDate(request.getParameter("importDate")));
        receipt.setCreatedBy(getCurrentUserId(request));
        receipt.setAttachmentPath(null);
        receipt.setNote(clean(request.getParameter("note")));

        receipt.setStatus("DRAFT");

        return receipt;
    }

    private List<PurchaseReceiptDetail> buildDetailsFromRequest(HttpServletRequest request) {
        String[] productIds = request.getParameterValues("productIds");
        String[] quantities = request.getParameterValues("quantities");
        String[] importPrices = request.getParameterValues("importPrices");

        List<PurchaseReceiptDetail> details = new ArrayList<>();

        if (productIds == null || quantities == null || importPrices == null) {
            return details;
        }

        int size = Math.min(productIds.length, Math.min(quantities.length, importPrices.length));

        for (int i = 0; i < size; i++) {
            PurchaseReceiptDetail detail = new PurchaseReceiptDetail();

            detail.setProductId(parseInt(productIds[i], 0));
            detail.setQuantity(parseInt(quantities[i], 0));
            detail.setImportPrice(parseDouble(importPrices[i], 0));

            details.add(detail);
        }

        return details;
    }

    private Timestamp parseImportDate(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        try {
            LocalDateTime localDateTime = LocalDateTime.parse(value);
            return Timestamp.valueOf(localDateTime);
        } catch (Exception e) {
            return null;
        }
    }

    private Integer getCurrentUserId(HttpServletRequest request) {
        Object currentUser = request.getSession().getAttribute("currentUser");

        if (currentUser == null) {
            return null;
        }

        String[] getterNames = {
                "getId",
                "getID",
                "getUserId",
                "getUserID"
        };

        for (String getterName : getterNames) {
            try {
                Object value = currentUser.getClass().getMethod(getterName).invoke(currentUser);

                if (value instanceof Number number) {
                    return number.intValue();
                }

                if (value != null) {
                    return Integer.parseInt(value.toString());
                }
            } catch (Exception ignored) {
            }
        }

        return null;
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private double parseDouble(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private String clean(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();

        if (trimmed.isEmpty()) {
            return null;
        }

        return trimmed;
    }
}