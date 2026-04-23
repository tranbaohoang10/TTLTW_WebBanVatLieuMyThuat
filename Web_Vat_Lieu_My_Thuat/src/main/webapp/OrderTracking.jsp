<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xem vận đơn</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f6fa;
            margin: 0;
            padding: 0;
        }

        .tracking-container {
            width: 900px;
            max-width: 95%;
            margin: 30px auto;
        }

        .tracking-card {
            background: #fff;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 18px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
        }

        .tracking-title {
            font-size: 24px;
            font-weight: 700;
            margin-bottom: 18px;
            color: #222;
        }

        .tracking-row {
            margin-bottom: 10px;
            line-height: 1.6;
        }

        .tracking-label {
            font-weight: 600;
            color: #333;
        }

        .tracking-status {
            display: inline-block;
            padding: 4px 10px;
            background: #e0f2fe;
            color: #0369a1;
            border-radius: 999px;
            font-weight: 600;
            font-size: 14px;
        }

        .tracking-empty {
            padding: 12px 14px;
            background: #f3f4f6;
            color: #666;
            border-radius: 8px;
        }

        .tracking-log-item {
            padding: 12px 0;
            border-bottom: 1px solid #eee;
        }

        .tracking-log-item:last-child {
            border-bottom: none;
        }

        .tracking-log-status {
            font-weight: 600;
            color: #222;
            margin-bottom: 4px;
        }

        .tracking-log-time {
            color: #666;
            font-size: 14px;
        }

        .tracking-actions {
            margin-top: 18px;
        }

        .btn-back {
            display: inline-block;
            padding: 10px 16px;
            background: #2563eb;
            color: #fff;
            text-decoration: none;
            border-radius: 8px;
        }

        .btn-back:hover {
            background: #1d4ed8;
            color: #fff;
        }
    </style>
</head>
<body>
<div class="tracking-container">
    <div class="tracking-card">
        <div class="tracking-title">Thông tin vận đơn</div>

        <div class="tracking-row">
            <span class="tracking-label">Mã đơn hàng:</span>
            #DH${order.id}
        </div>

        <c:if test="${not empty order.ghnOrderCode}">
            <div class="tracking-row">
                <span class="tracking-label">Mã vận đơn GHN:</span>
                    ${order.ghnOrderCode}
            </div>
        </c:if>

        <c:if test="${not empty trackingMessage}">
            <div class="tracking-empty">
                    ${trackingMessage}
            </div>
        </c:if>

        <c:if test="${not empty tracking}">
            <div class="tracking-row">
                <span class="tracking-label">Trạng thái:</span>
                <span class="tracking-status">${trackingStatusText}</span>
            </div>

            <div class="tracking-row">
                <span class="tracking-label">Dự kiến giao:</span>
                <c:choose>
                    <c:when test="${not empty trackingLeadtimeText}">
                        ${trackingLeadtimeText}
                    </c:when>
                    <c:otherwise>
                        Chưa có thông tin
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="tracking-row">
                <span class="tracking-label">Người nhận:</span>
                    ${tracking.toName}
            </div>

            <div class="tracking-row">
                <span class="tracking-label">Số điện thoại:</span>
                    ${tracking.toPhone}
            </div>

            <div class="tracking-row">
                <span class="tracking-label">Địa chỉ nhận:</span>
                    ${tracking.toAddress}
            </div>
        </c:if>
    </div>

    <div class="tracking-card">
        <div class="tracking-title" style="font-size: 20px;">Lịch sử vận đơn</div>

        <c:choose>
            <c:when test="${not empty tracking and not empty tracking.logs}">
                <c:forEach var="log" items="${tracking.logs}">
                    <div class="tracking-log-item">
                        <div class="tracking-log-status">
                                ${log.status}
                        </div>
                        <div class="tracking-log-time">
                                ${log.updatedDate}
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="tracking-empty">
                    Đơn hàng mới được tạo, GHN chưa cập nhật hành trình vận chuyển.
                </div>
            </c:otherwise>
        </c:choose>

        <div class="tracking-actions">
            <a href="${pageContext.request.contextPath}/order-detail?id=${order.id}" class="btn-back">
                Quay lại chi tiết đơn hàng
            </a>
        </div>
    </div>
</div>
</body>
</html>