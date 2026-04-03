<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đánh giá sản phẩm</title>
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
    a{ text-decoration: none; }

    /* content review */
    .breadcrumb {
        background-color: #f0f4f8;
        padding: 1rem 2.5rem;
        font-size: 0.875rem;
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

    .review-container {
        padding: 15px;
        border-radius: 10px;
        margin-top: 20px;
        background-color: white;
    }

    .filter-section {
        margin-bottom: 20px;
    }

    h2 {
        font-weight: bold;
        font-size: 20px;
        margin-bottom: 10px;
    }

    .filter-options {
        display: flex;
        gap: 10px;
        margin-bottom: 10px;
    }

    .filter-btn {
        padding: 5px 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        background-color: #fff;
        cursor: pointer;
        transition: background-color 0.2s;
    }

    .filter-btn.active,
    .filter-btn:hover {
        background-color: #2659F3;
        color: #fff;
    }

    .reviews-section {
        display: flex;
        flex-direction: column;
        gap: 20px;
    }

    .review {
        border-bottom: 1px solid #eee;
        padding-bottom: 15px;
        margin-bottom: 15px;
        height: fit-content;
    }

    .review h3 {
        margin-bottom: 5px;
        font-size: 16px;
        font-weight: 600;
    }

    .review p {
        margin-top: 5px;
    }

    .rating {
        color: #ff9800;
        font-size: 18px;
    }

    .timestamp {
        padding-top: 10px;
        font-size: 13px;
        color: #666;
    }

    .star-rating {
        display: flex;
        justify-content: center;
        white-space: nowrap;
    }

    .star-rating [type="radio"] {
        appearance: none;
    }

    .star-rating i {
        color: #eee;
        font-size: 30px;
        transition: 0.3s;
    }

    .star-rating label:is(:hover, :has(~ :hover)) i {
        transform: scale(1.35);
        color: #fffdba;
    }

    .star-rating label:has(~ :checked) i {
        color: rgb(245, 245, 74);
        text-shadow: 0 0 2px #ffffff, 0 0 10px #ffee58;
    }

    .area_submit {
        display: flex;
        width: 100%;
        justify-content: center;
    }

    .btn_writeReview {
        margin-top: 25px;
        font-size: 16px;
        padding: 10px 10px;
        background-color: #2659F3;
        color: #fff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    .form_review {
        justify-content: center;
    }

    .area_review {
        padding: 10px;
        width: 100%;
        height: 100px;
    }

    .form {
        padding: 20px;
        border-radius: 8px;
    }

    textarea:focus-visible,
    input:focus-visible {
        outline: 2px solid #b2d2f4;
        border: 1px solid #b2d2f4;
    }

    .form textarea {
        font-family: Arial, Helvetica, sans-serif;
        width: 100%;
        height: 10rem;
        margin-bottom: 1rem;
        border: 1px solid #cdc4c4;
        border-radius: 5px;
        padding: 0.8rem;
        font-size: 1rem;
    }

    .form-group {
        display: flex;
        gap: 1rem;
        flex: 1;
        margin-bottom: 1rem;
    }

    .form-group > * {
        flex-basis: 50%;
    }

    .form-group .infor_contact {
        width: 50%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 4px;
        font-size: 14px;
    }
</style>

<body>
<%@ include file="Header.jsp" %>

<nav>
    <!-- Breadcrumb -->
    <div class="breadcrumb">
        <a href="${pageContext.request.contextPath}/index.jsp">Trang chủ</a>
        <span>/</span>
        <a href="${pageContext.request.contextPath}/DetailsProductController?id=${product.id}">
            ${product.name}
        </a>
        <span>/</span>
        <span>Đánh giá sản phẩm</span>
    </div>

    <!-- KHỐI ĐÁNH GIÁ TỔNG QUAN + FORM GỬI ĐÁNH GIÁ -->
    <div class="review-container bg-while">
        <h2>Đánh giá sản phẩm: ${product.name}</h2>

        <!-- Điểm trung bình + số lượng đánh giá -->
        <c:if test="${reviewCount > 0}">
            <p>
                Trung bình:
                <strong>
                    <fmt:formatNumber value="${avgRating}" minFractionDigits="1" maxFractionDigits="1"/>
                </strong>
                / 5 (từ ${reviewCount} đánh giá)
            </p>
        </c:if>
        <c:if test="${reviewCount == 0}">
            <p>Chưa có đánh giá nào cho sản phẩm này. Hãy là người đầu tiên đánh giá!</p>
        </c:if>


        <div class="reviews-section">
            <c:choose>
                <c:when test="${empty sessionScope.currentUser}">
                    <p>Bạn cần đăng nhập để đánh giá sản phẩm.</p>
                </c:when>

                <c:when test="${canReview}">
                    <h3>
                        <c:choose>
                            <c:when test="${hasReviewed}">Chỉnh sửa đánh giá của bạn</c:when>
                            <c:otherwise>Viết đánh giá của bạn</c:otherwise>
                        </c:choose>
                    </h3>

                    <form action="${pageContext.request.contextPath}/Product_ReviewsController" method="post">
                        <input type="hidden" name="productID" value="${product.id}">

                        <div>
                            <label>Số sao:</label>
                            <input type="radio" name="rating" value="1"> 1
                            <input type="radio" name="rating" value="2"> 2
                            <input type="radio" name="rating" value="3"> 3
                            <input type="radio" name="rating" value="4"> 4
                            <input type="radio" name="rating" value="5"> 5
                        </div>

                        <div>
                            <label>Nhận xét:</label>
                            <textarea name="comment"></textarea>
                        </div>

                        <button type="submit">
                            <c:choose>
                                <c:when test="${hasReviewed}">Cập nhật đánh giá</c:when>
                                <c:otherwise>Gửi đánh giá</c:otherwise>
                            </c:choose>
                        </button>
                    </form>
                </c:when>

                <c:otherwise>
                    <p>Bạn chỉ được đánh giá sau khi đã mua sản phẩm này.</p>
                </c:otherwise>
            </c:choose>
        </div>



    <div class="review-container bg-while">
        <div class="filter-section">
            <h2>
                Đánh giá cho: ${product.name}
                (<span>${reviewCount}</span> đánh giá,
                trung bình <span>${avgRating}</span> ★)
            </h2>

        </div>

        <div class="reviews-section">
            <c:if test="${empty reviews}">
                <p>Chưa có đánh giá nào cho sản phẩm này.</p>
            </c:if>

            <c:forEach var="rv" items="${reviews}">
                <div class="review">
                    <h3>Người dùng ${rv.username}</h3>

                    <div class="rating">
                        <c:forEach var="i" begin="1" end="5">
                            <c:choose>
                                <c:when test="${i <= rv.rating}">★</c:when>
                                <c:otherwise>☆</c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>

                    <p>${rv.comment}</p>

                    <p class="timestamp">
                        <fmt:formatDate value="${rv.createAt}" pattern="dd/MM/yyyy HH:mm" />
                    </p>
                </div>
            </c:forEach>
        </div>
    </div>
</nav>

<%@ include file="Footer.jsp" %>
</body>
</html>
