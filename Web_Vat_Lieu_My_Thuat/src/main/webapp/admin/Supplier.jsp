<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý nhà cung cấp</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css">

    <link rel="stylesheet"
          href="https://cdn.datatables.net/1.13.8/css/jquery.dataTables.min.css">

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.8/js/jquery.dataTables.min.js"></script>

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

        #main .right .container {
            width: calc(100% - 100px);
            margin: 20px auto 0 auto;
        }

        .list-container {
            width: 95%;
            margin: 30px auto;
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0,0,0,.1);
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 18px;
        }

        .btn-add {
            background: #2659F5;
            color: white;
            border: none;
            padding: 9px 13px;
            border-radius: 5px;
            cursor: pointer;
        }

        .btn-add:hover {
            background: #17479D;
        }

        table.supplier-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }

        table.supplier-table th {
            background: #2659F5;
            color: white;
            padding: 12px;
            font-size: 14px;
        }

        table.supplier-table td {
            padding: 12px;
            background: #fafafa;
            border-bottom: 1px solid #ddd;
            border-left: 1px solid #ddd;
            border-right: 1px solid #ddd;
            vertical-align: top;
        }

        table.supplier-table tr:nth-child(even) td {
            background: #f0f0f0;
        }

        .btn-icon {
            color: white;
            padding: 8px 12px;
            border-radius: 5px;
            cursor: pointer;
            margin-right: 5px;
            border: none;
        }

        .btn-edit {
            background: #FFC107;
            color: black;
        }

        .btn-lock {
            background: #dc3545;
        }

        .btn-unlock {
            background: #28a745;
        }

        .badge-active {
            color: #28a745;
            font-weight: 600;
        }

        .badge-inactive {
            color: #dc3545;
            font-weight: 600;
        }

        .alert-success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
            padding: 10px 12px;
            border-radius: 6px;
            margin-bottom: 15px;
        }

        .alert-danger {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
            padding: 10px 12px;
            border-radius: 6px;
            margin-bottom: 15px;
        }

        .modal {
            display: none;
            position: fixed;
            inset: 0;
            background: rgba(0,0,0,.5);
            justify-content: center;
            align-items: center;
            z-index: 999;
            padding: 20px;
        }

        .modal-content {
            background: white;
            padding: 25px;
            width: 650px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,.2);
            max-height: 90vh;
            overflow-y: auto;
        }

        .modal-content label {
            display: block;
            margin-top: 10px;
            font-size: 14px;
            color: #444;
        }

        .modal-content input,
        .modal-content textarea {
            width: 100%;
            padding: 8px;
            margin-top: 4px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        .modal-content textarea {
            min-height: 80px;
            resize: vertical;
        }

        .modal-buttons {
            display: flex;
            justify-content: flex-end;
            margin-top: 20px;
            gap: 10px;
        }

        #btn-save {
            background: #2659F5;
            color: white;
            padding: 8px 14px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        #btn-close {
            background: #ccc;
            padding: 8px 14px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .muted {
            color: #666;
            font-size: 13px;
        }
    </style>
</head>

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

            <c:if test="${role == 'ADMIN' || permissions.contains('INVENTORY_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/inventory">
                    <i class="fa-solid fa-warehouse"></i>Quản lý tồn kho
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('SUPPLIER_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/suppliers" class="active">
                    <i class="fa-solid fa-truck-field"></i>Nhà cung cấp
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('USER_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/users">
                    <i class="fa-solid fa-person"></i>Quản lý người dùng
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
            <div class="list-container">
                <div class="header">
                    <div>
                        <h1>Danh sách nhà cung cấp</h1>
                    </div>

                    <button class="btn-add" id="btnAdd">Thêm nhà cung cấp</button>
                </div>

                <c:if test="${not empty sessionScope.supplierMessage}">
                    <div class="alert-success">${sessionScope.supplierMessage}</div>
                    <c:remove var="supplierMessage" scope="session"/>
                </c:if>

                <c:if test="${not empty sessionScope.supplierError}">
                    <div class="alert-danger">${sessionScope.supplierError}</div>
                    <c:remove var="supplierError" scope="session"/>
                </c:if>

                <table id="supplierTable" class="supplier-table display">
                    <thead>
                    <tr>
                        <th>STT</th>
                        <th>ID</th>
                        <th>Mã NCC</th>
                        <th>Tên nhà cung cấp</th>
                        <th>Số điện thoại</th>
                        <th>Email</th>
                        <th>Địa chỉ</th>
                        <th>Trạng thái</th>
                        <th>Tùy chọn</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="s" items="${suppliers}">
                        <tr>
                            <td></td>
                            <td>${s.id}</td>
                            <td><c:out value="${s.supplierCode}"/></td>
                            <td><c:out value="${s.name}"/></td>
                            <td><c:out value="${s.phone}"/></td>
                            <td><c:out value="${s.email}"/></td>
                            <td><c:out value="${s.address}"/></td>

                            <td>
                                <c:choose>
                                    <c:when test="${s.isActive == 1}">
                                        <span class="badge-active">Đang hoạt động</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge-inactive">Đã khóa</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <button type="button"
                                        class="btn-icon btn-edit btnEdit"
                                        data-id="${s.id}"
                                        data-supplier-code="${fn:escapeXml(s.supplierCode)}"
                                        data-name="${fn:escapeXml(s.name)}"
                                        data-phone="${fn:escapeXml(s.phone)}"
                                        data-email="${fn:escapeXml(s.email)}"
                                        data-address="${fn:escapeXml(s.address)}"
                                        data-note="${fn:escapeXml(s.note)}">
                                    <i class="fa-solid fa-pen-to-square"></i>
                                </button>

                                <form action="${pageContext.request.contextPath}/admin/suppliers"
                                      method="post"
                                      style="display:inline">
                                    <input type="hidden" name="action" value="toggleStatus">
                                    <input type="hidden" name="id" value="${s.id}">
                                    <input type="hidden" name="isActive" value="${s.isActive}">

                                    <button type="submit"
                                            class="btn-icon ${s.isActive == 1 ? 'btn-lock' : 'btn-unlock'}"
                                            onclick="return confirm('${s.isActive == 1 ? "Khóa" : "Mở khóa"} nhà cung cấp này?')">
                                        <c:choose>
                                            <c:when test="${s.isActive == 1}">
                                                <i class="fa-solid fa-lock"></i>
                                            </c:when>
                                            <c:otherwise>
                                                <i class="fa-solid fa-lock-open"></i>
                                            </c:otherwise>
                                        </c:choose>
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>
    </div>
</div>

<div id="modal" class="modal">
    <div class="modal-content">
        <h2 id="modalTitle">Thêm nhà cung cấp</h2>

        <form id="supplierForm"
              action="${pageContext.request.contextPath}/admin/suppliers"
              method="post">

            <input type="hidden" name="action" id="action" value="create">
            <input type="hidden" name="id" id="supplierId">

            <label>Mã nhà cung cấp</label>
            <input type="text"
                   id="supplierCode"
                   name="supplierCode"
                   maxlength="50"
                   placeholder="Ví dụ: NCC006"
                   required>

            <label>Tên nhà cung cấp</label>
            <input type="text"
                   id="name"
                   name="name"
                   maxlength="255"
                   required>

            <label>Số điện thoại</label>
            <input type="text"
                   id="phone"
                   name="phone"
                   maxlength="20">

            <label>Email</label>
            <input type="email"
                   id="email"
                   name="email"
                   maxlength="100">

            <label>Địa chỉ</label>
            <input type="text"
                   id="address"
                   name="address"
                   maxlength="255">

            <label>Ghi chú</label>
            <textarea id="note" name="note"></textarea>

            <div class="modal-buttons">
                <button id="btn-save" type="submit">Lưu</button>
                <button id="btn-close" type="button">Hủy</button>
            </div>
        </form>
    </div>
</div>

<script>
    const modal = document.getElementById("modal");
    const btnAdd = document.getElementById("btnAdd");
    const btnClose = document.getElementById("btn-close");

    const modalTitle = document.getElementById("modalTitle");
    const actionInput = document.getElementById("action");
    const supplierIdInput = document.getElementById("supplierId");
    const supplierCodeInput = document.getElementById("supplierCode");
    const nameInput = document.getElementById("name");
    const phoneInput = document.getElementById("phone");
    const emailInput = document.getElementById("email");
    const addressInput = document.getElementById("address");
    const noteInput = document.getElementById("note");

    function openModal() {
        modal.style.display = "flex";
    }

    function closeModal() {
        modal.style.display = "none";
    }

    btnAdd.addEventListener("click", function () {
        modalTitle.innerText = "Thêm nhà cung cấp";
        actionInput.value = "create";
        supplierIdInput.value = "";
        supplierCodeInput.value = "";
        nameInput.value = "";
        phoneInput.value = "";
        emailInput.value = "";
        addressInput.value = "";
        noteInput.value = "";

        openModal();
    });

    btnClose.addEventListener("click", closeModal);

    window.addEventListener("click", function (e) {
        if (e.target === modal) {
            closeModal();
        }
    });

    document.querySelectorAll(".btnEdit").forEach(function (btn) {
        btn.addEventListener("click", function () {
            modalTitle.innerText = "Cập nhật nhà cung cấp";
            actionInput.value = "update";

            supplierIdInput.value = btn.dataset.id || "";
            supplierCodeInput.value = btn.dataset.supplierCode || "";
            nameInput.value = btn.dataset.name || "";
            phoneInput.value = btn.dataset.phone || "";
            emailInput.value = btn.dataset.email || "";
            addressInput.value = btn.dataset.address || "";
            noteInput.value = btn.dataset.note || "";

            openModal();
        });
    });
</script>

<script>
    $(function () {
        const table = $("#supplierTable").DataTable({
            pageLength: 5,
            lengthChange: false,
            ordering: true,
            searching: true,
            info: false,
            language: {
                url: "https://cdn.datatables.net/plug-ins/1.13.8/i18n/vi.json"
            },
            columnDefs: [
                { orderable: false, targets: [0, 8] }
            ],
            order: [[1, "desc"]]
        });

        table.on("order.dt search.dt draw.dt", function () {
            const pageInfo = table.page.info();

            table.column(0, { search: "applied", order: "applied" }).nodes()
                .each(function (cell, i) {
                    cell.innerHTML = pageInfo.start + i + 1;
                });
        }).draw();
    });
</script>

</body>
</html>