<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giỏ hàng</title>
    <link rel="stylesheet" href="./assets/css/style.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
</head>
<style>
    .container {
        width: 1200px;
        margin: auto;
    }

    /* begin style shopping cart */

    .main {
        margin-top: 110px;
    }


    .container {
        width: 1200px;
        margin: auto;
    }

    .breadcrumb {
        /* background-color: #f3f4f6; */
        background: #DBE8FF;
        color: #17479d;
        font-weight: 500;
        font-size: 16px;
        padding: 12px 40px;
        border-bottom: 1px solid #e5e7eb;
        margin-top: 100px;
    }

    .grid {
        display: grid;
        grid-template-columns: 2fr 1fr;
        gap: 20px;
    }

    .card {
        background: #fff;
        border-radius: 12px;
        box-shadow: 0 2px 16px rgba(0, 0, 0, .08);
        border: 1px solid #e5e7eb;
    }

    .card-body {
        padding: 22px 26px;
    }

    .card-title {
        margin: 0 0 6px;
        font-size: 28px;
        font-weight: 700;
        color: #111827;
    }

    .subtle {
        color: #282b30;
    }

    .divider {
        height: 1px;
        background: #969aa3;
        margin: 16px 0;
    }

    .cart-item-card {
        background: #fff;
        border: 1px solid #e5e7eb;
        border-radius: 10px;
        box-shadow: 0 1px 6px rgba(0, 0, 0, 0.05);
        padding: 16px;
        margin-bottom: 16px;
        transition: box-shadow 0.2s ease;
    }

    .cart-list .item {
        padding: 8px 0;
    }

    .item-grid {
        display: grid;
        grid-template-columns: 80px 1fr auto auto;
        align-items: center;
        gap: 16px;
    }

    .product-info {
        flex: 1;
        min-width: 0;
    }

    .product-info {
        flex: 1;
        min-width: 0;
    }

    .thumb {
        width: 74px;
        height: 74px;
        border-radius: 8px;
        background: #f5f5f5;
        display: flex;
        align-items: center;
        justify-content: center;
        overflow: hidden;
        border: 1px solid #eee;
    }

    .thumb img {
        max-width: 100%;
        max-height: 100%;
        object-fit: cover;
    }

    .meta-title {
        font-weight: 700;
        color: #111827;
        margin-bottom: 4px;
        line-height: 1.35;
    }

    .muted {
        color: #6b7280;
        font-size: 14px;
    }

    .qty {
        display: inline-flex;
        align-items: center;
        background: #fff;
        border: 1px solid #e5e7eb;
        border-radius: 8px;
        overflow: hidden;
    }

    .qty button {
        width: 34px;
        height: 34px;
        border: none;
        background: #fff;
        font-size: 18px;
        cursor: pointer;
    }

    .qty button:hover {
        background: #f3f4f6;
    }

    .qty input[type="number"] {
        width: 50px;
        height: 34px;
        text-align: center;
        border: none;
        font-weight: 600;
        outline: none;
    }

    .qty button:first-child {
        border-right: 1px solid #e5e7eb;
    }

    .qty button:last-child {
        border-left: 1px solid #e5e7eb;
    }

    .qty-btn {
        width: 32px;
        height: 32px;
        border: none;
        background: #fff;
        border: 1px solid #e5e7eb;
        font-size: 16px;
        cursor: pointer;
        transition: all 0.2s;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .qty-btn:hover {
        background: #f3f4f6;
        border-color: #2659F3;
        color: #2659F3;
    }

    .price {
        font-weight: 600;
        color: #ef4444;
        font-size: 18px;
        min-width: 120px;
        text-align: right;
    }

    .qty-wrap {
        position: relative;
        display: inline-block;
        margin-left: auto;
        display: flex;
        justify-content: space-between;
        align-items: flex-end;
    }

    .summary .total {
        font-size: 22px;
        font-weight: 800;
        color: #ef4444;
    }

    .btn {
        width: 100%;
        border: none;
        border-radius: 8px;
        padding: 12px 14px;
        letter-spacing: .3px;
        background: #2659F3;
        color: #fff;
        cursor: pointer;
        margin-top: 8px;
        font-size: 1rem;
        font-weight: 600;
    }

    .card,
    .summary .card {
        border-radius: 12px;
    }

    .summary {
        height: fit-content;
    }

    .tinh-tien {
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-weight: 600;
        font-size: 18px;
        margin: 10px 0;
    }

    .tinh-tien p {
        margin: 0;
        color: #111827;
    }

    .tinh-tien .thanh-tien {
        color: #ef4444;
        font-weight: 700;
        font-size: 18px;
    }

    .qty-wrap {
        position: relative;
        display: inline-block;
        margin-left: auto;
    }

    .Xoa-sp {
        position: absolute;
        top: 0;
        right: 0;
        transform: translate(50%, -50%);
        width: 28px;
        height: 28px;
        border: none;
        border-radius: 50%;
        background: #111;
        color: #fff;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        box-shadow: 0 2px 8px rgba(0, 0, 0, .15);
        z-index: 2;
    }

    .qty-btn {
        width: 32px;
        height: 32px;
        border: none;
        background: #fff;
        border: 1px solid #e5e7eb;
        font-size: 16px;
        cursor: pointer;
        transition: all 0.2s;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .qty-btn:hover {
        background: #f3f4f6;
        border-color: #2659F3;
        color: #2659F3;
    }

    /* từ sửa sau */
    html,
    body {
        height: 100%;
        margin: 0;
        padding: 0;
    }

    body {
        display: flex;
        flex-direction: column;
        min-height: 100vh;
    }

    main {
        flex: 1;
    }

    /* end style shopping cart */
</style>

<body>
<%@ include file="Header.jsp" %>
<!-- Breadcrumb -->
<div class="breadcrumb">Trang chủ / Giỏ hàng</div>
<div class="container">
    <div class="grid">
        <!-- LEFT: CART -->
        <div class="card">
            <div class="card-body">
                <h2 class="card-title">Giỏ hàng của bạn</h2>
                <p class="subtle">
                    Bạn đang có <span
                        id="cart-total-quantity">${totalQuantityProducts}</span>
                    sản phẩm trong giỏ hàng
                </p>
                <div class="divider"></div>

                <form
                        action="${pageContext.request.contextPath}/AddToCart?action=update"
                        method="post">
                    <div class="cart-list">
                        <c:choose>
                            <c:when test="${empty cartItems}">
                                <p>Giỏ hàng của bạn đang trống.</p>
                            </c:when>

                            <c:otherwise>
                                <c:forEach var="item"
                                           items="${cartItems}">
                                    <div class="cart-item-card">
                                        <div class="item">
                                            <div class="item-grid">

                                                <div class="thumb">
                                                    <c:set var="cartThumbUrl" value="${item.thumbnail}" />
                                                    <c:if test="${not empty cartThumbUrl and not fn:startsWith(cartThumbUrl,'http') and not fn:startsWith(cartThumbUrl, pageContext.request.contextPath)}">
                                                        <c:choose>
                                                            <c:when test="${fn:startsWith(cartThumbUrl,'/')}">
                                                                <c:set var="cartThumbUrl" value="${pageContext.request.contextPath}${cartThumbUrl}" />
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:set var="cartThumbUrl" value="${pageContext.request.contextPath}/${cartThumbUrl}" />
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:if>

                                                    <img src="${cartThumbUrl}" alt="${item.name}"/>

                                                </div>

                                                <div
                                                        class="product-info">
                                                    <div
                                                            class="meta-title">
                                                            ${item.name}
                                                    </div>
                                                    <div class="muted">
                                                        Mã SP:
                                                            ${item.productId}
                                                    </div>
                                                </div>

                                                <div class="price">
                                                            <span
                                                                    class="price-value"
                                                                    id="subtotal-${item.productId}">
                                                                <fmt:formatNumber
                                                                        value="${item.priceAfterDiscount * item.quantity}"
                                                                        type="number"/>₫
                                                            </span>
                                                </div>

                                                <div class="qty-wrap">
                                                    <!-- nút Xóa -->
                                                    <a class="Xoa-sp"
                                                       href="${pageContext.request.contextPath}/AddToCart?action=remove&productId=${item.productId}"
                                                       aria-label="Xóa sản phẩm">
                                                        <i
                                                                class="fa-solid fa-xmark"></i>
                                                    </a>

                                                    <input type="hidden"
                                                           name="productId"
                                                           value="${item.productId}"/>

                                                    <div class="qty">
                                                        <button
                                                                type="button"
                                                                class="qty-btn"
                                                                onclick="decreaseQty(${item.productId})">
                                                            <i
                                                                    class="fa-solid fa-minus"></i>
                                                        </button>

                                                        <input
                                                                type="number"
                                                                name="quantity"
                                                                value="${item.quantity}"
                                                                min="1"
                                                                class="qty-input"
                                                                id="qty-${item.productId}"
                                                                data-product-id="${item.productId}"
                                                                readonly/>

                                                        <button
                                                                type="button"
                                                                class="qty-btn"
                                                                onclick="increaseQty(${item.productId})">
                                                            <i
                                                                    class="fa-solid fa-plus"></i>
                                                        </button>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- nút cập nhật -->
                </form>
            </div>
        </div>

        <!-- RIGHT: TỔNG TIỀN -->
        <div class="card summary">
            <div class="card-body">
                <div class="head">
                    <h3 style="margin:0;font-size:28px;font-weight:700">
                        Thông tin đơn hàng
                    </h3>
                </div>
                <div class="divider"></div>

                <div class="tinh-tien">
                    <p>Tổng tiền:</p>
                    <span class="thanh-tien">
                                <fmt:formatNumber value="${totalAmount}"
                                                  type="number"/>đ
                            </span>
                </div>

                <p class="muted">Phí vận chuyển sẽ được tính ở trang
                    Thanh toán</p>
                <p class="muted">Mã giảm giá được nhập ở trang Thanh
                    toán</p>

                <a href="${pageContext.request.contextPath}/checkout">
                    <c:choose>
                        <c:when test="${cartSize == 0}">
                            <button class="btn" disabled>Đặt
                                hàng
                            </button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn" ${cart.cartSize() == 0 ?
                                    'disabled' : ''}>Đặt hàng
                            </button>
                        </c:otherwise>
                    </c:choose>
                </a>
            </div>
        </div>
    </div>
</div>
<%@ include file="Footer.jsp" %>
<script>
    function increaseQty(productId) {
        const input = document.getElementById('qty-' + productId);
        let currentQty = parseInt(input.value, 10);
        if (isNaN(currentQty)) currentQty = 0;
        currentQty++;
        input.value = currentQty;
        updateCartItem(productId, currentQty);
    }

    function decreaseQty(productId) {
        const input = document.getElementById('qty-' + productId);
        let currentQty = parseInt(input.value, 10);
        if (isNaN(currentQty)) currentQty = 1;
        if (currentQty > 1) {
            currentQty--;
            input.value = currentQty;
            updateCartItem(productId, currentQty);
        }
    }

    document.addEventListener('DOMContentLoaded', function () {
        const updateUrl = '${pageContext.request.contextPath}/AddToCart?action=ajaxUpdate';

        const qtyInputs = document.querySelectorAll('.qty-input');

        qtyInputs.forEach(input => {
            input.addEventListener('change', function () {
                const productId = this.dataset.productId;
                let quantity = parseInt(this.value, 10);
                if (isNaN(quantity) || quantity < 1) {
                    quantity = 1;
                    this.value = 1;
                }
                updateCartItem(productId, quantity);
            });
        });
    });

    function formatCurrency(value) {
        return new Intl.NumberFormat('vi-VN').format(value) + 'đ';
    }

    function updateCartItem(productId, quantity) {
        const updateUrl = '${pageContext.request.contextPath}/AddToCart?action=ajaxUpdate';
        const params =
            'productId=' + encodeURIComponent(productId) +
            '&quantity=' + encodeURIComponent(quantity);

        fetch(updateUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            body: params
        })
            .then(resp => resp.json())
            .then(data => {
                if (!data.success) {
                    console.error('Update cart failed');
                    return;
                }

                // cập nhật thành tiền từng dòng
                const subSpan = document.getElementById('subtotal-' + productId);
                if (subSpan) {
                    subSpan.textContent = formatCurrency(data.itemSubtotal);
                }

                // cập nhật tổng tiền
                const totalSpan = document.querySelector('.thanh-tien');
                if (totalSpan) {
                    totalSpan.textContent = formatCurrency(data.totalAmount);
                }

                // cập nhật số trên icon giỏ hàng
                const cartIcon = document.getElementById('cartIcon');
                if (cartIcon) {
                    cartIcon.setAttribute('data-count', data.cartCount);
                }
                // cập nhật tổng sản phẩm sản phẩm
                const cartTotalQty = document.getElementById('cart-total-quantity');
                if (cartTotalQty) {
                    cartTotalQty.textContent = data.cartCount;
                }
            })
            .catch(err => console.error(err));
    }
</script>
</body>

</html>