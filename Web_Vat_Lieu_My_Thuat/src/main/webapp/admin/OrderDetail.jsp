<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Chi tiết đơn hàng</title>
</head>
<body>
<h2>Chi tiết đơn hàng</h2>

<p>Mã đơn: ${order.id}</p>
<p>Người nhận: ${order.fullName}</p>
<p>Email: ${order.email}</p>
<p>Số điện thoại: ${order.phoneNumber}</p>
<p>Địa chỉ: ${order.address}</p>
<p>Ngày dự kiến: ${order.expectedDeliveryDateText}</p>
<p>Tổng tiền: ${order.totalPrice}</p>
<p>Phí ship: ${order.shippingFee}</p>
<p>Trạng thái đơn: ${order.statusName}</p>
<p>Ghi chú: ${order.note}</p>
</body>
</html>