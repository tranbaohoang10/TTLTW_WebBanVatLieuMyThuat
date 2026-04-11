<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý đơn hàng</title>
    <link rel="stylesheet" href="../assets/css/style.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.8/css/jquery.dataTables.min.css">

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.8/js/jquery.dataTables.min.js"></script>

</head>
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
        border-left: #3B7DDD 2px solid;
    }

    .list-admin a.active {
        background-color: #203247;
        border-left: 4px solid #FFD700;
        /* hoặc màu khác */
        font-weight: bold;
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

    #main .right .container {
        display: flex;
        flex-direction: column;
        width: 100%;
        margin-top: 20px;
        margin-left: auto;
        margin-right: auto;
    }

    /* Make right column grow to fill remaining space so children can space-between */
    #main .right {
        flex: 1;
        background-color: #F9F9F9;
    }


    .order-container {
        width: 95%;
        margin: 30px auto;
        background: white;
        padding: 25px;
        border-radius: 10px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    }

    h1 {
        margin: 0;
        margin-bottom: 10px;
        color: #222;
    }

    .sub-title {
        color: #555;
        font-size: 16px;
        margin-bottom: 20px;
    }

    .order-table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 10px;
    }

    .order-table td:nth-child(6) {
        white-space: normal !important;
    }

    .order-table td:nth-child(8) {
        white-space: nowrap !important;
    }

    .order-table td:nth-child(9) {
        white-space: nowrap !important;
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

    /* Trạng thái đơn hàng */
    .status {
        padding: 6px 12px;
        border-radius: 8px;
        font-size: 13px;
        font-weight: bold;
    }

    .status.pending {
        background: #FFF3CD;
        color: #555;
    }

    .status.delivery {
        background: #2659F5;
        color: white;
    }

    .status.success {
        background: #D1FAE5;
        color: #0f5132;
    }

    .status.cancel {
        background: #ffd6d6;
        color: #b91c1c;
    }

    .btn-sm {
        background-color: #FFC107;
        color: black;
        border: none;
        padding: 6px 10px;
        cursor: pointer;
        font-size: 14px;
        border-radius: 4px;
        transition: 0.2s;
    }

    .btn-success {
        background-color: #28a745;
        color: white;
        border: none;
        padding: 6px 10px;
        cursor: pointer;
        font-size: 14px;
        border-radius: 4px;
        transition: 0.2s;
    }

    .btn-sm:hover {
        background-color: #e0a800;
    }

    /* Nút xoá */
    .btn-delete {
        background-color: #DC3545;
        color: white;
        border: none;
        padding: 6px 10px;
        cursor: pointer;
        font-size: 14px;
        border-radius: 4px;
        transition: 0.2s;
    }

    .btn-delete:hover {
        background-color: #b02a37;
    }


    .btn-sm i,
    .btn-delete i {
        font-size: 14px;
    }

    .search-box {
        margin-left: auto;
    }

    .search-box {
        display: flex;
        align-items: center;
        width: 400px;
        background: #fff;
        border: 1px solid #ddd;
        border-radius: 6px;
        padding-left: 10px;
        overflow: hidden;
    }

    .search-box input {
        border: none;
        outline: none;
        padding: 8px 10px;
        width: 100%;
        font-size: 14px;
    }

    .search-btn {
        background: #ddd;
        border: none;
        color: black;
        padding: 8px 12px;
        cursor: pointer;
        font-size: 14px;
        border-left: 1px solid #ddd;
    }

    .search-btn:hover {
        background-color: #ccc;
    }

    .search-btn i {
        font-size: 14px;
    }

    .pagination {
        display: flex;
        justify-content: center;
        align-items: center;
        margin: 25px 0;
        gap: 5px;
    }

    .page-link {
        padding: 6px 12px;
        border: 1px solid #d0d7de;
        border-radius: 4px;
        color: #0d6efd;
        background-color: white;
        text-decoration: none;
        font-size: 14px;
        transition: 0.2s;
    }

    .page-link:hover {
        background-color: #e9ecef;
    }

    .page-link.active {
        background-color: #2659F5;
        color: white;
        font-weight: bold;
        border-color: #2659F5;
    }

    .modal {
        display: none;
        position: fixed;
        inset: 0;
        background: rgba(0, 0, 0, 0.4);
        justify-content: center;
        align-items: center;
        z-index: 9999;
    }

    /* Khung modal */
    .category-box {
        width: 450px;
        background: #fff;
        border-radius: 8px;
        overflow: hidden;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
    }

    /* Header */
    .modal-header {
        padding: 14px 20px;
        border-bottom: 1px solid #eee;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .modal-header h3 {
        margin: 0;
        font-size: 18px;
    }

    .close-add {
        font-size: 22px;
        cursor: pointer;
    }

    /* Body */
    .modal-body {
        padding: 20px;
        display: flex;
        flex-direction: column;
        gap: 12px;
    }

    .modal-body input[type="text"],
    .modal-body input[type="file"] {
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 6px;
        outline: none;
    }

    .modal-body input:focus {
        border-color: #2659F5;
    }

    /* Footer */
    .modal-footer {
        padding: 12px 20px;
        border-top: 1px solid #eee;
        display: flex;
        justify-content: flex-end;
        gap: 10px;
    }

    .btn-cancel,
    .btn-cancel-edit {
        background: #6c757d;
        color: white;
        padding: 8px 14px;
        border: none;
        border-radius: 6px;
        cursor: pointer;
    }

    .btn-save,
    .btn-save-edit {
        background: #2659F5;
        color: white;
        padding: 8px 14px;
        border: none;
        border-radius: 6px;
        cursor: pointer;
    }

    .modal-body select {
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 6px;
        outline: none;
        font-size: 15px;
    }

    .modal-body select:focus {
        border-color: #2659F5;
    }
    .toast {
        position: fixed;
        top: 20px;
        right: 20px;
        min-width: 260px;
        max-width: 360px;
        padding: 12px 14px;
        border-radius: 10px;
        background: #111827;
        color: #fff;
        font-size: 14px;
        box-shadow: 0 8px 24px rgba(0,0,0,.18);
        opacity: 0;
        transform: translateY(-8px);
        pointer-events: none;
        transition: .25s ease;
        z-index: 999999;
    }

    .toast.show {
        opacity: 1;
        transform: translateY(0);
    }


    .dataTables_wrapper .dataTables_filter input {
        border: 1px solid #ddd;
        border-radius: 6px;
        padding: 8px 10px;
        outline: none;
        transition: 0.2s;
    }
    .dataTables_wrapper .dataTables_filter input:focus {
        border-color: #2659F5;
        box-shadow: 0 0 0 2px rgba(38, 89, 245, 0.15);
    }


    .dataTables_wrapper .dataTables_paginate .paginate_button {
        border: 1px solid #d0d7de !important;
        border-radius: 6px !important;
        padding: 6px 12px !important;
        margin: 0 3px !important;
        background: #fff !important;
        color: #2659F5 !important;
        transition: 0.2s;
    }
    .dataTables_wrapper .dataTables_paginate .paginate_button:hover {
        background: #e9ecef !important;
        border-color: #2659F5 !important;
        color: #2659F5 !important;
    }


    .dataTables_wrapper .dataTables_paginate .paginate_button.current,
    .dataTables_wrapper .dataTables_paginate .paginate_button.current:hover {
        background: #2659F5 !important;
        border-color: #2659F5 !important;
        color: #fff !important;
    }


    .dataTables_wrapper .dataTables_paginate .paginate_button.disabled,
    .dataTables_wrapper .dataTables_paginate .paginate_button.disabled:hover {
        opacity: 0.5;
        cursor: not-allowed !important;
        background: #fff !important;
        border-color: #d0d7de !important;
        color: #2659F5 !important;
    }


    table.dataTable thead .sorting:after,
    table.dataTable thead .sorting_asc:after,
    table.dataTable thead .sorting_desc:after {
        opacity: 0.65;
        color: #fff;
    }


    .order-table tbody tr:hover td {
        background: rgba(38, 89, 245, 0.06);
    }
    .actions {
        display: flex;
        gap: 6px;
        align-items: center;
    }

</style>

<body>
<c:if test="${not empty sessionScope.toast}">
    <div id="toast" class="toast show">
        <c:out value="${sessionScope.toast}"/>
    </div>
    <c:remove var="toast" scope="session"/>
</c:if>

<div id="main">
    <div class="left">
        <div class="list-admin">
            <a href="Admin.jsp" class="logo"><img
                    src="../assets/images/logo/logo.png" alt></a>
            <a href="${pageContext.request.contextPath}/admin/overview"><i class="fa-solid fa-house"></i> Tổng
                quan</a>
            <a href="${pageContext.request.contextPath}/admin/statistics"><i
                    class="fa-solid fa-chart-line"></i>Thống
                kê</a>
            <a href="${pageContext.request.contextPath}/admin/categories"><i class="fa-solid fa-list"></i>Quản
                lý danh
                mục</a>
            <a href="${pageContext.request.contextPath}/admin/products"><i
                    class="fa-solid fa-palette"></i>Quản
                lý sản phẩm</a>
            <a href="${pageContext.request.contextPath}/admin/users"><i
                    class="fa-solid fa-person"></i>Quản
                lý người dùng</a>
            <a href="${pageContext.request.contextPath}/admin/orders" class="active"><i
                    class="fa-solid fa-box-open"></i>Quản
                lý đơn hàng</a>
            <a href="${pageContext.request.contextPath}/admin/vouchers"><i
                    class="fa-solid fa-gift"></i>Quản lý
                khuyến mãi</a>
            <a href="${pageContext.request.contextPath}/admin/sliders"><i
                    class="fa-solid fa-sliders"></i>Quản lý Slider
                Show</a>
            <a href="${pageContext.request.contextPath}/admin/contacts"><i
                    class="fa-solid fa-address-book"></i>Quản lý liên
                hệ</a>
            <a href="${pageContext.request.contextPath}/logout"><i
                    class="fa-solid fa-right-from-bracket"></i>
                Đăng xuất</a>
        </div>
    </div>
    <div class="right">

        <div class="container">
            <div class="order-container">
                <h1>Quản lý đơn hàng</h1>

                <table id="orderTable" class="order-table display">
                    <thead>
                    <tr>
                        <th>Mã Đơn</th>
                        <th>Khách Hàng</th>
                        <th>Số Điện Thoại</th>
                        <th>Ngày Đặt</th>
                        <th>Địa Chỉ Giao</th>
                        <th>Sản Phẩm</th>
                        <th>Thành Tiền</th>
                        <th>Trạng Thái</th>
                        <th>Tùy Chọn</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="o" items="${orders}" varStatus="st">
                        <tr>
                            <td>DH<fmt:formatNumber value="${o.id}" pattern="00"/></td>
                            <td><c:out value="${o.fullName}" default="-"/></td>
                            <td><c:out value="${o.phoneNumber}" default="-"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty o.createAt}">
                                        <fmt:formatDate value="${o.createAt}" pattern="dd-MM-yyyy"/>
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                            <td><c:out value="${o.address}" default="-"/></td>
                            <td><c:out value="${o.productNames}" default="-"/></td>
                            <td><fmt:formatNumber value="${o.totalPrice}" type="number"/> VND</td>

                            <td>
                                <c:choose>
                                    <c:when test="${o.statusName == 'Đang xử lý'}">
                                        <span class="status pending">${o.statusName}</span>
                                    </c:when>
                                    <c:when test="${o.statusName == 'Đang vận chuyển'}">
                                        <span class="status delivery">${o.statusName}</span>
                                    </c:when>
                                    <c:when test="${o.statusName == 'Hoàn thành'}">
                                        <span class="status success">${o.statusName}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="status cancel"><c:out value="${o.statusName}" default="-"/></span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="action-col">
                                <div class="actions">
                                    <a href="${pageContext.request.contextPath}/admin/order-detail?id=${o.id}"
                                       class="btn btn-info btn-sm"
                                       title="Xem chi tiết đơn hàng">
                                        <i class="fa-solid fa-eye"></i>
                                    </a>

                                    <c:if test="${o.statusName == 'Đang xử lý'}">
                                        <form action="${pageContext.request.contextPath}/admin/orders/status"
                                              method="post" style="display:inline;">
                                            <input type="hidden" name="orderId" value="${o.id}">
                                            <input type="hidden" name="statusName" value="Đang vận chuyển">
                                            <button class="btn btn-success btn-sm" type="submit"
                                                    title="Chuyển sang đang vận chuyển">
                                                <i class="fa-solid fa-truck"></i>
                                            </button>
                                        </form>
                                    </c:if>

                                    <c:if test="${o.statusName == 'Đang vận chuyển'}">
                                        <form action="${pageContext.request.contextPath}/admin/orders/status"
                                              method="post" style="display:inline;">
                                            <input type="hidden" name="orderId" value="${o.id}">
                                            <input type="hidden" name="statusName" value="Hoàn thành">
                                            <button class="btn btn-success btn-sm" type="submit" title="Hoàn thành đơn">
                                                <i class="fa-solid fa-check"></i>
                                            </button>
                                        </form>
                                    </c:if>

                                    <c:if test="${o.statusName == 'Đang xử lý'}">
                                        <button class="btn btn-warning btn-sm btn-edit-order"
                                                data-id="${o.id}"
                                                data-name="${o.fullName}"
                                                data-phone="${o.phoneNumber}"
                                                data-address="${o.address}"
                                                data-status="${o.statusName}">
                                            <i class="fa-solid fa-pen-to-square"></i>
                                        </button>
                                    </c:if>


                                    <c:if test="${o.statusName == 'Đang xử lý'}">
                                        <form action="${pageContext.request.contextPath}/admin/orders/status"
                                              method="post" style="display:inline;"
                                              onsubmit="return confirm('Bạn chắc chắn muốn hủy đơn hàng này?');">
                                            <input type="hidden" name="orderId" value="${o.id}">
                                            <input type="hidden" name="statusName" value="Đã hủy">
                                            <button class="btn btn-danger btn-sm btn-delete" type="submit"
                                                    title="Hủy đơn">
                                                <i class="fa-solid fa-ban"></i>
                                            </button>
                                        </form>
                                    </c:if>

                                </div>
                            </td>

                        </tr>
                    </c:forEach>
                    </tbody>

                </table>

            </div>

        </div>

    </div>
    <!-- Modal Cập Nhật Đơn Hàng -->
    <div id="editOrderModal" class="modal">
        <div class="modal-content category-box">
            <div class="modal-header">
                <h3>Cập Nhật Đơn Hàng</h3>
                <span class="close-edit-order">&times;</span>
            </div>

            <form action="${pageContext.request.contextPath}/admin/orders/edit"
                  method="post" accept-charset="UTF-8">

                <div class="modal-body">
                    <input type="hidden" id="editOrderIdHidden" name="orderId">

                    <label>Mã đơn hàng:</label>
                    <input type="text" id="editOrderId" value="" disabled>

                    <label>Họ tên khách hàng:</label>
                    <input type="text" id="editOrderName" name="fullName" required>

                    <label>Số điện thoại:</label>
                    <input type="text" id="editOrderPhone" name="phoneNumber" required>

                    <label>Địa chỉ giao hàng:</label>
                    <input type="text" id="editOrderAddress" name="address" required>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn-cancel-edit">Hủy</button>
                    <button type="submit" class="btn-save-edit">Cập nhật</button>
                </div>
            </form>
        </div>
    </div>

</div>


</body>
<script>
    //datatable
    $(function () {
        const viUrl = "https://cdn.datatables.net/plug-ins/1.13.8/i18n/vi.json";

        $("#orderTable").DataTable({
            pageLength: 8,
            lengthChange: false,
            ordering: true,
            searching: true,
            info: false,
            language: {url: viUrl}
        });
    });

    const editOrderModal = document.getElementById("editOrderModal");
    const closeEditOrder = document.querySelector(".close-edit-order");
    const btnCancelOrder = document.querySelector(".btn-cancel-edit");

    function openEditModal(btn) {
        const id = btn.dataset.id;
        const name = btn.dataset.name || "";
        const phone = btn.dataset.phone || "";
        const address = btn.dataset.address || "";

        document.getElementById("editOrderIdHidden").value = id;
        document.getElementById("editOrderId").value = "DH" + String(id).padStart(2, "0");
        document.getElementById("editOrderName").value = name;
        document.getElementById("editOrderPhone").value = phone;
        document.getElementById("editOrderAddress").value = address;

        editOrderModal.style.display = "flex";
    }

    document.querySelectorAll(".btn-edit-order").forEach(btn => {
        btn.addEventListener("click", () => openEditModal(btn));
    });

    closeEditOrder.onclick = () => editOrderModal.style.display = "none";
    btnCancelOrder.onclick = () => editOrderModal.style.display = "none";


    (function () {
        const toast = document.getElementById("toast");
        if (!toast) return;

        setTimeout(() => {
            toast.classList.remove("show");
        }, 2200);

        setTimeout(() => {
            toast.remove();
        }, 2600);
    })();
</script>

</html>