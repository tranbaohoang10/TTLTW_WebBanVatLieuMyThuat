<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý danh mục</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />

    <!-- DataTables -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.8/css/jquery.dataTables.min.css" />
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.8/js/jquery.dataTables.min.js"></script>

    <style>
        #main { display:flex; }
        #main .left{ background-color:#17479D; height:100vh; width:17%; }
        #main .left .list-admin{ display:flex; flex-direction:column; gap:15px; }
        #main .left .list-admin a{ display:block; text-decoration:none; color:#fff; padding:10px 20px; }
        #main .left .list-admin a i{ margin-right:20px; }
        #main .left .list-admin a:hover{ background-color:#203247; border-left:2px solid #3B7DDD; }
        #main .left .list-admin .logo img{ width:100%; height:auto; margin:10px 0 20px 0; }
        #main .left .list-admin a.logo{ padding:0; }
        #main .left .list-admin a.logo:hover{ background-color:#203247; border-left:none; }
        .list-admin a.active{ background-color:#203247; border-left:4px solid #FFD700; font-weight:bold; }

        #main .right{ flex:1; background:#F9F9F9; }
        #main .right .container{
            display:flex; flex-direction:column; width:calc(100% - 100px);
            margin:20px auto 0 auto;
        }

        .list-container{
            width:95%; margin:30px auto; background:#fff; padding:25px;
            border-radius:10px; box-shadow:0 2px 8px rgba(0,0,0,.1);
        }

        .header{
            display:flex; justify-content:space-between; align-items:center; margin-bottom:20px;
        }

        .btn-add{
            background:#2659F5; color:#fff; border:none; padding:8px 12px;
            border-radius:5px; cursor:pointer;
        }
        .btn-add:hover{ background:#17479D; }

        table.category-table{ width:100%; border-collapse:collapse; margin-top:10px; }
        table.category-table th{
            background:#2659F5; color:#fff; padding:12px; font-size:14px;
        }
        table.category-table td{
            padding:12px; background:#fafafa;
            border-bottom:1px solid #ddd; border-left:1px solid #ddd; border-right:1px solid #ddd;
        }
        table.category-table tr:nth-child(even) td{ background:#f0f0f0; }

        .thumb{
            width:60px; height:60px; object-fit:cover; border-radius:8px;
            border:1px solid #ddd;
        }
        .col-thumb{ text-align:center; width:90px; }

        .btn-icon{
            color:#fff; padding:8px 12px; border-radius:5px; cursor:pointer;
            margin-right:5px; border:none;
        }
        .btn-edit{ background:#FFC107; color:#000; }

        .btn-icon:hover{ opacity:.85; }

        /* Badge trạng thái */
        .badge-active { color:#28a745; font-weight:600; }
        .badge-inactive { color:#dc3545; font-weight:600; }



        .dataTables_filter input,
        .dataTables_length select{
            padding:6px 10px; border-radius:6px; border:1px solid #ddd; outline:none;
        }

        .dataTables_paginate .paginate_button{ padding:6px 10px !important; border-radius:6px !important; }
        .dataTables_wrapper .dataTables_paginate .paginate_button:hover{
            background:#17479D !important; color:#fff !important; border:1px solid #17479D !important;
        }
        .dataTables_wrapper .dataTables_paginate .paginate_button.current{
            background:#17479D !important; color:#fff !important; border:1px solid #17479D !important;
        }

        .modal{
            display:none; position:fixed; top:0; left:0; width:100%; height:100%;
            background:rgba(0,0,0,.5); justify-content:center; align-items:center; z-index:999;
            padding:20px; overflow-y:auto;
        }
        .modal-content{
            background:#fff; padding:25px; width:600px; border-radius:10px;
            box-shadow:0 2px 10px rgba(0,0,0,.2); max-height:90vh; overflow-y:auto;
            animation:fadeIn .2s ease;
        }
        @keyframes fadeIn{
            from{ opacity:0; transform:scale(.95); }
            to{ opacity:1; transform:scale(1); }
        }
        .modal-content label{ display:block; margin-top:10px; font-size:14px; color:#444; }
        .modal-content input{ width:100%; padding:8px; margin-top:4px; border:1px solid #ccc; border-radius:5px; }
        .modal-buttons{ display:flex; justify-content:flex-end; margin-top:20px; gap:10px; }
        #btn-save{ background:#2659F5; color:#fff; padding:8px 14px; border:none; border-radius:5px; cursor:pointer; }
        #btn-close{ background:#ccc; padding:8px 14px; border:none; border-radius:5px; cursor:pointer; }

        .toggle-btn i { color: #fff; }

        .toggle-btn:active i,
        .toggle-btn:focus i {
            color: #000 !important;
        }
        .btn-lock{
            background:#dc3545;
            color:#fff;
        }

        .btn-unlock{
            background:#28a745;
            color:#fff;
        }

        .btn-lock:hover,
        .btn-unlock:hover{
            opacity:0.85;
        }
    </style>
</head>

<body>
<div id="main">
    <!-- SIDEBAR -->
    <div class="left">
        <div class="list-admin">
            <a href="Admin.jsp" class="logo"><img src="../assets/images/logo/logo.png" alt=""></a>
            <a href="${pageContext.request.contextPath}/admin/overview"><i class="fa-solid fa-house"></i> Tổng quan</a>
            <a href="${pageContext.request.contextPath}/admin/statistics"><i class="fa-solid fa-chart-line"></i>Thống kê</a>
            <a href="${pageContext.request.contextPath}/admin/categories" class="active">
                <i class="fa-solid fa-list"></i>Quản lý danh mục
            </a>
            <a href="${pageContext.request.contextPath}/admin/products"><i class="fa-solid fa-palette"></i>Quản lý sản phẩm</a>
            <a href="${pageContext.request.contextPath}/admin/users"><i class="fa-solid fa-person"></i>Quản lý người dùng</a>
            <a href="${pageContext.request.contextPath}/admin/orders"><i class="fa-solid fa-box-open"></i>Quản lý đơn hàng</a>
            <a href="${pageContext.request.contextPath}/admin/vouchers"><i class="fa-solid fa-gift"></i>Quản lý khuyến mãi</a>
            <a href="${pageContext.request.contextPath}/admin/sliders"><i class="fa-solid fa-sliders"></i>Quản lý Slider Show</a>
            <a href="${pageContext.request.contextPath}/admin/contacts"><i class="fa-solid fa-address-book"></i>Quản lý liên hệ</a>
            <a href="${pageContext.request.contextPath}/logout"><i class="fa-solid fa-right-from-bracket"></i> Đăng xuất</a>

        </div>
    </div>

    <!-- RIGHT -->
    <div class="right">
        <div class="container">

            <div class="list-container">
                <div class="header">
                    <h1>Danh sách danh mục</h1>
                    <button class="btn-add" id="btnAdd">Thêm danh mục</button>
                </div>

                <table id="categoryTable" class="category-table display">
                    <thead>
                    <tr>
                        <th>STT</th>
                        <th>ID</th>
                        <th>Tên danh mục</th>
                        <th>Hình ảnh</th>

                        <th>Trạng thái</th>
                        <th>Tùy chọn</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="c" items="${categories}">
                        <tr>
                            <td></td> <!-- STT để trống -->

                            <td>${c.id}</td>
                            <td>${c.categoryName}</td>

                            <c:set var="imgUrl" value="${c.thumbnail}" />
                            <c:if test="${not empty c.thumbnail and not fn:startsWith(c.thumbnail,'http')}">
                                <c:set var="imgUrl" value="${pageContext.request.contextPath}${c.thumbnail}" />
                            </c:if>

                            <td class="col-thumb">
                                <c:choose>
                                    <c:when test="${not empty c.thumbnail}">
                                        <img class="thumb" src="${imgUrl}" alt="category">
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <c:choose>
                                    <c:when test="${c.isActive == 1}">
                                        <span class="badge-active">Đang dùng</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge-inactive">Đã khóa</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>


                            <td>
                                <!-- EDIT -->
                                <button class="btn-icon btn-edit btnEdit"
                                        data-id="${c.id}"
                                        data-name="${c.categoryName}"
                                        data-thumbnail="${imgUrl}">
                                    <i class="fa-solid fa-pen-to-square"></i>
                                </button>

                                <!-- LOCK/UNLOCK -->
                                <form action="${pageContext.request.contextPath}/admin/categories"
                                      method="post" style="display:inline">
                                    <input type="hidden" name="action" value="toggleActive">
                                    <input type="hidden" name="id" value="${c.id}">
                                    <input type="hidden" name="isActive" value="${c.isActive}">

                                    <button class="btn-icon ${c.isActive == 1 ? 'btn-lock' : 'btn-unlock'}"
                                            type="submit"
                                            title="${c.isActive == 1 ? 'Khóa danh mục' : 'Mở khóa danh mục'}"
                                            onclick="return confirm('${c.isActive == 1 ? "Khóa" : "Mở khóa"} danh mục này?')">
                                        <c:choose>
                                            <c:when test="${c.isActive == 1}">
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

<!-- MODAL ADD/EDIT -->
<div id="modal" class="modal">
    <div class="modal-content">
        <h2 id="modalTitle">Thêm danh mục</h2>

        <form id="categoryForm"
              action="${pageContext.request.contextPath}/admin/categories"
              method="post"
              enctype="multipart/form-data">

            <input type="hidden" name="action" id="action" value="create">
            <input type="hidden" name="id" id="dbId" value="">

            <label>Tên danh mục</label>
            <input type="text" id="categoryName" name="categoryName" required>

            <label>Ảnh danh mục</label>
            <input type="file" id="thumbnail" name="thumbnail" accept="image/*">

            <img id="previewImg" style="width:120px; display:none; margin-top:10px;">
            <button type="button" id="removeImgBtn" style="display:none;">Xóa ảnh</button>

            <div class="modal-buttons">
                <button id="btn-save" type="submit">Lưu</button>
                <button id="btn-close" type="button">Hủy</button>
            </div>
        </form>
    </div>
</div>
</div>

<script>
    // ===== Modal controls =====
    const modal = document.getElementById("modal");
    const btnAdd = document.getElementById("btnAdd");
    const btnClose = document.getElementById("btn-close");
    const modalTitle = document.getElementById("modalTitle");
    const actionInput = document.getElementById("action");
    const dbId = document.getElementById("dbId");
    const nameInput = document.getElementById("categoryName");

    // preview
    const thumbnailInput = document.getElementById("thumbnail");
    const previewImg = document.getElementById("previewImg");
    const removeImgBtn = document.getElementById("removeImgBtn");

    function openModal(){ modal.style.display="flex"; }
    function closeModal(){ modal.style.display="none"; }

    btnAdd.addEventListener("click", () => {
        modalTitle.innerText = "Thêm danh mục";
        actionInput.value = "create";
        dbId.value = "";
        nameInput.value = "";

        thumbnailInput.value = "";
        previewImg.style.display = "none";
        removeImgBtn.style.display = "none";

        openModal();
    });

    btnClose.addEventListener("click", closeModal);
    window.addEventListener("click", (e) => { if(e.target === modal) closeModal(); });

    thumbnailInput.addEventListener("change", function(){
        const file = this.files[0];
        if(file){
            previewImg.src = URL.createObjectURL(file);
            previewImg.style.display = "block";
            removeImgBtn.style.display = "inline-block";
        }
    });

    removeImgBtn.addEventListener("click", function(){
        thumbnailInput.value = "";
        previewImg.src = "";
        previewImg.style.display = "none";
        removeImgBtn.style.display = "none";
    });

    // ===== Edit button fill =====
    document.querySelectorAll(".btnEdit").forEach(btn => {
        btn.addEventListener("click", () => {
            modalTitle.innerText = "Cập nhật danh mục";
            actionInput.value = "update";

            dbId.value = btn.dataset.id;
            nameInput.value = btn.dataset.name || "";

            const thumb = btn.dataset.thumbnail;
            if(thumb){
                previewImg.src = thumb;
                previewImg.style.display = "block";
                removeImgBtn.style.display = "inline-block";
            }else{
                previewImg.style.display = "none";
                removeImgBtn.style.display = "none";
            }

            thumbnailInput.value = "";
            openModal();
        });
    });
</script>

<script>
    // ===== DataTables + Auto STT =====
    $(function () {
        const viUrl = "https://cdn.datatables.net/plug-ins/1.13.8/i18n/vi.json";

        const table = $("#categoryTable").DataTable({
            pageLength: 5,
            lengthChange: false,
            ordering: true,
            searching: true,
            info: false,
            language: { url: viUrl },
            columnDefs: [

                // 0: STT, 3: Hình, 5: Tùy chọn
                { orderable: false, targets: [0, 3, 5] }
            ],
            order: [[1, "asc"]] // sort theo ID
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
