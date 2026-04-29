package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.Payment;

public class PaymentDao implements DaoInterface<Payment>{
    private final Jdbi jdbi;

    public PaymentDao() {
        this.jdbi = JDBIConnector.getJdbi();
    }

    @Override
    public int insert(Payment payment) {
        return 0;
    }

    @Override
    public int update(Payment payment) {
        return 0;
    }

    @Override
    public int delete(Payment id) {
        return 0;
    }

    public Payment findByName(String name) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT ID, paymentName, description FROM payments WHERE paymentName = :name")
                        .bind("name", name)
                        .mapToBean(Payment.class)
                        .findOne()
                        .orElse(null)
        );
    }
}
