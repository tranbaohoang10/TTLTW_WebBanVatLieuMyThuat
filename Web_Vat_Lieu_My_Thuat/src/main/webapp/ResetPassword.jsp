<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thiết lập lại mật khẩu</title>

    <style>
        * {
            box-sizing: border-box;
        }

        .reset-page {
            background: #dff1ff;
            padding-bottom: 40px;
            min-height: 600px;
        }

        .reset-page .container {
            width: 90%;
            max-width: 1730px;
            margin: 0 auto;
        }

        .reset-breadcrumb-wrap {
            background: #d7e5f7;
            padding: 22px 0;
        }

        .reset-breadcrumb {
            font-size: 18px;
            color: #1f2d3d;
        }

        .reset-breadcrumb a {
            text-decoration: none;
            color: #1f2d3d;
        }

        .reset-breadcrumb span {
            margin: 0 6px;
        }

        .reset-box {
            background: #f7f7f7;
            border-radius: 30px;
            margin: 28px auto 0;
            padding: 48px 0 60px;
            width: 92%;
        }

        .reset-title {
            font-size: 28px;
            font-weight: 500;
            margin: 0 0 28px 0;
            margin-left: 26%;
            color: #111;
        }

        .reset-subtitle {
            font-size: 18px;
            margin: 0 0 24px 0;
            margin-left: 26%;
            color: #111;
        }

        .reset-form {
            width: 48%;
            margin-left: 26%;
        }

        .reset-form-group {
            margin-bottom: 26px;
        }

        .reset-form-group label {
            display: block;
            font-size: 20px;
            margin-bottom: 12px;
            color: #111;
        }

        .reset-form-group label span {
            color: #e53935;
        }

        .reset-form-group input {
            width: 100%;
            height: 58px;
            border: 1px solid #dddddd;
            background: #fff;
            padding: 0 16px;
            font-size: 18px;
            outline: none;
        }

        .reset-form-group input:focus {
            border-color: #999;
        }

        .reset-actions {
            display: flex;
            align-items: center;
            gap: 28px;
            margin-top: 8px;
        }

        .reset-btn {
            border: none;
            background: #efd648;
            color: #111;
            font-size: 20px;
            padding: 14px 30px;
            border-radius: 22px;
            cursor: pointer;
            transition: 0.2s ease;
        }

        .reset-btn:hover {
            opacity: 0.92;
        }

        .reset-cancel {
            font-size: 18px;
            color: #6d6d6d;
            text-decoration: underline;
        }

        .reset-message {
            width: 48%;
            margin-left: 26%;
            margin-bottom: 18px;
            padding: 14px 16px;
            border-radius: 10px;
            font-size: 16px;
        }

        .reset-error {
            background: #ffe5e5;
            color: #c62828;
            border: 1px solid #ef9a9a;
        }

        @media (max-width: 1200px) {
            .reset-title,
            .reset-subtitle,
            .reset-form,
            .reset-message {
                margin-left: 18%;
            }

            .reset-form,
            .reset-message {
                width: 64%;
            }
        }

        @media (max-width: 768px) {
            .reset-box {
                width: 96%;
                padding: 32px 20px 40px;
            }

            .reset-title,
            .reset-subtitle,
            .reset-form,
            .reset-message {
                width: 100%;
                margin-left: 0;
            }

            .reset-title {
                font-size: 24px;
            }

            .reset-form-group label {
                font-size: 18px;
            }

            .reset-form-group input {
                height: 52px;
                font-size: 16px;
            }

            .reset-actions {
                flex-wrap: wrap;
                gap: 14px;
            }

            .reset-btn {
                font-size: 18px;
                padding: 12px 24px;
            }
        }
    </style>
</head>
<body>

<jsp:include page="Header.jsp"/>

<div class="reset-page">
    <div class="reset-breadcrumb-wrap">
        <div class="container">
            <div class="reset-breadcrumb">
                <a href="${pageContext.request.contextPath}/home">Trang chủ</a>
                <span>/</span>
                <a href="${pageContext.request.contextPath}/login">Tài khoản</a>
                <span>/</span>
                <span>Thiết lập lại mật khẩu</span>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="reset-box">
            <h1 class="reset-title">LẤY LẠI MẬT KHẨU</h1>
            <p class="reset-subtitle">Nhập mật khẩu mới</p>

            <c:if test="${not empty error}">
                <div class="reset-message reset-error">${error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/reset-password" method="post" class="reset-form">
                <input type="hidden" name="token" value="${token}">

                <div class="reset-form-group">
                    <label for="newPassword">Mật khẩu <span>*</span></label>
                    <input type="password" id="newPassword" name="newPassword" required>
                </div>

                <div class="reset-form-group">
                    <label for="confirmPassword">Xác nhận mật khẩu <span>*</span></label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required>
                </div>

                <div class="reset-actions">
                    <button type="submit" class="reset-btn">Đặt lại mật khẩu</button>
                    <a href="${pageContext.request.contextPath}/login" class="reset-cancel">Hủy</a>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="Footer.jsp"/>

</body>
</html>