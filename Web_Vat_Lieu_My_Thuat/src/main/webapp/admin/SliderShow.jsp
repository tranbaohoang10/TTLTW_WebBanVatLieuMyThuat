<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý slider show</title>
    <link rel="stylesheet" href="../assets/css/style.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="stylesheet"
          href="https://cdn.datatables.net/1.13.8/css/jquery.dataTables.min.css">
</head>

<style>
    html, body { height: 100%; }

    #main{
        display: flex;
        min-height: 100vh;
        align-items: stretch;
    }

    #main .left{
        background-color: #17479D;
        width: 17%;
        min-height: 100vh;
        height: auto;
    }

    #main .left .list-admin{
        display: flex;
        flex-direction: column;
        gap: 15px;
        min-height: 100vh;
    }

    #main .left .list-admin a{
        display: block;
        text-decoration: none;
        color: white;
        padding: 10px 20px;
    }

    #main .left .list-admin a i{ margin-right: 20px; }

    #main .left .list-admin a:hover{
        background-color: #203247;
        border-left: #3B7DDD 2px solid;
    }

    #main .left .list-admin .logo img{
        width: 100%;
        height: auto;
        margin: 10px 0 20px 0;
    }

    #main .left .list-admin a.logo{ padding: 0; }
    #main .left .list-admin a.logo:hover{
        background-color: #203247;
        border-left: none;
    }

    .list-admin a.active{
        background-color: #203247;
        border-left: 4px solid #FFD700;
        font-weight: bold;
    }

    #main .right{
        flex: 1;
        background-color: #F9F9F9;
    }

    #main .right .container{
        display: flex;
        flex-direction: column;
        width: calc(100% - 100px);
        margin: 20px auto 0;
    }

    .order-container{
        width: 95%;
        margin: 10px auto 5px;
        background: white;
        padding: 25px;
        border-radius: 10px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    }

    h1{
        margin: 0 0 10px;
        color:#222;
    }

    .search{
        display:flex;
        align-items:center;
        justify-content:flex-end;
        gap: 10px;
        width: 100%;
        margin: 0 auto 15px;
        flex-wrap: wrap;
    }

    .search-input-icon input{
        padding:10px 15px;
        border:none;
        outline:none;
        width:250px;
        font-size:14px;
    }

    .search-input-icon select{
        border:none;
        outline:none;
        padding:10px 10px;
        background:#fff;
        border-left: 1px solid #eee;
        font-size: 14px;
        cursor:pointer;
    }

    .icon i{ font-size:16px; color:#333; }

    .btn-them-banner{
        background-color:#2659F5;
        border:none;
        color:white;
        padding:8px 16px;
        font-size:14px;
        border-radius:5px;
        cursor:pointer;
        margin-left:0;
    }

    .btn-Sua{
        background-color:#FFC107;
        color:black;
        border:none;
        padding:6px 10px;
        cursor:pointer;
        font-size:14px;
        border-radius:4px;
        transition:.2s;
    }
    .btn-Sua:hover{ background-color:#e0a800; }

    .btn-Xoa{
        background-color:#DC3545;
        color:white;
        border:none;
        padding:6px 10px;
        cursor:pointer;
        font-size:14px;
        border-radius:4px;
        transition:.2s;
        margin-left: 6px;
    }
    .btn-Xoa:hover{ background-color:#b02a37; }

    .order-table{
        width:100%;
        border-collapse:collapse;
        margin-top:10px;
    }

    .order-table th{
        background:#2659F5;
        color:white;
        padding:12px;
        font-size:14px;
    }

    .order-table td{
        padding:12px;
        background:#fafafa;
    }

    .order-table tr:nth-child(even) td{ background:#f0f0f0; }

    .order-table td, .order-table th{
        border-bottom:1px solid #ddd;
        text-align:left;
    }

    .order-table img{
        width:100px;
        height:auto;
        border-radius:5px;
        border: 1px solid #eee;
        background: #fff;
    }

    .status{
        padding:6px 12px;
        border-radius:8px;
        font-size:13px;
        font-weight:bold;
        display:inline-block;
    }
    .status.success{ background:#D1FAE5; color:#0f5132; }
    .status.cancel{ background:#ffd6d6; color:#b91c1c; }


    #sliderTable_wrapper .dataTables_filter {
        float: none !important;
        text-align: right !important;
        margin-bottom: 15px;
    }

    #sliderTable_wrapper .dataTables_filter label {
        font-size: 16px;
        font-weight: 500;
        color: #111;
    }

    #sliderTable_wrapper .dataTables_filter input {
        margin-left: 8px !important;
        padding: 8px 12px;
        border: 1px solid #cfcfcf !important;
        border-radius: 6px;
        outline: none;
        min-width: 220px;
        background-color: #fff;
    }

    #sliderTable_wrapper .dataTables_filter input:focus {
        border-color: #2659F5 !important;
    }

    #sliderTable_wrapper .dataTables_paginate {
        float: none !important;
        text-align: center !important;
        margin-top: 20px;
    }

    #sliderTable_wrapper .dataTables_paginate .paginate_button {
        border: none !important;
        background: transparent !important;
        color: #111 !important;
        padding: 8px 14px !important;
        margin: 0 3px;
        border-radius: 4px;
        min-width: 38px;
    }

    #sliderTable_wrapper .dataTables_paginate .paginate_button.current,
    #sliderTable_wrapper .dataTables_paginate .paginate_button.current:hover {
        background: #2659F5 !important;
        color: white !important;
        border: 1px solid #2659F5 !important;
    }

    #sliderTable_wrapper .dataTables_paginate .paginate_button:hover {
        background: #e9ecef !important;
        color: #2659F5 !important;
    }

    #sliderTable_wrapper .dataTables_paginate .paginate_button.disabled,
    #sliderTable_wrapper .dataTables_paginate .paginate_button.disabled:hover {
        color: #888 !important;
        background: transparent !important;
        cursor: default !important;
    }
    .modal{
        position: fixed;
        inset:0;
        background: rgba(0,0,0,.4);
        display:none;
        align-items:center;
        justify-content:center;
        z-index: 999;
    }

    .modal-content{
        background:#fff;
        width: 430px;
        max-width: 95%;
        border-radius:10px;
        box-shadow:0 4px 15px rgba(0,0,0,.2);
        overflow:hidden;
        animation: fadeInScale .2s ease-out;
    }

    .modal-header{
        display:flex;
        justify-content:space-between;
        align-items:center;
        padding:12px 16px;
        background:#2659F5;
        color:#fff;
    }

    .modal-header h2{
        margin:0;
        font-size:18px;
        width: 100%;
        text-align:center;
    }

    .close-modal, .close-edit-modal{
        cursor:pointer;
        font-size:20px;
        padding:0 5px;
    }

    .modal-body{ padding:15px 16px 5px; }

    .form-group{
        display:flex;
        flex-direction:column;
        margin-bottom:10px;
    }

    .form-group label{
        font-size:14px;
        margin-bottom:4px;
        color:#333;
    }

    .form-group input, .form-group select{
        padding:8px 10px;
        border-radius:6px;
        border:1px solid #ddd;
        font-size:14px;
        outline:none;
    }

    .form-group input:focus, .form-group select:focus{
        border-color:#2659F5;
    }

    .modal-footer{
        display:flex;
        justify-content:flex-end;
        gap:8px;
        padding:10px 16px 14px;
        background:#f7f7f7;
    }

    .btn-cancel, .btn-save, .btn-edit-cancel, .btn-edit-save{
        border:none;
        border-radius:5px;
        padding:8px 14px;
        font-size:14px;
        cursor:pointer;
    }

    .btn-cancel, .btn-edit-cancel{ background:#e0e0e0; color:#333; }
    .btn-save, .btn-edit-save{ background:#2659F5; color:#fff; }

    @keyframes fadeInScale{
        from{ opacity:0; transform:scale(.9); }
        to{ opacity:1; transform:scale(1); }
    }
</style>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.datatables.net/1.13.8/js/jquery.dataTables.min.js"></script>
<body>
<c:set var="permissions" value="${sessionScope.permissions}" />
<c:set var="role" value="${sessionScope.currentUser.role}" />
<div id="main">
    <div class="left">
        <div class="list-admin">
            <a href="Admin.jsp" class="logo"><img src="../assets/images/logo/logo.png" alt=""></a>

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
              <a href="${pageContext.request.contextPath}/admin/inventory"><i class="fa-solid fa-warehouse"></i>Quản
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
                <a href="${pageContext.request.contextPath}/admin/sliders" class="active"><i class="fa-solid fa-sliders"></i>Quản lý
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
            <c:if test="${role == 'ADMIN' || (not empty permissions && permissions.contains('RECOMMENDATION_VIEW'))}">
                <a href="${pageContext.request.contextPath}/admin/recommendations">
                    <i class="fa-solid fa-wand-magic-sparkles"></i>
                    Quản lý gợi ý
                </a>
            </c:if>
            <a href="${pageContext.request.contextPath}/logout"><i class="fa-solid fa-right-from-bracket"></i>Đăng xuất</a>
        </div>
    </div>

    <div class="right">
        <div class="container">
            <div class="order-container">
                <h1>Danh sách slideshow</h1>

                <c:if test="${not empty error}">
                    <div style="background:#f8d7da;color:#721c24;padding:12px 14px;border-radius:6px;margin-bottom:15px;border-left:4px solid #dc3545;">
                            ${error}
                    </div>
                </c:if>

                <div class="search">
                    <button type="button" class="btn-them-banner">Thêm slideshow</button>
                </div>

                <table id="sliderTable" class="order-table display">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Tiêu đề</th>
                        <th>Trạng thái</th>
                        <th>Thứ tự</th>
                        <th>Ảnh</th>
                        <th>Tùy chọn</th>
                    </tr>
                    </thead>

                    <tbody id="sliderTbody">
                    <c:forEach var="s" items="${sliders}">
                        <tr>
                            <td>${s.id}</td>
                            <td class="col-title">${s.title}</td>

                            <td class="col-status">
                                <c:choose>
                                    <c:when test="${s.status == 1}">
                                        <span class="status success">Hiển thị</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="status cancel">Ẩn</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td class="col-order">${s.indexOrder}</td>

                            <td>
                                <img class="col-thumb" src="${s.thumbnail}" alt="banner">
                            </td>

                            <td>
                                <button type="button"
                                        class="btn-Sua btn-open-edit"
                                        data-id="${s.id}"
                                        data-title="${s.title}"
                                        data-status="${s.status}"
                                        data-order="${s.indexOrder}"

                                        data-thumb="${s.thumbnail}">
                                    <i class="fa-solid fa-pen-to-square"></i>
                                </button>

                                <form style="display:inline;" method="post" action="${pageContext.request.contextPath}/admin/sliders">
                                    <input type="hidden" name="action" value="toggle">
                                    <input type="hidden" name="id" value="${s.id}">
                                    <input type="hidden" name="currentStatus" value="${s.status}">
                                    <button class="btn-Sua" type="submit" title="Ẩn/Hiện">
                                        <i class="fa-solid fa-eye"></i>
                                    </button>
                                </form>

                                <form style="display:inline;" method="post"
                                      action="${pageContext.request.contextPath}/admin/sliders"
                                      onsubmit="return confirm('Xóa slider #${s.id}?');">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${s.id}">
                                    <button class="btn-Xoa" type="submit">
                                        <i class="fa-solid fa-trash"></i>
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>

                    <c:if test="${empty sliders}">
                        <tr><td colspan="6" style="text-align:center; padding:14px; color:#666;">Không có dữ liệu</td></tr>
                    </c:if>
                    </tbody>
                </table>



            </div>
        </div>
    </div>
</div>

<!-- ===== MODAL THÊM ===== -->
<div id="addSliderModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2>Thêm slideshow</h2>
            <span class="close-modal">&times;</span>
        </div>

        <form method="post" action="${pageContext.request.contextPath}/admin/sliders" enctype="multipart/form-data">
            <input type="hidden" name="action" value="create">

            <div class="modal-body">
                <div class="form-group">
                    <label>Tiêu đề</label>
                    <input type="text" name="title" placeholder="Nhập tiêu đề" required>
                </div>


                <div class="form-group">
                    <label>Trạng thái</label>
                    <select name="status">
                        <option value="1">Hiển thị</option>
                        <option value="0">Ẩn</option>
                    </select>
                </div>

                <div class="form-group">
                    <label>Thứ tự hiển thị (indexOrder)</label>
                    <input type="number" name="indexOrder" min="1" placeholder="Nhập thứ tự" required>
                </div>

                <div class="form-group">
                    <label>Upload ảnh</label>
                    <input type="file" name="thumbnailFile" accept="image/*" required>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn-cancel">Hủy</button>
                <button type="submit" class="btn-save">Lưu</button>
            </div>
        </form>
    </div>
</div>

<!-- ===== MODAL SỬA ===== -->
<div id="editSliderModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2>Chỉnh sửa slideshow</h2>
            <span class="close-edit-modal">&times;</span>
        </div>

        <form method="post" action="${pageContext.request.contextPath}/admin/sliders" enctype="multipart/form-data">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" id="editId">

            <div class="modal-body">
                <div class="form-group">
                    <label>Tiêu đề</label>
                    <input type="text" name="title" id="editTitle" required>
                </div>



                <div class="form-group">
                    <label>Trạng thái</label>
                    <select name="status" id="editStatus">
                        <option value="1">Hiển thị</option>
                        <option value="0">Ẩn</option>
                    </select>
                </div>

                <div class="form-group">
                    <label>Thứ tự hiển thị (indexOrder)</label>
                    <input type="number" name="indexOrder" id="editOrder" min="1" required>
                </div>

                <div class="form-group">
                    <label>Ảnh hiện tại</label>
                    <img id="editPreview" src="" alt="preview" style="width:100px;border-radius:6px;border:1px solid #eee;background:#fff;">
                </div>

                <div class="form-group">
                    <label>Upload ảnh mới (nếu muốn đổi)</label>
                    <input type="file" name="thumbnailFile" accept="image/*">
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn-edit-cancel">Hủy</button>
                <button type="submit" class="btn-edit-save">Lưu thay đổi</button>
            </div>
        </form>
    </div>
</div>

<script>
    const addModal = document.getElementById("addSliderModal");
    const editModal = document.getElementById("editSliderModal");

    const btnAdd = document.querySelector(".btn-them-banner");
    const btnCloseAdd = document.querySelector(".close-modal");
    const btnCancelAdd = document.querySelector("#addSliderModal .btn-cancel");

    const btnCloseEdit = document.querySelector(".close-edit-modal");
    const btnCancelEdit = document.querySelector("#editSliderModal .btn-edit-cancel");

    const editId = document.getElementById("editId");
    const editTitle = document.getElementById("editTitle");

    const editOrder = document.getElementById("editOrder");
    const editStatus = document.getElementById("editStatus");
    const editPreview = document.getElementById("editPreview");

    if (btnAdd) btnAdd.onclick = () => addModal.style.display = "flex";
    if (btnCloseAdd) btnCloseAdd.onclick = () => addModal.style.display = "none";
    if (btnCancelAdd) btnCancelAdd.onclick = () => addModal.style.display = "none";

    if (btnCloseEdit) btnCloseEdit.onclick = () => editModal.style.display = "none";
    if (btnCancelEdit) btnCancelEdit.onclick = () => editModal.style.display = "none";

    window.addEventListener("click", (e) => {
        if (e.target === addModal) addModal.style.display = "none";
        if (e.target === editModal) editModal.style.display = "none";
    });

    document.addEventListener("click", (e) => {
        const btn = e.target.closest(".btn-open-edit");
        if (!btn) return;

        editId.value = btn.dataset.id || "";
        editTitle.value = btn.dataset.title || "";
        editOrder.value = btn.dataset.order || "1";
        editStatus.value = btn.dataset.status || "1";

        editPreview.src = btn.dataset.thumb || "";

        editModal.style.display = "flex";
    });
</script>
<script>
    $(document).ready(function () {
        $('#sliderTable').DataTable({
            pageLength: 10,
            lengthChange: false,
            ordering: true,
            searching: true,
            info: false,
            dom: 'ftip',
            language: {
                search: "Tìm kiếm:",
                zeroRecords: "Không tìm thấy slideshow phù hợp",
                emptyTable: "Không có dữ liệu",
                paginate: {
                    previous: "Trước",
                    next: "Sau"
                }
            },
            columnDefs: [
                { orderable: false, targets: [4, 5] }
            ]
        });
    });
</script>

</body>
</html>
