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

    .success-popup-overlay {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, 0.45);
        display: flex;
        align-items: center;
        justify-content: center;
        z-index: 9999;
    }

    .success-popup-box {
        width: 380px;
        max-width: 90%;
        background: #fff;
        border-radius: 18px;
        padding: 30px 24px;
        text-align: center;
        box-shadow: 0 10px 30px rgba(0, 0, 0, 0.18);
        animation: popupFadeIn 0.25s ease;
    }

    .success-icon {
        width: 70px;
        height: 70px;
        margin: 0 auto 16px;
        border-radius: 50%;
        background: #22c55e;
        color: white;
        font-size: 38px;
        font-weight: bold;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .success-popup-box h3 {
        margin: 0 0 10px;
        font-size: 26px;
        color: #222;
    }

    .success-popup-box p {
        margin: 0 0 22px;
        font-size: 17px;
        color: #555;
        line-height: 1.5;
    }

    .success-popup-btn {
        border: none;
        background: #f0d548;
        color: #222;
        font-size: 16px;
        font-weight: 600;
        padding: 12px 24px;
        border-radius: 999px;
        cursor: pointer;
    }

    .success-popup-btn:hover {
        opacity: 0.95;
    }
    .input-error {
        display: block;
        margin-top: 8px;
        color: #d93025;
        font-size: 14px;
    }

    .reset-error {
        color: #d93025;
        font-size: 15px;
        margin-top: -8px;
        margin-bottom: 16px;
    }

    @keyframes popupFadeIn {
        from {
            opacity: 0;
            transform: translateY(-10px) scale(0.96);
        }
        to {
            opacity: 1;
            transform: translateY(0) scale(1);
        }
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
            <h2>ĐẶT LẠI MẬT KHẨU</h2>

            <form action="${pageContext.request.contextPath}/reset-password" method="post" class="reset-form">
                <input type="hidden" name="token" value="${token}">

                <div class="form-group">
                    <label for="newPassword">Mật khẩu <span>*</span></label>
                    <input type="password" id="newPassword" name="newPassword" required minlength="8"
                    pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{8,}$"
                           title="Mật khẩu có ít nhất 8 ký tự, gồm chữ hoa, chữ thường và ký tự đặc biệt.">
                    <span id="newPassword-error" class="input-error"></span>
                </div>

                <div class="form-group">
                    <label for="confirmPassword">Xác nhận mật khẩu <span>*</span></label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required >
                    <span id="confirm-error" class="input-error"></span>
                </div>
                <c:if test="${not empty error}">
                    <p class="reset-error">${error}</p>
                </c:if>

                <div class="form-action">
                    <button type="submit" class="btn-reset">Đặt lại mật khẩu</button>
                    <a href="${pageContext.request.contextPath}/login" class="btn-cancel">Hủy</a>
                </div>
            </form>
        </div>
    </div>
</div>
<c:if test="${not empty resetSuccess}">
    <div id="successPopup" class="success-popup-overlay">
        <div class="success-popup-box">
            <div class="success-icon">✓</div>
            <h3>Thành công</h3>
            <p>${resetSuccess}</p>
            <button type="button" class="success-popup-btn" onclick="goToLogin()">
                Đăng nhập ngay
            </button>
        </div>
    </div>
</c:if>

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
<script>
    function goToLogin() {
        window.location.href = "${pageContext.request.contextPath}/login";
    }
</script>
</body>
</html>