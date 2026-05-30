<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Lập phiếu nhập hàng</title>

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

        .alert-success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
            padding: 10px 12px;
            border-radius: 6px;
            margin-bottom: 15px;
        }

        .receipt-form {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 18px;
            margin-top: 20px;
        }

        .form-group {
            display: flex;
            flex-direction: column;
        }

        .form-group.full {
            grid-column: 1 / 3;
        }

        .form-group label {
            font-weight: 600;
            margin-bottom: 6px;
        }

        .form-group input,
        .form-group select,
        .form-group textarea {
            padding: 9px 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
        }

        .form-group textarea {
            min-height: 90px;
            resize: vertical;
        }

        .section-title {
            grid-column: 1 / 3;
            margin-top: 10px;
            padding-top: 18px;
            border-top: 1px solid #ddd;
            color: #17479D;
        }

        .product-placeholder {
            grid-column: 1 / 3;
            border: 2px dashed #bbb;
            border-radius: 8px;
            padding: 20px;
            background: #fafafa;
            color: #555;
            line-height: 1.7;
        }

        .btn-area {
            grid-column: 1 / 3;
            display: flex;
            justify-content: flex-end;
            gap: 10px;
            margin-top: 10px;
        }

        .btn-primary {
            background: #2659F5;
            color: white;
            border: none;
            padding: 10px 16px;
            border-radius: 6px;
            cursor: pointer;
        }

        .btn-primary:hover {
            background: #17479D;
        }

        .btn-secondary {
            background: #ccc;
            color: #222;
            text-decoration: none;
            padding: 10px 16px;
            border-radius: 6px;
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

            <c:if test="${role == 'ADMIN' || permissions.contains('SLIDER_VIEW')}">
                <a href="${ctx}/admin/sliders">
                    <i class="fa-solid fa-sliders"></i>Quản lý Slider Show
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('CONTACT_VIEW')}">
                <a href="${ctx}/admin/contacts">
                    <i class="fa-solid fa-address-book"></i>Quản lý liên hệ
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('LOG_VIEW')}">
                <a href="${ctx}/admin/logs">
                    <i class="fa-solid fa-clock-rotate-left"></i>Quản lý thao tác
                </a>
            </c:if>

            <a href="${ctx}/logout">
                <i class="fa-solid fa-right-from-bracket"></i>Đăng xuất
            </a>
        </div>
    </div>

    <div class="right">
        <div class="container">
            <div class="box">
                <h1>Lập phiếu nhập hàng</h1>


                <c:if test="${not empty sessionScope.purchaseReceiptMessage}">
                    <div class="alert-success">${sessionScope.purchaseReceiptMessage}</div>
                    <c:remove var="purchaseReceiptMessage" scope="session"/>
                </c:if>

                <form class="receipt-form"
                      method="post"
                      action="${ctx}/admin/purchase-receipts">

                    <div class="form-group">
                        <label>Nhà cung cấp</label>
                        <select name="supplierID" required>
                            <option value="">-- Chọn nhà cung cấp --</option>

                            <c:forEach var="s" items="${activeSuppliers}">
                                <option value="${s.id}">
                                        ${s.supplierCode} - ${s.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label>Ngày nhập hàng</label>
                        <input type="datetime-local"
                               name="importDate"
                               required>
                    </div>

                    <div class="form-group">
                        <label>Mã chứng từ từ nhà cung cấp</label>
                        <input type="text"
                               name="supplierDocumentCode"
                               maxlength="100"
                               placeholder="Ví dụ: HD-NCC-001">
                    </div>

                    <div class="form-group">
                        <label>Người nhập</label>
                        <input type="text"
                               value="${currentImporterName}"
                               readonly>
                    </div>

                    <div class="form-group full">
                        <label>Ghi chú</label>
                        <textarea name="note"
                                  placeholder="Nhập ghi chú cho phiếu nhập nếu có"></textarea>
                    </div>

                    <h2 class="section-title">Danh sách sản phẩm nhập</h2>

                    <div class="product-placeholder">
                        Khu vực thêm sản phẩm nhập sẽ được phát triển ở issue tiếp theo.
                        <br>
                        Mỗi dòng sản phẩm sẽ gồm:
                        <strong>sản phẩm, số lượng nhập, giá nhập và thành tiền.</strong>
                    </div>

                    <div class="btn-area">
                        <a class="btn-secondary" href="${ctx}/admin/inventory">
                            Quay lại tồn kho
                        </a>

                        <button class="btn-primary" type="submit">
                            Lưu thông tin phiếu nhập
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>