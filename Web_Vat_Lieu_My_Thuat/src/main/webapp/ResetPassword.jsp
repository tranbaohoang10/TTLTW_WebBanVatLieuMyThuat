<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt lại mật khẩu</title>
    <link rel="stylesheet" href="./assets/css/style.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
</head>

<style>
    .reset-wrapper {
        background: #dff1ff;
        padding: 36px 0 40px;
        min-height: 100vh;
    }

    .reset-breadcrumb {
        width: 90%;
        max-width: 1740px;
        margin: 0 auto 20px auto;
        font-size: 16px;
        color: #222;
    }

    .reset-breadcrumb a {
        color: #222;
        text-decoration: none;
    }

    .reset-breadcrumb span {
        margin: 0 6px;
    }

    .reset-container {
        width: 92%;
        max-width: 1750px;
        margin: 22px auto 0;
        background: #FFFFFF;
        border-radius: 28px;
        padding: 70px 0 80px;
        margin-top: 100px;
    }

    .reset-form-box {
        width: 48%;
        margin-left: 26%;
    }

    .reset-form-box h2 {
        font-size: 28px;
        font-weight: 500;
        color: #1d2b3a;
        margin: 0 0 28px;
        text-transform: uppercase;
    }

    .reset-desc {
        font-size: 18px;
        color: #111;
        margin-bottom: 30px;
    }

    .reset-form .form-group {
        margin-bottom: 28px;
    }

    .reset-form .form-group label {
        display: block;
        font-size: 18px;
        color: #111;
        margin-bottom: 12px;
    }

    .reset-form .form-group label span {
        color: red;
    }

    .reset-form .form-group input {
        width: 100%;
        height: 58px;
        border: 1px solid #ddd;
        background: white;
        padding: 0 16px;
        font-size: 17px;
        outline: none;
    }

    .reset-form .form-group input:focus {
        border-color: #aaa;
    }

    .form-action {
        display: flex;
        align-items: center;
        gap: 28px;
        margin-top: 10px;
    }

    .btn-reset {
        background: #edd23f;
        color: #111;
        border: none;
        border-radius: 22px;
        padding: 14px 28px;
        font-size: 18px;
        cursor: pointer;
    }

    .btn-reset:hover {
        opacity: 0.95;
    }

    .btn-cancel {
        color: #666;
        font-size: 17px;
        text-decoration: underline;
    }

    .reset-error {
        color: red;
        margin-bottom: 16px;
        font-size: 15px;
    }
</style>

<body>

<%@ include file="Header.jsp" %>

<div class="reset-wrapper">
    <div class="reset-breadcrumb">
        <a href="${pageContext.request.contextPath}/home">Trang chủ</a>
        <span>/</span>
        <a href="${pageContext.request.contextPath}/login">Tài khoản</a>
        <span>/</span>
        <span>Thiết lập lại mật khẩu</span>
    </div>

    <div class="reset-container">
        <div class="reset-form-box">
            <h2>LẤY LẠI MẬT KHẨU</h2>


            <form action="${pageContext.request.contextPath}/reset-password" method="post" class="reset-form">
                <input type="hidden" name="token" value="${token}">

                <div class="form-group">
                    <label for="newPassword">Mật khẩu <span>*</span></label>
                    <input type="password" id="newPassword" name="newPassword" required>
                </div>

                <div class="form-group">
                    <label for="confirmPassword">Xác nhận mật khẩu <span>*</span></label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required>
                </div>

                <div class="form-action">
                    <button type="submit" class="btn-reset">Đặt lại mật khẩu</button>
                    <a href="${pageContext.request.contextPath}/login" class="btn-cancel">Hủy</a>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="Footer.jsp" %>

<script>
    const newPassword = document.getElementById("newPassword");
    const confirmPassword = document.getElementById("confirmPassword");
    const confirmError = document.getElementById("confirm-error");

    function checkConfirmPassword() {
        const newPass = newPassword.value.trim();
        const confirmPass = confirmPassword.value.trim();

        if (confirmPass.length === 0) {
            confirmError.textContent = "";
            return;
        }

        if (newPass !== confirmPass) {
            confirmError.textContent = "Xác nhận mật khẩu không khớp";
        } else {
            confirmError.textContent = "";
        }
    }

    confirmPassword.addEventListener("input", checkConfirmPassword);
    newPassword.addEventListener("input", checkConfirmPassword);
</script>

</body>
</html>