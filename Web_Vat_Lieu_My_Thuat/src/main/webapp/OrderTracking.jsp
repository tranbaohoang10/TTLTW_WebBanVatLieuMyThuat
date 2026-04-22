<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xem vận đơn</title>
</head>
<body>
<h2>Thông tin vận đơn</h2>

<c:if test="${not empty trackingMessage}">
    <p>${trackingMessage}</p>
</c:if>

<c:if test="${not empty tracking}">
    <p>Mã GHN: ${tracking.orderCode}</p>
    <p>Trạng thái: ${tracking.status}</p>
    <p>Dự kiến giao: ${tracking.leadtime}</p>
    <p>Người nhận: ${tracking.toName}</p>
    <p>SĐT: ${tracking.toPhone}</p>
    <p>Địa chỉ: ${tracking.toAddress}</p>

    <h3>Lịch sử vận đơn</h3>
    <c:forEach var="log" items="${tracking.logs}">
        <p>${log.status} - ${log.updatedDate}</p>
    </c:forEach>
</c:if>

<p><a href="${pageContext.request.contextPath}/order-detail?id=${order.id}">Quay lại chi tiết đơn hàng</a></p>
</body>
</html>