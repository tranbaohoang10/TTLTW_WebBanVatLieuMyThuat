<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Chi tiết sản phẩm</title>

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f5f6f8;
            color: #222;
        }

        .container {
            width: 96%;
            margin: 25px auto;
        }

        .btn-back {
            display: inline-block;
            padding: 11px 18px;
            margin-bottom: 20px;
            color: white;
            background: #2f80ed;
            border-radius: 6px;
            text-decoration: none;
        }

        .btn-back i {
            margin-right: 7px;
        }

        .btn-back:hover {
            background: #1f6dcc;
        }

        h1 {
            margin: 5px 0 25px;
            font-size: 30px;
        }

        .detail-box {
            padding: 25px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
        }

        .detail-box h2 {
            margin-top: 0;
            margin-bottom: 20px;
            font-size: 22px;
        }

        .product-content {
            display: grid;
            grid-template-columns: 280px 1fr;
            gap: 30px;
        }

        .product-image {
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 280px;
            padding: 15px;
            background: #f7f8fa;
            border: 1px solid #e5e5e5;
            border-radius: 8px;
        }

        .product-image img {
            width: 100%;
            max-height: 300px;
            object-fit: contain;
        }

        .no-image {
            color: #777;
            text-align: center;
        }

        .no-image i {
            display: block;
            margin-bottom: 10px;
            font-size: 50px;
        }

        .detail-table {
            width: 100%;
            border-collapse: collapse;
        }

        .detail-table th,
        .detail-table td {
            padding: 13px 10px;
            border-bottom: 1px solid #e6e6e6;
            text-align: left;
        }

        .detail-table th {
            width: 230px;
            font-size: 16px;
        }

        .detail-table td {
            font-size: 16px;
        }

        .price {
            color: #d62828;
            font-weight: bold;
        }

        .status-active {
            display: inline-block;
            padding: 6px 12px;
            color: #18794e;
            background: #e8f7ed;
            border-radius: 15px;
            font-weight: bold;
        }

        .status-inactive {
            display: inline-block;
            padding: 6px 12px;
            color: #b42318;
            background: #fdeaea;
            border-radius: 15px;
            font-weight: bold;
        }

        .message-error {
            padding: 15px;
            color: #b42318;
            background: #fdeaea;
            border-radius: 6px;
        }

    </style>
</head>

<body>
<div class="container">

    <a href="${pageContext.request.contextPath}/admin/products"
       class="btn-back">
        <i class="fa-solid fa-arrow-left"></i>
        Quay lại
    </a>

    <h1>Chi tiết sản phẩm</h1>

    <c:choose>
        <c:when test="${not empty product}">

            <div class="detail-box">
                <h2>Thông tin sản phẩm</h2>

                <div class="product-content">

                    <div class="product-image">
                        <c:choose>
                            <c:when test="${not empty product.thumbnail}">
                                <c:choose>
                                    <c:when test="${fn:startsWith(product.thumbnail, 'http')}">
                                        <img src="${product.thumbnail}"
                                             alt="${product.name}">
                                    </c:when>

                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/${product.thumbnail}"
                                             alt="${product.name}">
                                    </c:otherwise>
                                </c:choose>
                            </c:when>

                            <c:otherwise>
                                <div class="no-image">
                                    <i class="fa-regular fa-image"></i>
                                    Chưa có ảnh sản phẩm
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <table class="detail-table">
                        <tr>
                            <th>ID sản phẩm</th>
                            <td>${product.id}</td>
                        </tr>

                        <tr>
                            <th>Tên sản phẩm</th>
                            <td>${product.name}</td>
                        </tr>

                        <tr>
                            <th>Danh mục</th>
                            <td> ${product.categoryName}</td>
                        </tr>

                        <tr>
                            <th>Thương hiệu</th>
                            <td>${product.brand} </td>
                        </tr>

                        <tr>
                            <th>Giá bán</th>
                            <td class="price">
                                <fmt:formatNumber value="${product.price}" type="number"/> đ
                            </td>
                        </tr>

                        <tr>
                            <th>Giảm giá mặc định</th>
                            <td>${product.discountDefault}%</td>
                        </tr>

                        <tr>
                            <th>Số lượng tồn kho</th>
                            <td>${product.quantityStock}</td>
                        </tr>

                        <tr>
                            <th>Số lượng đã bán</th>
                            <td>${product.soldQuantity}</td>
                        </tr>

                        <tr>
                            <th>Trạng thái sản phẩm</th>
                            <td>${product.status}</td>
                        </tr>

                        <tr>
                            <th>Trạng thái hoạt động</th>
                            <td>
                                <c:choose>
                                    <c:when test="${product.isActive eq 1}">
                                        <span class="status-active">
                                            Đang hoạt động
                                        </span>
                                    </c:when>

                                    <c:otherwise>
                                        <span class="status-inactive">
                                            Ngừng hoạt động
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>

                        <tr>
                            <th>Ngày tạo</th>
                            <td>${product.createAt} </td>
                        </tr>
                    </table>

                </div>
            </div>

        </c:when>

        <c:otherwise>
            <div class="message-error">
                Không tìm thấy thông tin sản phẩm.
            </div>
        </c:otherwise>
    </c:choose>

</div>
</body>
</html>
