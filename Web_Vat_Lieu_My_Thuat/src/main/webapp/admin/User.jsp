<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý người dùng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.8/css/jquery.dataTables.min.css"/>

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.8/js/jquery.dataTables.min.js"></script>
</head>
<style>
    #main {
        display: flex;
    }

    #main .left {
        background-color: #17479D;
        height: auto;
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

    #main .right .container {
        display: flex;
        flex-direction: column;
        width: calc(100% - 100px);
        margin-top: 20px;
        margin-left: auto;
        margin-right: auto;
    }


    #main .right {
        flex: 1;
        background-color: #F9F9F9;
    }

    #main .right .container .dashboard {
        display: flex;
        justify-content: space-between;
        align-items: center;
        flex-wrap: wrap;
        padding: 20px 10px;
        background-color: white;
        border-radius: 5px;
    }

    #main .right .container .dashboard h1 {
        width: 100%;
    }

    #main .right .container .dashboard .total-box {
        background-color: #17479D;
        color: white;
        border-radius: 5px;
    }

    #main .right .container .dashboard .total-box hr {
        border: 1px solid #ffffff33;
        width: 80%;
        margin-left: auto;
        margin-right: auto;
    }

    #main .right .container .dashboard .total-box h2,
    h3 {
        padding: 10px 20px;
        text-align: center;
    }

    .order-container {
        width: 95%;
        margin: 10px auto 5px;
        background: white;
        padding: 25px;
        border-radius: 10px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    }


    .search {
        display: flex;
        align-items: center;
        justify-content: end;
        gap: 10px;
        width: 100%;
        margin: 0 auto;
        margin-bottom: 15px;
    }

    .search-input-icon {
        display: flex;
        align-items: center;
        border: 1px solid #ddd;
        border-radius: 10px;
        overflow: hidden;
        background: #fff;
    }

    .search-input-icon input {
        padding: 10px 15px;
        border: none;
        outline: none;
        width: 250px;
        font-size: 14px;
    }

    .icon {
        background: #f1f1f1;
        padding: 10px 15px;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        border: none;
    }

    .icon i {
        font-size: 16px;
        color: #333;
    }

    .btn-Sua {
        background-color: #FFC107;
        color: black;
        border: none;
        padding: 6px 10px;
        cursor: pointer;
        font-size: 14px;
        border-radius: 4px;
        transition: 0.2s;
    }

    .btn-Sua:hover {
        background-color: #e0a800;
    }
    .btn-Xoa {
        background-color: #DC3545;
        color: white;
        border: none;
        padding: 6px 10px;
        cursor: pointer;
        font-size: 14px;
        border-radius: 4px;
        transition: 0.2s;
    }

    .btn-Xoa:hover {
        background-color: #b02a37;
    }
    #lockConfirmModal .btn-lock-cancel{
        border: none;
        background: #e0e0e0;
        border-radius: 5px;
        cursor: pointer;
    }
    .btn-unlock{
        background-color: #FFC107;
        color: black;
        border: none;
        padding: 6px 10px;
        cursor: pointer;
        font-size: 14px;
        border-radius: 4px;
        transition: 0.2s;
    }
    .btn-unlock:hover{ background-color:#e0a800; }

    #lockConfirmModal .modal-header{
        text-align: center;
    }
    #lockConfirmModal .close-lock-modal{
        cursor: pointer;
    }
    #lockConfirmModal .modal-header{
        display: flex;
        align-items: center;
        padding: 12px 16px;
    }

    #lockConfirmModal .modal-header h2{
        margin: 0;
        flex: 1;
        text-align: center;
    }
    #lockConfirmModal .modal-body {
        text-align: center;
    }

    /*  */


    h1 {
        margin: 0;
        margin-bottom: 10px;
        color: #222;
    }

    .order-table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 10px;
    }

    .order-table th {
        background: #2659F5;
        color: white;
        padding: 12px;
        font-size: 14px;
    }

    .order-table td {
        padding: 12px;
        background: #fafafa;
    }

    .order-table tr:nth-child(even) td {
        background: #f0f0f0;
    }

    .order-table td,
    .order-table th {
        border-bottom: 1px solid #ddd;
        text-align: left;
    }

    .modal {
        position: fixed;
        inset: 0;
        background: rgba(0, 0, 0, 0.4);
        display: none;
        align-items: center;
        justify-content: center;
        z-index: 999;
    }
    .btn-them-khach-hang {
        background-color: #2659F5;
        border: none;
        color: white;
        padding: 8px 16px;
        font-size: 14px;
        border-radius: 5px;
        cursor: pointer;
        margin-left: 0;
    }
    .modal-content {
        background: #fff;
        width: 400px;
        max-width: 95%;
        border-radius: 10px;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
        overflow: hidden;
        animation: fadeInScale 0.2s ease-out;
    }

    .modal-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 12px 16px;
        background-color: #2659F5;
        color: #fff;
        text-align: center;
    }

    .modal-header h2 {
        margin: 0;
        font-size: 18px;
        text-align: center;
    }

    #customerModal .modal-header h2 {
        width: 100%;
        text-align: center;
    }

    #editCustomerModal .modal-header h2 {
        width: 100%;
        text-align: center;
    }

    .close-modal {
        cursor: pointer;
        font-size: 20px;
        padding: 0 5px;
    }

    .close-edit-modal {
        cursor: pointer;
        font-size: 20px;
        padding: 0 5px;
    }

    .modal-body {
        padding: 15px 16px 5px;
    }

    .form-group {
        display: flex;
        flex-direction: column;
        margin-bottom: 10px;
    }

    .form-group label {
        font-size: 14px;
        margin-bottom: 4px;
        color: #333;
    }

    .form-group input,
    .form-group select {
        padding: 8px 10px;
        border-radius: 6px;
        border: 1px solid #ddd;
        font-size: 14px;
        outline: none;
    }

    .form-group input:focus,
    .form-group select:focus {
        border-color: #17479D;
    }

    .modal-footer {
        display: flex;
        justify-content: flex-end;
        gap: 8px;
        padding: 10px 16px 14px;
        background: #f7f7f7;
    }

    .btn-cancel,
    .btn-save {
        border: none;
        border-radius: 5px;
        padding: 8px 14px;
        font-size: 14px;
        cursor: pointer;
    }

    .btn-cancel {
        background: #e0e0e0;
        color: #333;
    }

    .btn-save {
        background: #2659F5;
        color: #fff;
    }

    .btn-edit-cancel,
    .btn-edit-save {
        border: none;
        border-radius: 5px;
        padding: 8px 15px;
        font-size: 14px;
        cursor: pointer;
    }

    .btn-edit-cancel {
        background: #e0e0e0;
        color: #333;
    }

    .btn-edit-save {
        background: #2659F5;
        color: #fff;
    }

    .alert-success-custom {
        background: #d1e7dd;
        color: #0f5132;
        padding: 12px 16px;
        border-radius: 8px;
        margin-bottom: 16px;
        border: 1px solid #badbcc;
    }

    .alert-fail-custom {
        background: #f8d7da;
        color: #842029;
        padding: 12px 16px;
        border-radius: 8px;
        margin-bottom: 16px;
        border: 1px solid #f5c2c7;
    }

    @keyframes fadeInScale {
        from {
            opacity: 0;
            transform: scale(0.9);
        }

        to {
            opacity: 1;
            transform: scale(1);
        }
    }

    .dataTables_paginate .paginate_button {
        padding: 6px 12px !important;
        margin: 0 3px !important;
        border: 1px solid #ccc !important;
        border-radius: 4px !important;
        background: white !important;
        color: #2659F5 !important;
    }

    .dataTables_paginate .paginate_button:hover {
        background: #e9ecef !important;
        color: #2659F5 !important;
    }

    .dataTables_paginate .paginate_button.current {
        background: #2659F5 !important;
        color: white !important;
        border-color: #2659F5 !important;
    }

    .dataTables_paginate .paginate_button.disabled {
        color: #999 !important;
        background: white !important;
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
            <c:if test="${role == 'ADMIN' || permissions.contains('INVENTORY_VIEW')}">
              <a href="${pageContext.request.contextPath}/admin/inventory"><i class="fa-solid fa-warehouse"></i>Quản
                lý tồn kho</a>
              </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('USER_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/users"  class="active"><i class="fa-solid fa-person"></i>Quản lý người dùng</a>
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
<a href="${pageContext.request.contextPath}/logout">
  <i class="fa-solid fa-right-from-bracket"></i> Đăng xuất
</a>

        </div>
    </div>
    <div class="right">

        <div class="container">

            <div class="order-container">
                <h1>Danh sách người dùng</h1>
                <c:if test="${msg == 'created_and_sent_mail'}">
                    <div class="alert-success-custom">
                        Thêm người dùng thành công và đã gửi email thiết lập mật khẩu.
                    </div>
                </c:if>

                <c:if test="${msg == 'success'}">
                    <div class="alert-success-custom">
                        Thao tác thành công.
                    </div>
                </c:if>

                <c:if test="${msg == 'fail'}">
                    <div class="alert-fail-custom">
                        Thao tác thất bại.
                    </div>
                </c:if>
                <div class="search">
                    <div class="search-input-icon">
                        <input id="userSearchInput" type="text"  placeholder="Tìm kiếm người dùng..." autocomplete="off">
                        <button type="button" class="icon" id="btnUserSearch"><i class="fa-solid fa-magnifying-glass"></i></button>
                    </div>
                    <button type="button" class="btn-them-khach-hang">Thêm người dùng</button>
                </div>

                <table id="userTable" class="order-table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Họ và tên</th>
                        <th>Số điện thoại</th>
                        <th>Địa chỉ</th>
                        <th>Ngày đăng ký</th>
                        <th>Năm sinh</th>
                        <th>Vai trò</th>
                        <th>Nhóm quyền</th>
                        <th>Tùy chọn</th>
                    </tr>
                    </thead>

                    <tbody id="userTbody">
                    <c:forEach var="u" items="${users}">
                        <tr>
                            <td class="col-id"><c:out value="${u.id}"/></td>
                            <td class="col-ten"><c:out value="${u.fullName}"/></td>
                            <td class="col-sdt"><c:out value="${u.phoneNumber}"/></td>
                            <td class="col-diachi"><c:out value="${u.address}"/></td>
                            <td><c:out value="${u.createAt}"/></td>
                            <td><c:out value="${u.dob}"/></td>
                            <td class="col-vaitro"><c:out value="${u.role}"/></td>

                            <td>
                                <c:choose>
                                    <c:when test="${empty u.groupId}">
                                        Chưa có nhóm
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="g" items="${groups}">
                                            <c:if test="${g.id == u.groupId}">
                                                <c:out value="${g.name}"/>
                                            </c:if>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <button type="button" class="btn-Sua"
                                        data-id="${u.id}"
                                        data-fullname="${u.fullName}"
                                        data-phone="${u.phoneNumber}"
                                        data-address="${u.address}"
                                        data-dob="${u.dob}"
                                        data-role="${u.role}"
                                        data-groupid="${u.groupId}">
                                    <i class="fa-solid fa-pen-to-square"></i>
                                </button>
                                <c:choose>
                                    <c:when test="${u.isActive == 1}">
                                        <form class="lockForm" method="post" action="${pageContext.request.contextPath}/admin/users" style="display:inline">
                                            <input type="hidden" name="action" value="lock"/>
                                            <input type="hidden" name="id" value="${u.id}"/>

                                            <button class="btn-Xoa btn-open-lock" type="button" title="Khóa">
                                                <i class="fa-solid fa-lock"></i>
                                            </button>
                                        </form>
                                    </c:when>

                                    <c:when test="${u.isActive == 3}">
                                        <form class="lockForm" method="post" action="${pageContext.request.contextPath}/admin/users" style="display:inline">
                                            <input type="hidden" name="action" value="unlock"/>
                                            <input type="hidden" name="id" value="${u.id}"/>
                                            <button class="btn-unlock btn-open-lock" type="button" title="Mở khóa">
                                                <i class="fa-solid fa-lock-open"></i>
                                            </button>
                                        </form>
                                    </c:when>

                                    <c:otherwise>
                                        <span style="font-size:12px; opacity:.7;">Chưa kích hoạt</span>
                                    </c:otherwise>
                                </c:choose>


                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>

                </table>


            </div>
        </div>

    </div>

</div>

<div id="customerModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2>Thêm người dùng</h2>
            <span class="close-modal">&times;</span>
        </div>

        <form method="post" action="${pageContext.request.contextPath}/admin/users">
            <input type="hidden" name="action" value="create"/>


            <div class="modal-body">
                <div class="form-group">
                    <label for="tenKH">Họ và tên</label>
                    <input type="text" id="tenKH" name="fullName" placeholder="Nhập họ và tên" required>
                </div>

                <div class="form-group">
                    <label for="emailKH">Email</label>
                    <input type="email" id="emailKH" name="email" placeholder="Nhập email" required>
                </div>

                <div class="form-group">
                    <label for="sdtKH">Số điện thoại</label>
                    <input type="text" id="sdtKH" name="phoneNumber" placeholder="Nhập số điện thoại">
                </div>

                <div class="form-group">
                    <label for="diaChiKH">Địa chỉ</label>
                    <input type="text" id="diaChiKH" name="address" placeholder="Nhập địa chỉ">
                </div>

                <div class="form-group">
                    <label for="dobKH">Ngày sinh</label>
                    <input type="date" id="dobKH" name="dob">
                </div>

                <div class="form-group">
                    <label for="vaiTroKH">Vai trò</label>
                    <select id="vaiTroKH" name="role">
                        <option value="USER" selected>USER</option>
                        <option value="ADMIN">ADMIN</option>
                        <option value="STAFF">STAFF</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="groupKH">Nhóm quyền</label>
                    <select id="groupKH" name="groupId">
                        <option value="">Chưa chọn nhóm</option>
                        <c:forEach var="g" items="${groups}">
                            <option value="${g.id}">
                                <c:out value="${g.name}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn-cancel">Hủy</button>
                <button type="submit" class="btn-save">Lưu</button>
            </div>
        </form>

    </div>
</div>

<div id="editCustomerModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2>Chỉnh sửa thông tin</h2>
            <span class="close-edit-modal">&times;</span>
        </div>

        <form method="post" action="${pageContext.request.contextPath}/admin/users">
            <input type="hidden" name="action" value="update"/>
            <input type="hidden" name="id" id="editId" />




            <div class="modal-body">
                <div class="form-group">
                    <label for="editTenKH">Họ và tên</label>
                    <input type="text" id="editTenKH" name="fullName" required>
                </div>

                <div class="form-group">
                    <label for="editSdtKH">Số điện thoại</label>
                    <input type="text" id="editSdtKH" name="phoneNumber">
                </div>

                <div class="form-group">
                    <label for="editDiaChiKH">Địa chỉ</label>
                    <input type="text" id="editDiaChiKH" name="address">
                </div>

                <div class="form-group">
                    <label for="editDobKH">Ngày sinh</label>
                    <input type="date" id="editDobKH" name="dob">
                </div>

                <div class="form-group">
                    <label for="editVaiTroKH">Vai trò</label>
                    <select id="editVaiTroKH" name="role">
                        <option value="USER">USER</option>
                        <option value="STAFF">STAFF</option>
                        <option value="ADMIN">ADMIN</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="editGroupKH">Nhóm quyền</label>
                    <select id="editGroupKH" name="groupId">
                        <option value="">Chưa chọn nhóm</option>
                        <c:forEach var="g" items="${groups}">
                            <option value="${g.id}">
                                <c:out value="${g.name}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn-edit-cancel">Hủy</button>
                <button type="submit" class="btn-edit-save">Lưu thay đổi</button>
            </div>
        </form>


    </div>
</div>
<div id="lockConfirmModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2>Xác nhận</h2>
            <span class="close-lock-modal">&times;</span>
        </div>
        <div class="modal-body">
            <p id="lockConfirmText">Bạn chắc chắn muốn khóa không?</p>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn-lock-cancel">Hủy</button>
            <button type="button" class="btn-lock-ok" id="lockConfirmOkBtn"
                    style="background:#DC3545;color:#fff;border:none;border-radius:5px;padding:8px 14px;cursor:pointer;">
                Đồng ý
            </button>
        </div>
    </div>
</div>


<script>
    const btnThemKH = document.querySelector('.btn-them-khach-hang');
    const modalAdd = document.getElementById('customerModal');
    const btnCloseAdd = document.querySelector('.close-modal');
    const btnCancelAdd = document.querySelector('.btn-cancel');

    btnThemKH?.addEventListener('click', () => {
        modalAdd.style.display = 'flex';
    });

    btnCloseAdd?.addEventListener('click', () => {
        modalAdd.style.display = 'none';
    });

    btnCancelAdd?.addEventListener('click', () => {
        modalAdd.style.display = 'none';
    });

    window.addEventListener('click', (e) => {
        if (e.target === modalAdd) modalAdd.style.display = 'none';
    });

    const modalEdit = document.getElementById("editCustomerModal");
    const btnCloseEdit = document.querySelector(".close-edit-modal");
    const btnEditCancel = document.querySelector(".btn-edit-cancel");

    document.addEventListener("click", (e) => {
        const btn = e.target.closest(".btn-Sua");
        if (!btn) return;

        modalEdit.style.display = "flex";

        const id = btn.dataset.id || "";
        const ten = btn.dataset.fullname || "";
        const sdt = btn.dataset.phone || "";
        const diachi = btn.dataset.address || "";
        const dob = btn.dataset.dob || "";
        const vaitro = (btn.dataset.role || "USER").toUpperCase();
        const groupId = btn.dataset.groupid || "";

        const editId = document.getElementById("editId");
        if (editId) editId.value = id;

        document.getElementById("editTenKH").value = ten;
        document.getElementById("editSdtKH").value = sdt;
        document.getElementById("editDiaChiKH").value = diachi;
        document.getElementById("editVaiTroKH").value = vaitro;
        document.getElementById("editGroupKH").value = groupId;

        const editDob = document.getElementById("editDobKH");
        if (editDob) editDob.value = dob;
    });


    btnCloseEdit?.addEventListener("click", () => {
        modalEdit.style.display = "none";
    });

    btnEditCancel?.addEventListener("click", () => {
        modalEdit.style.display = "none";
    });

    window.addEventListener("click", (e) => {
        if (e.target === modalEdit) modalEdit.style.display = "none";
    });
</script>
<script>
    const lockModal = document.getElementById("lockConfirmModal");
    const btnCloseLock = document.querySelector(".close-lock-modal");
    const btnLockCancel = document.querySelector(".btn-lock-cancel");
    const btnLockOk = document.querySelector(".btn-lock-ok");

    const lockConfirmText = document.getElementById("lockConfirmText");
    const lockConfirmOkBtn = document.getElementById("lockConfirmOkBtn");

    let formToSubmit = null;

    document.addEventListener("click", (e) => {
        const btn = e.target.closest(".btn-open-lock");
        if (!btn) return;

        formToSubmit = btn.closest("form");
        const action = formToSubmit.querySelector('input[name="action"]').value;

        if (action === "unlock") {
            lockConfirmText.textContent = "Bạn chắc chắn muốn mở khóa không?";
            lockConfirmOkBtn.textContent = "Mở khóa";
            lockConfirmOkBtn.style.background = "#2659F5";
        } else {
            lockConfirmText.textContent = "Bạn chắc chắn muốn khóa không?";
            lockConfirmOkBtn.textContent = "Khóa";
            lockConfirmOkBtn.style.background = "#DC3545";
        }

        lockModal.style.display = "flex";
    });

    btnCloseLock?.addEventListener("click", () => lockModal.style.display = "none");
    btnLockCancel?.addEventListener("click", () => lockModal.style.display = "none");

    btnLockOk?.addEventListener("click", () => {
        if (formToSubmit) formToSubmit.submit();
    });

    window.addEventListener("click", (e) => {
        if (e.target === lockModal) lockModal.style.display = "none";
    });
</script>
<script>
    $(document).ready(function () {
        var table = $('#userTable').DataTable({
            pageLength: 10,
            lengthChange: false,
            info: false,
            ordering: true,
            searching: true,
            dom: 'rtip',
            language: {
                url: 'https://cdn.datatables.net/plug-ins/1.13.8/i18n/vi.json'
            },
            columnDefs: [
                {
                    orderable: false,
                    targets: 8
                }
            ]
        });

        $('#userSearchInput').keyup(function () {
            var keyword = $(this).val();
            table.search(keyword).draw();
        });

        $('#btnUserSearch').click(function () {
            var keyword = $('#userSearchInput').val();
            table.search(keyword).draw();
        });
    });
</script>
</body>

</html>