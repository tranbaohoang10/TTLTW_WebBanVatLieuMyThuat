<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết đơn hàng</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>

    <style>
        .order-detail-wrap {
            padding: 20px 0 30px;
        }

        .order-detail-card {
            background: #fff;
            border: 1px solid #e5e7eb;
            border-radius: 12px;
            padding: 20px;
        }

        .order-detail-title {
            margin: 0 0 12px;
            font-size: 26px;
            font-weight: 700;
        }

        .order-meta {
            display: grid;
            grid-template-columns: 1fr 1fr;
            column-gap: 60px;
            row-gap: 10px;
            align-items: start;
        }

        .order-meta p {
            margin: 0;
        }

        .status-badge {
            display: inline-flex;
            align-items: center;
            padding: 6px 12px;
            border-radius: 999px;
            font-weight: 700;
            font-size: 14px;
        }

        .st-pending {
            background: #FEF3C7;
            color: #92400E;
        }

        .st-shipping {
            background: #DBEAFE;
            color: #1D4ED8;
        }

        .st-completed {
            background: #DCFCE7;
            color: #166534;
        }

        .st-cancelled {
            background: #FEE2E2;
            color: #B91C1C;
        }

        .order-items-title {
            margin: 16px 0 10px;
            font-size: 18px;
            font-weight: 700;
        }

        .od-item {
            display: flex;
            gap: 12px;
            align-items: center;
            padding: 14px 0;
            border-top: 1px solid #e5e7eb;
        }

        .od-item:first-of-type {
            border-top: none;
        }

        .od-thumb {
            width: 70px;
            height: 70px;
            border-radius: 10px;
            overflow: hidden;
            border: 1px solid #e5e7eb;
            flex-shrink: 0;
        }

        .od-thumb img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .od-info {
            flex: 1;
            min-width: 0;
        }

        .od-name {
            font-weight: 650;
            margin: 0;
        }

        .order-meta b {
            font-weight: 600;
        }

        .od-qty {
            margin: 4px 0 0;
            color: #6b7280;
        }

        .od-review-wrap {
            display: flex;
            align-items: center;
            gap: 12px;
            margin-top: 10px;
        }

        .od-price {
            text-align: right;
            min-width: 140px;
        }

        .od-unit {
            color: #111827;
        }

        .od-line {
            font-weight: 650;
            color: #e11d48;
            margin-top: 4px;
        }

        .od-summary {
            border-top: 1px solid #e5e7eb;
            padding-top: 14px;
            margin-top: 8px;
            text-align: right;
        }

        .od-summary p {
            margin: 6px 0;
        }

        .od-total {
            font-size: 20px;
            font-weight: 750;
            color: #e11d48;
        }

        .od-actions {
            display: flex;
            gap: 10px;
            justify-content: flex-end;
            margin-top: 12px;
        }

        .od-btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            height: 40px;
            padding: 0 18px;
            border-radius: 10px;
            font-size: 16px;
            font-weight: 600;
            text-decoration: none;
            border: 1px solid transparent;
            cursor: pointer;
            box-sizing: border-box;
            line-height: 1;
            appearance: none;
            -webkit-appearance: none;
            background: none;
        }

        .od-btn-back {
            border-color: #d1d5db;
            color: #111827;
            background: #fff;
        }

        .od-btn-cancel {
            border-color: #ef4444;
            color: #ef4444;
            background: #fff;
        }

        .od-btn-review {
            border-color: #2659F3;
            color: #2659F3;
            background: #fff;
        }

        .od-btn:hover {
            filter: brightness(0.97);
        }

        .page-offset {
            padding-top: 24px;
        }

        .order-meta .payment-line {
            grid-column: 2;
        }
        .order-expected-delivery {
            color: #b45309;
            font-weight: 600;
        }


        @media (min-width: 768px) {
            .page-offset {
                padding-top: 120px;
            }
        }

        @media (max-width: 768px) {
            .order-meta {
                grid-template-columns: 1fr;
            }

            .od-price {
                min-width: 120px;
            }
        }
        .od-btn-reorder {
            border-color: #16a34a;
            color: #fff;
            background: #16a34a;

        }

        .od-btn-reorder:hover {
            background: #15803d;
            cursor: pointer;
        }
    </style>
</head>

<body>
<%@ include file="Header.jsp" %>

<div class="page-offset">
    <div class="container order-detail-wrap">
        <div class="order-detail-card">

            <h2 class="order-detail-title">Chi tiết đơn hàng #DH0${order.id}</h2>

            <div class="order-meta">
                <p>
                    <b>Trạng thái:</b>
                    <c:choose>
                        <c:when test="${order.orderStatusId == 1}">
                            <span class="status-badge st-pending">${order.statusName}</span>
                        </c:when>
                        <c:when test="${order.orderStatusId == 2}">
                            <span class="status-badge st-shipping">${order.statusName}</span>
                        </c:when>
                        <c:when test="${order.orderStatusId == 3}">
                            <span class="status-badge st-completed">${order.statusName}</span>
                        </c:when>
                        <c:when test="${order.orderStatusId == 4}">
                            <span class="status-badge st-cancelled">${order.statusName}</span>
                        </c:when>
                        <c:otherwise>
                            <span class="status-badge">${order.statusName}</span>
                        </c:otherwise>
                    </c:choose>
                </p>

                <p>
                    <b>Ngày đặt:</b>
                    <fmt:formatDate value="${order.createAt}" pattern="dd/MM/yyyy HH:mm"/>
                </p>

                <p><b>Người nhận:</b> ${order.fullName}</p>
                <p><b>Số điện thoại:</b> ${order.phoneNumber}</p>
                <p><b>Địa chỉ:</b> ${order.address}</p>
                <p>
                    <b>Dự kiến nhận hàng:</b>
                    <span class="order-expected-delivery">
        <c:choose>
            <c:when test="${not empty order.expectedDeliveryDateText}">
                ${order.expectedDeliveryDateText}
            </c:when>
            <c:otherwise>
                Chưa có thông tin
            </c:otherwise>
        </c:choose>
    </span>
                </p>
                <p><b>Phương thức thanh toán:</b> ${order.paymentName}</p>

                <c:if test="${not empty order.note}">
                    <p><b>Ghi chú:</b> ${order.note}</p>
                </c:if>
            </div>

            <div class="order-items-title">Sản phẩm</div>

            <c:forEach var="item" items="${order.viewItems}">
                <div class="od-item">
                    <div class="od-thumb">
                        <c:set var="odThumbUrl" value="${item.thumbnail}"/>
                        <c:if test="${not empty odThumbUrl and not fn:startsWith(odThumbUrl,'http') and not fn:startsWith(odThumbUrl, pageContext.request.contextPath)}">
                            <c:choose>
                                <c:when test="${fn:startsWith(odThumbUrl,'/')}">
                                    <c:set var="odThumbUrl" value="${pageContext.request.contextPath}${odThumbUrl}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="odThumbUrl" value="${pageContext.request.contextPath}/${odThumbUrl}"/>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                        <img src="${odThumbUrl}" alt="${item.name}">
                    </div>

                    <div class="od-info">
                        <p class="od-name">${item.name}</p>
                        <p class="od-qty">Số lượng: ${item.quantity}</p>

                        <c:if test="${order.orderStatusId == 3 || order.orderStatusId == 4}">
                            <div class="od-review-wrap">

                                <c:if test="${order.orderStatusId == 3}">
                                    <a class="od-btn od-btn-review"
                                       href="${pageContext.request.contextPath}/Product_ReviewsController?id=${item.productId}">
                                        Đánh giá
                                    </a>
                                </c:if>

                                <form action="${pageContext.request.contextPath}/AddToCart"
                                      method="post"
                                      style="margin:0;">
                                    <input type="hidden" name="productId" value="${item.productId}">
                                    <input type="hidden" name="quantity" value="1">
                                    <input type="hidden" name="redirectTo" value="cart">
                                    <button type="submit" class="od-btn od-btn-reorder">
                                        Đặt lại
                                    </button>
                                </form>
                            </div>
                        </c:if>
                    </div>

                    <div class="od-price">
                        <div class="od-unit">
                            <fmt:formatNumber value="${item.price}" type="number"/>₫
                        </div>
                        <div class="od-line">
                            <fmt:formatNumber value="${item.lineTotal}" type="number"/>₫
                        </div>
                    </div>
                </div>
            </c:forEach>

            <div class="od-summary">
                <p>Giảm giá:
                    <b><fmt:formatNumber value="${order.discount}" type="number"/>₫</b>
                </p>
                <p>Phí vận chuyển:
                    <b><fmt:formatNumber value="${order.shippingFee}" type="number"/>₫</b>
                </p>
                <p>Tổng cộng:
                    <span class="od-total">
                        <fmt:formatNumber value="${order.totalPrice}" type="number"/>₫
                    </span>
                </p>
            </div>

            <div class="od-actions">
                <a class="od-btn od-btn-back"
                   href="${pageContext.request.contextPath}/order-history?status=all">Quay lại</a>

                <c:if test="${order.orderStatusId == 1}">
                    <a class="od-btn od-btn-cancel"
                       href="${pageContext.request.contextPath}/cancel-order?id=${order.id}">Hủy đơn</a>
                </c:if>
            </div>

        </div>
    </div>
</div>

<%@ include file="Footer.jsp" %>
</body>
</html>