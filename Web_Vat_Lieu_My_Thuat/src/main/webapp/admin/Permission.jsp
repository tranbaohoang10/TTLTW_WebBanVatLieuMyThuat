<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <title>Quản lý phân quyền</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
</head>

<style>
    #main {
        display: flex;
    }

    #main .left {
        background-color: #17479D;
        height: auto;
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
        border-left: #3B7DDD 2px solid;
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
        margin: 20px auto;
    }

    .permission-container {
        background: white;
        padding: 25px;
        border-radius: 10px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    }

    .permission-header {
        margin-bottom: 20px;
    }

    .permission-header h1 {
        margin: 0;
        color: #222;
    }

    .permission-header p {
        margin-top: 6px;
        color: #666;
        font-size: 14px;
    }

    .permission-layout {
        display: flex;
        gap: 20px;
        align-items: flex-start;
    }

    .group-box {
        width: 260px;
        background: #f8f9fa;
        padding: 15px;
        border-radius: 8px;
        border: 1px solid #ddd;
    }

    .group-box h3 {
        margin-top: 0;
        margin-bottom: 15px;
        text-align: left;
        padding: 0;
        color: #222;
    }

    .group-item {
        display: block;
        padding: 11px 12px;
        margin-bottom: 8px;
        background: white;
        border: 1px solid #ddd;
        text-decoration: none;
        color: #333;
        border-radius: 5px;
        font-size: 14px;
    }

    .group-item:hover {
        background: #e9ecef;
    }

    .group-item.active-group {
        background: #17479D;
        color: white;
        font-weight: bold;
        border-color: #17479D;
    }

    .permission-content {
        flex: 1;
    }

    .permission-content h2 {
        margin-top: 0;
        margin-bottom: 15px;
        color: #222;
        font-size: 22px;
    }

    .alert-success-custom {
        background: #d1e7dd;
        color: #0f5132;
        padding: 12px 16px;
        border-radius: 8px;
        margin-bottom: 16px;
        border: 1px solid #badbcc;
    }

    .permission-table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 10px;
    }

    .permission-table th {
        background: #2659F5;
        color: white;
        padding: 12px;
        font-size: 14px;
        text-align: left;
    }

    .permission-table td {
        padding: 14px 12px;
        border-bottom: 1px solid #ddd;
        background: #fafafa;
        vertical-align: top;
    }

    .permission-table tr:nth-child(even) td {
        background: #f0f0f0;
    }

    .permission-name {
        font-weight: bold;
        color: #222;
        width: 220px;
    }

    .checkbox-list {
        display: flex;
        flex-wrap: wrap;
        gap: 12px 25px;
    }

    .checkbox-list label {
        font-size: 14px;
        color: #333;
        cursor: pointer;
    }

    .checkbox-list input {
        margin-right: 6px;
        cursor: pointer;
    }

    .btn-save-permission {
        margin-top: 20px;
        background-color: #2659F5;
        border: none;
        color: white;
        padding: 10px 20px;
        font-size: 14px;
        border-radius: 5px;
        cursor: pointer;
    }

    .btn-save-permission:hover {
        background-color: #17479D;
    }

</style>

<body>

<c:set var="permissions" value="${sessionScope.permissions}" />
<c:set var="role" value="${sessionScope.currentUser.role}" />

<div id="main">
    <div class="left">
        <div class="list-admin">
            <a href="${pageContext.request.contextPath}/admin/overview" class="logo">
                <img src="${pageContext.request.contextPath}/assets/images/logo/logo.png" alt="">
            </a>

            <a href="${pageContext.request.contextPath}/admin/overview">
                <i class="fa-solid fa-house"></i>Tổng quan
            </a>

            <c:if test="${role == 'ADMIN' || permissions.contains('STATISTIC_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/statistics">
                    <i class="fa-solid fa-chart-line"></i>Thống kê
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('CATEGORY_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/categories">
                    <i class="fa-solid fa-list"></i>Quản lý danh mục
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('PRODUCT_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/products">
                    <i class="fa-solid fa-palette"></i>Quản lý sản phẩm
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('SUPPLIER_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/suppliers">
                    <i class="fa-solid fa-truck-field"></i>Nhà cung cấp
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('PURCHASE_RECEIPT_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/purchase-receipts">
                    <i class="fa-solid fa-file-invoice"></i>Phiếu nhập hàng
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('INVENTORY_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/inventory">
                    <i class="fa-solid fa-warehouse"></i>Quản lý tồn kho
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('USER_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/users">
                    <i class="fa-solid fa-person"></i>Quản lý người dùng
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('PERMISSION_MANAGE')}">
                <a href="${pageContext.request.contextPath}/admin/permissions" class="active">
                    <i class="fa-solid fa-user-shield"></i>Quản lý phân quyền
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('ORDER_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/orders">
                    <i class="fa-solid fa-box-open"></i>Quản lý đơn hàng
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('VOUCHER_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/vouchers">
                    <i class="fa-solid fa-gift"></i>Quản lý khuyến mãi
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('SLIDER_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/sliders">
                    <i class="fa-solid fa-sliders"></i>Quản lý Slider Show
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('CONTACT_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/contacts">
                    <i class="fa-solid fa-address-book"></i>Quản lý liên hệ
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('LOG_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/logs">
                    <i class="fa-solid fa-clock-rotate-left"></i>Quản lý thao tác
                </a>
            </c:if>

            <a href="${pageContext.request.contextPath}/logout">
                <i class="fa-solid fa-right-from-bracket"></i>Đăng xuất
            </a>
        </div>
    </div>

    <div class="right">
        <div class="container">
            <div class="permission-container">

                <div class="permission-header">
                    <h1>Quản lý phân quyền</h1>
                </div>

                <c:if test="${msg == 'success'}">
                    <div class="alert-success-custom">
                        Lưu phân quyền thành công. Nhân viên thuộc nhóm này cần đăng nhập lại để nhận quyền mới.
                    </div>
                </c:if>

                <div class="permission-layout">
                    <div class="group-box">
                        <h3>Nhóm nhân viên</h3>

                        <c:forEach var="group" items="${groups}">
                            <a class="group-item ${group.id == selectedGroupId ? 'active-group' : ''}"
                               href="${pageContext.request.contextPath}/admin/permissions?groupId=${group.id}">
                                <c:out value="${group.name}"/>
                            </a>
                        </c:forEach>
                    </div>

                    <div class="permission-content">
                        <h2>
                            Cấu hình quyền:
                            <c:out value="${selectedGroup.name}"/>
                        </h2>

                        <form method="post"
                              action="${pageContext.request.contextPath}/admin/permissions"
                              onsubmit="return confirm('Bạn chắc chắn muốn lưu phân quyền cho nhóm này không?')">

                            <input type="hidden" name="groupId" value="${selectedGroupId}">

                            <table class="permission-table">
                                <thead>
                                <tr>
                                    <th>Danh mục quản lý</th>
                                    <th>Hành động cho phép</th>
                                </tr>
                                </thead>

                                <tbody>
                                <tr>
                                    <td class="permission-name">Tổng quan</td>
                                    <td>
                                        <div class="checkbox-list">
                                            <label>
                                                <input type="checkbox" name="permissions" value="DASHBOARD_VIEW"
                                                ${groupPermissions.contains('DASHBOARD_VIEW') ? 'checked' : ''}>
                                                Xem
                                            </label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td class="permission-name">Thống kê</td>
                                    <td>
                                        <div class="checkbox-list">
                                            <label>
                                                <input type="checkbox" name="permissions" value="STATISTIC_VIEW"
                                                ${groupPermissions.contains('STATISTIC_VIEW') ? 'checked' : ''}>
                                                Xem
                                            </label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td class="permission-name">Quản lý danh mục</td>
                                    <td>
                                        <div class="checkbox-list">
                                            <label>
                                                <input type="checkbox" name="permissions" value="CATEGORY_VIEW"
                                                ${groupPermissions.contains('CATEGORY_VIEW') ? 'checked' : ''}>
                                                Xem
                                            </label>

                                            <label>
                                                <input type="checkbox" name="permissions" value="CATEGORY_CREATE"
                                                ${groupPermissions.contains('CATEGORY_CREATE') ? 'checked' : ''}>
                                                Thêm
                                            </label>

                                            <label>
                                                <input type="checkbox" name="permissions" value="CATEGORY_UPDATE"
                                                ${groupPermissions.contains('CATEGORY_UPDATE') ? 'checked' : ''}>
                                                Sửa
                                            </label>

                                            <label>
                                                <input type="checkbox" name="permissions" value="CATEGORY_LOCK"
                                                ${groupPermissions.contains('CATEGORY_LOCK') ? 'checked' : ''}>
                                                Khóa
                                            </label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td class="permission-name">Quản lý sản phẩm</td>
                                    <td>
                                        <div class="checkbox-list">
                                            <label>
                                                <input type="checkbox" name="permissions" value="PRODUCT_VIEW"
                                                ${groupPermissions.contains('PRODUCT_VIEW') ? 'checked' : ''}>
                                                Xem
                                            </label>

                                            <label>
                                                <input type="checkbox" name="permissions" value="PRODUCT_CREATE"
                                                ${groupPermissions.contains('PRODUCT_CREATE') ? 'checked' : ''}>
                                                Thêm
                                            </label>

                                            <label>
                                                <input type="checkbox" name="permissions" value="PRODUCT_UPDATE"
                                                ${groupPermissions.contains('PRODUCT_UPDATE') ? 'checked' : ''}>
                                                Sửa
                                            </label>

                                            <label>
                                                <input type="checkbox" name="permissions" value="PRODUCT_LOCK"
                                                ${groupPermissions.contains('PRODUCT_LOCK') ? 'checked' : ''}>
                                                Khóa
                                            </label>

                                            <label>
                                                <input type="checkbox" name="permissions" value="PRODUCT_IMPORT"
                                                ${groupPermissions.contains('PRODUCT_IMPORT') ? 'checked' : ''}>
                                                Import
                                            </label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td class="permission-name">Nhà cung cấp</td>
                                    <td>
                                        <div class="checkbox-list">
                                            <label>
                                                <input type="checkbox" name="permissions" value="SUPPLIER_VIEW"
                                                ${groupPermissions.contains('SUPPLIER_VIEW') ? 'checked' : ''}>
                                                Xem
                                            </label>

                                            <label>
                                                <input type="checkbox" name="permissions" value="SUPPLIER_CREATE"
                                                ${groupPermissions.contains('SUPPLIER_CREATE') ? 'checked' : ''}>
                                                Thêm
                                            </label>

                                            <label>
                                                <input type="checkbox" name="permissions" value="SUPPLIER_UPDATE"
                                                ${groupPermissions.contains('SUPPLIER_UPDATE') ? 'checked' : ''}>
                                                Sửa
                                            </label>

                                            <label>
                                                <input type="checkbox" name="permissions" value="SUPPLIER_LOCK"
                                                ${groupPermissions.contains('SUPPLIER_LOCK') ? 'checked' : ''}>
                                                Khóa
                                            </label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td class="permission-name">Phiếu nhập hàng</td>
                                    <td>
                                        <div class="checkbox-list">
                                            <label>
                                                <input type="checkbox" name="permissions" value="PURCHASE_RECEIPT_VIEW"
                                                ${groupPermissions.contains('PURCHASE_RECEIPT_VIEW') ? 'checked' : ''}>
                                                Xem
                                            </label>

                                            <label>
                                                <input type="checkbox" name="permissions" value="PURCHASE_RECEIPT_CREATE"
                                                ${groupPermissions.contains('PURCHASE_RECEIPT_CREATE') ? 'checked' : ''}>
                                                Thêm
                                            </label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td class="permission-name">Quản lý tồn kho</td>
                                    <td>
                                        <div class="checkbox-list">
                                            <label>
                                                <input type="checkbox" name="permissions" value="INVENTORY_VIEW"
                                                ${groupPermissions.contains('INVENTORY_VIEW') ? 'checked' : ''}>
                                                Xem
                                            </label>

                                            <label>
                                                <input type="checkbox" name="permissions" value="INVENTORY_IMPORT"
                                                ${groupPermissions.contains('INVENTORY_IMPORT') ? 'checked' : ''}>
                                                Nhập kho
                                            </label>

                                            <label>
                                                <input type="checkbox" name="permissions" value="INVENTORY_ADJUST"
                                                ${groupPermissions.contains('INVENTORY_ADJUST') ? 'checked' : ''}>
                                                Điều chỉnh
                                            </label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td class="permission-name">Quản lý người dùng</td>
                                    <td>
                                        <div class="checkbox-list">
                                            <label>
                                                <input type="checkbox" name="permissions" value="USER_VIEW"
                                                ${groupPermissions.contains('USER_VIEW') ? 'checked' : ''}>
                                                Xem
                                            </label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td class="permission-name">Quản lý đơn hàng</td>
                                    <td>
                                        <div class="checkbox-list">
                                            <label>
                                                <input type="checkbox" name="permissions" value="ORDER_VIEW"
                                                ${groupPermissions.contains('ORDER_VIEW') ? 'checked' : ''}>
                                                Xem
                                            </label>

                                            <label>
                                                <input type="checkbox" name="permissions" value="ORDER_DETAIL_VIEW"
                                                ${groupPermissions.contains('ORDER_DETAIL_VIEW') ? 'checked' : ''}>
                                                Chi tiết
                                            </label>

                                            <label>
                                                <input type="checkbox" name="permissions" value="ORDER_UPDATE_INFO"
                                                ${groupPermissions.contains('ORDER_UPDATE_INFO') ? 'checked' : ''}>
                                                Sửa thông tin
                                            </label>

                                            <label>
                                                <input type="checkbox" name="permissions" value="ORDER_UPDATE_STATUS"
                                                ${groupPermissions.contains('ORDER_UPDATE_STATUS') ? 'checked' : ''}>
                                                Cập nhật trạng thái
                                            </label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td class="permission-name">Quản lý khuyến mãi</td>
                                    <td>
                                        <div class="checkbox-list">
                                            <label>
                                                <input type="checkbox" name="permissions" value="VOUCHER_VIEW"
                                                ${groupPermissions.contains('VOUCHER_VIEW') ? 'checked' : ''}>
                                                Xem
                                            </label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td class="permission-name">Quản lý Slider Show</td>
                                    <td>
                                        <div class="checkbox-list">
                                            <label>
                                                <input type="checkbox" name="permissions" value="SLIDER_VIEW"
                                                ${groupPermissions.contains('SLIDER_VIEW') ? 'checked' : ''}>
                                                Xem
                                            </label>

                                            <label>
                                                <input type="checkbox" name="permissions" value="SLIDER_CREATE"
                                                ${groupPermissions.contains('SLIDER_CREATE') ? 'checked' : ''}>
                                                Thêm
                                            </label>

                                            <label>
                                                <input type="checkbox" name="permissions" value="SLIDER_UPDATE"
                                                ${groupPermissions.contains('SLIDER_UPDATE') ? 'checked' : ''}>
                                                Sửa
                                            </label>

                                            <label>
                                                <input type="checkbox" name="permissions" value="SLIDER_LOCK"
                                                ${groupPermissions.contains('SLIDER_LOCK') ? 'checked' : ''}>
                                                Khóa
                                            </label>

                                            <label>
                                                <input type="checkbox" name="permissions" value="SLIDER_DELETE"
                                                ${groupPermissions.contains('SLIDER_DELETE') ? 'checked' : ''}>
                                                Xóa
                                            </label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td class="permission-name">Quản lý liên hệ</td>
                                    <td>
                                        <div class="checkbox-list">
                                            <label>
                                                <input type="checkbox" name="permissions" value="CONTACT_VIEW"
                                                ${groupPermissions.contains('CONTACT_VIEW') ? 'checked' : ''}>
                                                Xem
                                            </label>

                                            <label>
                                                <input type="checkbox" name="permissions" value="CONTACT_REPLY_VIEW"
                                                ${groupPermissions.contains('CONTACT_REPLY_VIEW') ? 'checked' : ''}>
                                                Trả lời
                                            </label>

                                            <label>
                                                <input type="checkbox" name="permissions" value="CONTACT_DELETE"
                                                ${groupPermissions.contains('CONTACT_DELETE') ? 'checked' : ''}>
                                                Xóa
                                            </label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td class="permission-name">Quản lý thao tác</td>
                                    <td>
                                        <div class="checkbox-list">
                                            <label>
                                                <input type="checkbox" name="permissions" value="LOG_VIEW"
                                                ${groupPermissions.contains('LOG_VIEW') ? 'checked' : ''}>
                                                Xem
                                            </label>

                                            <label>
                                                <input type="checkbox" name="permissions" value="LOG_DETAIL_VIEW"
                                                ${groupPermissions.contains('LOG_DETAIL_VIEW') ? 'checked' : ''}>
                                                Chi tiết
                                            </label>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>

                            <button type="submit" class="btn-save-permission">
                                <i class="fa-solid fa-floppy-disk"></i>
                                Lưu phân quyền
                            </button>


                        </form>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

</body>
</html>