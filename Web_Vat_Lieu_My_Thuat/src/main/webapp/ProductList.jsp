<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<c:forEach var="p" items="${products}">
    <div class="list-product-list1">
        <a href="${pageContext.request.contextPath}/DetailsProductController?id=${p.id}">
            <c:set var="imgUrl" value="${p.thumbnail}" />
            <c:if test="${not empty imgUrl and not fn:startsWith(imgUrl,'http') and not fn:startsWith(imgUrl, pageContext.request.contextPath)}">
                <c:choose>
                    <c:when test="${fn:startsWith(imgUrl,'/')}">
                        <c:set var="imgUrl" value="${pageContext.request.contextPath}${imgUrl}" />
                    </c:when>
                    <c:otherwise>
                        <c:set var="imgUrl" value="${pageContext.request.contextPath}/${imgUrl}" />
                    </c:otherwise>
                </c:choose>
            </c:if>
            <img src="${imgUrl}" alt="All Products">

            <div class="list-product-list1-content">
                <div class="list-product-list1-content-socials">
                    <div class="list-product-list1-content-socials-1">
                        <%@ include file="BadgeType.jsp" %>
                    </div>
                    <div class="list-product-list1-content-socials-2">
                        <i class="fa-solid fa-arrow-up-right-dots"></i>
                        <span>Đã bán ${p.soldQuantity}</span>
                    </div>
                </div>
                <div class="list-product-list1-content-description">
                    <p class="content">${p.name}</p>
                    <div class="star">
                        <!-- full stars -->
                        <c:forEach var="i" begin="1" end="${p.fullStarCount}">
                            <i class="fa-solid fa-star"></i>
                        </c:forEach>

                        <!-- half star -->
                        <c:if test="${p.halfStar}">
                            <i class="fa-solid fa-star-half-stroke"></i>
                        </c:if>

                        <!-- empty stars -->
                        <c:forEach var="i" begin="1" end="${p.emptyStarCount}">
                            <i class="fa-regular fa-star"></i>
                        </c:forEach>

                        <span>
        (<fmt:formatNumber value="${p.avgRating}" maxFractionDigits="1" />)
    </span>
                    </div>
                    <h2 class="price-product-after">
                        <fmt:formatNumber value="${p.priceAfterDiscount}" type="number"/>₫
                    </h2>
                    <p class="price-product-before">
                        <fmt:formatNumber value="${p.price}" type="number" />₫
                    </p>
                    <span class="price-product-discount">
                        -${p.discountDefault}%
                    </span>
                    <div class="button">
                        <form action="${pageContext.request.contextPath}/AddToCart?action=add" method="post">
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
        </a>
    </div>
</c:forEach>
