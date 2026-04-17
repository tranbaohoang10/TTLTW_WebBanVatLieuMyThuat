<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thanh toán</title>
    <link rel="stylesheet" href="./assets/css/style.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>

</head>
<style>

    .breadcrumb {
        background-color: #f0f4f8;
        padding: 1rem 2.5rem;
        font-size: 0.95rem;
        margin-top: 20px;
        margin-bottom: 20px;
        border-radius: 6px;
        width: fit-content;
        margin-left: 2rem;
    }

    .breadcrumb a {
        color: #17479d;
        text-decoration: none;
        transition: color 0.3s;
    }

    .breadcrumb a:hover {
        color: #0d2a5e;
        text-decoration: underline;
    }

    .breadcrumb span {
        color: #666;
        margin: 0 0.5rem;
    }

    .checkout-container {
        max-width: 1200px;
        margin: 1.5rem auto;
        padding: 0 1.5rem;
        display: grid;
        grid-template-columns: 1fr 400px;
        gap: 1.5rem;
        align-items: start;
    }

    /* infor - Shipping Info Card */
    .form-infor-checkout {
        background: #ffffff;
        border-radius: 8px;
        padding: 24px;
        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
        border: 1px solid #e5e7eb;
    }

    .form-infor-checkout h4 {
        font-size: 1.25rem;
        margin: 0 0 20px 0;
        font-weight: 600;
        color: #111827;
    }

    .form-infor-checkout p {
        color: #6b7280;
        font-size: 0.9rem;
    }

    .form-infor-checkout form {
        margin-top: 0;
    }

    /* Input groups */
    .input-group {
        margin-bottom: 14px;
    }

    .input-group label {
        display: block;
        font-size: 0.875rem;
        font-weight: 500;
        color: #374151;
        margin-bottom: 6px;
    }

    .form-input-item {
        width: 100%;
        padding: 10px 14px;
        border-radius: 6px;
        border: 1px solid #d1d5db;
        background: #ffffff;
        font-size: 0.9rem;
        color: #1f2937;
        box-sizing: border-box;
        transition: all 0.2s ease;
    }

    .form-input-item:focus {
        outline: none;
        border-color: #3b82f6;
        box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.08);
    }

    .form-input-item::placeholder {
        color: #9ca3af;
        font-size: 0.875rem;
    }

    /* Error/Success messages */
    #error_email,
    #error_phone {
        display: none;
        font-size: 0.875rem;
        margin-top: 6px;
        font-weight: 500;
    }

    /* Row layout for email and phone */
    .form-infor-checkout .row {
        display: flex;
        gap: 16px;
        margin-left: 0;
        margin-right: 0;
    }

    .form-infor-checkout .col-md-6 {
        flex: 1;
        padding: 0;
    }

    /* Note section */
    .note {
        margin-top: 16px;
        margin-bottom: 16px;
    }

    .note label {
        display: block;
        font-weight: 600;
        color: #374151;
        margin-bottom: 8px;
        background: #f3f4f6;
        padding: 8px 12px;
        border-radius: 6px;
    }

    .form-control {
        width: 100%;
        min-height: 120px;
        padding: 12px;
        border-radius: 8px;
        border: 1px solid #d1d5db;
        background: #ffffff;
        resize: vertical;
        box-sizing: border-box;
        font-family: inherit;
        font-size: 0.95rem;
    }

    .form-control::placeholder {
        color: #9ca3af;
    }



    .export-bill input[type="checkbox"] {
        width: 18px;
        height: 18px;
        cursor: pointer;
    }



    /* Payment Method */
    .payment-method {
        margin-top: 20px;
        margin-bottom: 20px;
    }

    .payment-title {
        font-size: 1.25rem;
        font-weight: 600;
        color: #111827;
        margin-bottom: 12px;
    }

    .payment-options {
        display: flex;
        flex-direction: column;
        gap: 12px;
    }

    .payment-option {
        display: flex;
        align-items: center;
        padding: 14px;
        border: 2px solid #e5e7eb;
        border-radius: 8px;
        cursor: pointer;
        transition: all 0.2s ease;
        position: relative;
    }

    .payment-option:hover {
        border-color: #2659F3;
        background-color: #f0f9ff;
    }

    .payment-option input[type="radio"] {
        width: 18px;
        height: 18px;
        cursor: pointer;
        margin-right: 12px;
        accent-color: #2659F3;
    }

    .payment-option input[type="radio"]:checked ~ .payment-content {
        color: #2659F3;
    }

    .payment-option:has(input[type="radio"]:checked) {
        border-color: #2659F3;
        background-color: #f0f9ff;
    }

    .payment-content {
        display: flex;
        align-items: center;
        gap: 12px;
        flex: 1;
    }

    .payment-content img {
        width: 40px;
        height: 40px;
        object-fit: contain;
    }

    .payment-content span {
        font-size: 0.95rem;
        color: #374151;
        font-weight: 500;
    }

    /* Continue button - Orange style */
    .btn-continue {
        display: inline-block;
        background: #2659F3;
        color: white;
        padding: 14px 28px;
        border-radius: 8px;
        border: none;
        font-weight: 700;
        font-size: 1rem;
        cursor: pointer;
        margin-top: 8px;
        transition: transform 0.2s, box-shadow 0.2s;
        width: 100%;
        text-align: center;
    }

    .btn-continue:hover {
        transform: translateY(-2px);
        box-shadow: 0 8px 16px #17479d;
    }

    .total-amount {
        color: #ef4444;
        font-size: 25px;
        font-weight: 700;
    }

    /* Order Summary - Right Column */
    .order-summary {
        background: #ffffff;
        border-radius: 8px;
        padding: 24px;
        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
        border: 1px solid #e5e7eb;
        height: fit-content;
    }

    .cart-title {
        font-size: 1.125rem;
        color: #111827;
        margin: 0 0 20px 0;
        font-weight: 600;
        display: block;
    }

    /* Cart Items */

    .cart-items {
        margin-bottom: 20px;
        border-bottom: 1px solid #e5e7eb;
        padding-bottom: 16px;
    }

    .cart-item {
        display: flex;
        gap: 15px;
        padding: 15px 0;
        align-items: center;
        border-bottom: 1px solid #f3f4f6;
    }

    .cart-item:last-child {
        border-bottom: none;
    }


    .item-image {
        width: 80px;
        height: 80px;
        border-radius: 8px;
        overflow: hidden;
        position: relative;
        flex-shrink: 0;
        border: 1px solid #e5e7eb;
    }

    .item-image img {
        width: 100%;
        height: 100%;
        object-fit: cover;
    }


    .item-quantity {
        position: absolute;
        top: -8px;
        left: -8px;
        background-color: #fdecec;
        color: #ef4444;
        width: 28px;
        height: 28px;
        border-radius: 6px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 14px;
        font-weight: 700;
        border: 1px solid #fbcaca;
        box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
    }


    .item-details {
        flex: 1;
    }

    .item-name {
        font-size: 0.95rem;
        font-weight: 500;
        color: #111827;
        margin: 0;
        line-height: 1.3;
        max-width: 250px;
        text-align: left;
    }

    .item-price {
        font-size: 1rem;
        font-weight: 700;
        color: #ef4444;
        white-space: nowrap;
    }


    /* Discount Section */
    .discount-section {
        margin-bottom: 20px;
        padding-bottom: 16px;
        border-bottom: 1px solid #e5e7eb;
    }

    .discount-section label {
        display: block;
        font-size: 0.875rem;
        color: #6b7280;
        margin-bottom: 8px;
        font-weight: 500;
    }

    .discount-input-group {
        display: flex;
        gap: 8px;
    }

    .discount-input-group input {
        flex: 1;
        padding: 10px 14px;
        border: 1px solid #d1d5db;
        border-radius: 6px;
        font-size: 0.875rem;
        transition: all 0.2s ease;
    }

    .discount-input-group input:focus {
        outline: none;
        border-color: #2659F3;
        box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.08);
    }

    .discount-input-group input::placeholder {
        color: #9ca3af;
    }

    .btn-apply {
        padding: 10px 20px;
        background: #2659F3;
        color: white;
        border: none;
        border-radius: 6px;
        font-size: 0.875rem;
        font-weight: 600;
        cursor: pointer;
        transition: all 0.2s ease;
        white-space: nowrap;
    }

    .btn-apply:hover {
        background: #2563eb;
        transform: translateY(-1px);
        box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
    }

    .btn-apply:active {
        transform: translateY(0);
    }

    /* Price Summary */
    .price-summary {
        display: flex;
        flex-direction: column;
        gap: 10px;
    }

    .price-row {
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-size: 1.125rem;
        color: #6b7280;
    }

    .price-row.total {
        padding-top: 12px;
        border-top: 2px solid #e5e7eb;
        margin-top: 8px;
        font-size: 1.125rem;
        color: #111827;
        font-weight: 700;
    }

    .price-row.total  {
        color: #ef4444;
        font-size: 1.25rem;
    }



    a {
        text-decoration: none;
    }

    /* Responsive Design */
    @media (max-width: 992px) {
        .checkout-container {
            grid-template-columns: 1fr;
            gap: 1.5rem;
        }

        .order-summary {
            position: static;
        }
    }

    @media (max-width: 576px) {
        .checkout-container {
            padding: 0 1rem;
        }

        .form-infor-checkout,
        .order-summary {
            padding: 20px;
        }

        .item-image {
            width: 60px;
            height: 60px;
        }

        .cart-title {
            font-size: 18px;
        }
    }

    .des-heading a {
        color: red;
    }



    .order-summary {
        border-left: 1px solid #e5e7eb;
        padding-left: 24px;
    }

    /* Responsive */
    @media (max-width: 992px) {
        .form-infor-checkout .row {
            flex-direction: column;
            gap: 0;
        }

        .form-infor-checkout {
            padding: 20px;
        }
    }

    #voucherMsg {
        margin-top: 6px;
        font-size: 14px;
    }

    #voucherMsg.success {
        color: #16a34a;
    }

    #voucherMsg.error {
        color: #dc2626;
    }

    #voucherMsg.loading {
        color: #6b7280;
    }
    .delivery-estimate-row {
        padding: 10px 12px;
        background-color: #fff8e8;
        border: 1px solid #f2d38b;
        border-radius: 6px;
        gap: 12px;
    }

    .delivery-estimate-label {
        color: #555;
        font-weight: 500;
    }

    .delivery-estimate-value {
        color: #d48806;
        font-weight: 600;
        text-align: right;
    }
    #shippingAreaError {
        color: red;
    }


</style>

<body>
<%@ include file="Header.jsp" %>

<div class="container">
    <div class="main">
        <nav>
            <div class="breadcrumb">
                <a href="${pageContext.request.contextPath}/cart">Giỏ hàng</a>
                <span>/</span>
                <span>Thông tin giao hàng</span>
            </div>

            <form id="checkoutForm" action="${pageContext.request.contextPath}/place-order" method="post">
                <div class="checkout-container">

                    <!-- LEFT -->
                    <div class="form-infor-checkout">
                        <h4>Thông tin giao hàng</h4>

                        <div class="input-group">
                            <label for="fullname">Họ và tên</label>
                            <input type="text" class="form-input-item" id="fullname" name="fullName"
                                   placeholder="Họ và tên"
                                   value="${sessionScope.currentUser.fullName}" required>
                        </div>

                        <div class="row">
                            <div class="input-group col-md-6">
                                <label for="email">Email</label>
                                <input type="email" class="form-input-item" id="email" name="email"
                                       placeholder="Email"
                                       value="${sessionScope.currentUser.email}" required>
                                <span id="error_email"></span>
                            </div>
                            <div class="input-group col-md-6">
                                <label for="phone">Số điện thoại</label>
                                <input type="text" class="form-input-item" id="phone" name="phone"
                                       placeholder="Số điện thoại"
                                       value="${sessionScope.currentUser.phoneNumber}" required>
                                <span id="error_phone"></span>
                            </div>
                        </div>

                        <!-- nếu bạn cần submit về server thì thêm name -->
                        <div class="input-group">
                            <label for="provinceSelect">Tỉnh/Thành</label>
                            <select id="provinceSelect" name="provinceId" class="form-input-item">
                                <option value="">-- Chọn tỉnh/thành --</option>
                            </select>
                        </div>

                        <div class="input-group">
                            <label for="districtSelect">Quận/Huyện</label>
                            <select id="districtSelect" name="districtId" class="form-input-item" disabled>
                                <option value="">-- Chọn quận/huyện --</option>
                            </select>
                        </div>

                        <div class="input-group">
                            <label for="wardSelect">Phường/Xã</label>
                            <select id="wardSelect" name="wardCode" class="form-input-item" disabled>
                                <option value="">-- Chọn phường/xã --</option>
                            </select>
                        </div>
                        <div id="shippingAreaError" class="text-danger mt-2"></div>

                        <div class="input-group">
                            <label for="address">Địa chỉ</label>
                            <input type="text" class="form-input-item" id="address" name="address"
                                   placeholder="Địa chỉ"
                                   value="${sessionScope.currentUser.address}" required>
                        </div>

                        <div class="note">
                            <label for="content-notes">Ghi chú đơn hàng</label>
                            <textarea class="form-control" id="content-notes" name="note" rows="5"
                                      placeholder="Ghi chú đơn hàng..."></textarea>
                        </div>

                        <div class="payment-method">
                            <h4 class="payment-title">Phương thức thanh toán</h4>
                            <div class="payment-options">

                                <label class="payment-option">
                                    <input type="radio" name="payment" value="COD" checked>
                                    <div class="payment-content">
                                        <img src="${pageContext.request.contextPath}/assets/images/logo/cod.svg" alt="COD">
                                        <span>Thanh toán khi nhận hàng</span>
                                    </div>
                                </label>

                                <label class="payment-option">
                                    <input type="radio" name="payment" value="VNPAY">
                                    <div class="payment-content">
                                        <img src="${pageContext.request.contextPath}/assets/images/logo/OIP.webp" alt="VNPay">
                                        <span>Thanh toán qua VNPay</span>
                                    </div>
                                </label>

                            </div>
                        </div>
                    </div>

                    <!-- RIGHT -->
                    <div class="order-summary">
                        <h4 class="cart-title">Giỏ hàng</h4>

                        <div class="cart-items">
                            <c:forEach var="item" items="${sessionScope.cartTemp.carts.values()}">
                                <div class="cart-item">
                                    <div class="item-image">
                                        <c:set var="ckThumbUrl" value="${item.thumbnail}" />
                                        <c:if test="${not empty ckThumbUrl and not fn:startsWith(ckThumbUrl,'http') and not fn:startsWith(ckThumbUrl, pageContext.request.contextPath)}">
                                            <c:choose>
                                                <c:when test="${fn:startsWith(ckThumbUrl,'/')}">
                                                    <c:set var="ckThumbUrl" value="${pageContext.request.contextPath}${ckThumbUrl}" />
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="ckThumbUrl" value="${pageContext.request.contextPath}/${ckThumbUrl}" />
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                                                                    <img src="${ckThumbUrl}" alt="${item.name}">
                                        <span class="item-quantity">${item.quantity}</span>
                                    </div>

                                    <div class="item-details">
                                        <h5 class="item-name">${item.name}</h5>
                                        <p class="item-price">
                                            <fmt:formatNumber value="${item.priceAfterDiscount * item.quantity}" type="number"/>₫
                                        </p>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <div class="discount-section">
                            <div class="discount-input-group">
                                <input id="voucherCode" type="text" placeholder="Mã giảm giá">
                                <button id="btnApplyVoucher" type="button" class="btn-apply">Sử dụng</button>
                            </div>
                            <div id="voucherMsg" style="margin-top:6px; font-size:14px;"></div>
                        </div>
                        <input type="hidden" id="baseTotalToPay"
                               value="${Math.round(sessionScope.cartTemp.totalProductPrice - sessionScope.cartTemp.discount)}">
                        <input type="hidden" name="provinceId" id="provinceIdInput">
                        <input type="hidden" name="districtId" id="districtIdInput">
                        <input type="hidden" name="wardCode" id="wardCodeInput">
                        <div class="price-summary">
                            <div class="price-row">
                                <span>Tạm tính</span>
                                <span>
                                    <fmt:formatNumber value="${sessionScope.cartTemp.totalProductPrice}" type="number"/>₫
                                </span>
                            </div>
                            <div class="price-row">
                                <span>Giảm giá sản phẩm</span>
                                <span id="productDiscountValue">
                                    <fmt:formatNumber value="${sessionScope.cartTemp.productDiscount}" type="number"/>₫
                                </span>
                            </div>
                            <div class="price-row">
                                <span>Giảm phí vận chuyển</span>
                                <span id="shippingDiscountValue">
                                    <fmt:formatNumber value="${sessionScope.cartTemp.shippingDiscount}" type="number"/>₫
                                </span>
                            </div>

                            <div class="price-row">
                                <span>Phí vận chuyển</span>
                                <span id="shippingFeeText">
                                    <fmt:formatNumber value="${sessionScope.cartTemp.fee}" type="number"/>₫
                                </span>
                            </div>
                            <div class="price-row delivery-estimate-row">
                                <span class="delivery-estimate-label">Dự kiến nhận hàng</span>
                                <span id="expectedDeliveryText">Chưa có</span>
                            </div>

                            <div class="price-row total">
                                <span>Tổng cộng</span>
                                <span id="totalPayText" class="total-amount">
                                    <fmt:formatNumber value="${sessionScope.cartTemp.totalPriceToPay}" type="number"/>₫
                                </span>
                            </div>
                        </div>

                        <button type="submit" class="btn-continue">Thanh toán ngay</button>
                    </div>

                </div>
            </form>

        </nav>
    </div>
</div>

<%@ include file="Footer.jsp" %>

<script>
    (() => {
        const CTX = "<%=request.getContextPath()%>";

        const $ = (id) => document.getElementById(id);

        function vnd(n) {
            n = Math.round(Number(n) || 0);
            return n.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "₫";
        }
        function moneyToNumber(text) {
            return Number(String(text || "").replace(/[^\d]/g, "")) || 0;
        }

        function clearShippingInformation() {
            if (shippingFeeText) {
                shippingFeeText.textContent = vnd(0);
            }

            if (totalPayText && baseTotalToPay) {
                totalPayText.textContent = vnd(baseTotalToPay.value);
            }

            if (expectedDeliveryText) {
                expectedDeliveryText.textContent = "Vui lòng chọn khu vực giao hàng";
            }
        }

        const voucherCodeEl = $("voucherCode");
        const btnApplyVoucher = $("btnApplyVoucher");
        const voucherMsg = $("voucherMsg");
        const discountValue = $("discountValue");
        const totalPayText = $("totalPayText");
        const baseTotalToPay = $("baseTotalToPay");


        const provinceSelect = $("provinceSelect");
        const districtSelect = $("districtSelect");
        const wardSelect = $("wardSelect");
        const shippingFeeText = $("shippingFeeText");
        const expectedDeliveryText = $("expectedDeliveryText");

        const provinceIdInput = $("provinceIdInput");
        const districtIdInput = $("districtIdInput");
        const wardCodeInput = $("wardCodeInput");
        const shippingAreaError = $("shippingAreaError");
        const checkoutForm = $("checkoutForm");

        if (!provinceSelect || !districtSelect || !wardSelect) {
            console.warn("Missing select elements for GHN");
            return;
        }

        if (btnApplyVoucher) {
            btnApplyVoucher.addEventListener("click", async () => {
                try {
                    const code = (voucherCodeEl?.value || "").trim();
                    voucherMsg.textContent = "Đang áp dụng...";

                    const body = new URLSearchParams();
                    body.append("code", code);

                    const res = await fetch(CTX + "/voucher/apply", {
                        method: "POST",
                        headers: {"Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"},
                        body: body.toString(),
                        credentials: "same-origin"
                    });
                    voucherMsg.classList.remove("success", "error", "loading");
                    voucherMsg.classList.add("loading");
                    voucherMsg.textContent = "Đang áp dụng...";
                    const json = await res.json();
                    voucherMsg.classList.remove("success", "error", "loading");


                    if (!json.success) {
                        voucherMsg.classList.add("error");
                        voucherMsg.textContent = json.message || "Áp dụng thất bại";
                        return;
                    }
                    voucherMsg.classList.add("success");
                    voucherMsg.textContent = json.message || "Áp dụng thành công";
                    const productDiscountValue = document.getElementById("productDiscountValue");
                    const shippingDiscountValue = document.getElementById("shippingDiscountValue");

                    if (productDiscountValue) {
                        productDiscountValue.textContent = vnd(json.productDiscount || 0);
                    }

                    if (shippingDiscountValue) {
                        shippingDiscountValue.textContent = vnd(json.shippingDiscount || 0);
                    }

                    if (shippingFeeText) {
                        if (json.finalFee != null) {
                            shippingFeeText.textContent = vnd(json.finalFee);
                        } else {
                            shippingFeeText.textContent = vnd(json.fee || 0);
                        }
                    }

                    if (totalPayText) {
                        totalPayText.textContent = vnd(json.totalToPay || 0);
                    }
                } catch (e) {
                    console.error("Voucher error:", e);
                    voucherMsg.textContent = "Lỗi áp dụng voucher";
                }
            });
        }
        function updateShippingAreaInputs() {
            if (provinceIdInput) {
                provinceIdInput.value = provinceSelect.value || "";
            }
            if (districtIdInput) {
                districtIdInput.value = districtSelect.value || "";
            }
            if (wardCodeInput) {
                wardCodeInput.value = wardSelect.value || "";
            }
        }

        async function loadProvinces() {
            try {
                const res = await fetch(CTX + "/ghn/provinces", { credentials: "same-origin" });
                if (!res.ok) throw new Error("HTTP " + res.status);

                const json = await res.json();
                const list = json.data || [];

                provinceSelect.innerHTML = `<option value="">-- Chọn tỉnh/thành --</option>`;
                list.forEach(p => {
                    const opt = document.createElement("option");
                    opt.value = p.ProvinceID;
                    opt.textContent = p.ProvinceName;
                    provinceSelect.appendChild(opt);
                });

                console.log("Loaded provinces:", list.length);
            } catch (e) {
                console.error("Load provinces failed:", e);
                provinceSelect.innerHTML = `<option value="">Không tải được tỉnh/thành</option>`;
            }
        }

        async function loadDistricts(provinceId) {
            try {
                districtSelect.disabled = true;
                wardSelect.disabled = true;
                districtSelect.innerHTML = `<option value="">-- Chọn quận/huyện --</option>`;
                wardSelect.innerHTML = `<option value="">-- Chọn phường/xã --</option>`;

                const res = await fetch(CTX + "/ghn/districts?provinceId=" + encodeURIComponent(provinceId),
                    { credentials: "same-origin" }
                );
                if (!res.ok) throw new Error("HTTP " + res.status);

                const json = await res.json();
                const list = json.data || [];

                list.forEach(d => {
                    const opt = document.createElement("option");
                    opt.value = d.DistrictID;
                    opt.textContent = d.DistrictName;
                    districtSelect.appendChild(opt);
                });

                districtSelect.disabled = false;
                console.log("Loaded districts:", list.length);
            } catch (e) {
                console.error("Load districts failed:", e);
                districtSelect.innerHTML = `<option value="">Không tải được quận/huyện</option>`;
            }
        }

        async function loadWards(districtId) {
            try {
                wardSelect.disabled = true;
                wardSelect.innerHTML = `<option value="">-- Chọn phường/xã --</option>`;

                const res = await fetch(CTX + "/ghn/wards?districtId=" + encodeURIComponent(districtId),
                    { credentials: "same-origin" }
                );
                if (!res.ok) throw new Error("HTTP " + res.status);

                const json = await res.json();
                const list = json.data || [];

                list.forEach(w => {
                    const opt = document.createElement("option");
                    opt.value = w.WardCode;
                    opt.textContent = w.WardName;
                    wardSelect.appendChild(opt);
                });

                wardSelect.disabled = false;
                console.log("Loaded wards:", list.length);
            } catch (e) {
                console.error("Load wards failed:", e);
                wardSelect.innerHTML = `<option value="">Không tải được phường/xã</option>`;
            }
        }

        async function calcFee(districtId, wardCode) {
            try {
                const body = new URLSearchParams();
                body.append("districtId", districtId);
                body.append("wardCode", wardCode);

                const res = await fetch(CTX + "/ghn/fee", {
                    method: "POST",
                    headers: {"Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"},
                    body: body.toString(),
                    credentials: "same-origin"
                });

                if (!res.ok) throw new Error("HTTP " + res.status);

                const json = await res.json();
                if (!json.success) {
                    clearShippingInformation()
                    return;
                }

                if (shippingFeeText) shippingFeeText.textContent = vnd(json.fee);
                if (totalPayText) totalPayText.textContent = vnd(json.totalToPay);
                if (expectedDeliveryText) {
                    if (json.expectedDeliveryDateText && json.expectedDeliveryDateText.trim() !== "") {
                        expectedDeliveryText.textContent = json.expectedDeliveryDateText;
                    } else {
                        expectedDeliveryText.textContent = "Chưa tính được ngày nhận hàng";
                    }
                }
            } catch (e) {
                console.error("Calc fee failed:", e);
            }
        }

        provinceSelect.addEventListener("change", () => {
            const pid = provinceSelect.value;
            districtSelect.disabled = true;
            wardSelect.disabled = true;
            districtSelect.innerHTML = `<option value="">-- Chọn quận/huyện --</option>`;
            wardSelect.innerHTML = `<option value="">-- Chọn phường/xã --</option>`;
            clearShippingInformation();
            updateShippingAreaInputs();
            if (!pid) return;
            loadDistricts(pid);
        });

        districtSelect.addEventListener("change", () => {
            const did = districtSelect.value;
            if (!did) return;
            wardSelect.disabled = true;
            wardSelect.innerHTML = `<option value="">-- Chọn phường/xã --</option>`;
            clearShippingInformation();
            updateShippingAreaInputs();
            loadWards(did);
        });

        wardSelect.addEventListener("change", () => {
            const did = districtSelect.value;
            const wcode = wardSelect.value;
            updateShippingAreaInputs();
            if (shippingAreaError) {
                shippingAreaError.textContent = "";
            }

            if (!did || !wcode) {
                clearShippingInformation();
                return;
            }
            calcFee(did, wcode);
        });
        if (checkoutForm) {
            checkoutForm.addEventListener("submit", (event) => {
                updateShippingAreaInputs();

                const provinceId = provinceSelect.value;
                const districtId = districtSelect.value;
                const wardCode = wardSelect.value;

                if (!provinceId || !districtId || !wardCode) {
                    event.preventDefault();

                    if (shippingAreaError) {
                        shippingAreaError.textContent = "Vui lòng chọn đầy đủ khu vực giao hàng.";
                    } else {
                        alert("Vui lòng chọn đầy đủ khu vực giao hàng.");
                    }

                    return;
                }
                if (shippingAreaError) {
                    shippingAreaError.textContent = "";
                }
            });
        }

        loadProvinces();
    })();


</script>

</body>

</html>
