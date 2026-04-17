package vn.edu.nlu.fit.mythuatshop.Service;

import vn.edu.nlu.fit.mythuatshop.Dao.VoucherDao;
import vn.edu.nlu.fit.mythuatshop.Model.Cart;
import vn.edu.nlu.fit.mythuatshop.Model.Voucher;

import java.time.LocalDateTime;
import java.util.List;

public class VoucherService {
    private final VoucherDao voucherDao = new VoucherDao();

    public VoucherApplyResult apply(String rawCode, Cart cart) {
        if (cart == null || cart.cartSize() == 0) {
            return VoucherApplyResult.fail("Giỏ hàng trống");
        }

        if (rawCode == null || rawCode.trim().isEmpty()) {
            return VoucherApplyResult.fail("Vui lòng nhập mã giảm giá");
        }

        String code = rawCode.trim().toUpperCase();

        Voucher v = voucherDao.findByCode(code);
        if (v == null) {
            return VoucherApplyResult.fail("Mã giảm giá không tồn tại");
        }

        if (v.getIsActive() != 1) {
            return VoucherApplyResult.fail("Mã giảm giá không hoạt động");
        }

        LocalDateTime now = LocalDateTime.now();
        if (v.getStartDate() != null && now.isBefore(v.getStartDate())) {
            return VoucherApplyResult.fail("Mã giảm giá chưa đến thời gian áp dụng");
        }
        if (v.getEndDate() != null && now.isAfter(v.getEndDate())) {
            return VoucherApplyResult.fail("Mã giảm giá đã hết hạn");
        }

        if (v.getQuantityUsed() >= v.getQuantity()) {
            return VoucherApplyResult.fail("Mã giảm giá đã hết lượt sử dụng");
        }

        double subtotal = cart.getTotalProductPrice();
        if (subtotal < v.getMinOrderValue()) {
            return VoucherApplyResult.fail("Đơn hàng chưa đạt giá trị tối thiểu để áp dụng");
        }

        double discount = Math.min(v.getVoucherCash(), subtotal);
        cart.setDiscount(discount);
        cart.setVoucherId(v.getId());
        return VoucherApplyResult.ok(discount);
    }

    public void clear(Cart cart) {
        if (cart == null) return;
        cart.clearVoucher();
    }
    public boolean lock(int id) {
        return voucherDao.lock(id) > 0;
    }
    public boolean unlock(int id) {
        return voucherDao.unlock(id) > 0;
    }
    public List<Voucher> getAll() {
        return voucherDao.findAll();
    }

    public Voucher getById(int id) {
        return voucherDao.findById(id);
    }

    public boolean create(Voucher v) {
        return voucherDao.insert(v) > 0;
    }

    public boolean update(Voucher v) {
        return voucherDao.update(v) > 0;
    }

    public boolean delete(int id) {
        return voucherDao.delete(id) > 0;
    }
}
