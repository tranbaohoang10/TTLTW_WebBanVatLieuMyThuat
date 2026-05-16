<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết thao tác</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css">

    <style>
        #main {
            display: flex;
        }

        #main .left {
            background-color: #17479D;
            height: 100vh;
            width: 17%;
        }

        #main .left .list-admin {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        #main .left .list-admin a {
            display: block;
            text-decoration: none;
            color: white;
            padding: 10px 20px;
        }

        #main .left .list-admin a i {
            margin-right: 20px;
        }

        #main .left .list-admin a:hover {
            background-color: #203247;
            border-left: 2px solid #3B7DDD;
        }

        #main .left .list-admin .logo img {
            width: 100%;
            height: auto;
            margin: 10px 0 20px 0;
        }

        #main .left .list-admin a.logo {
            padding: 0;
        }

        .list-admin a.active {
            background-color: #203247;
            border-left: 4px solid #FFD700;
            font-weight: bold;
        }

        #main .right {
            flex: 1;
            background-color: #F9F9F9;
            min-height: 100vh;
        }

        #main .right .container {
            width: calc(100% - 100px);
            margin: 20px auto 0;
        }

        .detail-container {
            width: 95%;
            margin: 30px auto;
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        .detail-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
        }

        .detail-header h1 {
            margin: 0;
        }

        .btn-back {
            background-color: #2659F5;
            color: white;
            padding: 9px 14px;
            border-radius: 5px;
            text-decoration: none;
        }

        .btn-back:hover {
            opacity: 0.8;
        }

        .info-box {
            margin-bottom: 25px;
        }

        .info-box h3 {
            margin-bottom: 12px;
            color: #222;
        }

        .info-table {
            width: 100%;
            border-collapse: collapse;
        }

        .info-table td {
            padding: 12px;
            border: 1px solid #ddd;
        }

        .info-table .label {
            width: 200px;
            font-weight: bold;
            background-color: #f0f0f0;
        }

        pre {
            background-color: #f3f3f3;
            padding: 15px;
            border-radius: 6px;
            white-space: pre-wrap;
            word-break: break-word;
            font-size: 14px;
            line-height: 1.5;
        }

        .empty-text {
            color: #777;
            font-style: italic;
        }
    </style>
</head>

<body>
<div id="main">
    <div class="left">
        <div class="list-admin">
            <a href="${pageContext.request.contextPath}/admin/overview" class="logo">
                <img src="${pageContext.request.contextPath}/assets/images/logo/logo.png" alt="">
            </a>

            <a href="${pageContext.request.contextPath}/admin/overview" ><i
                    class="fa-solid fa-house"></i>
                Tổng quan</a>
            <c:if test="${sessionScope.currentUser.role eq 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/admin/statistics"><i class="fa-solid fa-chart-line"></i>Thống
                    kê</a>
                <a href="${pageContext.request.contextPath}/admin/categories"><i class="fa-solid fa-list"></i>Quản lý danh
                    mục</a>
            </c:if>
            <a href="${pageContext.request.contextPath}/admin/products"><i class="fa-solid fa-palette"></i>Quản
                lý sản phẩm</a>
            <c:if test="${sessionScope.currentUser.role eq 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/admin/users" ><i class="fa-solid fa-person"></i>Quản lý người dùng</a>
            </c:if>
            <a href="${pageContext.request.contextPath}/admin/orders"><i class="fa-solid fa-box-open"></i>Quản
                lý đơn hàng</a>
            <c:if test="${sessionScope.currentUser.role eq 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/admin/vouchers"><i class="fa-solid fa-gift"></i>Quản lý
                    khuyến mãi</a>
                <a href="${pageContext.request.contextPath}/admin/sliders"><i class="fa-solid fa-sliders"></i>Quản lý
                    Slider Show</a>
            </c:if>
            <a href="${pageContext.request.contextPath}/admin/contacts"><i class="fa-solid fa-address-book"></i>Quản lý
                liên hệ</a>
            <c:if test="${sessionScope.currentUser.role eq 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/admin/logs" class="active">
                    <i class="fa-solid fa-clock-rotate-left" ></i>Quản lý thao tác
                </a>
            </c:if>

            <a href="${pageContext.request.contextPath}/logout">
                <i class="fa-solid fa-right-from-bracket"></i>Đăng xuất
            </a>
        </div>
    </div>

    <div class="right">
        <div class="container">
            <div class="detail-container">
                <div class="detail-header">
                    <h1>Chi tiết thao tác</h1>

                    <a class="btn-back" href="${pageContext.request.contextPath}/admin/logs">
                        <i class="fa-solid fa-arrow-left"></i> Quay lại
                    </a>
                </div>

                <div class="info-box">
                    <h3>Thông tin chung</h3>

                    <table class="info-table">
                        <tr>
                            <td class="label">Mã log</td>
                            <td>${log.id}</td>
                        </tr>

                        <tr>
                            <td class="label">Hành động</td>
                            <td>${log.label}</td>
                        </tr>

                        <tr>
                            <td class="label">Người thực hiện</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty log.userName}">
                                        ${log.userName}
                                    </c:when>
                                    <c:otherwise>
                                        User ID: ${log.userId}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>

                        <tr>
                            <td class="label">Thời gian</td>
                            <td>
                                <fmt:formatDate value="${log.time}" pattern="dd/MM/yyyy HH:mm:ss"/>
                            </td>
                        </tr>

                        <tr>
                            <td class="label">Chức năng</td>
                            <td>${log.location}</td>
                        </tr>
                    </table>
                </div>

                <div class="info-box">
                    <h3>Dữ liệu trước khi thay đổi</h3>

                    <c:choose>
                        <c:when test="${not empty log.beforeData}">
                            <pre>${beforeData}</pre>
                        </c:when>
                        <c:otherwise>
                            <p class="empty-text">Không có dữ liệu trước khi thay đổi.</p>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="info-box">
                    <h3>Dữ liệu sau khi thay đổi</h3>

                    <c:choose>
                        <c:when test="${not empty log.afterData}">
                            <pre>${afterData}</pre>
                        </c:when>
                        <c:otherwise>
                            <p class="empty-text">Không có dữ liệu sau khi thay đổi.</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>