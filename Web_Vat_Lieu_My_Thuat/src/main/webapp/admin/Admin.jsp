<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
      <!DOCTYPE html>
      <html lang="en">

      <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Tổng quan</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="https://cdn.datatables.net/1.13.8/css/jquery.dataTables.min.css" />

        <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.8/js/jquery.dataTables.min.js"></script>
      </head>
      <style>
        #main {
          display: flex;
          align-items: stretch;
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

        #main .right .container .dashboard .information-admin {
          display: flex;
          justify-content: space-between;
          align-items: center;
          width: 100%;
        }

        #main .right .container .dashboard .information-admin h1 {
          margin-left: 20px;
        }

        #main .right .container .dashboard .information-admin .account-admin {
          margin-right: 20px;
          display: flex;
          align-items: center;
          gap: 20px;
          margin-bottom: 20px;
        }

        #main .right .container .dashboard .information-admin .account-admin .logo-admin {
          background-color: orange;
          padding: 15px;
          border-radius: 50%;
          color: white;
        }

        #main .right .container .dashboard .information-admin .account-admin span {
          display: inline-block;
          font-weight: bold;
          color: #333;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
          min-width: 0;
          max-width: 50%;

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
          margin: 30px auto;
          background: white;
          padding: 25px;
          border-radius: 10px;
          box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }

        .product-container {
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
        .order-table td:last-child {
          text-align: center;
          vertical-align: middle;
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
          border-left: 1px solid #ddd;
          border-right: 1px solid #ddd;
        }

        /* Trạng thái đơn hàng */
        .status {
          display: inline-flex;
          align-items: center;
          justify-content: center;
          min-width: 110px;
          padding: 6px 12px;
          border-radius: 8px;
          font-size: 13px;
          font-weight: bold;
          text-align: center;
        }

        .status.pending {
          background: #FFF3CD;
          color: #555;
        }

        .status.success {
          background: #D1FAE5;
          color: #0f5132;
        }

        .status.delivery {
          background: #2659F5;
          color: #fff;
        }

        .status.cancel {
          background: #ffd6d6;
          color: #b91c1c;
        }

        .product-table {
          width: 100%;
          border-collapse: collapse;
          margin-top: 10px;
        }

        .product-table th {
          background: #2659F5;
          color: white;
          padding: 12px;
          font-size: 14px;
        }

        .product-table td {
          padding: 12px;
          background: #fafafa;
        }

        .product-table tr:nth-child(even) td {
          background: #f0f0f0;
        }

        .product-table td,
        .product-table th {
          border-bottom: 1px solid #ddd;
          text-align: left;
          border-left: 1px solid #ddd;
          border-right: 1px solid #ddd;
        }

        .product-table td:last-child {
          text-align: center;
        }

        .product-table .xoa-sanpham {
          background: #ff4d4d;
          border: none;
          color: white;
          padding: 8px 12px;
          border-radius: 5px;
          cursor: pointer;
        }

        .product-table .xoa-sanpham:hover {
          opacity: 0.9;
        }
        /* DataTables: giữ layout đẹp */
        .dataTables_wrapper {
          width: 100%;
          margin-top: 10px;
        }

        .dataTables_filter input,
        .dataTables_length select {
          padding: 6px 10px;
          border-radius: 6px;
          border: 1px solid #ddd;
          outline: none;
        }

        .dataTables_filter {
          margin-bottom: 10px;
        }

        .dataTables_paginate .paginate_button {
          padding: 6px 10px !important;
          border-radius: 6px !important;
        }

        .dataTables_paginate .paginate_button.current {
          background: #2659F5 !important;
          color: #fff !important;
          border: none !important;
        }
        /* nút phân trang DataTables */
        .dataTables_wrapper .dataTables_paginate .paginate_button {
          border-radius: 8px;
          border: 1px solid transparent !important;
        }

        /* hover */
        .dataTables_wrapper .dataTables_paginate .paginate_button:hover {
          background: #17479D !important;
          color: #fff !important;
          border: 1px solid #17479D !important;
        }

        /* nút đang active */
        .dataTables_wrapper .dataTables_paginate .paginate_button.current {
          background: #17479D !important;
          color: #fff !important;
          border: 1px solid #17479D !important;
        }

      </style>

      <body>
        <div id="main">
          <div class="left">
            <div class="list-admin">
              <a href="${pageContext.request.contextPath}/admin/overview" class="logo"><img
                  src="${pageContext.request.contextPath}/assets/images/logo/logo.png" alt></a>
              <a href="${pageContext.request.contextPath}/admin/overview" class="active"><i
                  class="fa-solid fa-house"></i>
                Tổng quan</a>
              <a href="${pageContext.request.contextPath}/admin/statistics"><i class="fa-solid fa-chart-line"></i>Thống
                kê</a>
              <a href="${pageContext.request.contextPath}/admin/categories"><i class="fa-solid fa-list"></i>Quản lý danh
                mục</a>
              <a href="${pageContext.request.contextPath}/admin/products"><i class="fa-solid fa-palette"></i>Quản
                lý sản phẩm</a>
                <a href="${pageContext.request.contextPath}/admin/users" ><i
                        class="fa-solid fa-person"></i>Quản lý người dùng</a>

              <a href="${pageContext.request.contextPath}/admin/orders"><i class="fa-solid fa-box-open"></i>Quản
                lý đơn hàng</a>
              <a href="${pageContext.request.contextPath}/admin/vouchers"><i class="fa-solid fa-gift"></i>Quản lý
                khuyến mãi</a>
              <a href="${pageContext.request.contextPath}/admin/sliders"><i class="fa-solid fa-sliders"></i>Quản lý
                Slider Show</a>
              <a href="${pageContext.request.contextPath}/admin/contacts"><i class="fa-solid fa-address-book"></i>Quản lý
                liên hệ</a>
              <a href="${pageContext.request.contextPath}/logout"><i class="fa-solid fa-right-from-bracket"></i>
                Đăng xuất</a>
            </div>
          </div>
          <div class="right">
            <div class="container">
              <div class="dashboard">
                <div class="information-admin">
                  <h1>Tổng Quan</h1>
                  <div class="account-admin">
                    <div class="logo-admin">TH</div>
                    <span>${sessionScope.currentUser.fullName}</span>
                  </div>
                </div>
                <div class="total-nguoidung total-box">
                  <h2><i class="fa-solid fa-chart-pie"></i> Tổng người dùng</h2>
                  <hr>
                  <h3>${totalUsers}<span> Người dùng </span></h3>
                </div>
                <div class="total-sanpham total-box">
                  <h2><i class="fa-brands fa-product-hunt"></i> Tổng sản phẩm</h2>
                  <hr>
                  <h3>${totalProducts} <span>Sản phẩm trong kho </span></h3>
                </div>
                <div class="total-doanhthu total-box">
                  <h2><i class="fa-solid fa-chart-line"></i>Tổng doanh thu</h2>
                  <hr>
                  <h3>
                    <fmt:formatNumber value="${totalRevenue}" type="number" />đ
                  </h3>
                </div>
                <div class="total-donhangbanduoc total-box">
                  <h2><i class="fa-solid fa-file-invoice"></i> Tổng số đơn</h2>
                  <hr>
                  <h3>${totalOrders}<span> Đơn hàng</span></h3>
                </div>
              </div>
            </div>
            <div class="container">
              <div class="order-container">
                <h1>Đơn hàng mới</h1>

                <table id="orderTable" class="order-table display">
                  <thead>
                  <tr>
                    <th>STT</th>
                    <th>Khách Hàng</th>
                    <th>Số Điện Thoại</th>
                    <th>Ngày Đặt</th>
                    <th>Địa Chỉ Giao</th>
                    <th>Sản Phẩm</th>
                    <th>Thành Tiền</th>
                    <th>Trạng Thái ĐH</th>
                  </tr>
                  </thead>

                  <tbody>
                  <c:forEach var="order" items="${latestOrders}" varStatus="st">
                    <tr>
                      <td>${st.index + 1}</td>
                      <td><c:out value="${order.fullName}" default="-" /></td>
                      <td><c:out value="${order.phoneNumber}" default="-" /></td>
                      <td>
                        <c:choose>
                          <c:when test="${not empty order.createAt}">
                            <fmt:formatDate value="${order.createAt}" pattern="dd-MM-yyyy"/>
                          </c:when>
                          <c:otherwise>-</c:otherwise>
                        </c:choose>
                      </td>
                      <td><c:out value="${order.address}" default="-" /></td>
                      <td><c:out value="${order.productNames}" default="-" /></td>
                      <td><fmt:formatNumber value="${order.totalPrice}" type="number"/>đ</td>
                      <td>
                        <c:choose>
                          <c:when test="${order.statusName == 'Chờ xác nhận' || order.statusName == 'Đang xử lý'}">
                            <span class="status pending">${order.statusName}</span>
                          </c:when>
                          <c:when test="${order.statusName == 'Đang giao'}">
                            <span class="status delivery">${order.statusName}</span>
                          </c:when>
                          <c:when test="${order.statusName == 'Hoàn thành'}">
                            <span class="status success">${order.statusName}</span>
                          </c:when>
                          <c:otherwise>
                            <span class="status cancel"><c:out value="${order.statusName}" default="-" /></span>
                          </c:otherwise>
                        </c:choose>
                      </td>
                    </tr>
                  </c:forEach>
                  </tbody>
                </table>
              </div>
              <div class="product-container">
                <h1>Sản phẩm bán chạy trong tháng</h1>

                <table id="productTable" class="product-table display">
                  <thead>
                  <tr>
                    <th>STT</th>
                    <th>Mã sản phẩm</th>
                    <th>Tên sản phẩm</th>
                    <th>Danh mục</th>
                    <th>Giá</th>
                    <th>Ngày tạo</th>
                    <th>Đã bán</th>
                  </tr>
                  </thead>

                  <tbody>
                  <c:forEach var="p" items="${topProductsMonth}" varStatus="st">
                    <tr>
                      <td>${st.index + 1}</td>
                      <td>SP${p.id}</td>
                      <td><c:out value="${p.name}" default="-" /></td>
                      <td><c:out value="${p.categoryName}" default="-" /></td>
                      <td><fmt:formatNumber value="${p.price}" type="number"/>đ</td>
                      <td>
                        <c:choose>
                          <c:when test="${not empty p.createAt}">
                            <fmt:formatDate value="${p.createAt}" pattern="dd-MM-yyyy"/>
                          </c:when>
                          <c:otherwise>-</c:otherwise>
                        </c:choose>
                      </td>
                      <td><c:out value="${p.soldQty}" default="0" /></td>
                    </tr>
                  </c:forEach>
                  </tbody>
                </table>
              </div>
            </div>
          </div>

        </div>
      </body>
      <script>
        $(function () {
          const viUrl = "https://cdn.datatables.net/plug-ins/1.13.8/i18n/vi.json";

          $("#orderTable").DataTable({
            pageLength: 5,
            lengthChange: false,
            ordering: true,
            searching: true,
            info: false,
            language: { url: viUrl }
          });

          $("#productTable").DataTable({
            pageLength: 5,
            lengthChange: false,
            ordering: true,
            searching: true,
            info: false,
            language: { url: viUrl }
          });
        });
      </script>
      </html>