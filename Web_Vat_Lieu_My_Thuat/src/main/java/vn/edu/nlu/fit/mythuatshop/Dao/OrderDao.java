package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.Order;
import vn.edu.nlu.fit.mythuatshop.Model.OrderDetail;
import vn.edu.nlu.fit.mythuatshop.Model.OrderItem;

import java.util.List;

public class OrderDao implements DaoInterface<Order> {
    private final Jdbi jdbi;
    private final ProductDao productDao = new ProductDao();


    public OrderDao() {
        this.jdbi = JDBIConnector.getJdbi();
    }

    public int insert(Order order, boolean updateInventory) {
        return jdbi.inTransaction(handle -> {
            String sql = "INSERT INTO Orders (userID, fullName, email, phoneNumber, address, " +
                    "deliveryProvinceId, deliveryDistrictId, deliveryWardCode, expectedDeliveryTime, expectedDeliveryDateText, " +
                    " totalPrice, paymentID, orderStatusID, voucherID, discount,shippingFee, note,paymentStatus ) " +
                    "VALUES (:userID, :fullName, :email, :phoneNumber, :address, " +
                    ":deliveryProvinceId, :deliveryDistrictId, :deliveryWardCode, :expectedDeliveryTime, :expectedDeliveryDateText, " +
                    " :totalPrice, :paymentID, :orderStatusID, :voucherID, :discount,:shippingFee, :note, :paymentStatus)";
            int orderId = handle.createUpdate(sql)
                    .bind("userID", order.getUserId())
                    .bind("fullName", order.getFullName())
                    .bind("email", order.getEmail())
                    .bind("phoneNumber", order.getPhoneNumber())
                    .bind("address", order.getAddress())
                    .bind("deliveryProvinceId", order.getDeliveryProvinceId())
                    .bind("deliveryDistrictId", order.getDeliveryDistrictId())
                    .bind("deliveryWardCode", order.getDeliveryWardCode())
                    .bind("expectedDeliveryTime", order.getExpectedDeliveryTime())
                    .bind("expectedDeliveryDateText", order.getExpectedDeliveryDateText())
                    .bind("totalPrice", order.getTotalPrice())
                    .bind("paymentID", order.getPaymentId())
                    .bind("orderStatusID", order.getOrderStatusId())
                    .bind("voucherID", order.getVoucherId())
                    .bind("discount", order.getDiscount())
                    .bind("shippingFee", order.getShippingFee())
                    .bind("note", order.getNote())
                    .bind("paymentStatus", order.getPaymentStatus())
                    .executeAndReturnGeneratedKeys("ID")
                    .mapTo(Integer.class)
                    .one();

            for (OrderDetail d : order.getItems()) {
                handle.createUpdate(
                                "INSERT INTO Order_Details (orderID, productID, quantity, price) " +
                                        "VALUES (:orderID, :productID, :quantity, :price)")
                        .bind("orderID", orderId)
                        .bind("productID", d.getProductId())
                        .bind("quantity", d.getQuantity())
                        .bind("price", d.getPrice())
                        .execute();
            }
            if (updateInventory) {
                for (OrderDetail d : order.getItems()) {
                    int affected = productDao.updateStockAndSold(handle, d.getProductId(), d.getQuantity());
                    if (affected == 0) {
                        throw new IllegalStateException("Không đủ tồn kho cho sản phẩm : " + d.getProductId());
                    }
                }
            }
            return orderId;
        });
    }

    public Order confirmVnpayPaid(int orderId, long amountVnd) {
        return jdbi.inTransaction(handle -> {
            String sql = """
                        SELECT ID, userID, fullName, email, phoneNumber, address,
                               totalPrice, paymentID, orderStatusID, voucherID, discount,shippingFee, note
                        FROM Orders
                        WHERE ID = :id
                    """;

            Order order = handle.createQuery(sql)
                    .bind("id", orderId)
                    .map((rs, ctx) -> {
                        Order o = new Order();
                        o.setId(rs.getInt("ID"));
                        o.setUserId(rs.getInt("userID"));
                        o.setFullName(rs.getString("fullName"));
                        o.setEmail(rs.getString("email"));
                        o.setPhoneNumber(rs.getString("phoneNumber"));
                        o.setAddress(rs.getString("address"));
                        o.setTotalPrice(rs.getDouble("totalPrice"));
                        o.setPaymentId(rs.getInt("paymentID"));
                        o.setOrderStatusId(rs.getInt("orderStatusID"));
                        o.setVoucherId((Integer) rs.getObject("voucherID"));
                        o.setDiscount(rs.getDouble("discount"));
                        o.setShippingFee(rs.getDouble("shippingFee"));
                        o.setNote(rs.getString("note"));
                        return o;
                    })
                    .findOne()
                    .orElse(null);

            if (order == null) return null;

            long dbAmountVnd = java.math.BigDecimal.valueOf(order.getTotalPrice())
                    .setScale(0, java.math.RoundingMode.HALF_UP)
                    .longValue();

            if (dbAmountVnd != amountVnd) {
                throw new IllegalStateException("Sai số tiền thanh toán cho đơn hàng #DH0=" + orderId);
            }

            List<OrderDetail> details = handle.createQuery("""
                                SELECT productID, quantity, price
                                FROM Order_Details
                                WHERE orderID = :oid
                            """)
                    .bind("oid", orderId)
                    .map((rs, ctx) -> {
                        OrderDetail d = new OrderDetail();
                        d.setProductId(rs.getInt("productID"));
                        d.setQuantity(rs.getInt("quantity"));
                        d.setPrice(rs.getDouble("price"));
                        return d;
                    })
                    .list();

            for (OrderDetail d : details) {
                int affected = productDao.updateStockAndSold(handle, d.getProductId(), d.getQuantity());
                if (affected == 0) {
                    throw new IllegalStateException("Không đủ tồn kho cho sản phẩm: " + d.getProductId());
                }
            }


            handle.createUpdate("""
                                UPDATE Orders
                                SET orderStatusID = (
                                        SELECT ID FROM Order_Statuses WHERE statusName = 'Đang xử lý' LIMIT 1
                                    ),
                                    paymentStatus = 'Đã thanh toán'
                                WHERE ID = :id
                            """)
                    .bind("id", orderId)
                    .execute();


            order.setItems(details);
            return order;
        });
    }

    public void markVnpayFailed(int orderId) {
        String sql = """
                    UPDATE Orders
                    SET orderStatusID = (
                        SELECT ID FROM Order_Statuses WHERE statusName = 'Đã hủy' LIMIT 1
                    ),
                    paymentStatus = 'Thanh toán thất bại'
                    WHERE ID = :id
                """;
        jdbi.useHandle(handle -> handle.createUpdate(sql)
                .bind("id", orderId)
                .execute()
        );
    }


    public List<Order> findOrdersByUser(int userId, Integer statusId) {
        String sql =
                "SELECT o.ID            AS id, " +
                        "       o.userID        AS userId, " +
                        "       o.fullName      AS fullName, " +
                        "       o.email         AS email, " +
                        "       o.phoneNumber   AS phoneNumber, " +
                        "       o.address       AS address, " +
                        "       o.expectedDeliveryDateText AS expectedDeliveryDateText, " +
                        "       o.totalPrice    AS totalPrice, " +
                        "       o.paymentStatus AS paymentStatus, " +
                        "       o.paymentID     AS paymentId, " +
                        "       o.orderStatusID AS orderStatusId, " +
                        "       o.voucherID     AS voucherId, " +
                        "       o.discount      AS discount, " +
                        "       o.shippingFee AS shippingFee, " +
                        "       o.createAt      AS createAt, " +
                        "       o.note          AS note, " +
                        "       os.statusName   AS statusName " +

                        "FROM Orders o " +
                        "JOIN Order_Statuses os ON os.ID = o.orderStatusID " +
                        "WHERE o.userID = :userId ";

        if (statusId != null) {
            sql += " AND o.orderStatusID = :statusId ";
        }

        sql += " ORDER BY o.createAt DESC";

        String finalSql = sql;

        return jdbi.withHandle(handle -> {
            var query = handle.createQuery(finalSql)
                    .bind("userId", userId);

            if (statusId != null) {
                query.bind("statusId", statusId);
            }

            return query.mapToBean(Order.class).list();
        });
    }

    public List<OrderItem> findOrderItemsView(int orderId) {
        String sql =
                "SELECT d.productID AS productId, " +
                        "       p.name      AS name, " +
                        "       p.thumbnail AS thumbnail, " +
                        "       d.quantity  AS quantity, " +
                        "       d.price     AS price " +
                        "FROM Order_Details d " +
                        "JOIN Products p ON p.ID = d.productID " +
                        "WHERE d.orderID = :orderId";

        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("orderId", orderId)
                        .mapToBean(OrderItem.class)
                        .list()
        );
    }

    public Order findOrderHeaderByIdAndUser(int orderId, int userId) {
        String sql = "SELECT o.ID            AS id, " +
                "       o.userID        AS userId, " +
                "       o.fullName      AS fullName, " +
                "       o.email         AS email, " +
                "       o.phoneNumber   AS phoneNumber, " +
                "       o.address       AS address, " +
                "       o.expectedDeliveryDateText AS expectedDeliveryDateText, " +
                "       o.totalPrice    AS totalPrice, " +
                "       o.paymentID     AS paymentId, " +
                "       o.orderStatusID AS orderStatusId, " +
                "       o.voucherID     AS voucherId, " +
                "       o.discount      AS discount, " +
                "       o.shippingFee AS shippingFee, " +
                "       o.createAt      AS createAt, " +
                "       o.note          AS note, " +
                "       os.statusName   AS statusName, " +
                "       p.paymentName   AS paymentName " +
                "FROM Orders o " +
                "JOIN Order_Statuses os ON os.ID = o.orderStatusID " +
                "JOIN payments p ON p.ID = o.paymentID " +
                "WHERE o.ID = :orderId AND o.userID = :userId";

        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("orderId", orderId)
                        .bind("userId", userId)
                        .mapToBean(Order.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public boolean cancelOrder(int userId, int orderId) {
        return jdbi.inTransaction(handle -> {

            int updated = handle.createUpdate("""
                UPDATE Orders
                SET orderStatusID = 4
                WHERE ID = :oid
                  AND userID = :uid
                  AND orderStatusID = 1
            """)
                    .bind("oid", orderId)
                    .bind("uid", userId)
                    .execute();

            if (updated != 1) return false;

            handle.createUpdate("    UPDATE Products p\n" +
                                "    JOIN Order_Details od ON od.productID = p.id\n" +
                                "    SET p.quantityStock = p.quantityStock + od.quantity,\n" +
                                "        p.soldQuantity = GREATEST(p.soldQuantity - od.quantity, 0)\n" +
                                "    WHERE od.orderID = :oid\n")
                    .bind("oid", orderId)
                    .execute();

            return true;
        });
    }



    public List<Order> findAllForAdmin() {
        String sql = """
                    SELECT
                        o.ID            AS id,
                        o.fullName      AS fullName,
                        o.phoneNumber   AS phoneNumber,
                        o.createAt      AS createAt,
                        o.address       AS address,
                        o.totalPrice    AS totalPrice,
                        o.paymentStatus AS paymentStatus,
                        os.statusName   AS statusName,
                        GROUP_CONCAT(p.name ORDER BY p.name SEPARATOR ', ') AS productNames
                    FROM Orders o
                    JOIN Order_Statuses os ON os.ID = o.orderStatusID
                    LEFT JOIN Order_Details od ON od.orderID = o.ID
                    LEFT JOIN Products p ON p.ID = od.productID
                    GROUP BY o.ID, o.fullName, o.phoneNumber, o.createAt, o.address, o.totalPrice,o.paymentStatus, os.statusName
                    ORDER BY o.createAt DESC
                """;

        return jdbi.withHandle(h -> h.createQuery(sql).mapToBean(Order.class).list());
    }

    public Order findOrderForAdmin(int orderId) {
        String sql = """
                    SELECT o.ID AS id,
                           os.statusName AS statusName,
                           p.paymentName AS paymentName,
                           o.paymentStatus AS paymentStatus
                    FROM Orders o
                    JOIN Order_Statuses os ON os.ID = o.orderStatusID
                    JOIN Payments p ON p.ID = o.paymentID
                    WHERE o.ID = :id
                """;

        return jdbi.withHandle(h -> h.createQuery(sql)
                .bind("id", orderId)
                .mapToBean(Order.class)
                .findOne()
                .orElse(null));
    }

    public boolean updateOrderStatusByName(int orderId, String statusName) {
        String sql = """
                    UPDATE Orders
                    SET orderStatusID = (SELECT ID FROM Order_Statuses WHERE statusName = :name LIMIT 1)
                    WHERE ID = :id
                """;
        int n = jdbi.withHandle(h -> h.createUpdate(sql)
                .bind("id", orderId)
                .bind("name", statusName)
                .execute());
        return n == 1;
    }

    public boolean updatePaymentStatus(int orderId, String paymentStatus) {
        String sql = "UPDATE Orders SET paymentStatus = :ps WHERE ID = :id";
        int n = jdbi.withHandle(h -> h.createUpdate(sql)
                .bind("id", orderId)
                .bind("ps", paymentStatus)
                .execute());
        return n == 1;
    }

    public boolean updateInfoIfProcessing(int orderId, String fullName, String phone, String address) {
        String sql = """
                    UPDATE Orders o
                    JOIN Order_Statuses os ON os.ID = o.orderStatusID
                    SET o.fullName = :fullName,
                        o.phoneNumber = :phone,
                        o.address = :address
                    WHERE o.ID = :orderId
                      AND os.statusName = 'Đang xử lý'
                """;

        int affected = jdbi.withHandle(h -> h.createUpdate(sql)
                .bind("fullName", fullName)
                .bind("phone", phone)
                .bind("address", address)
                .bind("orderId", orderId)
                .execute());

        return affected == 1;
    }


    @Override
    public int insert(Order order) {
        return 0;
    }

    @Override
    public int update(Order order) {
        return 0;
    }

    @Override
    public int delete(Order id) {
        return 0;
    }

    @Override
    public Order findByName(String name) {
        return null;
    }

}


