<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Quản lý gợi ý sản phẩm</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/style.css">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css">
</head>

<style>
    * {
        box-sizing: border-box;
    }

    body {
        margin: 0;
        font-family: Arial, sans-serif;
        background: #f9f9f9;
    }

    #main {
        display: flex;
        min-height: 100vh;
    }

    #main .left {
        width: 17%;
        background-color: #17479D;
    }

    #main .left .list-admin {
        display: flex;
        flex-direction: column;
        gap: 15px;
    }

    #main .left .list-admin a {
        display: block;
        padding: 10px 20px;
        color: white;
        text-decoration: none;
    }

    #main .left .list-admin a i {
        margin-right: 20px;
    }

    #main .left .list-admin a:hover {
        background-color: #203247;
        border-left: 2px solid #3B7DDD;
    }

    #main .left .list-admin a.active {
        background-color: #203247;
        border-left: 4px solid #FFD700;
        font-weight: bold;
    }

    #main .left .list-admin a.logo {
        padding: 0;
    }

    #main .left .list-admin a.logo:hover {
        border-left: none;
    }

    #main .left .list-admin .logo img {
        width: 100%;
        height: auto;
        margin: 10px 0 20px;
    }

    #main .right {
        flex: 1;
    }

    .container {
        width: 92%;
        margin: 30px auto;
    }

    .recommendation-box {
        padding: 25px;
        background: white;
        border-radius: 8px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    }

    .recommendation-box h1 {
        margin-top: 0;
        color: #222;
    }

    .description {
        color: #555;
        line-height: 1.6;
    }

    .process-box {
        margin-top: 25px;
        padding: 20px;
        background: #f5f7fb;
        border-radius: 6px;
    }

    .process-box h2 {
        margin-top: 0;
        font-size: 20px;
    }

    .process-box ol {
        margin-bottom: 0;
        line-height: 1.8;
    }
    .process-box {
        margin-top: 25px;
    }

    .process-box h2 {
        margin-top: 0;
        margin-bottom: 20px;
        font-size: 20px;
    }

    .stat-list {
        display: grid;
        grid-template-columns: repeat(2, 1fr);
        gap: 20px;
    }

    .stat-item {
        display: flex;
        align-items: center;
        gap: 15px;
        padding: 20px;
        background: #f5f7fb;
        border-radius: 8px;
    }

    .stat-icon {
        width: 50px;
        height: 50px;
        display: flex;
        align-items: center;
        justify-content: center;
        border-radius: 50%;
        background: #17479d;
        color: white;
        font-size: 20px;
    }

    .stat-item p {
        margin: 0 0 8px;
        color: #666;
    }

    .stat-item h3 {
        margin: 0;
        font-size: 24px;
        color: #222;
    }

</style>

<body>
<div id="main">

    <div class="left">
        <div class="list-admin">
            <a href="Admin.jsp" class="logo"><img src="../assets/images/logo/logo.png" alt=""></a>
            <a href="${pageContext.request.contextPath}/admin/overview"><i
                    class="fa-solid fa-house"></i>
                Tổng quan</a>
            <c:if test="${role == 'ADMIN' || permissions.contains('STATISTIC_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/statistics"><i class="fa-solid fa-chart-line"></i>Thống
                    kê</a>
            </c:if>
            <c:if test="${role == 'ADMIN' || permissions.contains('CATEGORY_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/categories"><i class="fa-solid fa-list"></i>Quản lý danh
                    mục</a>
            </c:if>
            <c:if test="${role == 'ADMIN' || permissions.contains('PRODUCT_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/products"><i class="fa-solid fa-palette"></i>Quản
                    lý sản phẩm</a>
            </c:if>
            <c:if test="${role == 'ADMIN' || permissions.contains('INVENTORY_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/inventory"><i class="fa-solid fa-warehouse"></i>Quản
                    lý tồn kho</a>
            </c:if>
            <c:if test="${role == 'ADMIN' || permissions.contains('USER_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/users" ><i class="fa-solid fa-person"></i>Quản lý người dùng</a>
            </c:if>
            <c:if test="${role == 'ADMIN' || permissions.contains('ORDER_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/orders"><i class="fa-solid fa-box-open"></i>Quản
                    lý đơn hàng</a>
            </c:if>
            <c:if test="${role == 'ADMIN' || permissions.contains('VOUCHER_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/vouchers"><i class="fa-solid fa-gift"></i>Quản lý
                    khuyến mãi</a>
            </c:if>
            <c:if test="${role == 'ADMIN' || permissions.contains('SLIDER_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/sliders"><i class="fa-solid fa-sliders"></i>Quản lý
                    Slider Show</a>
            </c:if>
            <c:if test="${role == 'ADMIN' || permissions.contains('CONTACT_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/contacts"><i class="fa-solid fa-address-book"></i>Quản lý
                    liên hệ</a>
            </c:if>
            <c:if test="${role == 'ADMIN' || permissions.contains('LOG_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/logs">
                    <i class="fa-solid fa-clock-rotate-left"></i>Quản lý thao tác
                </a>
            </c:if>
            <c:if test="${role == 'ADMIN' || (not empty permissions && permissions.contains('RECOMMENDATION_VIEW'))}">
                <a href="${pageContext.request.contextPath}/admin/recommendations" class="active">

                    <i class="fa-solid fa-wand-magic-sparkles"></i>
                    Quản lý gợi ý
                </a>
            </c:if>
            <a href="${pageContext.request.contextPath}/logout"><i class="fa-solid fa-right-from-bracket"></i> Đăng xuất</a>

        </div>
    </div>

    <div class="right">
        <div class="container">

            <div class="recommendation-box">
                <h1>Quản lý gợi ý sản phẩm</h1>

                <div class="process-box">
                    <h2>Thông tin dữ liệu gợi ý</h2>

                    <div class="stat-list">

                        <div class="stat-item">
                            <div class="stat-icon">
                                <i class="fa-solid fa-arrow-pointer"></i>
                            </div>

                            <div>
                                <p>Tổng số hành vi</p>
                                <h3>${totalInteractions}</h3>
                            </div>
                        </div>

                        <div class="stat-item">
                            <div class="stat-icon">
                                <i class="fa-solid fa-users"></i>
                            </div>

                            <div>
                                <p>Người dùng có hành vi</p>
                                <h3>${totalUsers}</h3>
                            </div>
                        </div>

                        <div class="stat-item">
                            <div class="stat-icon">
                                <i class="fa-solid fa-boxes-stacked"></i>
                            </div>

                            <div>
                                <p>Kết quả gợi ý hiện có</p>
                                <h3>${totalRecommendations}</h3>
                            </div>
                        </div>

                        <div class="stat-item">
                            <div class="stat-icon">
                                <i class="fa-solid fa-clock"></i>
                            </div>

                            <div>
                                <p>Cập nhật gần nhất</p>
                                <h3>${lastUpdatedTime}</h3>
                            </div>
                        </div>

                    </div>
                </div>
            </div>

        </div>
    </div>

</div>
</body>
</html>