<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng Nhập</title>

    <link rel="stylesheet" href="./assets/css/style.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>

    <style>
        .main {
            margin: 0;
            padding: 0;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            background: linear-gradient(to left, #2659F3, #09BCE4);
        }

        .container {
            width: 460px;
            min-height: 480px;
            height: auto;
            margin: auto;
            padding: 45px;
            box-sizing: border-box;
            background-color: rgba(0, 0, 0, 0.4);
            border-radius: 10px;
            position: relative;
            overflow: visible;
            transition: 0.5s ease;
        }

        h4 {
            color: white;
            text-align: center;
            text-transform: uppercase;
            margin-bottom: 30px;
            font-weight: 700;
        }

        .form-group {
            margin-bottom: 12px;
        }

        .form-group label {
            display: block;
            color: white;
            margin-bottom: 5px;
        }

        .form-group input {
            width: 100%;
            padding: 10px 0;
            background: transparent;
            border: none;
            border-bottom: 1px solid #ffffff;
            color: white;
            outline: none;
            box-sizing: border-box;
        }

        .form-group input::placeholder {
            color: #a0a0a0;
        }

        .forgot-password {
            text-align: center;
            margin-bottom: 20px;
        }

        .forgot-password a {
            color: #ffc107;
            text-decoration: none;
        }

        .forgot-password a:hover {
            color: #6C785B;
        }

        .btn-login {
            width: 100%;
            background-color: #F5DF4D;
            padding: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-bottom: 15px;
            color: #343A40;
            font-weight: bold;
        }

        .chuataikhoan {
            color: white;
            text-align: center;
        }

        .link-dki {
            color: #FFC107;
            text-decoration: none;
        }

        .link-dki:hover {
            color: #6C785B;
        }

        .btn-login-gg {
            margin-top: 12px;
        }

        .btn-gg {
            display: block;
            width: 100%;
            text-align: center;
            background-color: #DE3F32;
            color: white;
            border: none;
            border-radius: 5px;
            padding: 10px;
            margin-top: 10px;
            cursor: pointer;
            text-decoration: none;
            box-sizing: border-box;
        }

        .btn-gg i {
            margin-right: 8px;
        }

        p {
            color: white;
        }

        .tb_loi {
            margin-top: 10px;
            margin-bottom: 10px;
        }

        .tb-success {
            color: #00ff99;
        }

        .tb-error {
            color: #ff6666;
        }

        .tb-warning {
            color: #FFD700;
        }

        #email-error {
            color: #FFD700;
            font-size: 14px;
        }

        #tb-phien {
            position: fixed;
            inset: 0;
            background: rgba(0, 0, 0, 0.55);
            display: none;
            align-items: center;
            justify-content: center;
            z-index: 9999;
        }

        .session-modal-card {
            background: linear-gradient(135deg, #13438F, #1E76EF);
            border-radius: 16px;
            padding: 24px 28px;
            min-width: 320px;
            max-width: 420px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.35);
            text-align: center;
            color: #fff;
            border: 1px solid rgba(245, 223, 77, 0.9);
        }

        .session-modal-icon {
            width: 56px;
            height: 56px;
            border-radius: 50%;
            background: rgba(245, 223, 77, 0.15);
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 12px auto;
            color: #F5DF4D;
            font-size: 26px;
        }

        .session-modal-title {
            margin: 0;
            font-size: 20px;
            font-weight: 700;
        }

        .session-modal-text {
            margin: 10px 0 18px 0;
            font-size: 14px;
            color: #E5ECFF;
        }

        .btn-dongphien {
            padding: 8px 20px;
            border-radius: 999px;
            border: none;
            background: #F5DF4D;
            color: #343A40;
            font-weight: 600;
            cursor: pointer;
            font-size: 14px;
        }

        .btn-dongphien i {
            color: #343A40;
        }
    </style>
</head>
<body>

<%@ include file="Header.jsp" %>

<div class="main">
    <div class="container">
        <h4>Đăng Nhập</h4>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" placeholder="Nhập email" required
                       pattern="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$"
                       title="Email phải đúng định dạng, ví dụ: ten@gmail.com">
                <span id="email-error"></span>
            </div>

            <div class="form-group">
                <label for="password">Mật Khẩu:</label>
                <input type="password"  id="password" name="password" placeholder="Nhập mật khẩu" required>
            </div>

            <div class="tb_loi">
                <%
                    String timeout = request.getParameter("timeout");
                    String verify = request.getParameter("verify");

                    Object warningObj = request.getAttribute("warning");
                    Object errorObj = request.getAttribute("error");

                    String warning = "";
                    String error = "";

                    if (warningObj != null) {
                        warning = warningObj.toString();
                    }

                    if (errorObj != null) {
                        error = errorObj.toString();
                    }
                %>
                <%
                    if ("success".equals(verify)) {
                %>
                <div class="tb-success">Tài khoản đã xác thực thành công! Bạn hãy đăng nhập.</div>
                <%
                } else if ("failed".equals(verify)) {
                %>
                <div class="tb-error">Link xác thực không hợp lệ hoặc đã hết hạn.</div>
                <%
                } else if (warning != null && !warning.trim().isEmpty()) {
                %>
                <div class="tb-warning"><%= warning %></div>
                <%
                } else if (error != null && !error.trim().isEmpty()) {
                %>
                <div class="tb-error"><%= error %></div>
                <%
                    }
                %>
            </div>

            <div class="forgot-password">
                <p>
                    Quên mật khẩu? Nhấn vào
                    <a href="${pageContext.request.contextPath}/forgotpassword">đây</a>
                </p>
            </div>

            <button type="submit" class="btn-login">Đăng Nhập</button>

            <div class="chuataikhoan">
                Bạn chưa có tài khoản
                <a href="${pageContext.request.contextPath}/register" class="link-dki">Đăng ký tại đây</a>
            </div>

            <div class="btn-login-gg">
                <a class="btn-gg" href="${pageContext.request.contextPath}/oauth2/google">
                    <i class="fa-brands fa-google"></i> Google
                </a>
            </div>
        </form>
    </div>
</div>

<div id="tb-phien">
    <div class="session-modal-card">
        <div class="session-modal-icon">
            <i class="fa-solid fa-clock-rotate-left"></i>
        </div>
        <h3 class="session-modal-title">Phiên đăng nhập đã hết hạn</h3>
        <p class="session-modal-text">
            Vui lòng đăng nhập lại để tiếp tục sử dụng hệ thống.
        </p>
        <button id="dong-model" class="btn-dongphien">
            <i class="fa-solid fa-x"></i> Đóng
        </button>
    </div>
</div>

<script>
    window.onload = function () {
        var timeout = "<%= timeout %>";

        if (timeout === "1") {
            var modal = document.getElementById("tb-phien");
            var closeBtn = document.getElementById("dong-model");

            if (modal != null) {
                modal.style.display = "flex";
            }

            if (closeBtn != null) {
                closeBtn.onclick = function () {
                    modal.style.display = "none";

                    var emailInput = document.getElementById("email");
                    if (emailInput != null) {
                        emailInput.focus();
                    }
                };
            }
        }
    };
</script>

<script>
    var emailInput = document.getElementById("email");
    var emailError = document.getElementById("email-error");

    var emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;

    emailInput.addEventListener("input", function () {
        var emailValue = emailInput.value.trim();

        if (emailValue.length === 0) {
            emailError.innerHTML = "";
            return;
        }

        if (emailRegex.test(emailValue)) {
            emailError.innerHTML = "";
        } else {
            emailError.innerHTML = "Email không hợp lệ (vd: ten@gmail.com)";
        }
    });
</script>

<%@ include file="Footer.jsp" %>

</body>
</html>