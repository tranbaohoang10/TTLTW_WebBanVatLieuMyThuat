<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    /* Begin header */
    #header-trang-chu {
        background-color: var(--color-main);
        display: flex;
        align-items: center;
        position: fixed;
        z-index: 999;
        width: 100%;
        top: 0;
    }

    #header-trang-chu .logo-trang-chu {
        margin-left: 50px;
        margin-right: 50px;
        margin-top: 25px;
        margin-bottom: 25px;
    }

    #header-trang-chu .logo-trang-chu img {
        background-color: transparent;
        width: 200px;
        height: 100%;
    }

    #header-trang-chu .tim-kiem-san-pham {
        position: relative;
        display: flex;
    }

    #header-trang-chu .tim-kiem-san-pham input {
        padding: 10px 15px;
        width: 600px;
        border-radius: 10px;
        border: none;
    }

    /* NÚT KÍNH LÚP */
    #header-trang-chu .tim-kiem-san-pham .btn-search {
        position: absolute;
        right: 0;
        top: 0;
        width: 50px;
        height: 100%;
        background-color: #0b234d;
        border: none;
        cursor: pointer;
        border-top-right-radius: 10px;
        border-bottom-right-radius: 10px;

        display: flex;
        align-items: center;
        justify-content: center;
    }

    /* Icon bên trong */
    #header-trang-chu .tim-kiem-san-pham .btn-search i {
        color: white;
        font-size: 16px;
    }


    #header-trang-chu .header-contact {
        display: flex;
        align-items: center;
    }

    #header-trang-chu .header-contact i {
        width: 30px;
        height: 30px;
        border-radius: 50%;
        background-color: #0b234d;
        line-height: 30px;
        color: white;
    }

    #header-trang-chu .header-contact {
        margin-left: 100px;
        margin-right: 50px;
        cursor: pointer;
    }

    #header-trang-chu .header-contact .contact {
        margin-left: 15px;
    }

    #header-trang-chu .header-contact .contact .contact-phone-number {
        color: white;
        font-weight: 600;
        font-size: 18px;
    }

    #header-trang-chu .header-contact .contact .contact-phone-help {
        color: white;
        font-size: 16px;
    }

    #header-trang-chu .header-dangnhap-dangki {
        display: flex;
        align-items: center;
        cursor: pointer;
    }

    #header-trang-chu .header-dangnhap-dangki i {
        width: 30px;
        height: 30px;
        border-radius: 50%;
        background-color: #0b234d;
        line-height: 30px;
        color: white;
    }

    #header-trang-chu .header-dangnhap-dangki {
        margin-right: 50px;
    }

    #header-trang-chu .header-dangnhap-dangki .dangnhap-dangki {
        margin-left: 15px;
        max-width: 100px;
        }

    #header-trang-chu .header-dangnhap-dangki .dangnhap-dangki .dangnhap {
        color: white;
        font-weight: 600;
        font-size: 18px;

        display: block;
        width: 100%;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }

    #header-trang-chu .header-dangnhap-dangki .dangnhap-dangki .dangki {
        color: white;
        font-size: 16px;
    }

    #header-trang-chu .header-giohang {
        font-size: 25px;
        color: white;
        position: relative;
        cursor: pointer;
    }

    #header-trang-chu .header-giohang::after {
        display: inline-flex;
        content: attr(data-count);
        width: 20px;
        height: 20px;
        left: 20px;
        bottom: 15px;
        justify-content: center;
        align-items: center;
        font-size: 15px;
        background-color: red;
        position: absolute;
        border-radius: 50%;
        transition: all 0.3s ease;
    }
    .search-suggest-box{
        position: absolute;
        top: calc(100% + 6px);
        left: 0;
        width: 100%;
        background: #fff;
        border-radius: 10px;
        box-shadow: 0 8px 24px rgba(0,0,0,0.12);
        overflow: hidden;
        z-index: 9999;
        max-height: 320px;
        overflow-y: auto;
    }

    .search-suggest-item{
        display: flex;
        align-items: center;
        gap: 10px;
        padding: 10px 12px;
        cursor: pointer;
    }

    .search-suggest-item:hover{
        background: #f2f6ff;
    }

    .search-suggest-item img{
        width: 44px;
        height: 44px;
        border-radius: 8px;
        object-fit: cover;
    }

    .search-suggest-item .name{
        font-size: 14px;
        color: #111;
        font-weight: 600;
    }

    .search-suggest-item .meta{
        font-size: 12px;
        color: #555;
    }

    .search-suggest-item .stock-out{
        color: #dc2626;
        font-weight: 600;
        margin-left: 8px;
    }


    /* Animation khi cập nhật giỏ hàng */
    #header-trang-chu .header-giohang.cart-updated::after {
        animation: cartCountPulse 0.6s ease;
    }

    @keyframes cartCountPulse {
        0% {
            transform: scale(1);
            background-color: red;
        }
        50% {
            transform: scale(1.4);
            background-color: #10b981;
        }
        100% {
            transform: scale(1);
            background-color: red;
        }
    }
    a{
        text-decoration: none;
    }
    /* End header */
</style>
<header id="header-trang-chu" class="trang-chu">
    <div class="logo-trang-chu">

        <a href="${pageContext.request.contextPath}/home">

            <img src="./assets/images/logo/logo.png" alt>
        </a>
    </div>
    <form action="${pageContext.request.contextPath}/search"
          method="get"
          class="tim-kiem-san-pham"
          id="headerSearchForm">

        <input type="text"
               id="headerSearchInput"
               name="keyword"
               autocomplete="off"
               placeholder="Tìm kiếm sản phẩm...">

        <button type="submit" class="btn-search">
            <i class="fa-solid fa-magnifying-glass"></i>
        </button>

    </form>
    <div class="header-contact">
        <i class="fa-solid fa-phone"></i>
        <div class="contact">
            <div class="contact-phone-number">1900 866 819</div>
            <div class="contact-phone-help">Hỗ trợ khách hàng</div>
        </div>
    </div>
    <c:choose>
        <%-- Đã đăng nhập --%>
        <c:when test="${not empty sessionScope.currentUser}">
            <div class="header-dangnhap-dangki">
                <i class="fa-solid fa-user"></i>
                <div class="dangnhap-dangki">
                    <!-- đổi 'fullName' cho đúng với #field trong Users (name, username, ten, ...) -->
                    <a href="${pageContext.request.contextPath}/profile" class="dangnhap">
                        Hi, ${sessionScope.currentUser.fullName}
                    </a>
                    <div class="dangki">
                        <a href="${pageContext.request.contextPath}/logout"
                            style="color: white;">
                            Đăng xuất
                        </a>
                    </div>
                </div>
            </div>
        </c:when>

        <%-- Chưa đăng nhập --%>
<%--        <c:otherwise>--%>
<%--            <a href="login" class="link header-dangnhap-dangki">--%>
<%--                <i class="fa-solid fa-user"></i>--%>
<%--                <div class="dangnhap-dangki">--%>
<%--                    <div class="dangnhap">Đăng nhập</div>--%>
<%--                    <div class="dangki">Đăng kí</div>--%>
<%--                </div>--%>
<%--            </a>--%>
<%--        </c:otherwise>--%>
        <c:otherwise>
            <div class="link header-dangnhap-dangki">
                <i class="fa-solid fa-user"></i>
                <div class="dangnhap-dangki">
                    <a href="login" class="dangnhap">Đăng nhập</a>
                    <a href="register" class="dangki">Đăng kí</a>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
    <a href="${pageContext.request.contextPath}/cart"
       class="link header-giohang"
       id="cartIcon"
       data-count="${empty sessionScope.cartCount ? 0 : sessionScope.cartCount}">
        <i class="fa-solid fa-bag-shopping"></i>
    </a>

</header>


