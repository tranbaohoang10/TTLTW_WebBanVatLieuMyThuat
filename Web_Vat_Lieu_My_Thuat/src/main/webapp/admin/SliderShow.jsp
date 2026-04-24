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

    .search-input-icon{
        display:flex;
        align-items:center;
        border:1px solid #ddd;
        border-radius:10px;
        overflow:hidden;
        background:#fff;
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

    .icon{
        background:#f1f1f1;
        padding:10px 15px;
        display:flex;
        align-items:center;
        justify-content:center;
        cursor:pointer;
        border:none;
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

    .pagination{
        display:flex;
        justify-content:center;
        align-items:center;
        margin:25px 0;
        gap:5px;
        flex-wrap: wrap;
    }

    .page-link{
        padding:6px 12px;
        border:1px solid #d0d7de;
        border-radius:4px;
        color:#0d6efd;
        background:white;
        text-decoration:none;
        font-size:14px;
        transition:.2s;
    }
    .page-link:hover{ background:#e9ecef; }
    .page-link.active{
        background:#2659F5;
        color:white;
        font-weight:bold;
        border-color:#2659F5;
    }

    /* ===== MODAL ===== */
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

<body>
<div id="main">
    <div class="left">
        <div class="list-admin">
            <a href="Admin.jsp" class="logo"><img src="../assets/images/logo/logo.png" alt=""></a>

            <a href="${pageContext.request.contextPath}/admin/overview"><i class="fa-solid fa-house"></i> Tổng quan</a>
            <a href="${pageContext.request.contextPath}/admin/statistics"><i class="fa-solid fa-chart-line"></i>Thống kê</a>
            <a href="${pageContext.request.contextPath}/admin/categories"><i class="fa-solid fa-list"></i>Quản lý danh mục</a>
            <a href="${pageContext.request.contextPath}/admin/products"><i class="fa-solid fa-palette"></i>Quản lý sản phẩm</a>
            <a href="${pageContext.request.contextPath}/admin/users"><i class="fa-solid fa-person"></i>Quản lý người dùng</a>
            <a href="${pageContext.request.contextPath}/admin/orders"><i class="fa-solid fa-box-open"></i>Quản lý đơn hàng</a>
            <a href="${pageContext.request.contextPath}/admin/vouchers"><i class="fa-solid fa-gift"></i>Quản lý khuyến mãi</a>

            <a href="${pageContext.request.contextPath}/admin/sliders" class="active">
                <i class="fa-solid fa-sliders"></i>Quản lý Slider Show
            </a>

            <a href="${pageContext.request.contextPath}/admin/contacts"><i class="fa-solid fa-address-book"></i>Quản lý liên hệ</a>
            <a href="${pageContext.request.contextPath}/admin/logs">
                <i class="fa-solid fa-clock-rotate-left"></i>Quản lý thao tác
            </a>
            <a href="${pageContext.request.contextPath}/logout"><i class="fa-solid fa-right-from-bracket"></i>Đăng xuất</a>
        </div>
    </div>

    <div class="right">
        <div class="container">
            <div class="order-container">
                <h1>Danh sách slideshow</h1>

                <div class="search">
                    <div class="search-input-icon">
                        <form id="sliderSearchForm" action="${pageContext.request.contextPath}/admin/sliders" method="get" style="display:flex; align-items:center;">
                            <input id="sliderSearchInput" name="q" value="${q}" placeholder="Tìm slider..." autocomplete="off"/>
<%--                            <select id="sliderSizeSelect" name="size">--%>
<%--                                <option value="5"  ${size==5?'selected':''}>5</option>--%>
<%--                                <option value="10" ${size==10?'selected':''}>10</option>--%>
<%--                                <option value="20" ${size==20?'selected':''}>20</option>--%>
<%--                            </select>--%>
                            <button class="icon" type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
                        </form>
                    </div>

                    <button type="button" class="btn-them-banner">Thêm slideshow</button>

                    <div id="sliderSearchStatus" style="width:100%; text-align:right; font-size:13px; color:#666;"></div>
                </div>

                <table class="order-table">
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

                <div id="sliderPagination" class="pagination">
                    <c:if test="${page > 1}">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/admin/sliders?q=${q}&size=${size}&page=${page-1}">Trước</a>
                    </c:if>

                    <c:forEach var="p" begin="1" end="${totalPages}">
                        <a class="page-link ${p==page ? 'active' : ''}"
                           href="${pageContext.request.contextPath}/admin/sliders?q=${q}&size=${size}&page=${p}">${p}</a>
                    </c:forEach>

                    <c:if test="${page < totalPages}">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/admin/sliders?q=${q}&size=${size}&page=${page+1}">Sau</a>
                    </c:if>
                </div>

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
    // ===== MODAL ADD/EDIT (event delegation để AJAX render vẫn bấm được) =====
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

    // Mở modal edit bằng delegation (bấm nút nào cũng ăn kể cả sau AJAX)
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
    // ===== AJAX search + paging =====
    const ctxSlider = "${pageContext.request.contextPath}";
    const sForm = document.getElementById("sliderSearchForm");
    const sInput = document.getElementById("sliderSearchInput");
    const sSize = document.getElementById("sliderSizeSelect");
    const sTbody = document.getElementById("sliderTbody");
    const sPaging = document.getElementById("sliderPagination");
    const sStatus = document.getElementById("sliderSearchStatus");

    let sTimer = null;
    let sAbort = null;

    function text(v){ return (v == null) ? "" : String(v); }

    function renderRows(list){
        if (!sTbody) return;
        sTbody.innerHTML = "";

        if (!list || list.length === 0){
            const tr = document.createElement("tr");
            const td = document.createElement("td");
            td.colSpan = 6;
            td.style.textAlign = "center";
            td.style.padding = "14px";
            td.style.color = "#666";
            td.innerText = "Không có dữ liệu";
            tr.appendChild(td);
            sTbody.appendChild(tr);
            return;
        }

        list.forEach((s) => {
            const tr = document.createElement("tr");

            // ID
            const tdId = document.createElement("td");
            tdId.innerText = s.id;

            // Title
            const tdTitle = document.createElement("td");
            tdTitle.className = "col-title";
            tdTitle.innerText = text(s.title);

            // Status
            const tdStatus = document.createElement("td");
            tdStatus.className = "col-status";
            const span = document.createElement("span");
            if (s.status === 1){
                span.className = "status success";
                span.innerText = "Hiển thị";
            } else {
                span.className = "status cancel";
                span.innerText = "Ẩn";
            }
            tdStatus.appendChild(span);

            // IndexOrder
            const tdOrder = document.createElement("td");
            tdOrder.className = "col-order";
            tdOrder.innerText = text(s.indexOrder);

            // Thumbnail
            const tdThumb = document.createElement("td");
            const img = document.createElement("img");
            let src = text(s.thumbnail);
            // nếu backend trả về "/context/uploads/..." thì dùng luôn
            img.src = src;
            img.alt = "banner";
            tdThumb.appendChild(img);

            // Options
            const tdOpt = document.createElement("td");

            const btnEdit = document.createElement("button");
            btnEdit.type = "button";
            btnEdit.className = "btn-Sua btn-open-edit";
            btnEdit.dataset.id = text(s.id);
            btnEdit.dataset.title = text(s.title);
            btnEdit.dataset.status = text(s.status);
            btnEdit.dataset.order = text(s.indexOrder);

            btnEdit.dataset.thumb = text(s.thumbnail);
            btnEdit.innerHTML = '<i class="fa-solid fa-pen-to-square"></i>';

            const formToggle = document.createElement("form");
            formToggle.method = "post";
            formToggle.action = ctxSlider + "/admin/sliders";
            formToggle.style.display = "inline";

            const tAction = document.createElement("input");
            tAction.type = "hidden";
            tAction.name = "action";
            tAction.value = "toggle";

            const tId = document.createElement("input");
            tId.type = "hidden";
            tId.name = "id";
            tId.value = s.id;

            const tCur = document.createElement("input");
            tCur.type = "hidden";
            tCur.name = "currentStatus";
            tCur.value = s.status;

            const btnToggle = document.createElement("button");
            btnToggle.type = "submit";
            btnToggle.className = "btn-Sua";
            btnToggle.title = "Ẩn/Hiện";
            btnToggle.innerHTML = '<i class="fa-solid fa-eye"></i>';

            formToggle.appendChild(tAction);
            formToggle.appendChild(tId);
            formToggle.appendChild(tCur);
            formToggle.appendChild(btnToggle);

            const formDel = document.createElement("form");
            formDel.method = "post";
            formDel.action = ctxSlider + "/admin/sliders";
            formDel.style.display = "inline";
            formDel.onsubmit = () => confirm("Xóa slider #" + s.id + "?");

            const dAction = document.createElement("input");
            dAction.type = "hidden";
            dAction.name = "action";
            dAction.value = "delete";

            const dId = document.createElement("input");
            dId.type = "hidden";
            dId.name = "id";
            dId.value = s.id;

            const btnDel = document.createElement("button");
            btnDel.type = "submit";
            btnDel.className = "btn-Xoa";
            btnDel.innerHTML = '<i class="fa-solid fa-trash"></i>';

            formDel.appendChild(dAction);
            formDel.appendChild(dId);
            formDel.appendChild(btnDel);

            tdOpt.appendChild(btnEdit);
            tdOpt.appendChild(formToggle);
            tdOpt.appendChild(formDel);

            tr.appendChild(tdId);
            tr.appendChild(tdTitle);
            tr.appendChild(tdStatus);
            tr.appendChild(tdOrder);
            tr.appendChild(tdThumb);
            tr.appendChild(tdOpt);

            sTbody.appendChild(tr);
        });
    }

    function renderPaging(totalPages, currentPage){
        if (!sPaging) return;
        sPaging.innerHTML = "";

        function addLink(label, page, active){
            const a = document.createElement("a");
            a.href = "#";
            a.className = "page-link" + (active ? " active" : "");
            a.dataset.page = page;
            a.innerText = label;
            sPaging.appendChild(a);
        }

        if (currentPage > 1) addLink("Trước", currentPage - 1, false);
        for (let p = 1; p <= totalPages; p++) addLink(String(p), p, p === currentPage);
        if (currentPage < totalPages) addLink("Sau", currentPage + 1, false);
    }

    async function fetchSliders(q, page, size){
        if (sAbort) sAbort.abort();
        sAbort = new AbortController();

        if (sStatus) sStatus.innerText = "Đang tìm...";

        const url = new URL(ctxSlider + "/admin/sliders", window.location.origin);
        url.searchParams.set("ajax", "1");
        url.searchParams.set("q", q || "");
        url.searchParams.set("page", String(page || 1));
        url.searchParams.set("size", String(size || 10));

        const res = await fetch(url.toString(), {
            signal: sAbort.signal,
            headers: { "X-Requested-With": "XMLHttpRequest" }
        });

        const ct = res.headers.get("content-type") || "";
        if (!ct.includes("application/json")){
            window.location.href = ctxSlider + "/login";
            return;
        }

        const data = await res.json();

        renderRows(data.sliders);
        renderPaging(data.totalPages || 1, data.currentPage || 1);

        if (sStatus) sStatus.innerText = "";

        const newUrl = new URL(window.location.href);
        newUrl.searchParams.set("q", data.q || "");
        newUrl.searchParams.set("page", String(data.currentPage || 1));
        newUrl.searchParams.set("size", String(data.size || 10));
        window.history.replaceState({}, "", newUrl);
    }

    // gõ tới đâu tìm tới đó
    if (sInput){
        sInput.addEventListener("input", () => {
            clearTimeout(sTimer);
            sTimer = setTimeout(() => {
                fetchSliders(sInput.value || "", 1, parseInt(sSize?.value || "10", 10));
            }, 300);
        });
    }

    // đổi size -> fetch lại
    if (sSize){
        sSize.addEventListener("change", () => {
            fetchSliders(sInput?.value || "", 1, parseInt(sSize.value || "10", 10));
        });
    }

    // chặn submit -> fetch
    if (sForm){
        sForm.addEventListener("submit", (e) => {
            e.preventDefault();
            fetchSliders(sInput?.value || "", 1, parseInt(sSize?.value || "10", 10));
        });
    }

    // paging ajax
    if (sPaging){
        sPaging.addEventListener("click", (e) => {
            const a = e.target.closest("a.page-link");
            if (!a) return;
            e.preventDefault();
            fetchSliders(sInput?.value || "", parseInt(a.dataset.page || "1", 10), parseInt(sSize?.value || "10", 10));
        });
    }
</script>

</body>
</html>
