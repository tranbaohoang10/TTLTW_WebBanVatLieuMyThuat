<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Quản lý khuyến mãi</title>
  <link rel="stylesheet" href="../assets/css/style.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
    integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
    crossorigin="anonymous" referrerpolicy="no-referrer" />

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

  #main .left .list-admin {
    display: flex;
    flex-direction: column;
    gap: 15px;
  }
    #main .left .list-admin{
       min-height: 100vh;
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


    #main .right{
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

  /* sửa margin */
  .order-container {
    width: 95%;
    margin: 10px auto 5px;
    background: white;
    padding: 25px;
    border-radius: 10px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  }

  /*  */

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
    margin-top: 5px;
  }

  .btn-Xoa:hover {
    background-color: #b02a37;
  }

  .btn-them-km {
    background-color: #2659F5;
    border: none;
    color: white;
    padding: 8px 16px;
    font-size: 14px;
    border-radius: 5px;
    cursor: pointer;
    margin-left: 0;
  }

  /*  */


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

  .status.success {
    background: #D1FAE5;
    color: #0f5132;
  }

  .status.cancel {
    background: #ffd6d6;
    color: #b91c1c;
  }

  /* ================== MODAL THÊM KHÁCH HÀNG ================== */
  .modal {
    position: fixed;
    inset: 0;
    /* top:0; right:0; bottom:0; left:0 */
    background: rgba(0, 0, 0, 0.4);
    display: none;
    /* Ẩn mặc định */
    align-items: center;
    /* Căn giữa theo chiều dọc */
    justify-content: center;
    /* Căn giữa theo chiều ngang */
    z-index: 999;
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
  }

  .modal-header h2 {
    margin: 0;
    font-size: 18px;
    text-align: center;
  }

  #Dialog-them-km .modal-header h2 {
    width: 100%;
    text-align: center;
  }

  #Dialog-sua-km .modal-header h2 {
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

  /* =========Xem trang trc sau======== */

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

  /* Hiệu ứng nhỏ khi mở modal */
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
/*  style cho modal xóa km*/
  #Dialog-xoa-km .modal-body {
      text-align: center;
  }

  #Dialog-xoa-km .modal-header{
      display: flex;
      align-items: center;
      padding: 12px 16px;
  }

  #Dialog-xoa-km .modal-header h2{
      margin: 0;
      flex: 1;
      text-align: center;
  }
  #Dialog-xoa-km .btn-delete-cancel{
      border: none;
      background: #e0e0e0;
      border-radius: 5px;
      cursor: pointer;
  }
  #Dialog-xoa-km .btn-delete-confirm{
      background-color: #DC3545;
      color: white;
      border: none;
      padding: 6px 10px;
      cursor: pointer;
      font-size: 14px;
      border-radius: 4px;
      transition: 0.2s;
  }
  #Dialog-xoa-km .btn-delete-confirm:hover{
      background-color: #b02a37;
  }
    .category-box {
        max-height: 90vh;
        overflow-y: auto;
    }
    input[readonly] {
        background: #f1f1f1;
        color: #777;
        cursor: not-allowed;
    }
</style>

<body>
  <div id="main">
    <div class="left">
      <div class="list-admin">
        <a href="Admin.jsp" class="logo"><img src="../assets/images/logo/logo.png" alt></a>
        <a href="${pageContext.request.contextPath}/admin/overview"><i class="fa-solid fa-house"></i> Tổng quan</a>
        <a href="${pageContext.request.contextPath}/admin/statistics"><i class="fa-solid fa-chart-line"></i>Thống
          kê</a>
        <a href="${pageContext.request.contextPath}/admin/categories"><i class="fa-solid fa-list"></i>Quản lý danh
          mục</a>
        <a href="${pageContext.request.contextPath}/admin/products"><i class="fa-solid fa-palette"></i>Quản
          lý sản phẩm</a>
        <a href="${pageContext.request.contextPath}/admin/users"><i class="fa-solid fa-person"></i>Quản
          lý người dùng</a>
        <a href="${pageContext.request.contextPath}/admin/orders"><i class="fa-solid fa-box-open"></i>Quản
          lý đơn hàng</a>
          <a href="${pageContext.request.contextPath}/admin/vouchers" class="active">
              <i class="fa-solid fa-gift"></i>Quản lý khuyến mãi
          </a>
          <a href="${pageContext.request.contextPath}/admin/sliders"><i class="fa-solid fa-sliders"></i>Quản lý
          Slider Show</a>
        <a href="${pageContext.request.contextPath}/admin/contacts"><i class="fa-solid fa-address-book"></i>Quản lý liên hệ</a>
        <a href="${pageContext.request.contextPath}/logout"><i class="fa-solid fa-right-from-bracket"></i>
          Đăng xuất</a>
      </div>
    </div>
    <div class="right">

      <div class="container">

        <div class="order-container">
          <h1>Danh sách khuyến mãi</h1>
            <div class="search">
                <div class="search-input-icon">
                    <form id="voucherSearchForm" action="${pageContext.request.contextPath}/admin/vouchers" method="get" style="display:flex;">
                        <input id="voucherSearchInput" type="text" name="keyword" placeholder="Tìm kiếm khuyến mãi..." value="${keyword}" autocomplete="off">
                        <button type="submit" class="icon">
                            <i class="fa-solid fa-magnifying-glass"></i>
                        </button>
                    </form>
                    <div id="voucherSearchStatus" style="margin:6px 0; font-size:13px; color:#666;"></div>

                </div>

                <button type="button" class="btn-them-km">Thêm khuyến mãi</button>
            </div>


            <table class="order-table">
            <thead>
              <tr>
                <th>STT</th>
                <th>Mã khuyến mãi</th>
                <th>Tên khuyến mãi</th>
                <th>Mô tả</th>
                <th>Ngày bắt đầu</th>
                <th>Ngày kết thúc</th>
                <th>Giảm giá</th>
                <th>Tùy chọn</th>
              </tr>
            </thead>

              <tbody id="voucherTbody">
              <c:if test="${empty vouchers}">
                  <tr>
                      <td colspan="8">Không có khuyến mãi nào.</td>
                  </tr>
              </c:if>

              <c:forEach var="v" items="${vouchers}" varStatus="st">
                  <tr data-id="${v.id}"
                      data-min="${v.minOrderValue}"
                      data-used="${v.quantityUsed}"
                      data-active="${v.isActive}"
                      data-qty="${v.quantity}">
                      <td>${(currentPage - 1) * 10 + st.index + 1}</td>
                      <td class="col-code">${v.code}</td>
                      <td class="col-name">${v.name}</td>
                      <td class="col-desc">${v.description}</td>
                      <td class="col-start" data-value="${v.startDate}">
                              ${v.startDate.toString().substring(0, 10)}
                      </td>
                      <td class="col-end" data-value="${v.endDate}">
                              ${v.endDate.toString().substring(0, 10)}
                      </td>
                      <td class="col-cash" data-value="${v.voucherCash}">${v.voucherCash}đ</td>
                      <td>
                          <!-- nút Sửa: mở modal, không cần submit -->
                          <button class="btn-Sua" type="button">
                              <i class="fa-solid fa-pen-to-square"></i>
                          </button>

                          <!-- nút Xóa: form POST -->
                          <form action="${pageContext.request.contextPath}/admin/vouchers" method="post"
                                style="display:inline">
                              <input type="hidden" name="action" value="delete">
                              <input type="hidden" name="id" value="${v.id}">
                              <!-- ĐỂ type="button" ĐỂ KHÔNG SUBMIT NGAY -->
                              <button class="btn-Xoa" type="button">
                                  <i class="fa-solid fa-trash"></i>
                              </button>
                          </form>
                      </td>
                  </tr>
              </c:forEach>
              </tbody>


          </table>
            <div id="voucherPagination" class="pagination">
                <!-- Nút Trước -->
                <c:if test="${currentPage > 1}">
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/admin/vouchers?page=${currentPage - 1}&keyword=${keyword}">
                        Trước
                    </a>
                </c:if>

                <!-- Các số trang -->
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <a class="page-link ${i == currentPage ? 'active' : ''}"
                       href="${pageContext.request.contextPath}/admin/vouchers?page=${i}&keyword=${keyword}">
                            ${i}
                    </a>
                </c:forEach>

                <!-- Nút Sau -->
                <c:if test="${currentPage < totalPages}">
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/admin/vouchers?page=${currentPage + 1}&keyword=${keyword}">
                        Sau
                    </a>
                </c:if>
            </div>

        </div>
      </div>
    </div>

  </div>

  </div>

  <!-- =============Dialog thêm khuyến mãi============= -->
  <div id="Dialog-them-km" class="modal">
    <div class="modal-content category-box">
      <div class="modal-header">
        <h2>Thêm khuyến mãi</h2>
          <span class="close-modal">&times;</span>
      </div>
        <form action="${pageContext.request.contextPath}/admin/vouchers" method="post">
      <div class="modal-body">
          <input type="hidden" name="action" value="create">
          <input type="hidden" name="quantityUsed" value="0">
          <input type="hidden" name="isActive" value="1">

          <div class="form-group">
          <label for="maKM">Mã khuyến mãi</label>
          <input type="text" id="maKM" name="code" placeholder="Nhập mã khuyến mãi">
        </div>

        <div class="form-group">
          <label for="tenKM">Tên khuyến mãi</label>
          <input type="text" id="tenKM" name="name" placeholder="Nhập tên khuyến mãi">
        </div>

        <div class="form-group">
          <label for="mota">Mô tả</label>
          <input type="text" id="mota" name="description" placeholder="Nhập mô tả">
        </div>

        <div class="form-group">
          <label for="ngaybatdau">Ngày bắt đầu</label>
          <input type="date" id="ngaybatdau" name="startDate" placeholder="Nhập ngày bắt đầu">
        </div>

        <div class="form-group">
          <label for="ngàyketthuc">Ngày ngày kết thúc</label>
          <input type="date" id="ngàyketthuc" name="endDate" placeholder="Nhập ngày kết thúc">
        </div>
          <div class="form-group">
              <label>Loại voucher</label>
              <select id="voucherType"  name="voucherType">
                  <option value="cash">Giảm tiền mặt</option>
                  <option value="percent">Giảm phần trăm</option>
                  <option value="ship">Miễn phí vận chuyển</option>
              </select>
          </div>
          <div class="form-group">
              <label for="giamgia">Giảm giá tiền mặt</label>
              <input type="number" id="giamgia" name="voucherCash" value="0" placeholder="Nhập số tiền giảm">
          </div>

          <div class="form-group">
              <label for="voucherPercent">Phần trăm giảm</label>
              <input type="number" id="voucherPercent" name="voucherPercent" value="0" placeholder="Nhập phần trăm giảm">
          </div>

          <div class="form-group">
              <label for="maxDiscount">Giảm tối đa</label>
              <input type="number" id="maxDiscount" name="maxDiscount" value="0" placeholder="Nhập mức giảm tối đa">
          </div>

          <div class="form-group">
              <label for="minOrderValue">Đơn tối thiểu</label>
              <input type="number" id="minOrderValue" name="minOrderValue" value="0" placeholder="Nhập đơn tối thiểu">
          </div>

        <div class="form-group">
          <label for="sl">Số lượng</label>
          <input type="number" id="sl" name="quantity" placeholder="Nhập số lượng">
        </div>
      </div>

      <div class="modal-footer">
        <button type="button" class="btn-cancel">Hủy</button>
        <button type="submit" class="btn-save">Lưu</button>
      </div>
        </form>
    </div>
  </div>
  <!-- ========== Dialog chỉnh sửa khuyến mãi ========== -->
  <div id="Dialog-sua-km" class="modal">
      <div class="modal-content">
          <div class="modal-header">
              <h2>Chỉnh sửa khuyến mãi</h2>
              <span class="close-edit-modal">&times;</span>
          </div>

          <form id="editVoucherForm" action="${pageContext.request.contextPath}/admin/vouchers" method="post">
              <div class="modal-body">
                  <input type="hidden" name="action" value="update">
                  <input type="hidden" name="id" id="editId">
                  <input type="hidden" name="minOrderValue" id="editMinOrderValue">
                  <input type="hidden" name="quantityUsed" id="editQuantityUsed">
                  <input type="hidden" name="isActive" id="editIsActive" value="1">

                  <div class="form-group">
                      <label for="editCode">Mã khuyến mãi</label>
                      <input type="text" id="editCode" name="code" required>
                  </div>

                  <div class="form-group">
                      <label for="editName">Tên khuyến mãi</label>
                      <input type="text" id="editName" name="name" required>
                  </div>

                  <div class="form-group">
                      <label for="editDesc">Mô tả</label>
                      <input type="text" id="editDesc" name="description">
                  </div>

                  <div class="form-group">
                      <label for="editStartDate">Ngày bắt đầu</label>
                      <input type="date" id="editStartDate" name="startDate">
                  </div>

                  <div class="form-group">
                      <label for="editEndDate">Ngày kết thúc</label>
                      <input type="date" id="editEndDate" name="endDate">
                  </div>

                  <div class="form-group">
                      <label for="editCash">Giảm giá</label>
                      <input type="number" id="editCash" name="voucherCash" required>
                  </div>

                  <div class="form-group">
                      <label for="editQuantity">Số lượng</label>
                      <input type="number" id="editQuantity" name="quantity" required>
                  </div>
              </div>

              <div class="modal-footer">
                  <button type="button" class="btn-edit-cancel">Hủy</button>
                  <button type="submit" class="btn-edit-save">Lưu thay đổi</button>
              </div>
          </form>
      </div>
  </div>

  <!-- ========== Dialog XÓA khuyến mãi ========== -->
  <div id="Dialog-xoa-km" class="modal">
      <div class="modal-content">
          <div class="modal-header">
              <h2>Xóa khuyến mãi</h2>
              <span class="close-delete-modal">&times;</span>
          </div>

          <div class="modal-body">
              <p>Bạn chắc chắn muốn xóa khuyến mãi này?</p>
          </div>

          <div class="modal-footer">
              <button type="button" class="btn-delete-cancel">Hủy</button>
              <button type="button" class="btn-delete-confirm">Xóa</button>
          </div>
      </div>
  </div>

  <script>
      // mở modal thêm
      const btnThemKM = document.querySelector('.btn-them-km');
      const modalAdd = document.getElementById('Dialog-them-km');
      const btnCloseAdd = document.querySelector('.close-modal');
      const btnCancelAdd = document.querySelector('.btn-cancel');

      if (btnThemKM) btnThemKM.onclick = () => modalAdd.style.display = 'flex';
      if (btnCloseAdd) btnCloseAdd.onclick = () => modalAdd.style.display = 'none';
      if (btnCancelAdd) btnCancelAdd.onclick = () => modalAdd.style.display = 'none';

      // click ngoài đóng modal thêm
      window.addEventListener('click', e => {
          if (e.target === modalAdd) modalAdd.style.display = 'none';
      });

      // modal sửa
      const modalEdit = document.getElementById("Dialog-sua-km");
      const btnCloseEdit = document.querySelector(".close-edit-modal");
      const btnEditCancel = document.querySelector(".btn-edit-cancel");

      document.addEventListener("click", (e) => {
          // ===== SỬA =====
          const editBtn = e.target.closest(".btn-Sua");
          if (editBtn) {
              const row = editBtn.closest("tr");

              const id = row.getAttribute("data-id");
              const code = row.querySelector(".col-code").innerText.trim();
              const name = row.querySelector(".col-name").innerText.trim();
              const desc = row.querySelector(".col-desc").innerText.trim();

              const start = row.querySelector(".col-start").getAttribute("data-value") || "";
              const end   = row.querySelector(".col-end").getAttribute("data-value") || "";
              const cash  = row.querySelector(".col-cash").getAttribute("data-value") || "0";

              const min = row.dataset.min || "0";
              const used = row.dataset.used || "0";
              const active = row.dataset.active || "1";
              const qty = row.dataset.qty || "";

              document.getElementById("editId").value = id;
              document.getElementById("editCode").value = code;
              document.getElementById("editName").value = name;
              document.getElementById("editDesc").value = desc;
              document.getElementById("editStartDate").value = start.substring(0, 10);
              document.getElementById("editEndDate").value   = end.substring(0, 10);
              document.getElementById("editCash").value = cash;

              document.getElementById("editQuantity").value = qty;
              document.getElementById("editMinOrderValue").value = min;
              document.getElementById("editQuantityUsed").value = used;
              document.getElementById("editIsActive").value = active;

              modalEdit.style.display = "flex";
              return;
          }

          // ===== XÓA =====
          const delBtn = e.target.closest(".btn-Xoa");
          if (delBtn) {
              deleteForm = delBtn.closest("form");
              deleteModal.style.display = "flex";
          }
      });

      // // ===== Modal XÓA khuyến mãi =====
      const deleteModal = document.getElementById("Dialog-xoa-km");
      const btnCloseDelete = document.querySelector(".close-delete-modal");
      const btnDeleteCancel = document.querySelector(".btn-delete-cancel");
      const btnDeleteConfirm = document.querySelector(".btn-delete-confirm");


      // Bấm "Xóa" trong modal => submit form
      if (btnDeleteConfirm) {
          btnDeleteConfirm.onclick = () => {
              if (deleteForm) {
                  deleteForm.submit();
                  deleteModal.style.display = "none";
                  deleteForm = null;
              }
          };
      }

      // Bấm Hủy hoặc dấu X => đóng modal
      if (btnCloseDelete) btnCloseDelete.onclick = () => {
          deleteModal.style.display = "none";
          deleteForm = null;
      };
      if (btnDeleteCancel) btnDeleteCancel.onclick = () => {
          deleteModal.style.display = "none";
          deleteForm = null;
      };

      // Click ra ngoài modal => đóng
      window.addEventListener("click", e => {
          if (e.target === deleteModal) {
              deleteModal.style.display = "none";
              deleteForm = null;
          }
      });
  </script>
  <script>
      const ctxVoucher = "${pageContext.request.contextPath}";
      const PAGE_SIZE = 10;

      const vForm = document.getElementById("voucherSearchForm");
      const vInput = document.getElementById("voucherSearchInput");
      const vTbody = document.getElementById("voucherTbody");
      const vPaging = document.getElementById("voucherPagination");
      const vStatus = document.getElementById("voucherSearchStatus");

      let vTimer = null;
      let vAbort = null;

      function text(s){ return (s == null) ? "" : String(s); }

      function renderVoucherRows(list, currentPage) {
          if (!vTbody) return;
          vTbody.innerHTML = "";

          if (!list || list.length === 0) {
              const tr = document.createElement("tr");
              const td = document.createElement("td");
              td.colSpan = 8;
              td.innerText = "Không có khuyến mãi nào.";
              tr.appendChild(td);
              vTbody.appendChild(tr);
              return;
          }

          list.forEach((v, idx) => {
              const tr = document.createElement("tr");
              tr.setAttribute("data-id", v.id);
              tr.dataset.min = v.minOrderValue ?? 0;
              tr.dataset.used = v.quantityUsed ?? 0;
              tr.dataset.active = v.isActive ?? 1;
              tr.dataset.qty = v.quantity ?? 0;

              // STT
              const tdStt = document.createElement("td");
              tdStt.innerText = (currentPage - 1) * PAGE_SIZE + idx + 1;

              const tdCode = document.createElement("td");
              tdCode.className = "col-code";
              tdCode.innerText = text(v.code);

              const tdName = document.createElement("td");
              tdName.className = "col-name";
              tdName.innerText = text(v.name);

              const tdDesc = document.createElement("td");
              tdDesc.className = "col-desc";
              tdDesc.innerText = text(v.description);

              const tdStart = document.createElement("td");
              tdStart.className = "col-start";
              tdStart.setAttribute("data-value", text(v.startDate));
              tdStart.innerText = text(v.startDate);

              const tdEnd = document.createElement("td");
              tdEnd.className = "col-end";
              tdEnd.setAttribute("data-value", text(v.endDate));
              tdEnd.innerText = text(v.endDate);

              const tdCash = document.createElement("td");
              tdCash.className = "col-cash";
              tdCash.setAttribute("data-value", text(v.voucherCash));
              tdCash.innerText = text(v.voucherCash) + "đ";

              const tdOpt = document.createElement("td");

              const btnEdit = document.createElement("button");
              btnEdit.type = "button";
              btnEdit.className = "btn-Sua";
              btnEdit.innerHTML = '<i class="fa-solid fa-pen-to-square"></i>';

              const formDel = document.createElement("form");
              formDel.action = ctxVoucher + "/admin/vouchers";
              formDel.method = "post";
              formDel.style.display = "inline";

              const inAction = document.createElement("input");
              inAction.type = "hidden";
              inAction.name = "action";
              inAction.value = "delete";

              const inId = document.createElement("input");
              inId.type = "hidden";
              inId.name = "id";
              inId.value = v.id;

              const btnDel = document.createElement("button");
              btnDel.type = "button";
              btnDel.className = "btn-Xoa";
              btnDel.innerHTML = '<i class="fa-solid fa-trash"></i>';

              formDel.appendChild(inAction);
              formDel.appendChild(inId);
              formDel.appendChild(btnDel);

              tdOpt.appendChild(btnEdit);
              tdOpt.appendChild(formDel);

              tr.appendChild(tdStt);
              tr.appendChild(tdCode);
              tr.appendChild(tdName);
              tr.appendChild(tdDesc);
              tr.appendChild(tdStart);
              tr.appendChild(tdEnd);
              tr.appendChild(tdCash);
              tr.appendChild(tdOpt);

              vTbody.appendChild(tr);
          });
      }

      function renderVoucherPaging(totalPages, currentPage) {
          if (!vPaging) return;
          vPaging.innerHTML = "";

          function addLink(label, page, active) {
              const a = document.createElement("a");
              a.href = "#";
              a.className = "page-link" + (active ? " active" : "");
              a.dataset.page = page;
              a.innerText = label;
              vPaging.appendChild(a);
          }

          if (currentPage > 1) addLink("Trước", currentPage - 1, false);

          for (let p = 1; p <= totalPages; p++) {
              addLink(String(p), p, p === currentPage);
          }

          if (currentPage < totalPages) addLink("Sau", currentPage + 1, false);
      }

      async function fetchVouchers(keyword, page) {
          if (vAbort) vAbort.abort();
          vAbort = new AbortController();

          if (vStatus) vStatus.innerText = "Đang tìm...";

          const url = new URL(ctxVoucher + "/admin/vouchers", window.location.origin);
          url.searchParams.set("ajax", "1");
          url.searchParams.set("keyword", keyword || "");
          url.searchParams.set("page", String(page || 1));

          const res = await fetch(url.toString(), {
              signal: vAbort.signal,
              headers: { "X-Requested-With": "XMLHttpRequest" }
          });

          const ct = res.headers.get("content-type") || "";
          if (!ct.includes("application/json")) {
              window.location.href = ctxVoucher + "/login";
              return;
          }

          const data = await res.json();

          renderVoucherRows(data.vouchers, data.currentPage);
          renderVoucherPaging(data.totalPages, data.currentPage);

          if (vStatus) vStatus.innerText = "";

          // update URL không reload
          const newUrl = new URL(window.location.href);
          newUrl.searchParams.set("keyword", data.keyword || "");
          newUrl.searchParams.set("page", String(data.currentPage || 1));
          window.history.replaceState({}, "", newUrl);
      }

      // gõ tới đâu tìm tới đó
      vInput && vInput.addEventListener("input", () => {
          clearTimeout(vTimer);
          vTimer = setTimeout(() => fetchVouchers(vInput.value || "", 1), 300);
      });

      // chặn submit
      vForm && vForm.addEventListener("submit", (e) => {
          e.preventDefault();
          fetchVouchers(vInput.value || "", 1);
      });

      // phân trang ajax
      vPaging && vPaging.addEventListener("click", (e) => {
          const a = e.target.closest("a.page-link");
          if (!a) return;
          e.preventDefault();
          fetchVouchers(vInput.value || "", parseInt(a.dataset.page || "1", 10));
      });

      const voucherType = document.getElementById("voucherType");
      const voucherCash = document.getElementById("giamgia");
      const voucherPercent = document.getElementById("voucherPercent");
      const maxDiscount = document.getElementById("maxDiscount");

      function changeVoucherType() {
          const type = voucherType.value;

          voucherCash.readOnly = false;
          voucherPercent.readOnly = false;
          maxDiscount.readOnly = false;

          if (type === "cash") {
              voucherPercent.readOnly = true;
              maxDiscount.readOnly = true;
              voucherPercent.value = 0;
              maxDiscount.value = 0;
          } else if (type === "percent") {
              voucherCash.readOnly = true;
              voucherCash.value = 0;
          } else if (type === "ship") {
              voucherCash.readOnly = true;
              voucherPercent.readOnly = true;
              maxDiscount.readOnly = true;

              voucherCash.value = 0;
              voucherPercent.value = 0;
              maxDiscount.value = 0;
          }
      }

      voucherType.addEventListener("change", changeVoucherType);
      changeVoucherType();
  </script>
</body>

</html>