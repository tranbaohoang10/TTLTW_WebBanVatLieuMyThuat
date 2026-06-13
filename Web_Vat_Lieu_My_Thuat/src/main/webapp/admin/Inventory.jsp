<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý tồn kho</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.8/css/jquery.dataTables.min.css"/>

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.8/js/jquery.dataTables.min.js"></script>

    <style>
        #main {
            display: flex;
        }

        #main .left {
            background-color: #17479D;
            height: auto;
            width: 17%;
            position: sticky;
            top: 0;
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
            min-height: 100vh;
        }

        .container {
            width: 95%;
            margin: 30px auto;
        }

        .box {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0,0,0,.1);
            margin-bottom: 30px;
        }

        h1 {
            margin-top: 0;
            color: #17479D;
        }

        table {
            width: 100%;
        }

        th {
            background: #2659F5;
            color: white;
            padding: 12px;
            font-size: 14px;
        }

        td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
            background: #fafafa;
        }

        .badge {
            padding: 5px 8px;
            border-radius: 5px;
            font-weight: bold;
            display: inline-block;
        }

        .badge-danger {
            background: #dc3545;
            color: white;
        }

        .badge-warning {
            background: #ffc107;
            color: #222;
        }

        .badge-success {
            background: #28a745;
            color: white;
        }

        .form-inline {
            display: flex;
            gap: 6px;
            align-items: center;
        }

        .form-inline input {
            padding: 6px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        button {
            border: none;
            border-radius: 5px;
            padding: 7px 10px;
            cursor: pointer;
            color: white;
            white-space: nowrap;
        }

        .btn-import {
            background: #28a745;
        }

        .btn-adjust {
            background: #ffc107;
            color: #222;
        }

        .inventory-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 15px;
            margin-bottom: 15px;
        }

        .report-actions {
            display: flex;
            gap: 10px;
            align-items: center;
        }

        .btn-report {
            background: #198754;
        }

        .btn-open-sheet {
            background: #0d6efd;
            color: white;
            text-decoration: none;
            padding: 8px 12px;
            border-radius: 5px;
            display: inline-block;
            white-space: nowrap;
        }

        .btn-open-sheet:hover {
            background: #0b5ed7;
        }

        .alert-success {
            background: #d4edda;
            color: #155724;
            padding: 10px;
            border-radius: 6px;
            margin-bottom: 15px;
        }

        .alert-error {
            background: #f8d7da;
            color: #721c24;
            padding: 10px;
            border-radius: 6px;
            margin-bottom: 15px;
        }
        .stock-alert-section {
            margin-top: 10px;
        }

        .stock-alert-title {
            font-weight: bold;
            margin-bottom: 5px;
        }

        .stock-alert-list {
            margin: 5px 0 0 18px;
            padding: 0;
        }

        .stock-alert-list li {
            margin-bottom: 4px;
        }
        .stock-alert-box {
            background: #fff3cd;
            color: #856404;
            border: 1px solid #ffeeba;
            padding: 12px 14px;
            border-radius: 6px;
            margin-bottom: 15px;
        }

        .stock-alert-box strong {
            color: #6b4f00;
        }

        .danger-text {
            color: #dc3545;
            font-weight: bold;
        }

        .warning-text {
            color: #ff9800;
            font-weight: bold;
        }
    </style>
</head>

<body>
<c:set var="permissions" value="${sessionScope.permissions}"/>
<c:set var="role" value="${sessionScope.currentUser.role}"/>
<div id="main">
    <div class="left">
        <div class="list-admin">
            <a href="${pageContext.request.contextPath}/admin/Admin.jsp" class="logo">
                <img src="${pageContext.request.contextPath}/assets/images/logo/logo.png" alt="">
            </a>

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
            <c:if test="${role == 'ADMIN' || permissions.contains('SUPPLIER_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/suppliers"><i class="fa-solid fa-truck-field"></i>Nhà cung cấp
                </a>
            </c:if>
            <c:if test="${role == 'ADMIN' || permissions.contains('PURCHASE_RECEIPT_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/purchase-receipts">
                    <i class="fa-solid fa-file-invoice"></i>Phiếu nhập hàng
                </a>
            </c:if>
            <c:if test="${role == 'ADMIN' || permissions.contains('INVENTORY_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/inventory" class="active"><i class="fa-solid fa-warehouse"></i>Quản
                    lý tồn kho</a>
            </c:if>
            <c:if test="${role == 'ADMIN' || permissions.contains('USER_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/users" ><i class="fa-solid fa-person"></i>Quản lý người dùng</a>
            </c:if>
            <c:if test="${role == 'ADMIN' || permissions.contains('PERMISSION_MANAGE')}">
                <a href="${pageContext.request.contextPath}/admin/permissions" >
                    <i class="fa-solid fa-user-shield"></i>Quản lý phân quyền
                </a>
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
                <a href="${pageContext.request.contextPath}/admin/contacts" ><i class="fa-solid fa-address-book"></i>Quản lý
                    liên hệ</a>
            </c:if>
            <c:if test="${role == 'ADMIN' || permissions.contains('LOG_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/logs">
                    <i class="fa-solid fa-clock-rotate-left"></i>Quản lý thao tác
                </a>
            </c:if>
            <a href="${pageContext.request.contextPath}/logout"><i class="fa-solid fa-right-from-bracket"></i>Đăng xuất</a>
        </div>
    </div>

    <div class="right">
        <div class="container">

            <c:if test="${not empty sessionScope.inventoryMessage}">
                <div class="alert-success">${sessionScope.inventoryMessage}</div>
                <c:remove var="inventoryMessage" scope="session"/>
            </c:if>

            <c:if test="${not empty sessionScope.inventoryError}">
                <div class="alert-error">${sessionScope.inventoryError}</div>
                <c:remove var="inventoryError" scope="session"/>
            </c:if>
            <c:if test="${outOfStockCount > 0 || lowStockCount > 0}">
                <div class="stock-alert-box">
                    <strong>Cảnh báo tồn kho:</strong>
                    Có <span class="danger-text">${outOfStockCount}</span> sản phẩm đã hết hàng
                    và <span class="warning-text">${lowStockCount}</span> sản phẩm sắp hết hàng
                    với ngưỡng cảnh báo là ${lowStockThreshold}.

                    <c:if test="${not empty outOfStockProducts}">
                        <div class="stock-alert-section">
                            <div class="stock-alert-title danger-text">Sản phẩm hết hàng:</div>

                            <ul class="stock-alert-list">
                                <c:forEach var="p" items="${outOfStockProducts}">
                                    <li>
                                        ID ${p.id} - <c:out value="${p.name}" />
                                        <span class="danger-text">(Tồn kho: ${p.quantityStock})</span>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>

                    <c:if test="${not empty lowStockProducts}">
                        <div class="stock-alert-section">
                            <div class="stock-alert-title warning-text">Sản phẩm sắp hết hàng:</div>

                            <ul class="stock-alert-list">
                                <c:forEach var="p" items="${lowStockProducts}">
                                    <li>
                                        ID ${p.id} - <c:out value="${p.name}" />
                                        <span class="warning-text">(Tồn kho: ${p.quantityStock})</span>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>
                </div>
            </c:if>
            <div class="box">
                <div class="inventory-header">
                    <h1>Quản lý tồn kho</h1>

                    <div class="report-actions">
                        <form method="post"
                              action="${pageContext.request.contextPath}/admin/inventory"
                              onsubmit="return confirm('Bạn muốn cập nhật báo cáo tồn kho lên Google Sheet hôm nay?')">
                            <input type="hidden" name="action" value="updateGoogleSheetReport">
                            <button type="submit" class="btn-report">
                                <i class="fa-solid fa-table"></i> Cập nhật Google Sheet
                            </button>
                        </form>

                        <a class="btn-open-sheet"
                           href="${googleSheetUrl}"
                           target="_blank">
                            <i class="fa-solid fa-arrow-up-right-from-square"></i> Mở Google Sheet
                        </a>
                    </div>
                </div>

                <table id="inventoryTable" class="display">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Tên sản phẩm</th>
                        <th>Tồn kho</th>
                        <th>Đã bán</th>
                        <th>Trạng thái kho</th>
                        <th>Nhập kho</th>
                        <th>Chỉnh tồn</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="p" items="${products}">
                        <tr>
                            <td>${p.id}</td>
                            <td>${p.name}</td>
                            <td>${p.quantityStock}</td>
                            <td>${p.soldQuantity}</td>

                            <td>
                                <c:choose>
                                    <c:when test="${p.quantityStock == 0}">
                                        <span class="badge badge-danger">Hết hàng</span>
                                    </c:when>
                                    <c:when test="${p.quantityStock <= 10}">
                                        <span class="badge badge-warning">Sắp hết</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge badge-success">Còn hàng</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <form class="form-inline" method="post" action="${pageContext.request.contextPath}/admin/inventory">
                                    <input type="hidden" name="action" value="importStock">
                                    <input type="hidden" name="productId" value="${p.id}">
                                    <input type="number" name="quantity" min="1" required placeholder="SL" style="width:70px;">
                                    <input type="text" name="note" placeholder="Ghi chú" style="width:130px;">
                                    <button type="submit" class="btn-import">Nhập</button>
                                </form>
                            </td>

                            <td>
                                <form class="form-inline" method="post" action="${pageContext.request.contextPath}/admin/inventory"
                                      onsubmit="return confirm('Bạn chắc muốn chỉnh tồn kho sản phẩm này?')">
                                    <input type="hidden" name="action" value="adjustStock">
                                    <input type="hidden" name="productId" value="${p.id}">
                                    <input type="number" name="newStock" min="0" required placeholder="Tồn mới" style="width:90px;">
                                    <input type="text" name="note" placeholder="Lý do" style="width:130px;">
                                    <button type="submit" class="btn-adjust">Chỉnh</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="box">
                <h1>Lịch sử biến động tồn kho</h1>

                <table id="historyTable" class="display">
                    <thead>
                    <tr>
                        <th>Thời gian</th>
                        <th>Sản phẩm</th>
                        <th>Loại</th>
                        <th>Số lượng</th>
                        <th>Trước</th>
                        <th>Sau</th>
                        <th>Đơn hàng</th>
                        <th>Ghi chú</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="h" items="${history}">
                        <tr>
                            <td>${h.createAt}</td>
                            <td>${h.productName}</td>

                            <td>
                                <c:choose>
                                    <c:when test="${h.type == 'IMPORT'}">Nhập kho</c:when>
                                    <c:when test="${h.type == 'SALE'}">Bán hàng</c:when>
                                    <c:when test="${h.type == 'CANCEL'}">Hủy đơn</c:when>
                                    <c:when test="${h.type == 'ADJUST'}">Chỉnh kho</c:when>
                                    <c:otherwise>${h.type}</c:otherwise>
                                </c:choose>
                            </td>

                            <td>${h.quantity}</td>
                            <td>${h.beforeStock}</td>
                            <td>${h.afterStock}</td>

                            <td>
                                <c:choose>
                                    <c:when test="${not empty h.orderId}">
                                        #DH${h.orderId}
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>

                            <td>${h.note}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

        </div>
    </div>
</div>

<script>
    $(function () {
        const viUrl = "https://cdn.datatables.net/plug-ins/1.13.8/i18n/vi.json";

        $("#inventoryTable").DataTable({
            pageLength: 10,
            lengthChange: false,
            ordering: true,
            searching: true,
            info: false,
            language: { url: viUrl }
        });

        $("#historyTable").DataTable({
            pageLength: 10,
            lengthChange: false,
            ordering: true,
            searching: true,
            info: false,
            order: [],
            language: { url: viUrl }
        });
    });
</script>

</body>
</html>