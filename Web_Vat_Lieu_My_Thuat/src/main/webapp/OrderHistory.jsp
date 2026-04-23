<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lịch sử mua hàng</title>
    <link rel="stylesheet" href="./assets/css/style.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>

</head>

<body>
<style>
    .container {
        width: 1200px;
        margin: auto;
    }


    /* Begin section-trangchinh-danhmuc-sanpham */
    #section-trangchinh-danhmuc-sanpham {
        background-color: #DBE8FF;
    }

    #section-trangchinh-danhmuc-sanpham .content {

        padding: 20px 0;
        margin-bottom: 30px;
    }

    #section-trangchinh-danhmuc-sanpham .content a {
        text-decoration: none;
    }

    #section-trangchinh-danhmuc-sanpham .content a {
        color: black;
    }

    #section-trangchinh-danhmuc-sanpham .content a:nth-child(2) {
        color: #17479D;
    }

    /* End section-trangchinh-danhmuc-sanpham */
    .section-main-ca-nhan .container .thongtin-canhan {
        flex: 1;
        background-color: #F1F1F1;
    }

    .section-main-ca-nhan .container .thongtin-canhan form {
        padding: 20px;
        display: flex;
        flex-direction: column;
    }

    .section-main-ca-nhan .container .thongtin-canhan form h2 {
        color: #FB9F10;
    }

    .section-main-ca-nhan .container .thongtin-canhan form input {
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 5px;
        margin-bottom: 10px;
    }

    .section-main-ca-nhan .container .thongtin-canhan form input.button {
        padding: 10px 25px;
        color: white;
        background-color: #2659F3;
        border-radius: 20px;
        font-weight: 600;
        display: inline-block;
        margin-bottom: 10px;
        margin: 0 auto;
        cursor: pointer;
        font-size: 16px;
    }

    .section-main-ca-nhan .container .thongtin-canhan form input.button:hover {
        opacity: 0.9;
    }

    /* order history */
    .order-history {
        flex: 1;
        background: #fff;
        padding: 24px;
        border-radius: 12px;
        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
        border: 1px solid #e5e7eb;
        overflow: hidden;

    }


    .order-tabs {
        display: flex;
        gap: 10px;
        margin-bottom: 20px;
    }

    .order-tabs button {
        flex: 1;
        background: #f1f5ff;
        border: none;
        border-radius: 8px;
        padding: 10px;
        font-weight: 600;
        cursor: pointer;
        color: #2659F3;
        transition: all 0.3s;
    }

    .order-tabs button.active,
    .order-tabs button:hover {
        background: linear-gradient(135deg, #2659F3 0%, #1e40af 100%);
        color: white;
    }

    .order-card {
        border: 1px solid #e5e7eb;
        border-radius: 10px;
        margin-bottom: 25px;
        overflow: hidden;
    }

    .order-header {
        background: #f9fafb;
        padding: 12px 20px;
        display: flex;
        justify-content: space-between;
        border-bottom: 1px solid #e5e7eb;
        font-weight: 600;
    }

    .order-status {
        padding: 4px 12px;
        border-radius: 20px;
        font-size: 0.9rem;
    }

    .order-status.pending {
        background: #fff3cd;
        color: #856404;
    }

    .order-status.success {
        background: #d1fae5;
        color: #065f46;
    }

    .order-status.canceled {
        background: #fee2e2;
        color: #991b1b;
    }

    .order-items {
        padding: 15px 20px;
        display: flex;
        flex-direction: column;
        gap: 12px;
    }

    .order-item {
        display: flex;
        align-items: center;
        gap: 16px;
        padding: 12px 0;
    }

    .order-item img {
        width: 80px;
        height: 80px;
        border-radius: 8px;
        object-fit: cover;
        border: 1px solid #e5e7eb;
        flex-shrink: 0;
    }

    .order-info {
        flex: 1;
    }

    .order-name {
        font-weight: 600;
        margin: 0;
    }

    .order-quantity {
        color: #6b7280;
        margin: 4px 0 0;
    }

    .order-price {
        text-align: right;
    }

    .order-price p {
        color: #6b7280;
        margin: 0;
    }

    .order-price strong {
        color: #dc2626;
        font-size: 1rem;
    }

    .order-footer {
        background: #f9fafb;
        padding: 12px 20px;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .order-buttons .btn-review,
    .order-buttons .btn-cancel {
        display: inline-block;
        padding: 8px 16px;
        border-radius: 8px;
        cursor: pointer;
        transition: 0.3s;
        font-weight: 600;
        text-decoration: none;
    }

    .order-buttons {
        display: flex;
        gap: 8px;
    }


    .order-buttons .btn-review {
        border: 1px solid #2659F3;
        background: #ffffff;
        color: #2659F3;
    }

    .order-buttons .btn-review:hover {
        background: #eff6ff;
    }

    .order-buttons .btn-cancel {
        border: 1px solid #f43f3f;
        background: #ffffff;
        color: #f43f3f;
    }

    .order-buttons .btn-cancel:hover {
        background: #fee2e2;
    }


    .order-history {
        font-size: 16px;
    }


    .order-name {
        font-size: 17px;
        font-weight: 600;
        color: #111827;
    }


    .order-quantity,
    .order-price p {
        font-size: 15px;
    }


    .order-price strong {
        font-size: 17px;
        font-weight: 700;
        color: #dc2626;
    }


    .order-header {
        font-size: 16px;
    }

    .order-buttons button {
        font-size: 15px;
    }


    .total {
        font-size: 20px;
        color: #ef4444;
    }

    .link {
        display: flex;
        align-items: center;
        text-decoration: none;
        color: inherit;
    }

    .order-status.shipping {
        color: #1d4ed8;
        background: #dbeafe;
        border: 1px solid #93c5fd;
    }

    .order-status.cancelled {
        color: #dc2626;
        background: #fee2e2;
        border: 1px solid #fca5a5;
    }

    .order-delivery-label {
            font-weight: 600;
        font-size: 16px;
    }

    .order-delivery-date {
        color: #d97706;
        font-weight: 600;
        font-size: 16px;
    }
    .order-delivery-info {
        display: flex;
        align-items: center;
        gap: 10px;
        padding-left: 24px;
        padding-right: 24px;
        margin-bottom: 22px;
    }
    .cancel-modal {
        position: fixed;
        inset: 0;
        background: rgba(0, 0, 0, 0.35);
        display: none;
        align-items: center;
        justify-content: center;
        z-index: 9999;
    }

    .cancel-modal-content {
        width: 420px;
        max-width: 92%;
        background: #fff;
        border-radius: 14px;
        padding: 22px;
        box-shadow: 0 10px 30px rgba(0, 0, 0, 0.18);
    }

    .cancel-modal-content h3 {
        margin: 0 0 16px;
        font-size: 20px;
        color: #17479D;
    }

    .cancel-reason-select,
    .cancel-other-input {
        width: 100%;
        padding: 12px 14px;
        border: 1px solid #d9e2f2;
        border-radius: 10px;
        font-size: 15px;
        outline: none;
        box-sizing: border-box;
    }

    .cancel-other-wrap {
        margin-top: 12px;
        display: none;
    }

    .cancel-modal-actions {
        margin-top: 18px;
        display: flex;
        justify-content: flex-end;
        gap: 10px;
    }

    .cancel-modal-actions button {
        padding: 10px 16px;
        border-radius: 10px;
        border: none;
        cursor: pointer;
        font-weight: 600;
    }

    .cancel-modal-actions button[type="button"] {
        background: #e5e7eb;
        color: #111827;
    }

    .cancel-modal-actions button[type="submit"] {
        background: #dc2626;
        color: #fff;
    }
    /* ENd section-main-ca-nhan */
</style>
<%@ include file="Header.jsp" %>
<!-- Begin section info mithuat -->
<div id="section-info-mithuat">
    <div class="container">
    </div>
</div>
<!-- End section info mithuat -->
<!-- Begin section trang chinh - danh muc - san pham -->
<div id="section-trangchinh-danhmuc-sanpham">
    <div class="container">
        <div class="content">
            <a href>
                Trang chủ /
            </a>
            <a href>
                Tài khoản
            </a>
        </div>
    </div>
</div>
<!-- End section trang chinh - danh muc - san pham -->
<!-- Begin section main -->
<div class="section-main-ca-nhan">
    <div class="container">
        <div class="canhan">
            <c:set var="activePage" value="orders" scope="request"/>
            <%@ include file="AccountSideBar.jsp" %>
            <div class="order-history">
                <c:set var="st" value="${empty currentStatus ? 'all' : currentStatus}"/>

                <div class="order-tabs">
                    <a href="${pageContext.request.contextPath}/order-history?status=all">
                        <button class="${st == 'all' ? 'active' : ''}">Tất cả</button>
                    </a>

                    <a href="${pageContext.request.contextPath}/order-history?status=pending">
                        <button class="${st == 'pending' ? 'active' : ''}">Chờ xác nhận</button>
                    </a>
                    <a href="${pageContext.request.contextPath}/order-history?status=shipping">
                        <button class="${st == 'shipping' ? 'active' : ''}">Đang vận chuyển</button>
                    </a>
                    <a href="${pageContext.request.contextPath}/order-history?status=completed">
                        <button class="${st == 'completed' ? 'active' : ''}">Hoàn thành</button>
                    </a>
                    <a href="${pageContext.request.contextPath}/order-history?status=cancelled">
                        <button class="${st == 'cancelled' ? 'active' : ''}">Đã hủy</button>
                    </a>
                </div>
                <c:choose>
                    <c:when test="${empty orders}">
                        <p>Bạn chưa có đơn hàng nào.</p>
                    </c:when>

                    <c:otherwise>
                        <c:forEach var="order" items="${orders}">
                            <div class="order-card">
                                <div class="order-header">
                                    <span>Mã đơn: #DH${order.id}</span>

                                    <c:choose>
                                        <c:when test="${order.orderStatusId == 1}">
                                            <span class="order-status pending">${order.statusName}</span>
                                        </c:when>
                                        <c:when test="${order.orderStatusId == 3}">
                                            <span class="order-status success">${order.statusName}</span>
                                        </c:when>
                                        <c:when test="${order.orderStatusId == 4}">
                                            <span class="order-status cancelled">${order.statusName}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="order-status shipping">${order.statusName}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <div class="order-items">
                                    <c:forEach var="item" items="${order.viewItems}">
                                        <div class="order-item">
                                            <c:set var="hisThumbUrl" value="${item.thumbnail}"/>
                                            <c:if test="${not empty hisThumbUrl and not fn:startsWith(hisThumbUrl,'http') and not fn:startsWith(hisThumbUrl, pageContext.request.contextPath)}">
                                                <c:choose>
                                                    <c:when test="${fn:startsWith(hisThumbUrl,'/')}">
                                                        <c:set var="hisThumbUrl"
                                                               value="${pageContext.request.contextPath}${hisThumbUrl}"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="hisThumbUrl"
                                                               value="${pageContext.request.contextPath}/${hisThumbUrl}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>

                                            <img src="${hisThumbUrl}" alt="${item.name}">
                                            <div class="order-info">
                                                <p class="order-name">${item.name}</p>
                                                <p class="order-quantity">Số lượng: ${item.quantity}</p>
                                            </div>
                                            <div class="order-price">
                                                <p>
                                                    <fmt:formatNumber value="${item.price}" type="number"/>₫
                                                </p>
                                                <strong>
                                                    <fmt:formatNumber value="${item.lineTotal}" type="number"/>₫
                                                </strong>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                                <div class="order-delivery-info">
                                    <span class="order-delivery-label">Dự kiến nhận :</span>
                                    <span class="order-delivery-date">
                                <c:choose>
                                    <c:when test="${not empty order.expectedDeliveryDateText}">
                                        ${order.expectedDeliveryDateText}
                                    </c:when>
                                    <c:otherwise>
                                        Chưa có thông tin
                                    </c:otherwise>
                                </c:choose>
                            </span>
                                </div>

                                <div class="order-footer">
                        <span class="total">Tổng cộng:
                            <strong>
                                <fmt:formatNumber value="${order.totalPrice}" type="number"/>₫
                            </strong>
                        </span>

                                    <div class="order-buttons">
                                        <a href="${pageContext.request.contextPath}/order-detail?id=${order.id}"
                                           class="btn-review">Xem chi tiết</a>

                                        <c:if test="${order.orderStatusId == 1}">
                                            <button type="button"
                                                    class="btn-cancel"
                                                    onclick="openCancelModal(${order.id})">
                                                Hủy đơn
                                            </button>
                                        </c:if>

                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>


            </div>
        </div>
    </div>
</div>
</div>
</div>
<!-- End section main -->

<%@ include file="Footer.jsp" %>
<div id="cancelModal" class="cancel-modal" style="display:none;">
    <div class="cancel-modal-content">
        <h3>Chọn lý do hủy đơn</h3>

        <form action="${pageContext.request.contextPath}/cancel-order" method="post" onsubmit="return prepareCancelReason()">
            <input type="hidden" name="orderId" id="cancelOrderId">
            <input type="hidden" name="cancelReason" id="finalCancelReason">

            <select id="cancelReasonSelect" class="cancel-reason-select" required onchange="toggleOtherReason()">
                <option value="">-- Chọn lý do --</option>
                <option value="Tôi muốn thay đổi sản phẩm">Tôi muốn thay đổi sản phẩm</option>
                <option value="Tôi muốn thay đổi địa chỉ nhận hàng">Tôi muốn thay đổi địa chỉ nhận hàng</option>
                <option value="Tôi tìm được giá tốt hơn">Tôi tìm được giá tốt hơn</option>
                <option value="Tôi không còn nhu cầu mua nữa">Tôi không còn nhu cầu mua nữa</option>
                <option value="Đặt nhầm đơn hàng">Đặt nhầm đơn hàng</option>
                <option value="Lý do khác">Lý do khác</option>
            </select>

            <div id="otherReasonWrap" class="cancel-other-wrap">
                <input type="text"
                       id="otherReasonInput"
                       class="cancel-other-input"
                       placeholder="Nhập lý do khác">
            </div>

            <div class="cancel-modal-actions">
                <button type="button" onclick="closeCancelModal()">Đóng</button>
                <button type="submit">Xác nhận hủy</button>
            </div>
        </form>
    </div>
</div>
</body>
<script>
    function openCancelModal(orderId) {
        document.getElementById("cancelOrderId").value = orderId;
        document.getElementById("cancelModal").style.display = "flex";

        document.getElementById("cancelReasonSelect").value = "";
        document.getElementById("otherReasonInput").value = "";
        document.getElementById("finalCancelReason").value = "";
        document.getElementById("otherReasonWrap").style.display = "none";
    }

    function closeCancelModal() {
        document.getElementById("cancelModal").style.display = "none";
    }

    function toggleOtherReason() {
        const select = document.getElementById("cancelReasonSelect").value;
        const otherWrap = document.getElementById("otherReasonWrap");
        const otherInput = document.getElementById("otherReasonInput");

        if (select === "Lý do khác") {
            otherWrap.style.display = "block";
            otherInput.required = true;
        } else {
            otherWrap.style.display = "none";
            otherInput.required = false;
            otherInput.value = "";
        }
    }

    function prepareCancelReason() {
        const select = document.getElementById("cancelReasonSelect").value;
        const otherInput = document.getElementById("otherReasonInput").value.trim();
        const finalReason = document.getElementById("finalCancelReason");

        if (!select) {
            alert("Vui lòng chọn lý do hủy đơn");
            return false;
        }

        if (select === "Lý do khác") {
            if (!otherInput) {
                alert("Vui lòng nhập lý do khác");
                return false;
            }
            finalReason.value = otherInput;
        } else {
            finalReason.value = select;
        }

        return true;
    }

    window.onclick = function (event) {
        const modal = document.getElementById("cancelModal");
        if (event.target === modal) {
            closeCancelModal();
        }
    }
</script>
</html>