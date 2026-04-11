<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang chủ</title>
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

    /* ========== Slider ========== */
    .w3-left,
    .w3-right {
        position: absolute;
        top: 50%;
        /* căn giữa slider */
        left: 40px;
        /* vị trí nút trái */
        right: auto;
        transform: translateY(-50%);
        background-color: rgba(0, 0, 0, 0.4);
        color: white;
        padding: 14px;
        font-size: 24px;
        cursor: pointer;
        border-radius: 50%;
        user-select: none;
        transition: 0.3s;
        z-index: 10;
    }

    .w3-right {
        left: auto;
        right: 40px;
        /* vị trí nút phải */
    }

    .w3-left:hover,
    .w3-right:hover {
        background-color: rgba(0, 0, 0, 0.8);
    }

    /* Ảnh chiếm full màn hình */
    .w3-content {
        width: 100%;
        max-width: 100%;
        position: relative;
        overflow: hidden;
        margin-bottom: 100px;
        /* bù lại phần header fixed */
    }

    .mySlides {
        display: none;
        width: 100%;
        height: 80vh;
        object-fit: contain;
        background-color: #000;
        /* hoặc #fff tùy thiết kế */
    }

    /* Nút điều hướng */
    .w3-left,
    .w3-right {
        position: absolute;
        top: 50%;
        transform: translateY(-50%);
        background-color: rgba(0, 0, 0, 0.4);
        color: white;
        padding: 12px;
        font-size: 24px;
        cursor: pointer;
        border-radius: 50%;
        transition: 0.3s;
        user-select: none;
    }

    .w3-left:hover,
    .w3-right:hover {
        background-color: rgba(0, 0, 0, 0.8);
    }

    .w3-left {
        left: 30px;
    }

    .w3-right {
        right: 30px;
    }

    /* Dấu chấm dưới */
    .w3-badge {
        cursor: pointer;
        height: 14px;
        width: 14px;
        padding: 0;
        margin: 0 3px;
        border-radius: 50%;
        display: inline-block;
        background-color: rgba(255, 255, 255, 0.6);
    }

    .w3-white {
        background-color: white !important;
    }

    .w3-display-bottommiddle {
        position: absolute;
        bottom: 20px;
        text-align: center;
    }

    /* End slider show */
    /* Begin section main */
    .section-but {
        margin-bottom: 100px;
    }

    .section-but .container {
        padding: 15px;
        border-radius: 15px;
        box-shadow: rgba(149, 157, 165, 0.2) 0px 8px 24px;
    }

    .section-but img {
        width: 100%;
        height: auto;
        cursor: pointer;
        border-radius: 15px;

    }

    .section-but .section-but-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .section-but .section-but-header h2 {
        font-size: 20px;
        color: #17479D;
    }

    .section-but .section-but-header .list-type a {
        display: inline-block;
        padding: 8px 20px;
        text-decoration: none;
        color: black;
        border: 1px solid #ddd;
        border-radius: 30px;
        margin-left: 5px;
    }

    .section-but .section-but-header .list-type a:nth-child(1) {
        text-decoration: none;
        color: #17479D;
        border: 1px solid #17479D;
    }

    .section-but .section-but-header .list-type a:last-child {

        margin-right: 3px;
    }

    .section-but .list-product {
        display: flex;
        gap: 20px;
        margin-bottom: 30px;
        flex-wrap: wrap;
    }

    .section-but .list-product .list-product-list1 {
        border-radius: 15px;
        background-color: #F8FAFF;
        border: 1px solid #D7E5FF;
        width: calc(20% - 18px);
        cursor: pointer;
    }

    .section-but .list-product .list-product-list1 img {
        border-radius: 15px;
        width: 100%;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content-socials {
        display: flex;
        align-items: center;
        gap: 10px;
        margin-top: 10px;

    }

    .section-but .list-product .list-product-list1 .list-product-list1-content {
        padding: 0 15px 15px;
        text-align: center;
    }

    /* chu y */
    .section-but .list-product .list-product-list1 .list-product-list1-content .content {
        width: 200px;
        white-space: nowrap;
        /* không cho xuống dòng */
        overflow: hidden;
        /* ẩn phần bị tràn */
        text-overflow: ellipsis;
        /* thêm dấu … */
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content-socials .list-product-list1-content-socials-1 {
        width: 60px;
        border-radius: 10px;
        text-align: center;
        line-height: 30px;
        /* height: 30px; */
        /* padding: 5px; */
        background-color: #FFE0E2;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content-socials .list-product-list1-content-socials-1 i {
        color: #ED1C24;
        font-size: 10px;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content-socials .list-product-list1-content-socials-1 span {
        /* display: inline-block; */
        color: #ED1C24;
        font-size: 12px;
        font-weight: 600;
        margin-left: 5px;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content-socials .list-product-list1-content-socials-2 {
        width: 100px;
        border-radius: 10px;
        text-align: center;
        line-height: 30px;
        /* height: 30px; */
        background-color: #E0EBFF;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content-socials .list-product-list1-content-socials-2 i {
        color: #268EF8;
        font-size: 10px;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content-socials .list-product-list1-content-socials-2 span {
        color: #268EF8;
        font-size: 12px;
        font-weight: 600;
        margin-left: 5px;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content .list-product-list1-content-description p {
        font-size: 14px;
        color: black;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content .list-product-list1-content-description .star i {
        color: #F3C63F
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content .list-product-list1-content-description .star span {
        color: #F3C63F;
        margin-left: 10px;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content .list-product-list1-content-description .price-product-after {
        color: #268EF8;
        font-weight: 600;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content .list-product-list1-content-description .price-product-before {
        color: #ccc;
        font-weight: 600;
        text-decoration: line-through;
        display: inline-block;
        margin: 0;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content .list-product-list1-content-description .price-product-discount {
        color: #ED1C24;
        font-weight: 600;
        margin-left: 5px;
        width: 60px;
        border-radius: 20px;
        text-align: center;
        line-height: 30px;
        /* height: 30px; */
        /* padding: 5px; */
        display: inline-block;
        background-color: #FFE0E2;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content .list-product-list1-content-description .button {
        margin-top: 15px;
        text-align: center;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content .list-product-list1-content-description .button .btn-xemchitiet {
        min-width: 64%;
        padding: 10px 0;
        background-color: transparent;
        border: 1px solid #268EF8;
        border-radius: 10px;
        color: white;
        cursor: pointer;

    }

    .section-but .list-product .list-product-list1 .list-product-list1-content .list-product-list1-content-description .button .btn-xemchitiet {
        color: #268EF8;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content .list-product-list1-content-description .button .btn-xemchitiet:hover {
        background-color: #268EF8;
        color: white;
    }

    .section-but .section-but-button {
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .section-but .section-but-button .button {
        background-color: #17479D;
        border: none;
        padding: 15px 15px;
        border-radius: 20px;
        color: white;
        cursor: pointer;
        font-size: 16px;
    }

    .section-but .section-but-button .button:hover {
        opacity: 0.9;
    }

    .category-title {
        text-transform: uppercase;
    }


    /* End section main */
</style>

<body>
<%@ include file="Header.jsp" %>
<div id="section-info-mithuat">
    <div class="container">
        <%@ include file="CategoryMenu.jsp" %>
    </div>
</div>

<div class="w3-content w3-display-container">
    <c:forEach var="slider" items="${sliders}">
        <a href="${pageContext.request.contextPath}/${slider.linkTo}"> <c:set var="imgUrl" value="${slider.thumbnail}"/>
            <c:if test="${not empty imgUrl and not fn:startsWith(imgUrl,'http') and not fn:startsWith(imgUrl, pageContext.request.contextPath)}">
                <c:choose>
                    <c:when test="${fn:startsWith(imgUrl,'/')}">
                        <c:set var="imgUrl" value="${pageContext.request.contextPath}${imgUrl}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="imgUrl" value="${pageContext.request.contextPath}/${imgUrl}"/>
                    </c:otherwise>
                </c:choose>
            </c:if>
            <img class="mySlides" src="${imgUrl}">
        </a>
    </c:forEach>

    <div class="w3-left" onclick="plusDivs(-1)">&#10094;</div>
    <div class="w3-right" onclick="plusDivs(1)">&#10095;</div>

    <div class="w3-display-bottommiddle" style="width:100%">
        <span class="w3-badge demo" onclick="currentDiv(1)"></span>
        <span class="w3-badge demo" onclick="currentDiv(2)"></span>
        <span class="w3-badge demo" onclick="currentDiv(3)"></span>
    </div>
</div>

<script>
    let slideIndex = 0;
    showDivs(slideIndex);

    function plusDivs(n) {
        showDivs(slideIndex += n);
    }

    function currentDiv(n) {
        showDivs(slideIndex = n - 1);
    }

    function showDivs(n) {
        let i;
        const slides = document.getElementsByClassName("mySlides");
        const dots = document.getElementsByClassName("demo");
        if (n >= slides.length) {
            slideIndex = 0
        }
        if (n < 0) {
            slideIndex = slides.length - 1
        }

        for (i = 0; i < slides.length; i++) slides[i].style.display = "none";
        for (i = 0; i < dots.length; i++) dots[i].classList.remove("w3-white");

        slides[slideIndex].style.display = "block";
        dots[slideIndex].classList.add("w3-white");
    }

    setInterval(() => {
        plusDivs(1);
    }, 4000);
</script>
<c:forEach var="entry" items="${productsByCategory}">
    <c:set var="cat" value="${entry.key}"/>
    <c:set var="products" value="${entry.value}"/>

    <div class="section-but">
        <div class="container">
            <c:set var="imgUrl" value="${cat.thumbnail}"/>
            <c:if test="${not empty imgUrl and not fn:startsWith(imgUrl,'http') and not fn:startsWith(imgUrl, pageContext.request.contextPath)}">
                <c:if test="${fn:startsWith(cat.thumbnail, 'http')}">
                    <img src="${cat.thumbnail}" alt="${cat.categoryName}">
                </c:if>
                <c:if test="${not fn:startsWith(cat.thumbnail, 'http')}">
                    <img src="${pageContext.request.contextPath}/${cat.thumbnail}" alt="${cat.categoryName}">
                </c:if>
            </c:if>

            <img src="${imgUrl}" alt="category"/>

            <div class="section-but-header">
                <h2 class="category-title">${cat.categoryName}</h2>
            </div>

            <div class="section-but-content">
                <div class="list-product">
                    <c:forEach var="p" items="${products}">
                        <div class="list-product-list1">
                            <a href="${pageContext.request.contextPath}/DetailsProductController?id=${p.id}">
                                <img src="${p.thumbnail}" alt="${p.name}">
                            </a>

                            <div class="list-product-list1-content">
                                <div class="list-product-list1-content-socials">
                                    <%@ include file="BadgeType.jsp" %>
                                    <div class="list-product-list1-content-socials-2">
                                        <i class="fa-solid fa-arrow-up-right-dots"></i>
                                        <span>Đã bán ${p.soldQuantity}</span>
                                    </div>
                                </div>

                                <div class="list-product-list1-content-description">
                                    <p class="content">
                                        <a href="${pageContext.request.contextPath}/DetailsProductController?id=${p.id}"
                                           style="text-decoration: none; color: black;">
                                                ${p.name}
                                        </a>
                                    </p>

                                    <div class="star">
                                        <c:forEach var="i" begin="1" end="${p.fullStarCount}">
                                            <i class="fa-solid fa-star"></i>
                                        </c:forEach>

                                        <c:if test="${p.halfStar}">
                                            <i class="fa-solid fa-star-half-stroke"></i>
                                        </c:if>

                                        <c:forEach var="i" begin="1" end="${p.emptyStarCount}">
                                            <i class="fa-regular fa-star"></i>
                                        </c:forEach>

                                        <span>(${p.avgRating})</span>
                                    </div>

                                    <h2 class="price-product-after">
                                        <fmt:formatNumber value="${p.priceAfterDiscount}" type="number"/>đ
                                    </h2>

                                    <p class="price-product-before">
                                        <fmt:formatNumber value="${p.price}" type="number"/>đ
                                    </p>

                                    <span class="price-product-discount">-${p.discountDefault}%</span>

                                    <div class="button">
                                        <form action="${pageContext.request.contextPath}/AddToCart?action=add" method="post" class="form-add-cart">
                                            <input type="hidden" name="productId" value="${p.id}">
                                            <input type="hidden" name="quantity" value="1">
                                            <button type="submit" class="btn-xemchitiet">
                                                <i class="fa-solid fa-cart-plus"></i>
                                                Thêm vào giỏ
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <div class="section-but-button">
                <a class="button"
                   href="${pageContext.request.contextPath}/category?categoryId=${cat.id}">
                    Xem tất cả <i class="fa-solid fa-angle-right"></i>
                </a>
            </div>
        </div>
    </div>
</c:forEach>


<!-- End section -->
<%@ include file="Footer.jsp" %>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const forms = document.querySelectorAll(".form-add-cart");

        forms.forEach(function (form) {
            form.addEventListener("submit", function (e) {
                e.preventDefault();

                const data = new FormData(form);
                const body = new URLSearchParams(data).toString();

                fetch(form.action, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
                        "X-Requested-With": "XMLHttpRequest"
                    },
                    body: body
                })
                    .then(function (res) {
                        if (res.status === 401) {
                            window.location.href = "${pageContext.request.contextPath}/login";
                            return null;
                        }
                        return res.json();
                    })

                    .then(function (data) {
                        if (data.success) {
                            const cartIcon = document.getElementById("cartIcon");
                            if (cartIcon) {
                                cartIcon.setAttribute("data-count", data.cartCount);
                            }
                            alert("Đã thêm vào giỏ hàng");
                        } else {
                            alert("Thêm vào giỏ hàng thất bại");
                        }
                    })
                    .catch(function () {
                        alert("lỗi");
                    });
            });
        });
    });
</script>
</body>

</html>