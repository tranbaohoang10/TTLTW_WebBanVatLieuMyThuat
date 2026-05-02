  <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
  <!DOCTYPE html>
  <html lang="en">

  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thống kê</title>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.8/css/jquery.dataTables.min.css">
    <link rel="stylesheet" href="../assets/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
      integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
      crossorigin="anonymous" referrerpolicy="no-referrer" />

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

    #main .right .container .thongke-doanhthu {
      display: flex;
      justify-content: space-between;
      flex-wrap: wrap;
      padding: 20px 10px;
      background-color: white;
      border-radius: 5px;
      align-items: stretch;
    }

    #main .right .container .thongke-doanhthu h1 {
      width: 100%;
    }

    #main .right .container .thongke-doanhthu .tong-doanhthu {
      width: 50%;
      border-right: 2px solid #ddd;
      background-color: #17479D;
      color: white;
      text-align: center;
      border-radius: 10px;

      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
    }


    #main .right .container .thongke-doanhthu .tong-doanhthu hr {
      border: 1px solid #ffffff33;
      width: 80%;
      margin-left: auto;
      margin-right: auto;
    }

    #main .right .container .thongke-doanhthu .bieudo-doanhthu {
      width: 45%;
      min-height: 300px;
      background: white;
      border-radius: 10px;
      padding: 20px;
      box-shadow: 0 1px 5px rgba(0, 0, 0, 0.1);
    }

    .product-container {
      width: 95%;
      margin: 30px auto;
      background: white;
      padding: 25px;
      border-radius: 10px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    }

    .product-container .thoigian .sothang label[for="sothang"] {
      padding: 10px 12px;
      font-size: 18px;
    }

    .product-container .thoigian .sothang input {
      padding: 10px 12px;
      width: 100px;
      border: 1px solid #d0d7de;
      border-radius: 6px;
      font-size: 14px;
      color: #333;
      transition: 0.25s;
      background: #fff;
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

    /* Khung form thời gian */
    .product-container .thoigian {
      display: flex;
      justify-content: center;
      gap: 30px;
      margin: 20px 0;
      padding: 15px 0;
    }

    /* Label */
    .product-container .thoigian label {
      font-size: 15px;
      font-weight: 600;
      color: #2659F5
    }

    /* Input ngày */
    .product-container .thoigian input[type="date"] {
      padding: 10px 12px;
      width: 200px;
      border: 1px solid #d0d7de;
      border-radius: 6px;
      font-size: 14px;
      color: #333;
      transition: 0.25s;
      background: #fff;
    }

    /* Khi focus */
    .product-container .thoigian input[type="date"]:focus {
      border-color: #3B7DDD;
      box-shadow: 0 0 4px rgba(59, 125, 221, 0.4);
      outline: none;
    }

    /* Hover */
    .product-container .thoigian input[type="date"]:hover {
      border-color: #2659F5
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

    .dataTables_wrapper .dataTables_filter input,
    .dataTables_wrapper .dataTables_length select{
      border:1px solid #d0d7de !important;
      border-radius:6px !important;
      padding:6px 10px !important;
      outline:none !important;
    }
  </style>

  <body>
  <!-- Chart.js -->
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

  <div id="main">
    <div class="left">
      <div class="list-admin">
        <a href="Admin.jsp" class="logo"><img src="../assets/images/logo/logo.png" alt=""></a>
          <a href="${pageContext.request.contextPath}/admin/overview" ><i
                  class="fa-solid fa-house"></i>
              Tổng quan</a>
          <c:if test="${sessionScope.currentUser.role eq 'ADMIN'}">
              <a href="${pageContext.request.contextPath}/admin/statistics" class="active"><i class="fa-solid fa-chart-line"></i>Thống
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
          <a href="${pageContext.request.contextPath}/admin/contacts"><i class="fa-solid fa-address-book"></i>Quản lý
              liên hệ</a>
          <c:if test="${sessionScope.currentUser.role eq 'ADMIN'}">
              <a href="${pageContext.request.contextPath}/admin/logs">
                  <i class="fa-solid fa-clock-rotate-left"></i>Quản lý thao tác
              </a>
          </c:if>
        <a href="${pageContext.request.contextPath}/logout"><i class="fa-solid fa-right-from-bracket"></i> Đăng xuất</a>
      </div>
    </div>

    <div class="right">
      <div class="container">

        <!-- DOANH THU -->
        <div class="thongke-doanhthu">
          <h1>Thống kê doanh thu</h1>

          <div class="tong-doanhthu">
            <h2>Tổng doanh thu</h2>
            <hr>
            <h3><fmt:formatNumber value="${totalYear}" type="number" groupingUsed="true"/> VNĐ</h3>
          </div>

          <div class="bieudo-doanhthu">
            <canvas id="chartDoanhThu"></canvas>
          </div>
        </div>

        <!-- BEST SELLER (ALL TIME) -->
        <div class="product-container">
          <h1>Thống kê sản phẩm bán chạy theo số lượng bán</h1>

          <table id="tblBest" class="product-table display">
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
            <c:choose>
              <c:when test="${empty bestTable}">
                <tr>
                  <td colspan="7" style="text-align:center;padding:16px;">
                    Không có dữ liệu sản phẩm bán chạy
                  </td>
                </tr>
              </c:when>
              <c:otherwise>
                <c:forEach var="p" items="${bestTable}">
                  <tr>
                    <td></td>
                    <td>SP${p.productId}</td>
                    <td><c:out value="${p.productName}"/></td>
                    <td><c:out value="${p.categoryName}"/></td>
                    <td><fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/></td>
                    <td><fmt:formatDate value="${p.createAt}" pattern="dd-MM-yyyy"/></td>
                    <td>${p.soldQty}</td>
                  </tr>
                </c:forEach>
              </c:otherwise>
            </c:choose>
            </tbody>
          </table>

          <div class="product-chart-container" style="
          width: 95%;
          margin: 30px auto;
          background: white;
          padding: 25px;
          border-radius: 10px;
          box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        ">
            <h1>Biểu đồ sản phẩm bán chạy</h1>
            <canvas id="chartBestSeller"></canvas>
          </div>
        </div>

        <!-- NO SALE (FILTER MONTHS) -->
        <div class="product-container">
          <h1>Thống kê sản phẩm không bán được</h1>

          <form method="get" action="${pageContext.request.contextPath}/admin/statistics" class="thoigian">
            <div class="sothang">
              <label for="noSaleMonths">Nhập số tháng</label>
              <input type="number" id="noSaleMonths" name="noSaleMonths" min="1" max="12"
                     value="${noSaleMonths}">
            </div>
            <button type="submit"
                    style="padding:10px 16px;border:none;border-radius:6px;background:#2659F5;color:#fff;cursor:pointer;">
              Lọc
            </button>
          </form>

          <table id="tblNoSale" class="product-table display">
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
            <c:choose>
              <c:when test="${empty noSaleTable}">
                <tr>
                  <td colspan="7" style="text-align:center;padding:16px;">
                    Không có sản phẩm nào không bán trong ${noSaleMonths} tháng gần đây
                  </td>
                </tr>
              </c:when>
              <c:otherwise>
                <c:forEach var="p" items="${noSaleTable}">
                  <tr>
                    <td></td>
                    <td>SP${p.productId}</td>
                    <td><c:out value="${p.productName}"/></td>
                    <td><c:out value="${p.categoryName}"/></td>
                    <td><fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/></td>
                    <td><fmt:formatDate value="${p.createAt}" pattern="dd-MM-yyyy"/></td>
                    <td>${p.soldQuantity}</td>
                  </tr>
                </c:forEach>
              </c:otherwise>
            </c:choose>
            </tbody>
          </table>
        </div>

      </div>
    </div>
  </div>

  <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
  <script src="https://cdn.datatables.net/1.13.8/js/jquery.dataTables.min.js"></script>

  <script>
    const revenueLabels = [
      'Tháng 1','Tháng 2','Tháng 3','Tháng 4','Tháng 5','Tháng 6',
      'Tháng 7','Tháng 8','Tháng 9','Tháng 10','Tháng 11','Tháng 12'
    ];

    const revenueData = [
      <c:forEach var="r" items="${revYear}" varStatus="st">
      ${r.revenue}<c:if test="${!st.last}">,</c:if>
      </c:forEach>
    ];

    new Chart(document.getElementById('chartDoanhThu'), {
      type: 'line',
      data: {
        labels: revenueLabels,
        datasets: [{
          label: 'Doanh thu (VNĐ)',
          data: revenueData,
          fill: true,
          tension: 0.4
        }]
      },
      options: {
        responsive: true,
        plugins: { legend: { display: false } },
        scales: {
          y: {
            beginAtZero: true,
            ticks: { callback: (value) => Number(value).toLocaleString('vi-VN') + 'đ' }
          }
        }
      }
    });

    // ===== CHART BEST SELLER TOP 5 =====
    const bestLabels = [
      <c:forEach var="b" items="${bestChart}" varStatus="st">
      "SP${b.productId}"<c:if test="${!st.last}">,</c:if>
      </c:forEach>
    ];

    const bestData = [
      <c:forEach var="b" items="${bestChart}" varStatus="st">
      ${b.soldQty}<c:if test="${!st.last}">,</c:if>
      </c:forEach>
    ];

    if (bestLabels.length === 0) {
      bestLabels.push("Chưa có dữ liệu");
      bestData.push(0);
    }

    new Chart(document.getElementById('chartBestSeller'), {
      type: 'bar',
      data: {
        labels: bestLabels,
        datasets: [{
          label: 'Số lượng bán',
          data: bestData,
          borderRadius: 6,
          maxBarThickness: 70
        }]
      },
      options: {
        responsive: true,
        plugins: { legend: { display: false } },
        scales: { y: { beginAtZero: true } }
      }
    });

    // ===== DATATABLES INIT (2 bảng) =====
    $(function () {
      const viLang = {
        emptyTable: "Không có dữ liệu",
        info: "Hiển thị _START_ đến _END_ của _TOTAL_ dòng",
        infoEmpty: "Hiển thị 0 đến 0 của 0 dòng",
        lengthMenu: "Hiển thị _MENU_ dòng",
        loadingRecords: "Đang tải...",
        processing: "Đang xử lý...",
        search: "Tìm:",
        zeroRecords: "Không tìm thấy dữ liệu phù hợp",
        paginate: { first: "Đầu", last: "Cuối", next: "Sau", previous: "Trước" }
      };

      function initTable(id) {
        const dt = $(id).DataTable({
          pageLength: 10,
          lengthMenu: [5, 10, 25, 50, 100],
          language: viLang,
          columnDefs: [
            { orderable: false, targets: 0 } // cột STT không sort
          ]
        });

        // Đánh lại STT theo trang/sort/search
        dt.on('order.dt search.dt draw.dt', function () {
          dt.column(0, { search: 'applied', order: 'applied' }).nodes().each(function (cell, i) {
            cell.innerHTML = i + 1;
          });
        }).draw();

        return dt;
      }

      initTable('#tblBest');
      initTable('#tblNoSale');

      // Clamp input months 1..12
      $('#noSaleMonths').on('input', function () {
        let v = parseInt(this.value || "1");
        if (v < 1) v = 1;
        if (v > 12) v = 12;
        this.value = v;
      });
    });
  </script>

  </body>

  </html>