package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.OrderStatus;

public class OrderStatusDao implements DaoInterface<OrderStatus> {
    private final Jdbi jdbi;

    public OrderStatusDao() {
        this.jdbi = JDBIConnector.getJdbi();
    }

    @Override
    public int insert(OrderStatus orderStatus) {
        return 0;
    }

    @Override
    public int update(OrderStatus orderStatus) {
        return 0;
    }

    @Override
    public int delete(OrderStatus id) {
        return 0;
    }

    @Override
    public OrderStatus findByName(String name) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT ID, statusName FROM order_statuses WHERE statusName = :name")
                        .bind("name", name)
                        .mapToBean(OrderStatus.class)
                        .findOne()
                        .orElse(null)
        );
    }
}
