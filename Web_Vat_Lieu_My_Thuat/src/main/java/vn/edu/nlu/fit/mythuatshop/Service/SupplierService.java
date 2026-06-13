package vn.edu.nlu.fit.mythuatshop.Service;

import vn.edu.nlu.fit.mythuatshop.Dao.SupplierDao;
import vn.edu.nlu.fit.mythuatshop.Model.Supplier;

import java.util.List;
import java.util.regex.Pattern;

public class SupplierService {
    private final SupplierDao supplierDao;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private static final Pattern SUPPLIER_CODE_PATTERN =
            Pattern.compile("^[A-Za-z0-9_-]{2,50}$");

    public SupplierService() {
        this.supplierDao = new SupplierDao();
    }

    public List<Supplier> getAllSuppliers() {
        return supplierDao.findAll();
    }

    public List<Supplier> getActiveSuppliers() {
        return supplierDao.findActiveSuppliers();
    }

    public Supplier getSupplierById(int id) {
        if (id <= 0) {
            return null;
        }

        return supplierDao.findById(id);
    }

    public boolean create(Supplier supplier) {
        normalize(supplier);
        validate(supplier, null);

        supplier.setIsActive(1);

        return supplierDao.insert(supplier) > 0;
    }

    public boolean update(Supplier supplier) {
        normalize(supplier);

        if (supplier.getId() <= 0) {
            throw new IllegalArgumentException("Nhà cung cấp không hợp lệ.");
        }

        Supplier old = supplierDao.findById(supplier.getId());

        if (old == null) {
            throw new IllegalArgumentException("Không tìm thấy nhà cung cấp cần cập nhật.");
        }

        validate(supplier, supplier.getId());

        return supplierDao.update(supplier) > 0;
    }

    public boolean toggleStatus(int id, int currentIsActive) {
        if (id <= 0) {
            throw new IllegalArgumentException("Nhà cung cấp không hợp lệ.");
        }

        int newStatus = currentIsActive == 1 ? 0 : 1;

        return supplierDao.updateStatus(id, newStatus) > 0;
    }

    private void normalize(Supplier supplier) {
        supplier.setSupplierCode(clean(supplier.getSupplierCode()));

        if (supplier.getSupplierCode() != null) {
            supplier.setSupplierCode(supplier.getSupplierCode().toUpperCase());
        }

        supplier.setName(clean(supplier.getName()));
        supplier.setPhone(clean(supplier.getPhone()));
        supplier.setEmail(clean(supplier.getEmail()));
        supplier.setAddress(clean(supplier.getAddress()));
        supplier.setNote(clean(supplier.getNote()));
    }

    private void validate(Supplier supplier, Integer excludeId) {
        if (supplier.getSupplierCode() == null || supplier.getSupplierCode().isBlank()) {
            throw new IllegalArgumentException("Vui lòng nhập mã nhà cung cấp.");
        }

        if (!SUPPLIER_CODE_PATTERN.matcher(supplier.getSupplierCode()).matches()) {
            throw new IllegalArgumentException("Mã nhà cung cấp chỉ được gồm chữ, số, dấu gạch dưới hoặc gạch ngang.");
        }

        if (supplierDao.existsBySupplierCode(supplier.getSupplierCode(), excludeId)) {
            throw new IllegalArgumentException("Mã nhà cung cấp đã tồn tại.");
        }

        if (supplier.getName() == null || supplier.getName().isBlank()) {
            throw new IllegalArgumentException("Vui lòng nhập tên nhà cung cấp.");
        }

        if (supplier.getName().length() > 255) {
            throw new IllegalArgumentException("Tên nhà cung cấp không được vượt quá 255 ký tự.");
        }

        if (supplier.getPhone() != null && supplier.getPhone().length() > 20) {
            throw new IllegalArgumentException("Số điện thoại không được vượt quá 20 ký tự.");
        }

        if (supplier.getEmail() != null && !supplier.getEmail().isBlank()) {
            if (supplier.getEmail().length() > 100
                    || !EMAIL_PATTERN.matcher(supplier.getEmail()).matches()) {
                throw new IllegalArgumentException("Email nhà cung cấp không hợp lệ.");
            }
        }

        if (supplier.getAddress() != null && supplier.getAddress().length() > 255) {
            throw new IllegalArgumentException("Địa chỉ không được vượt quá 255 ký tự.");
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