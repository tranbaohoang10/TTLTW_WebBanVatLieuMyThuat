<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<html>
<head>
    <title>Chi tiết đơn hàng</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f6fa;
            margin: 0;
            padding: 20px;
        }

        .page-title {
            font-size: 28px;
            font-weight: bold;
            margin-bottom: 20px;
            color: #222;
        }

        .detail-wrapper {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }

        .detail-box {
            background: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
        }

        .detail-box h3 {
            margin-top: 0;
            margin-bottom: 15px;
            color: #333;
        }

        .info-table {
            width: 100%;
            border-collapse: collapse;
        }

        .info-table td {
            padding: 10px 8px;
            border-bottom: 1px solid #eee;
            vertical-align: top;
        }

        .info-label {
            width: 180px;
            font-weight: bold;
            color: #444;
        }

        .product-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }

        .product-table th,
        .product-table td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }

        .product-table th {
            background: #f0f0f0;
        }

        .product-thumb {
            width: 70px;
            height: 70px;
            object-fit: cover;
            border-radius: 6px;
            border: 1px solid #ddd;
        }

        .back-btn {
            display: inline-block;
            text-decoration: none;
            background: #2d89ef;
            color: white;
            padding: 10px 16px;
            border-radius: 6px;
            margin-bottom: 20px;
        }

        .empty-text {
            color: #888;
            font-style: italic;
        }
        .btn-view-tracking {
            display: inline-block;
            padding: 8px 14px;
            background: #2563eb;
            color: #fff;
            text-decoration: none;
            border-radius: 6px;
            font-weight: 500;
        }

        .btn-view-tracking:hover {
            background: #1d4ed8;
            color: #fff;
        }

        .empty-text {
            color: #777;
            font-style: italic;
        }
    </style>
</head>
<body>

<a class="back-btn" href="${pageContext.request.contextPath}/admin/orders">Quay lại</a>

<div class="page-title">Chi tiết đơn hàng</div>

<div class="detail-wrapper">

    <div class="detail-box">
        <h3>Thông tin đơn hàng</h3>
        <table class="info-table">
            <tr>
                <td class="info-label">Mã đơn hàng</td>
                <td>${order.id}</td>
            </tr>
            <tr>
                <td class="info-label">Người nhận</td>
                <td>${order.fullName}</td>
            </tr>
            <tr>
                <td class="info-label">Email</td>
                <td>${order.email}</td>
            </tr>
            <tr>
                <td class="info-label">Số điện thoại</td>
                <td>${order.phoneNumber}</td>
            </tr>
            <tr>
                <td class="info-label">Địa chỉ</td>
                <td>${order.address}</td>
            </tr>
            <tr>
                <td class="info-label">Ngày đặt</td>
                <td>${order.createAt}</td>
            </tr>
            <tr>
                <td class="info-label">Ngày dự kiến</td>
                <td>${order.expectedDeliveryDateText}</td>
            </tr>
            <tr>
                <td class="info-label">Mã vận đơn</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty order.ghnOrderCode}">
                            ${order.ghnOrderCode}
                        </c:when>
                        <c:otherwise>
                            <span class="empty-text">Chưa có vận đơn</span>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td class="info-label">Xem vận đơn</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty order.ghnOrderCode}">
                            <a href="${pageContext.request.contextPath}/admin/order-tracking?id=${order.id}"
                               class="btn-view-tracking">
                                Xem vận đơn
                            </a>
                        </c:when>
                        <c:otherwise>
                            <span class="empty-text">Chưa có vận đơn</span>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td class="info-label">Trạng thái đơn</td>
                <td>${order.statusName}</td>
            </tr>
            <tr>
                <td class="info-label">Trạng thái thanh toán</td>
                <td>${order.paymentStatus}</td>
            </tr>
            <tr>
                <td class="info-label">Giảm giá</td>
                <td>
                    <fmt:formatNumber value="${order.discount}" type="number" groupingUsed="true"/> đ
                </td>
            </tr>
            <tr>
                <td class="info-label">Phí ship</td>
                <td>
                    <fmt:formatNumber value="${order.shippingFee}" type="number" groupingUsed="true"/> đ
                </td>
            </tr>
            <tr>
                <td class="info-label">Tổng tiền</td>
                <td>
                    <fmt:formatNumber value="${order.totalPrice}" type="number" groupingUsed="true"/> đ
                </td>
            </tr>
            <tr>
                <td class="info-label">Ghi chú</td>
                <td>${order.note}</td>
            </tr>
            <c:if test="${order.orderStatusId == 4}">
                <tr>
                    <td class="info-label">Lý do hủy đơn</td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty order.cancelReason}">
                                ${order.cancelReason}
                            </c:when>
                            <c:otherwise>
                                <span class="empty-text">Không có lý do hủy.</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:if>
        </table>
    </div>

    <div class="detail-box">
        <h3>Sản phẩm trong đơn</h3>

        <c:choose>
            <c:when test="${not empty order.viewItems}">
                <table class="product-table">
                    <thead>
                    <tr>
                        <th>Ảnh</th>
                        <th>Tên sản phẩm</th>
                        <th>Số lượng</th>
                        <th>Đơn giá</th>
                        <th>Thành tiền</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${order.viewItems}">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${fn:startsWith(item.thumbnail, 'http')}">
                                        <img class="product-thumb" src="${item.thumbnail}" alt="${item.name}">
                                    </c:when>
                                    <c:otherwise>
                                        <img class="product-thumb" src="${pageContext.request.contextPath}/${item.thumbnail}" alt="${item.name}">
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${item.name}</td>
                            <td>${item.quantity}</td>
                            <td>
                                <fmt:formatNumber value="${item.price}" type="number" groupingUsed="true"/> đ
                            </td>
                            <td>
                                <fmt:formatNumber value="${item.price * item.quantity}" type="number" groupingUsed="true"/> đ
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p class="empty-text">Đơn hàng này chưa có sản phẩm.</p>
            </c:otherwise>
        </c:choose>
    </div>

</div>

</body>
</html>