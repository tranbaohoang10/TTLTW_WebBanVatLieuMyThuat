<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="r" value="${purchaseReceipt}" />

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết phiếu nhập hàng</title>

    <link rel="stylesheet" href="${ctx}/assets/css/style.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css">

    <style>
        #main {
            display: flex;
        }

        #main .left {
            background-color: #17479D;
            min-height: 100vh;
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
            background: #F9F9F9;
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

        .muted {
            color: #666;
            font-size: 14px;
            margin-top: 6px;
        }

        .info-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px 25px;
            margin-top: 20px;
        }

        .info-item {
            background: #f8f9fa;
            border: 1px solid #ddd;
            border-radius: 6px;
            padding: 12px;
        }

        .info-label {
            font-weight: 600;
            color: #555;
            margin-bottom: 5px;
        }

        .info-value {
            color: #222;
        }

        .receipt-detail-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        .receipt-detail-table th {
            background: #2659F5;
            color: white;
            padding: 11px;
            font-size: 14px;
        }

        .receipt-detail-table td {
            border: 1px solid #ddd;
            padding: 10px;
            background: #fff;
            vertical-align: middle;
        }

        .receipt-detail-table tr:nth-child(even) td {
            background: #f7f7f7;
        }

        .text-right {
            text-align: right;
        }

        .total-row td {
            font-weight: bold;
            background: #eef3ff !important;
            color: #17479D;
        }

        .btn-back {
            display: inline-block;
            background: #6c757d;
            color: white;
            text-decoration: none;
            padding: 9px 14px;
            border-radius: 6px;
            margin-bottom: 15px;
        }

        .btn-back:hover {
            background: #565e64;
        }

        .status-completed {
            color: #28a745;
            font-weight: 600;
        }

        .status-draft {
            color: #ff9800;
            font-weight: 600;
        }

        .empty-message {
            margin-top: 15px;
            padding: 14px;
            background: #f8f9fa;
            border: 1px dashed #bbb;
            border-radius: 6px;
            color: #666;
        }
        .status-badge {
            display: inline-block;
            padding: 6px 10px;
            border-radius: 20px;
            font-size: 13px;
            font-weight: 600;
        }

        .status-success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
    </style>
</head>

<body>
<c:set var="permissions" value="${sessionScope.permissions}" />
<c:set var="role" value="${sessionScope.currentUser.role}" />

<div id="main">
    <div class="left">
        <div class="list-admin">
            <a href="${ctx}/admin/overview" class="logo">
                <img src="${ctx}/assets/images/logo/logo.png" alt="">
            </a>

            <a href="${ctx}/admin/overview">
                <i class="fa-solid fa-house"></i>Tổng quan
            </a>

            <c:if test="${role == 'ADMIN' || permissions.contains('STATISTIC_VIEW')}">
                <a href="${ctx}/admin/statistics">
                    <i class="fa-solid fa-chart-line"></i>Thống kê
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('CATEGORY_VIEW')}">
                <a href="${ctx}/admin/categories">
                    <i class="fa-solid fa-list"></i>Quản lý danh mục
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('PRODUCT_VIEW')}">
                <a href="${ctx}/admin/products">
                    <i class="fa-solid fa-palette"></i>Quản lý sản phẩm
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('INVENTORY_VIEW')}">
                <a href="${ctx}/admin/inventory">
                    <i class="fa-solid fa-warehouse"></i>Quản lý tồn kho
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('SUPPLIER_VIEW')}">
                <a href="${ctx}/admin/suppliers">
                    <i class="fa-solid fa-truck-field"></i>Nhà cung cấp
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('PURCHASE_RECEIPT_VIEW')}">
                <a href="${ctx}/admin/purchase-receipts" class="active">
                    <i class="fa-solid fa-file-invoice"></i>Phiếu nhập hàng
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('USER_VIEW')}">
                <a href="${ctx}/admin/users">
                    <i class="fa-solid fa-person"></i>Quản lý người dùng
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('ORDER_VIEW')}">
                <a href="${ctx}/admin/orders">
                    <i class="fa-solid fa-box-open"></i>Quản lý đơn hàng
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('VOUCHER_VIEW')}">
                <a href="${ctx}/admin/vouchers">
                    <i class="fa-solid fa-gift"></i>Quản lý khuyến mãi
                </a>
            </c:if>

            <a href="${ctx}/logout">
                <i class="fa-solid fa-right-from-bracket"></i>Đăng xuất
            </a>
        </div>
    </div>

    <div class="right">
        <div class="container">
            <a class="btn-back" href="${ctx}/admin/purchase-receipts">
                <i class="fa-solid fa-arrow-left"></i> Quay lại danh sách phiếu nhập
            </a>

            <div class="box">
                <h1>Chi tiết phiếu nhập #${r.id}</h1>
                <div class="muted">
                    Thông tin chung và danh sách sản phẩm đã nhập trong phiếu.
                </div>

                <div class="info-grid">
                    <div class="info-item">
                        <div class="info-label">Mã phiếu nhập</div>
                        <div class="info-value">#${r.id}</div>
                    </div>

                    <div class="info-item">
                        <div class="info-label">Nhà cung cấp</div>
                        <div class="info-value">
                            <c:out value="${r.supplierName}" />
                        </div>
                    </div>

                    <div class="info-item">
                        <div class="info-label">Ngày nhập</div>
                        <div class="info-value">
                            <fmt:formatDate value="${r.importDate}" pattern="dd/MM/yyyy HH:mm" />
                        </div>
                    </div>

                    <div class="info-item">
                        <div class="info-label">Người nhập</div>
                        <div class="info-value">
                            <c:choose>
                                <c:when test="${not empty r.createdByName}">
                                    <c:out value="${r.createdByName}" />
                                </c:when>
                                <c:otherwise>Admin</c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <div class="info-item">
                        <div class="info-label">Trạng thái</div>
                        <div class="info-value">
                            <span class="status-badge status-success">Thành công</span>
                        </div>
                    </div>

                    <div class="info-item">
                        <div class="info-label">Tổng tiền phiếu nhập</div>
                        <div class="info-value">
                            <fmt:formatNumber value="${r.totalAmount}" type="number" groupingUsed="true" /> đ
                        </div>
                    </div>

                    <div class="info-item" style="grid-column: 1 / 3;">
                        <div class="info-label">Ghi chú</div>
                        <div class="info-value">
                            <c:choose>
                                <c:when test="${not empty r.note}">
                                    <c:out value="${r.note}" />
                                </c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>

            <div class="box">
                <h1>Danh sách sản phẩm nhập</h1>

                <c:choose>
                    <c:when test="${empty purchaseReceiptDetails}">
                        <div class="empty-message">
                            Phiếu nhập này chưa có sản phẩm nào.
                        </div>
                    </c:when>

                    <c:otherwise>
                        <table class="receipt-detail-table">
                            <thead>
                            <tr>
                                <th>STT</th>
                                <th>Sản phẩm</th>
                                <th>Số lượng nhập</th>
                                <th>Giá nhập</th>
                                <th>Thành tiền</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach var="d" items="${purchaseReceiptDetails}" varStatus="loop">
                                <tr>
                                    <td>${loop.index + 1}</td>

                                    <td>
                                        <c:out value="${d.productName}" />
                                    </td>

                                    <td class="text-right">
                                        <fmt:formatNumber value="${d.quantity}" type="number" groupingUsed="true" />
                                    </td>

                                    <td class="text-right">
                                        <fmt:formatNumber value="${d.importPrice}" type="number" groupingUsed="true" /> đ
                                    </td>

                                    <td class="text-right">
                                        <fmt:formatNumber value="${d.lineTotal}" type="number" groupingUsed="true" /> đ
                                    </td>
                                </tr>
                            </c:forEach>

                            <tr class="total-row">
                                <td colspan="4" class="text-right">Tổng tiền</td>
                                <td class="text-right">
                                    <fmt:formatNumber value="${r.totalAmount}" type="number" groupingUsed="true" /> đ
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

</body>
</html>