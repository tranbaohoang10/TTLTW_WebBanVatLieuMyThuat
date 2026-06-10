<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Lập phiếu nhập hàng</title>

    <link rel="stylesheet" href="${ctx}/assets/css/style.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css">

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

        .container {
            width: 95%;
            margin: 30px auto;
        }

        .box {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0,0,0,.1);
            margin-bottom: 30px;
        }

        h1 {
            margin-top: 0;
            color: #17479D;
        }

        .muted {
            color: #666;
            font-size: 14px;
            margin-top: 6px;
        }

        .alert-success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
            padding: 10px 12px;
            border-radius: 6px;
            margin-bottom: 15px;
        }

        .receipt-form {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 18px;
            margin-top: 20px;
        }

        .form-group {
            display: flex;
            flex-direction: column;
        }

        .form-group.full {
            grid-column: 1 / 3;
        }

        .form-group label {
            font-weight: 600;
            margin-bottom: 6px;
        }

        .form-group input,
        .form-group select,
        .form-group textarea {
            padding: 9px 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
        }

        .form-group textarea {
            min-height: 90px;
            resize: vertical;
        }

        .section-title {
            grid-column: 1 / 3;
            margin-top: 10px;
            padding-top: 18px;
            border-top: 1px solid #ddd;
            color: #17479D;
        }

        .product-placeholder {
            grid-column: 1 / 3;
            border: 2px dashed #bbb;
            border-radius: 8px;
            padding: 20px;
            background: #fafafa;
            color: #555;
            line-height: 1.7;
        }

        .btn-area {
            grid-column: 1 / 3;
            display: flex;
            justify-content: flex-end;
            gap: 10px;
            margin-top: 10px;
        }

        .btn-primary {
            background: #2659F5;
            color: white;
            border: none;
            padding: 10px 16px;
            border-radius: 6px;
            cursor: pointer;
        }

        .btn-primary:hover {
            background: #17479D;
        }

        .btn-secondary {
            background: #ccc;
            color: #222;
            text-decoration: none;
            padding: 10px 16px;
            border-radius: 6px;
        }
        .receipt-items-table {
            grid-column: 1 / 3;
            width: 100%;
            border-collapse: collapse;
            margin-top: 5px;
        }

        .receipt-items-table th {
            background: #2659F5;
            color: white;
            padding: 10px;
            font-size: 14px;
        }

        .receipt-items-table td {
            border: 1px solid #ddd;
            padding: 8px;
            background: #fff;
        }

        .receipt-items-table select,
        .receipt-items-table input {
            width: 100%;
            padding: 7px;
            box-sizing: border-box;
        }

        .btn-add-row {
            background: #28a745;
            color: white;
            border: none;
            padding: 9px 13px;
            border-radius: 5px;
            cursor: pointer;
        }

        .btn-remove-row {
            background: #dc3545;
            color: white;
            border: none;
            padding: 7px 10px;
            border-radius: 5px;
            cursor: pointer;
        }

        .total-box {
            grid-column: 1 / 3;
            display: flex;
            justify-content: flex-end;
            align-items: center;
            gap: 12px;
            font-size: 18px;
            font-weight: bold;
            color: #17479D;
            margin-top: 10px;
        }

        .total-box input {
            width: 180px;
            text-align: right;
            font-weight: bold;
        }
        .alert-danger {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
            padding: 10px 12px;
            border-radius: 6px;
            margin-bottom: 15px;
        }
        .receipt-list-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        .receipt-list-table th {
            background: #2659F5;
            color: white;
            padding: 11px;
            font-size: 14px;
        }

        .receipt-list-table td {
            border: 1px solid #ddd;
            padding: 10px;
            background: #fff;
            vertical-align: middle;
        }

        .receipt-list-table tr:nth-child(even) td {
            background: #f7f7f7;
        }

        .status-completed {
            color: #28a745;
            font-weight: 600;
        }

        .status-draft {
            color: #ff9800;
            font-weight: 600;
        }

        .btn-detail {
            display: inline-block;
            background: #17479D;
            color: white;
            padding: 7px 11px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 13px;
        }

        .btn-detail:hover {
            background: #0f3475;
        }

        .empty-message {
            margin-top: 15px;
            padding: 14px;
            background: #f8f9fa;
            border: 1px dashed #bbb;
            border-radius: 6px;
            color: #666;
        }
        .receipt-list-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
            background: white;
            border-radius: 8px;
            overflow: hidden;
        }

        .receipt-list-table th {
            background: #2659F5;
            color: white;
            padding: 12px 10px;
            font-size: 14px;
            text-align: left;
        }

        .receipt-list-table td {
            border-bottom: 1px solid #e0e0e0;
            padding: 12px 10px;
            background: #fff;
            vertical-align: middle;
            font-size: 14px;
        }

        .receipt-list-table tr:nth-child(even) td {
            background: #f8f9fa;
        }

        .receipt-list-table tr:hover td {
            background: #eef3ff;
        }

        .btn-detail {
            display: inline-block;
            background: #17479D;
            color: white;
            padding: 7px 12px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 13px;
            font-weight: 500;
        }

        .btn-detail:hover {
            background: #0f3475;
        }
        .status-badge {
            display: inline-block;
            padding: 6px 10px;
            border-radius: 20px;
            font-size: 13px;
            font-weight: 600;
            white-space: nowrap;
        }

        .status-success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .alert-danger {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
            padding: 10px 12px;
            border-radius: 6px;
            margin-bottom: 15px;
        }
    </style>

</head>

<body>
<c:set var="permissions" value="${sessionScope.permissions}" />
<c:set var="role" value="${sessionScope.currentUser.role}" />

<div id="main">
    <div class="left">
        <div class="list-admin">
            <a href="${ctx}/admin/overview" class="logo">
                <img src="${ctx}/assets/images/logo/logo.png" alt="">
            </a>

            <a href="${ctx}/admin/overview">
                <i class="fa-solid fa-house"></i>Tổng quan
            </a>

            <c:if test="${role == 'ADMIN' || permissions.contains('STATISTIC_VIEW')}">
                <a href="${ctx}/admin/statistics">
                    <i class="fa-solid fa-chart-line"></i>Thống kê
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('CATEGORY_VIEW')}">
                <a href="${ctx}/admin/categories">
                    <i class="fa-solid fa-list"></i>Quản lý danh mục
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('PRODUCT_VIEW')}">
                <a href="${ctx}/admin/products">
                    <i class="fa-solid fa-palette"></i>Quản lý sản phẩm
                </a>
            </c:if>
            <c:if test="${role == 'ADMIN' || permissions.contains('SUPPLIER_VIEW')}">
                <a href="${ctx}/admin/suppliers">
                    <i class="fa-solid fa-truck-field"></i>Nhà cung cấp
                </a>
            </c:if>
            <c:if test="${role == 'ADMIN' || permissions.contains('PURCHASE_RECEIPT_VIEW')}">
                <a href="${pageContext.request.contextPath}/admin/purchase-receipts" class="active">
                    <i class="fa-solid fa-file-invoice"></i>Phiếu nhập hàng
                </a>
            </c:if>
            <c:if test="${role == 'ADMIN' || permissions.contains('INVENTORY_VIEW')}">
                <a href="${ctx}/admin/inventory">
                    <i class="fa-solid fa-warehouse"></i>Quản lý tồn kho
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('USER_VIEW')}">
                <a href="${ctx}/admin/users">
                    <i class="fa-solid fa-person"></i>Quản lý người dùng
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('PERMISSION_MANAGE')}">
                <a href="${pageContext.request.contextPath}/admin/permissions" >
                    <i class="fa-solid fa-user-shield"></i>Quản lý phân quyền
                </a>
            </c:if>
            <c:if test="${role == 'ADMIN' || permissions.contains('ORDER_VIEW')}">
                <a href="${ctx}/admin/orders">
                    <i class="fa-solid fa-box-open"></i>Quản lý đơn hàng
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('VOUCHER_VIEW')}">
                <a href="${ctx}/admin/vouchers">
                    <i class="fa-solid fa-gift"></i>Quản lý khuyến mãi
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('SLIDER_VIEW')}">
                <a href="${ctx}/admin/sliders">
                    <i class="fa-solid fa-sliders"></i>Quản lý Slider Show
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('CONTACT_VIEW')}">
                <a href="${ctx}/admin/contacts">
                    <i class="fa-solid fa-address-book"></i>Quản lý liên hệ
                </a>
            </c:if>

            <c:if test="${role == 'ADMIN' || permissions.contains('LOG_VIEW')}">
                <a href="${ctx}/admin/logs">
                    <i class="fa-solid fa-clock-rotate-left"></i>Quản lý thao tác
                </a>
            </c:if>

            <a href="${ctx}/logout">
                <i class="fa-solid fa-right-from-bracket"></i>Đăng xuất
            </a>
        </div>
    </div>

    <div class="right">
        <div class="container">
            <div class="box">
                <h1>Lập phiếu nhập hàng</h1>


                <c:if test="${not empty sessionScope.purchaseReceiptMessage}">
                    <div class="alert-success">${sessionScope.purchaseReceiptMessage}</div>
                    <c:remove var="purchaseReceiptMessage" scope="session"/>
                </c:if>
                <c:if test="${not empty sessionScope.purchaseReceiptError}">
                    <div class="alert-danger">${sessionScope.purchaseReceiptError}</div>
                    <c:remove var="purchaseReceiptError" scope="session"/>
                </c:if>
                <form class="receipt-form"
                      method="post"
                      action="${ctx}/admin/purchase-receipts">

                    <div class="form-group">
                        <label>Nhà cung cấp</label>
                        <select name="supplierID" required>
                            <option value="">-- Chọn nhà cung cấp --</option>

                            <c:forEach var="s" items="${activeSuppliers}">
                                <option value="${s.id}">
                                        ${s.supplierCode} - ${s.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label>Ngày nhập hàng</label>
                        <input type="datetime-local"
                               id="importDateInput"
                               name="importDate"
                               required>
                    </div>

                    <div class="form-group">
                        <label>Người nhập</label>
                        <input type="text"
                               value="${currentImporterName}"
                               readonly>
                    </div>

                    <div class="form-group full">
                        <label>Ghi chú</label>
                        <textarea name="note"
                                  placeholder="Nhập ghi chú cho phiếu nhập nếu có"></textarea>
                    </div>

                    <h2 class="section-title">Danh sách sản phẩm nhập</h2>

                    <table class="receipt-items-table">
                        <thead>
                        <tr>
                            <th style="width: 40px;">STT</th>
                            <th>Sản phẩm</th>
                            <th style="width: 130px;">Tồn hiện tại</th>
                            <th style="width: 130px;">Số lượng nhập</th>
                            <th style="width: 160px;">Giá nhập</th>
                            <th style="width: 180px;">Thành tiền</th>
                            <th style="width: 80px;">Xóa</th>
                        </tr>
                        </thead>

                        <tbody id="receiptItemsBody">
                        </tbody>
                    </table>

                    <div class="form-group full">
                        <button type="button" class="btn-add-row" id="btnAddProductRow">
                            <i class="fa-solid fa-plus"></i> Thêm sản phẩm
                        </button>
                    </div>

                    <div class="total-box">
                        <span>Tổng tiền phiếu nhập:</span>
                        <input type="text" id="totalAmountText" value="0" readonly>
                        <input type="hidden" name="totalAmount" id="totalAmount" value="0">
                    </div>

                    <template id="productRowTemplate">
                        <tr>
                            <td class="row-index"></td>

                            <td>
                                <select name="productIds" class="product-select" required>
                                    <option value="">-- Chọn sản phẩm --</option>

                                    <c:forEach var="p" items="${activeProducts}">
                                        <option value="${p.id}"
                                                data-stock="${p.quantityStock}">
                                            ID ${p.id} - ${p.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </td>

                            <td>
                                <input type="text" class="current-stock" value="0" readonly>
                            </td>

                            <td>
                                <input type="number"
                                       name="quantities"
                                       class="quantity-input"
                                       min="1"
                                       value="1"
                                       required>
                            </td>

                            <td>
                                <input type="number"
                                       name="importPrices"
                                       class="import-price-input"
                                       min="1"
                                       step="0.01"
                                       value="1"
                                       required>
                            </td>

                            <td>
                                <input type="text" class="line-total-text" value="0" readonly>
                                <input type="hidden" name="lineTotals" class="line-total" value="0">
                            </td>

                            <td>
                                <button type="button" class="btn-remove-row">
                                    <i class="fa-solid fa-trash"></i>
                                </button>
                            </td>
                        </tr>
                    </template>

                    <div class="btn-area">
                        <a class="btn-secondary" href="${ctx}/admin/inventory">
                            Quay lại tồn kho
                        </a>

                        <button class="btn-primary" type="submit">
                            Lưu phiếu nhập
                        </button>
                    </div>
                </form>
            </div>
            <div class="box">
                <h1>Danh sách phiếu nhập hàng</h1>
                <div class="muted">
                    Danh sách các phiếu nhập hàng đã được tạo trong hệ thống.
                </div>

                <c:choose>
                    <c:when test="${empty purchaseReceipts}">
                        <div class="empty-message">
                            Chưa có phiếu nhập hàng nào được tạo.
                        </div>
                    </c:when>

                    <c:otherwise>
                        <table class="receipt-list-table">
                            <thead>
                            <tr>
                                <th>STT</th>
                                <th>Mã phiếu nhập</th>
                                <th>Nhà cung cấp</th>
                                <th>Ngày nhập</th>
                                <th>Người nhập</th>
                                <th>Tổng tiền</th>
                                <th>Ghi chú</th>
                                <th>Trạng thái</th>
                                <th>Chi tiết</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach var="r" items="${purchaseReceipts}" varStatus="loop">
                                <tr>
                                    <td>${loop.index + 1}</td>

                                    <td>#${r.id}</td>

                                    <td>
                                        <c:out value="${r.supplierName}" />
                                    </td>

                                    <td>
                                        <fmt:formatDate value="${r.importDate}" pattern="dd/MM/yyyy HH:mm" />
                                    </td>

                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty r.createdByName}">
                                                <c:out value="${r.createdByName}" />
                                            </c:when>
                                            <c:otherwise>
                                                Admin
                                            </c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td>
                                        <fmt:formatNumber value="${r.totalAmount}" type="number" groupingUsed="true" /> đ
                                    </td>

                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty r.note}">
                                                <c:out value="${r.note}" />
                                            </c:when>
                                            <c:otherwise>
                                                -
                                            </c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td>
                                        <c:choose>
                                            <c:when test="${r.status == 'COMPLETED'}">
                                                <span class="status-badge status-success">Thành công</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="status-badge status-success">Thành công</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td>
                                        <a class="btn-detail"
                                           href="${ctx}/admin/purchase-receipts/detail?id=${r.id}">
                                            Xem chi tiết
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
<script>
    const receiptItemsBody = document.getElementById("receiptItemsBody");
    const productRowTemplate = document.getElementById("productRowTemplate");
    const btnAddProductRow = document.getElementById("btnAddProductRow");
    const totalAmountText = document.getElementById("totalAmountText");
    const totalAmountInput = document.getElementById("totalAmount");
    const receiptForm = document.querySelector(".receipt-form");
    const importDateInput = document.getElementById("importDateInput");

    function formatMoney(value) {
        const numberValue = Number(value) || 0;

        return numberValue.toLocaleString("vi-VN", {
            maximumFractionDigits: 0
        });
    }

    function parseNumber(value) {
        const numberValue = Number(value);

        if (isNaN(numberValue)) {
            return 0;
        }

        return numberValue;
    }

    function updateRowIndexes() {
        const rows = receiptItemsBody.querySelectorAll("tr");

        rows.forEach(function (row, index) {
            row.querySelector(".row-index").innerText = index + 1;
        });
    }

    function updateRowStock(row) {
        const select = row.querySelector(".product-select");
        const selectedOption = select.options[select.selectedIndex];
        const stockInput = row.querySelector(".current-stock");

        if (!selectedOption || !selectedOption.dataset.stock) {
            stockInput.value = "0";
            return;
        }

        stockInput.value = selectedOption.dataset.stock;
    }

    function updateRowTotal(row) {
        const quantity = parseNumber(row.querySelector(".quantity-input").value);
        const importPrice = parseNumber(row.querySelector(".import-price-input").value);
        const lineTotal = quantity * importPrice;

        row.querySelector(".line-total").value = lineTotal;
        row.querySelector(".line-total-text").value = formatMoney(lineTotal);
    }

    function updateTotalAmount() {
        let total = 0;

        receiptItemsBody.querySelectorAll(".line-total").forEach(function (input) {
            total += parseNumber(input.value);
        });

        totalAmountInput.value = total;
        totalAmountText.value = formatMoney(total);
    }

    function updateAllTotals() {
        receiptItemsBody.querySelectorAll("tr").forEach(function (row) {
            updateRowStock(row);
            updateRowTotal(row);
        });

        updateRowIndexes();
        updateTotalAmount();
    }

    function isDuplicateProduct() {
        const selectedIds = [];
        const selects = receiptItemsBody.querySelectorAll(".product-select");

        for (const select of selects) {
            const productId = select.value;

            if (!productId) {
                continue;
            }

            if (selectedIds.includes(productId)) {
                return true;
            }

            selectedIds.push(productId);
        }

        return false;
    }

    function addProductRow() {
        const clone = productRowTemplate.content.cloneNode(true);
        const row = clone.querySelector("tr");

        row.querySelector(".product-select").addEventListener("change", function () {
            updateRowStock(row);
            updateRowTotal(row);
            updateTotalAmount();
        });

        row.querySelector(".quantity-input").addEventListener("input", function () {
            updateRowTotal(row);
            updateTotalAmount();
        });

        row.querySelector(".import-price-input").addEventListener("input", function () {
            updateRowTotal(row);
            updateTotalAmount();
        });

        row.querySelector(".btn-remove-row").addEventListener("click", function () {
            row.remove();
            updateAllTotals();
        });

        receiptItemsBody.appendChild(row);
        updateAllTotals();
    }

    btnAddProductRow.addEventListener("click", addProductRow);

    receiptForm.addEventListener("submit", function (event) {
        if (!importDateInput || !importDateInput.value) {
            event.preventDefault();
            alert("Vui lòng chọn ngày nhập hàng.");
            return;
        }

        const selectedImportDate = new Date(importDateInput.value);
        const now = new Date();

        if (selectedImportDate > now) {
            event.preventDefault();
            alert("Ngày nhập hàng không được lớn hơn thời điểm hiện tại.");
            return;
        }
        const rows = receiptItemsBody.querySelectorAll("tr");

        if (rows.length === 0) {
            event.preventDefault();
            alert("Vui lòng thêm ít nhất một sản phẩm vào phiếu nhập.");
            return;
        }

        for (const row of rows) {
            const productId = row.querySelector(".product-select").value;
            const quantity = parseNumber(row.querySelector(".quantity-input").value);
            const importPrice = parseNumber(row.querySelector(".import-price-input").value);

            if (!productId) {
                event.preventDefault();
                alert("Vui lòng chọn sản phẩm cho tất cả các dòng.");
                return;
            }

            if (quantity <= 0) {
                event.preventDefault();
                alert("Số lượng nhập phải lớn hơn 0.");
                return;
            }

            if (importPrice < 0) {
                event.preventDefault();
                alert("Giá nhập không được âm.");
                return;
            }
        }

        if (isDuplicateProduct()) {
            event.preventDefault();
            alert("Không nên chọn trùng sản phẩm trong cùng một phiếu nhập.");
        }
    });

    addProductRow();
    function toDatetimeLocalValue(date) {
        const offset = date.getTimezoneOffset();
        const localDate = new Date(date.getTime() - offset * 60000);
        return localDate.toISOString().slice(0, 16);
    }

    if (importDateInput) {
        importDateInput.max = toDatetimeLocalValue(new Date());
    }
</script>
</body>
</html>