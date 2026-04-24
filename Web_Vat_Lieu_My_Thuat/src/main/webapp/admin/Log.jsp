<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Quản lý log</title>

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

        #main .left .list-admin a.logo:hover {
            background-color: #203247;
            border-left: none;
        }

        .list-admin a.active {
            background-color: #203247;
            border-left: 4px solid #FFD700;
            font-weight: bold;
        }

        #main .right {
            flex: 1;
            background-color: #F9F9F9;
        }

        #main .right .container {
            width: calc(100% - 100px);
            margin: 20px auto 0;
        }

        .log-container {
            width: 95%;
            margin: 30px auto;
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        .log-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
        }

        .log-header h1 {
            margin: 0;
            color: #222;
        }

        .log-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }

        .log-table th {
            background: #2659F5;
            color: white;
            padding: 12px;
            font-size: 14px;
        }

        .log-table td {
            padding: 12px;
            background: #fafafa;
        }

        .log-table tr:nth-child(even) td {
            background: #f0f0f0;
        }

        .log-table td,
        .log-table th {
            border-bottom: 1px solid #ddd;
            text-align: left;
            border-left: 1px solid #ddd;
            border-right: 1px solid #ddd;
        }

        .btn-detail {
            background-color: #2659F5;
            color: white;
            padding: 8px 12px;
            border-radius: 5px;
            text-decoration: none;
            display: inline-block;
        }

        .btn-detail:hover {
            opacity: 0.8;
        }

        .text-center {
            text-align: center;
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

            <a href="${pageContext.request.contextPath}/admin/overview">
                <i class="fa-solid fa-house"></i>Tổng quan
            </a>

            <a href="${pageContext.request.contextPath}/admin/statistics">
                <i class="fa-solid fa-chart-line"></i>Thống kê
            </a>

            <a href="${pageContext.request.contextPath}/admin/categories">
                <i class="fa-solid fa-list"></i>Quản lý danh mục
            </a>

            <a href="${pageContext.request.contextPath}/admin/products">
                <i class="fa-solid fa-palette"></i>Quản lý sản phẩm
            </a>

            <a href="${pageContext.request.contextPath}/admin/users">
                <i class="fa-solid fa-person"></i>Quản lý người dùng
            </a>

            <a href="${pageContext.request.contextPath}/admin/orders">
                <i class="fa-solid fa-box-open"></i>Quản lý đơn hàng
            </a>

            <a href="${pageContext.request.contextPath}/admin/vouchers">
                <i class="fa-solid fa-gift"></i>Quản lý khuyến mãi
            </a>

            <a href="${pageContext.request.contextPath}/admin/sliders">
                <i class="fa-solid fa-sliders"></i>Quản lý Slider Show
            </a>

            <a href="${pageContext.request.contextPath}/admin/contacts">
                <i class="fa-solid fa-address-book"></i>Quản lý liên hệ
            </a>

            <a href="${pageContext.request.contextPath}/admin/logs" class="active">
                <i class="fa-solid fa-clock-rotate-left"></i>Quản lý thao tác
            </a>

            <a href="${pageContext.request.contextPath}/logout">
                <i class="fa-solid fa-right-from-bracket"></i>Đăng xuất
            </a>
        </div>
    </div>

    <div class="right">
        <div class="container">
            <div class="log-container">
                <div class="log-header">
                    <h1>Quản lý thao tác</h1>
                </div>

                <table class="log-table">
                    <thead>
                    <tr>
                        <th>STT</th>
                        <th>Hành động</th>
                        <th>Người thực hiện</th>
                        <th>Thời gian</th>
                        <th>Chức năng</th>
                        <th>Chi tiết</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="log" items="${logs}" varStatus="st">
                        <tr>
                            <td>${st.index + 1}</td>

                            <td>${log.label}</td>

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

                            <td>
                                <fmt:formatDate value="${log.time}" pattern="dd/MM/yyyy HH:mm:ss"/>
                            </td>

                            <td>${log.location}</td>

                            <td class="text-center">
                                <a href="#" class="btn-detail">
                                    <i class="fa-solid fa-eye"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>

                    <c:if test="${empty logs}">
                        <tr>
                            <td colspan="6" class="text-center">Chưa có log nào.</td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>