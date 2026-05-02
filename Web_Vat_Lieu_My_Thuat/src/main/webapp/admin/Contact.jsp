<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý liên hệ</title>
    <link rel="stylesheet" href="../assets/css/style.css">
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
        /* hoặc màu khác */
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
        background: #484ddf;
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
        background: #ccc;
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

    .order-table td:nth-child(6) {
        max-width: 250px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }

    /* phan hoi */
    .btn-reply {
        background-color: #0D6EFD;
        /* xanh kiểu bootstrap */
        color: white;
        border: none;
        padding: 6px 10px;
        cursor: pointer;
        font-size: 14px;
        border-radius: 4px;
        transition: 0.2s;
    }

    .btn-reply:hover {
        background-color: #0b5ed7;
    }

    .btn-reply i {
        font-size: 14px;
    }

    /* form gui phan hoi */
    .modal {
        display: none;
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, 0.5);
    }

    .reply-box {
        width: 500px;
        background: white;
        margin: 100px auto;
        padding: 0;
        border-radius: 8px;
    }

    .modal-header {
        padding: 20px;
        border-bottom: 1px solid #ddd;
    }

    .modal-header h3 {
        margin: 0;
        font-size: 18px;
        color: #333;
    }

    .close-reply {
        float: right;
        font-size: 24px;
        cursor: pointer;
        color: #999;
    }

    .close-reply:hover {
        color: #333;
    }

    .modal-body {
        padding: 20px;
    }

    .modal-body p {
        margin: 0 0 15px 0;
        font-size: 14px;
    }

    .modal-body label {
        display: block;
        margin-bottom: 8px;
        font-size: 14px;
        font-weight: bold;
    }

    .modal-body textarea {
        width: 100%;
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 4px;
        font-size: 14px;
        box-sizing: border-box;
    }

    .modal-body textarea:focus {
        border-color: #2659F3;
        outline: none;
    }

    .modal-footer {
        padding: 15px 20px;
        border-top: 1px solid #ddd;
        text-align: right;
    }

    .btn-cancel {
        background: #f5f5f5;
        color: #333;
        padding: 8px 20px;
        border: 1px solid #ddd;
        border-radius: 4px;
        cursor: pointer;
        margin-right: 10px;
    }

    .btn-cancel:hover {
        background: #e5e5e5;
    }

    .btn-send {
        background: #2659F3;
        color: white;
        padding: 8px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    .btn-send:hover {
        background: #1e40af;
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
        opacity: 0.6;
        color: #fff;
    }


    .order-table tbody tr:hover td {
        background: rgba(38, 89, 245, 0.06);
    }

</style>

<body>
<c:if test="${not empty sessionScope.toast}">
    <div id="toastMsg" style="
      position: fixed; right: 18px; top: 18px; z-index: 99999;
      background: #111; color:#fff; padding:10px 14px; border-radius:8px;">
            ${sessionScope.toast}
    </div>

    <c:remove var="toast" scope="session"/>

    <script>
        setTimeout(() => {
            const t = document.getElementById("toastMsg");
            if (t) t.style.display = "none";
        }, 1800);
    </script>
</c:if>
<div id="main">
    <div class="left">
        <div class="list-admin">
            <a href="Admin.jsp" class="logo"><img
                    src="../assets/images/logo/logo.png" alt></a>
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
            <a href="${pageContext.request.contextPath}/admin/contacts" class="active"><i class="fa-solid fa-address-book"></i>Quản lý
                liên hệ</a>
            <c:if test="${sessionScope.currentUser.role eq 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/admin/logs">
                    <i class="fa-solid fa-clock-rotate-left"></i>Quản lý thao tác
                </a>
            </c:if>
            <a href="${pageContext.request.contextPath}/logout"><i
                    class="fa-solid fa-right-from-bracket"></i>
                Đăng xuất</a>
        </div>
    </div>
    <div class="right">

        <div class="container">
            <div class="order-container">
                <h1>Quản lý liên hệ</h1>
                <table id="contactTable" class="order-table display">
                    <thead>
                    <tr>
                        <th>STT</th>
                        <th>Họ Tên</th>
                        <th>Số Điện Thoại</th>
                        <th>Email</th>
                        <th>Ngày Gửi</th>
                        <th>Nội Dung</th>
                        <th>Trạng Thái</th>
                        <th>Tùy Chọn</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="ct" items="${contacts}" varStatus="st">
                        <tr>
                            <td>${st.index + 1}</td>
                            <td><c:out value="${ct.fullName}" default="-"/></td>
                            <td><c:out value="${ct.phoneNumber}" default="-"/></td>
                            <td><c:out value="${ct.email}" default="-"/></td>

                            <td>
                                <c:choose>
                                    <c:when test="${not empty ct.createAt}">
                                        <fmt:formatDate value="${ct.createAt}" pattern="dd-MM-yyyy"/>
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>

                            <td title="<c:out value='${ct.message}'/>">
                                <c:out value="${ct.message}" default="-"/>
                            </td>

                            <td>
                                <c:choose>
                                    <c:when test="${ct.status == 'Chưa xử lý' || ct.status == 'Chờ phản hồi'}">
                                        <span class="status pending"><c:out value="${ct.status}"/></span>
                                    </c:when>
                                    <c:when test="${ct.status == 'Đã phản hồi' || ct.status == 'Đã xử lý'}">
                                        <span class="status success"><c:out value="${ct.status}"/></span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="status cancel"><c:out value="${ct.status}" default="-"/></span>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <c:if test="${ct.status != 'Đã phản hồi' && ct.status != 'Đã xử lý'}">
                                    <button class="btn btn-reply btn-sm"
                                            data-id="${ct.id}"
                                            data-email="${ct.email}">
                                        <i class="fa-solid fa-reply"></i>
                                    </button>
                                </c:if>

                                <form action="${pageContext.request.contextPath}/admin/contacts/delete"
                                      method="post"
                                      style="display:inline;"
                                      onsubmit="return confirmDelete();">
                                    <input type="hidden" name="id" value="${ct.id}">
                                    <button type="submit" class="btn btn-danger btn-sm btn-delete">
                                        <i class="fa-solid fa-trash"></i>
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

    <!-- Modal Form Phản Hồi -->
    <div id="replyModal" class="modal">
        <div class="reply-box">
            <div class="modal-header">
                <span class="close-reply">&times;</span>
                <h3>Phản Hồi Khách Hàng</h3>
            </div>

            <form action="${pageContext.request.contextPath}/admin/contacts/reply" method="post">
                <div class="modal-body">
                    <input type="hidden" name="id" id="replyContactId">

                    <p><strong>Email:</strong> <span id="replyEmail"></span></p>

                    <label>Tiêu đề:</label>
                    <input type="text" name="subject" value="Phản hồi liên hệ -Thiên Long"
                           style="width:100%; padding:10px; border:1px solid #ddd; border-radius:4px; margin-bottom:12px;">

                    <label for="replyMessage">Nội dung phản hồi:</label>
                    <textarea id="replyMessage" name="replyMessage"
                              placeholder="Nhập nội dung phản hồi..." rows="6"></textarea>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn-cancel">Hủy</button>
                    <button type="submit" class="btn-send">Gửi phản hồi</button>
                </div>
            </form>
        </div>
    </div>
</div>


<script>

    function confirmDelete() {
        return confirm("Bạn có chắc chắn muốn xóa liên hệ này không?");
    }

    const modal = document.getElementById('replyModal');
    const closeBtn = document.querySelector('.close-reply');
    const cancelBtn = document.querySelector('#replyModal .btn-cancel');

    const replyIdEl = document.getElementById('replyContactId');
    const replyEmailEl = document.getElementById('replyEmail');
    const replyMessageEl = document.getElementById('replyMessage');

    document.addEventListener('click', function (e) {
        const btn = e.target.closest('.btn-reply');
        if (!btn) return;

        replyIdEl.value = btn.getAttribute('data-id');
        replyEmailEl.textContent = btn.getAttribute('data-email') || "-";
        replyMessageEl.value = "";

        modal.style.display = 'block';
    });

    function closeModal() {
        modal.style.display = 'none';
    }

    closeBtn.addEventListener('click', closeModal);
    cancelBtn.addEventListener('click', closeModal);

    window.addEventListener('click', (e) => {
        if (e.target === modal) closeModal();
    });

    $(function () {
        const viUrl = "https://cdn.datatables.net/plug-ins/1.13.8/i18n/vi.json";

        const dt = $("#contactTable").DataTable({
            pageLength: 8,
            lengthChange: false,
            ordering: true,
            searching: true,
            info: false,
            language: {url: viUrl}
        });


        $("#contactSearch").on("keyup", function () {
            dt.search(this.value).draw();
        });
    });
</script>
</body>

</html>