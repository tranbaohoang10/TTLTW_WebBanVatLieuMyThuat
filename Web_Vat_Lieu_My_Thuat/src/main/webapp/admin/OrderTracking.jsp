<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Vận đơn admin</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f5f5;
            margin: 0;
            padding: 20px;
        }

        .box {
            background: white;
            max-width: 900px;
            margin: 0 auto 20px auto;
            padding: 20px;
            border-radius: 10px;
        }

        .title {
            font-size: 28px;
            font-weight: bold;
            margin-bottom: 20px;
        }

        .row {
            margin-bottom: 12px;
        }

        .label {
            font-weight: bold;
        }

        .status {
            display: inline-block;
            padding: 4px 10px;
            background: #e0f2fe;
            color: #0369a1;
            border-radius: 20px;
            font-weight: bold;
        }

        .empty {
            padding: 10px 12px;
            background: #f1f1f1;
            color: #666;
            border-radius: 6px;
        }

        .log-item {
            padding: 10px 0;
            border-bottom: 1px solid #eee;
        }

        .log-item:last-child {
            border-bottom: none;
        }

        .btn-back {
            display: inline-block;
            padding: 10px 16px;
            background: #2563eb;
            color: white;
            text-decoration: none;
            border-radius: 6px;
        }

        .btn-back:hover {
            background: #1d4ed8;
            color: white;
        }
    </style>
</head>
<body>

<div class="box">
    <div class="title">Thông tin vận đơn</div>

    <div class="row">
        <span class="label">Mã đơn hàng:</span> #${order.id}
    </div>

    <div class="row">
        <span class="label">Mã vận đơn GHN:</span> ${order.ghnOrderCode}
    </div>

    <div class="row">
        <span class="label">Trạng thái:</span>
        <span class="status">${trackingStatusText}</span>
    </div>

    <div class="row">
        <span class="label">Dự kiến giao:</span>
        ${leadtimeText}
    </div>

    <div class="row">
        <span class="label">Người nhận:</span>
        ${tracking.toName}
    </div>

    <div class="row">
        <span class="label">Số điện thoại:</span>
        ${tracking.toPhone}
    </div>

    <div class="row">
        <span class="label">Địa chỉ nhận:</span>
        ${tracking.toAddress}
    </div>
</div>

<div class="box">
    <div class="title" style="font-size: 22px;">Lịch sử vận đơn</div>

    <c:choose>
        <c:when test="${not empty tracking.logs}">
            <c:forEach var="log" items="${tracking.logs}">
                <div class="log-item">
                    <div><strong>${log.status}</strong></div>
                    <div>${log.updatedDate}</div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="empty">Đơn hàng mới được tạo, GHN chưa cập nhật hành trình vận chuyển.</div>
        </c:otherwise>
    </c:choose>

    <div style="margin-top: 20px;">
        <a href="${pageContext.request.contextPath}/admin/order-detail?id=${order.id}" class="btn-back">
            Quay lại chi tiết đơn hàng
        </a>
    </div>
</div>
</body>
</html>