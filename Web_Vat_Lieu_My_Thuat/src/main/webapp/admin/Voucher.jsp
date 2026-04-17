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
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.8/css/jquery.dataTables.min.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.8/js/jquery.dataTables.min.js"></script>
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
        background: #203247;
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


    #main .right{
        flex: 1;
        background-color: #F9F9F9;
    }
    #main .right .container {
        width: calc(100% - 100px);
        margin: 20px auto 0;
    }

    .order-container {
        width: 95%;
        margin: 10px auto 5px;
        padding: 25px;
        background: white;
        border-radius: 10px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
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
    h1 {
        margin: 0 0 15px;
        color: #222;
    }
    .action-buttons {
        display: flex;
        align-items: center;
        gap: 6px;
        flex-wrap: nowrap;
    }

    .action-buttons form {
        margin: 0;
    }

    .action-buttons button {
        margin: 0;
    }
    .btn-Sua,
    .btn-Xoa,
    .btn-Khoa,
    .btn-MoKhoa {
        min-width: 42px;
        height: 34px;
        display: inline-flex;
        align-items: center;
        justify-content: center;
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
    .btn-Khoa {
        background-color: #DC3545;
        color: white;
        border: none;
        padding: 6px 10px;
        cursor: pointer;
        font-size: 14px;
        border-radius: 4px;
        transition: 0.2s;
        margin-left: 5px;
    }

    .btn-Khoa:hover {
        background-color: #b02a37;
    }
    .btn-MoKhoa {
        background-color: #FFC107;
        color: black;
        border: none;
        padding: 6px 10px;
        cursor: pointer;
        font-size: 14px;
        border-radius: 4px;
        transition: 0.2s;
        margin-left: 5px;
    }

    .btn-MoKhoa:hover {
        background-color:#e0a800;
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

    #Dialog-lock-voucher .modal-body {
        text-align: center;
    }

    #Dialog-lock-voucher .modal-header {
        display: flex;
        align-items: center;
        padding: 12px 16px;
    }

    #Dialog-lock-voucher .modal-header h2 {
        margin: 0;
        flex: 1;
        text-align: center;
    }

    #Dialog-lock-voucher .close-lock-voucher-modal {
        cursor: pointer;
        font-size: 20px;
        padding: 0 5px;
    }

    #Dialog-lock-voucher .btn-lock-cancel {
        border: none;
        background: #e0e0e0;
        border-radius: 5px;
        cursor: pointer;
        padding: 8px 14px;
    }

    #Dialog-lock-voucher .btn-lock-confirm {
        background-color: #DC3545;
        color: white;
        border: none;
        padding: 8px 14px;
        cursor: pointer;
        font-size: 14px;
        border-radius: 5px;
        transition: 0.2s;
    }

    #Dialog-lock-voucher .btn-lock-confirm:hover {
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

    .table-toolbar {
        display: flex;
        justify-content: flex-end;
        align-items: center;
        gap: 12px;
        margin-bottom: 20px;
    }

    #customSearchWrap {
        display: flex;
        align-items: center;
    }


    .dataTables_filter label {
        display: flex;
        align-items: center;
        border: 1px solid #dcdcdc;
        border-radius: 14px;
        overflow: hidden;
        background: #fff;
        height: 48px;
        min-width: 415px;
        margin: 0;
    }

    .dataTables_filter label span,
    .dataTables_filter label small {
        display: none;
    }

    .dataTables_filter input {
        border: none !important;
        outline: none !important;
        box-shadow: none !important;
        width: 350px !important;
        height: 48px;
        padding: 0 18px !important;
        font-size: 15px;
        margin-left: 0 !important;
        color: #333;
        background: #fff;
    }

    #customSearchWrap {
        display: flex;
        align-items: center;
    }

    .dataTables_filter label {
        display: flex;
        align-items: center;
        margin: 0;
    }

    .dataTables_filter label span,
    .dataTables_filter label small {
        display: none;
    }

    .dataTables_filter input {
        border: none !important;
        outline: none !important;
        box-shadow: none !important;
        width: 360px !important;
        height: 48px;
        padding: 0 18px !important;
        font-size: 15px;
        margin-left: 0 !important;
        color: #333;
        background: #fff;
    }

    .search-dt-box {
        display: flex;
        align-items: center;
        border: 1px solid #dcdcdc;
        border-radius: 14px;
        overflow: hidden;
        background: #fff;
        height: 48px;
    }

    .search-dt-icon {
        width: 64px;
        height: 48px;
        display: flex;
        align-items: center;
        justify-content: center;
        background: #f3f3f3;
        border-left: 1px solid #e5e5e5;
        color: #222;
        font-size: 16px;
    }

    .btn-them-km {
        background: #2f5cf5;
        color: white;
        border: none;
        border-radius: 8px;
        padding: 12px 22px;
        font-size: 15px;
        font-weight: 500;
        cursor: pointer;
        white-space: nowrap;
    }

    .btn-them-km:hover {
        background: #234be0;
    }

    .dataTables_wrapper .dataTables_paginate {
        float: none !important;
        text-align: center !important;
        margin-top: 24px;
    }

    .dataTables_wrapper .dataTables_paginate .paginate_button {
        min-width: 42px;
        height: 42px;
        line-height: 42px !important;
        padding: 0 14px !important;
        margin: 0 4px !important;
        border: 1px solid #d9d9d9 !important;
        border-radius: 8px !important;
        background: #fff !important;
        color: #2f5cf5 !important;
        font-size: 15px;
    }

    .dataTables_wrapper .dataTables_paginate .paginate_button:hover {
        background: #f5f7ff !important;
        color: #2f5cf5 !important;
        border-color: #cfd8ff !important;
    }

    .dataTables_wrapper .dataTables_paginate .paginate_button.current,
    .dataTables_wrapper .dataTables_paginate .paginate_button.current:hover {
        background: #2f5cf5 !important;
        color: #fff !important;
        border: 1px solid #2f5cf5 !important;
    }

    .dataTables_wrapper .dataTables_paginate .paginate_button.disabled,
    .dataTables_wrapper .dataTables_paginate .paginate_button.disabled:hover {
        color: #9fb3ff !important;
        background: #fff !important;
        border: 1px solid #d9d9d9 !important;
        opacity: 1;
    }

    .dataTables_wrapper .dataTables_info {
        display: none;
    }

    .dataTables_wrapper .dataTables_length {
        display: none;
    }

    .status-active,
    .status-locked {
        display: inline-block;
        padding: 5px 10px;
        border-radius: 20px;
        font-size: 14px;
        font-weight: 400;
        font-family: inherit;
        line-height: 1.4;
    }
    .status-active {
        background-color: #d1f7d6;
        color: #198754;
    }

    .status-locked {
        background-color: #f8d7da;
        color: #dc3545;
    }
    #Dialog-sua-km .modal-content {
        max-height: 90vh;
        overflow-y: auto;
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
            <div class="table-toolbar">
                <div id="customSearchWrap"></div>
                <button type="button" class="btn-them-km">Thêm khuyến mãi</button>
            </div>


            <table id="voucherTable" class="order-table">
            <thead>
              <tr>
                <th>STT</th>
                <th>Mã khuyến mãi</th>
                <th>Tên khuyến mãi</th>
                <th>Mô tả</th>
                <th>Ngày bắt đầu</th>
                <th>Ngày kết thúc</th>
                <th>Giảm giá</th>
                  <th>Trạng thái</th>
                <th>Tùy chọn</th>
              </tr>
            </thead>

              <tbody>
              <c:if test="${empty vouchers}">
                  <tr>
                      <td colspan="9">Không có khuyến mãi nào.</td>
                  </tr>
              </c:if>

              <c:forEach var="v" items="${vouchers}" varStatus="st">
                  <tr data-id="${v.id}"
                      data-min="${v.minOrderValue}"
                      data-used="${v.quantityUsed}"
                      data-active="${v.isActive}"
                      data-qty="${v.quantity}"
                      data-type="${v.voucherType}"
                      data-percent="${v.voucherPercent}"
                      data-max-discount="${v.maxDiscount}"
                      data-cash="${v.voucherCash}">

                      <td>${st.index + 1}</td>
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
                          <c:if test="${v.isActive == 1}">
                              <span class="status-active">Đang hoạt động</span>
                          </c:if>
                          <c:if test="${v.isActive == 0}">
                              <span class="status-locked">Đã khóa</span>
                          </c:if>
                      </td>
                      <td>
                          <div class="action-buttons">
                          <button class="btn-Sua" type="button">
                              <i class="fa-solid fa-pen-to-square"></i>
                          </button>

                          <form action="${pageContext.request.contextPath}/admin/vouchers" method="post"
                                style="display:inline">
                              <input type="hidden" name="action" value="delete">
                              <input type="hidden" name="id" value="${v.id}">
                              <button class="btn-Xoa" type="button">
                                  <i class="fa-solid fa-trash"></i>
                              </button>
                          </form>
                          <c:if test="${v.isActive == 1}">
                              <form class="lockVoucherForm" action="${pageContext.request.contextPath}/admin/vouchers" method="post" style="display:inline">
                                  <input type="hidden" name="action" value="lock">
                                  <input type="hidden" name="id" value="${v.id}">
                                  <button class="btn-Khoa btn-open-lock-voucher" type="button" title="Khóa voucher">
                                      <i class="fa-solid fa-lock"></i>
                                  </button>
                              </form>
                          </c:if>

                          <c:if test="${v.isActive == 0}">
                              <form class="lockVoucherForm" action="${pageContext.request.contextPath}/admin/vouchers" method="post" style="display:inline">
                                  <input type="hidden" name="action" value="unlock">
                                  <input type="hidden" name="id" value="${v.id}">
                                  <button class="btn-MoKhoa btn-open-lock-voucher" type="button" title="Mở khóa voucher">
                                      <i class="fa-solid fa-lock-open"></i>
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

  </div>

  </div>

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
          <input type="text" id="maKM" name="code" placeholder="Nhập mã khuyến mãi" required>
        </div>

        <div class="form-group">
          <label for="tenKM">Tên khuyến mãi</label>
          <input type="text" id="tenKM" name="name" placeholder="Nhập tên khuyến mãi" required>
        </div>

        <div class="form-group">
          <label for="mota">Mô tả</label>
          <input type="text" id="mota" name="description" placeholder="Nhập mô tả">
        </div>

        <div class="form-group">
          <label for="ngaybatdau">Ngày bắt đầu</label>
          <input type="date" id="ngaybatdau" name="startDate" placeholder="Nhập ngày bắt đầu" required>
        </div>

        <div class="form-group">
          <label for="ngàyketthuc">Ngày ngày kết thúc</label>
          <input type="date" id="ngàyketthuc" name="endDate" placeholder="Nhập ngày kết thúc" required>
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
          <input type="number" id="sl" name="quantity" min="1" placeholder="Nhập số lượng" required>
        </div>
      </div>

      <div class="modal-footer">
        <button type="button" class="btn-cancel">Hủy</button>
        <button type="submit" class="btn-save">Lưu</button>
      </div>
        </form>
    </div>
  </div>
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
                      <label for="editVoucherType">Loại voucher</label>
                      <select id="editVoucherType" name="voucherType">
                          <option value="cash">Giảm tiền mặt</option>
                          <option value="percent">Giảm phần trăm</option>
                          <option value="ship">Miễn phí vận chuyển</option>
                      </select>
                  </div>

                  <div class="form-group">
                      <label for="editVoucherCash">Giảm giá tiền mặt</label>
                      <input type="number" id="editVoucherCash" name="voucherCash" value="0" placeholder="Nhập số tiền giảm">
                  </div>

                  <div class="form-group">
                      <label for="editVoucherPercent">Phần trăm giảm</label>
                      <input type="number" id="editVoucherPercent" name="voucherPercent" value="0" placeholder="Nhập phần trăm giảm">
                  </div>

                  <div class="form-group">
                      <label for="editMaxDiscount">Giảm tối đa</label>
                      <input type="number" id="editMaxDiscount" name="maxDiscount" value="0" placeholder="Nhập mức giảm tối đa">
                  </div>

                  <div class="form-group">
                      <label for="editMinOrderValueInput">Đơn tối thiểu</label>
                      <input type="number" id="editMinOrderValueInput" name="minOrderValue" value="0" placeholder="Nhập đơn tối thiểu">
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
  <div id="Dialog-lock-voucher" class="modal">
      <div class="modal-content">
          <div class="modal-header">
              <h2>Xác nhận</h2>
              <span class="close-lock-voucher-modal">&times;</span>
          </div>

          <div class="modal-body">
              <p id="lockVoucherMessage">Bạn chắc chắn muốn khóa voucher này?</p>
          </div>

          <div class="modal-footer">
              <button type="button" class="btn-lock-cancel">Hủy</button>
              <button type="button" class="btn-lock-confirm">Đồng ý</button>
          </div>
      </div>
  </div>
  <script>
      const btnThemKM = document.querySelector('.btn-them-km');
      const modalAdd = document.getElementById('Dialog-them-km');
      const btnCloseAdd = document.querySelector('.close-modal');
      const btnCancelAdd = document.querySelector('.btn-cancel');

      if (btnThemKM) btnThemKM.onclick = () => modalAdd.style.display = 'flex';
      if (btnCloseAdd) btnCloseAdd.onclick = () => modalAdd.style.display = 'none';
      if (btnCancelAdd) btnCancelAdd.onclick = () => modalAdd.style.display = 'none';

      window.addEventListener('click', e => {
          if (e.target === modalAdd) modalAdd.style.display = 'none';
      });

      const modalEdit = document.getElementById("Dialog-sua-km");
      const btnCloseEdit = document.querySelector(".close-edit-modal");
      const btnEditCancel = document.querySelector(".btn-edit-cancel");
      if (btnThemKM) btnThemKM.onclick = () => modalAdd.style.display = 'flex';
      if (btnCloseAdd) btnCloseAdd.onclick = () => modalAdd.style.display = 'none';
      if (btnCancelAdd) btnCancelAdd.onclick = () => modalAdd.style.display = 'none';
      if (btnCloseEdit) {
          btnCloseEdit.onclick = () => {
              modalEdit.style.display = "none";
          };
      }

      if (btnEditCancel) {
          btnEditCancel.onclick = () => {
              modalEdit.style.display = "none";
          };
      }
      let deleteForm = null;


      document.addEventListener("click", (e) => {
          const editBtn = e.target.closest(".btn-Sua");
          if (editBtn) {
              const row = editBtn.closest("tr");

              const id = row.getAttribute("data-id");
              const code = row.querySelector(".col-code").innerText.trim();
              const name = row.querySelector(".col-name").innerText.trim();
              const desc = row.querySelector(".col-desc").innerText.trim();

              const start = row.querySelector(".col-start").getAttribute("data-value") || "";
              const end   = row.querySelector(".col-end").getAttribute("data-value") || "";
              const voucherType = row.dataset.type || "cash";
              const voucherCash = row.dataset.cash || "0";
              const voucherPercent = row.dataset.percent || "0";
              const maxDiscount = row.dataset.maxDiscount || "0";
              const minOrderValue = row.dataset.min || "0";
              const quantityUsed = row.dataset.used || "0";
              const isActive = row.dataset.active || "1";
              const quantity = row.dataset.qty || "";

              document.getElementById("editId").value = id;
              document.getElementById("editCode").value = code;
              document.getElementById("editName").value = name;
              document.getElementById("editDesc").value = desc;
              document.getElementById("editStartDate").value = start.substring(0, 10);
              document.getElementById("editEndDate").value   = end.substring(0, 10);

              document.getElementById("editVoucherType").value = voucherType;
              document.getElementById("editVoucherCash").value = voucherCash;
              document.getElementById("editVoucherPercent").value = voucherPercent;
              document.getElementById("editMaxDiscount").value = maxDiscount;
              document.getElementById("editMinOrderValueInput").value = minOrderValue;
              document.getElementById("editQuantity").value = quantity;
              document.getElementById("editQuantityUsed").value = quantityUsed;
              document.getElementById("editIsActive").value = isActive;

              changeEditVoucherType();
              modalEdit.style.display = "flex";
              return;
          }

          const delBtn = e.target.closest(".btn-Xoa");
          if (delBtn) {
              deleteForm = delBtn.closest("form");
              deleteModal.style.display = "flex";
          }
      });

      const deleteModal = document.getElementById("Dialog-xoa-km");
      const btnCloseDelete = document.querySelector(".close-delete-modal");
      const btnDeleteCancel = document.querySelector(".btn-delete-cancel");
      const btnDeleteConfirm = document.querySelector(".btn-delete-confirm");


      if (btnDeleteConfirm) {
          btnDeleteConfirm.onclick = () => {
              if (deleteForm) {
                  deleteForm.submit();
                  deleteModal.style.display = "none";
                  deleteForm = null;
              }
          };
      }

      if (btnCloseDelete) btnCloseDelete.onclick = () => {
          deleteModal.style.display = "none";
          deleteForm = null;
      };
      if (btnDeleteCancel) btnDeleteCancel.onclick = () => {
          deleteModal.style.display = "none";
          deleteForm = null;
      };

      window.addEventListener("click", e => {
          if (e.target === deleteModal) {
              deleteModal.style.display = "none";
              deleteForm = null;
          }
      });
  </script>
  <script>
      const lockVoucherModal = document.getElementById("Dialog-lock-voucher");
      const btnCloseLockVoucher = document.querySelector(".close-lock-voucher-modal");
      const btnLockCancel = document.querySelector(".btn-lock-cancel");
      const btnLockConfirm = document.querySelector(".btn-lock-confirm");
      const lockVoucherMessage = document.getElementById("lockVoucherMessage");

      let lockVoucherForm = null;

      document.addEventListener("click", function (e) {
          const lockBtn = e.target.closest(".btn-open-lock-voucher");
          if (!lockBtn) return;

          lockVoucherForm = lockBtn.closest("form");
          const action = lockVoucherForm.querySelector('input[name="action"]').value;

          if (action === "lock") {
              lockVoucherMessage.innerText = "Bạn chắc chắn muốn khóa voucher này?";
              btnLockConfirm.innerText = "Khóa";
              btnLockConfirm.style.backgroundColor = "#DC3545";
          } else {
              lockVoucherMessage.innerText = "Bạn chắc chắn muốn mở khóa voucher này?";
              btnLockConfirm.innerText = "Mở khóa";
              btnLockConfirm.style.backgroundColor = "#2659F5";
          }

          lockVoucherModal.style.display = "flex";
      });

      if (btnCloseLockVoucher) {
          btnCloseLockVoucher.onclick = function () {
              lockVoucherModal.style.display = "none";
              lockVoucherForm = null;
          };
      }

      if (btnLockCancel) {
          btnLockCancel.onclick = function () {
              lockVoucherModal.style.display = "none";
              lockVoucherForm = null;
          };
      }

      if (btnLockConfirm) {
          btnLockConfirm.onclick = function () {
              if (lockVoucherForm) {
                  lockVoucherForm.submit();
              }
          };
      }

      window.addEventListener("click", function (e) {
          if (e.target === lockVoucherModal) {
              lockVoucherModal.style.display = "none";
              lockVoucherForm = null;
          }
      });
      function changeEditVoucherType() {
          const type = editVoucherType.value;

          editVoucherCash.readOnly = false;
          editVoucherPercent.readOnly = false;
          editMaxDiscount.readOnly = false;

          if (type === "cash") {
              editVoucherPercent.readOnly = true;
              editMaxDiscount.readOnly = true;
              editVoucherPercent.value = 0;
              editMaxDiscount.value = 0;
          } else if (type === "percent") {
              editVoucherCash.readOnly = true;
              editVoucherCash.value = 0;
          } else if (type === "ship") {
              editVoucherCash.readOnly = true;
              editVoucherPercent.readOnly = true;
              editMaxDiscount.readOnly = true;

              editVoucherCash.value = 0;
              editVoucherPercent.value = 0;
              editMaxDiscount.value = 0;
          }
      }

      if (editVoucherType) {
          editVoucherType.addEventListener("change", changeEditVoucherType);
      }
  </script>
  <script>

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

  <script>
      $(document).ready(function () {
          let table = $('#voucherTable').DataTable({
              pageLength: 10,
              lengthChange: false,
              info: false,
              language: {
                  search: "",
                  searchPlaceholder: "Tìm kiếm khuyến mãi...",
                  paginate: {
                      previous: "Trước",
                      next: "Sau"
                  },
                  zeroRecords: "Không tìm thấy khuyến mãi nào",
                  emptyTable: "Không có dữ liệu"
              },
              columnDefs: [
                  { orderable: false, targets: 0 },
                  { orderable: false, targets: 8 }
              ],
              dom: 'f<"bottom"rtp>'
          });

          $('.dataTables_filter').appendTo('#customSearchWrap');

          const searchInput = $('.dataTables_filter input');
          searchInput.wrap('<div class="search-dt-box"></div>');
          $('.search-dt-box').append('<div class="search-dt-icon"><i class="fa-solid fa-magnifying-glass"></i></div>');

          table.on('order.dt search.dt draw.dt', function () {
              table.column(0, { search: 'applied', order: 'applied', page: 'current' })
                  .nodes()
                  .each(function (cell, i) {
                      cell.innerHTML = i + 1;
                  });
          }).draw();
      });
  </script>
  <script>
      const addVoucherForm = document.querySelector('#Dialog-them-km form');
      const editVoucherFormCheck = document.getElementById('editVoucherForm');

      function checkVoucherDates(startInput, endInput) {
          if (!startInput || !endInput) return true;

          const startDate = startInput.value;
          const endDate = endInput.value;

          if (startDate && endDate && endDate < startDate) {
              alert("Ngày kết thúc phải lớn hơn hoặc bằng ngày bắt đầu");
              endInput.focus();
              return false;
          }

          return true;
      }

      if (addVoucherForm) {
          addVoucherForm.addEventListener("submit", function (e) {
              const startInput = document.getElementById("ngaybatdau");
              const endInput = document.getElementById("ngàyketthuc");

              if (!checkVoucherDates(startInput, endInput)) {
                  e.preventDefault();
              }
          });
      }

      if (editVoucherFormCheck) {
          editVoucherFormCheck.addEventListener("submit", function (e) {
              const startInput = document.getElementById("editStartDate");
              const endInput = document.getElementById("editEndDate");

              if (!checkVoucherDates(startInput, endInput)) {
                  e.preventDefault();
              }
          });
      }
  </script>

</body>

</html>