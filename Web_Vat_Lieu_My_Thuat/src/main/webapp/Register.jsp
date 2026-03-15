<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký</title>
    <link rel="stylesheet" href="./assets/css/style.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

</head>
<style>
    .container {
        width: 1200px;
        margin: auto;
    }

    /* style login */
    .main {
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
        background-color: #1E76EF;
        padding-top: 50px;
        background: linear-gradient(to left, #2659F3, #09BCE4);
    }

    .container {
        background-color: rgba(0, 0, 0, 0.4);
        padding: 40px 50px;
        transition: 0.5s ease;
        width: 530px;
        box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
        border-radius: 10px;
    }

    h2 {
        color: white;
        text-align: center;
        text-transform: uppercase;
        margin-bottom: 15px;
    }

    .btn-doi button {
        flex: 1;
        border: none;
        border-radius: 25px;
        padding: 12px 0;
        font-weight: bold;
        cursor: pointer;
        font-size: 14px;
        transition: 0.3s;
    }

    .infor {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 15px 20px;
        margin-bottom: 20px;
    }

    .infor .tt-chitiet label {
        font-size: 17px;
        font-weight: normal;
        color: white;
    }

    .infor .tt-chitiet input {
        width: 100%;
        border: none;
        padding: 8px;
        border-bottom: 1px solid #ccc;
        outline: none;
        font-size: 14px;
        background: none;
        color: white;
        outline: none;

        border-radius: 8px;
        padding: 10px;
        color: #fff;
        transition: border-color 0.3s;

    }

    .infor .tt-chitiet input::placeholder {
        color: #B0B0B0;
    }


    .btn-DangKy {
        background: linear-gradient(90deg, #FFD700, #FFC107);
        width: 100%;
        background-color: #F5DF4D;
        padding: 10px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        margin-bottom: 15px;
        color: #000;
        font-weight: bold;
        transition: 0.3s;
    }

    .btn-DangKy:hover {
        transform: scale(1.05);
    }

    .dang-nhap a {
        color: #FFD700;
        text-decoration: none;

    }

    .dang-nhap a:hover {
        color: #6C785B;

    }

    .btn-dang-nhap-tt {
        display: flex;
        justify-content: space-between;
    }

    .btn-dang-nhap-tt button {
        width: 48%;
        border: none;
        border-radius: 8px;
        padding: 10px;
        font-size: 14px;
        color: white;
        cursor: pointer;
        display: flex;
        align-items: center;
        justify-content: center;
        gap: 8px;
    }

    .btn-gg {
        background-color: #DE3F32;
        color: white;
        border: none;
        border-radius: 5px;
        padding: 10px;
        padding-left: 80px;
        padding-right: 80px;
        margin-left: 27%;
        cursor: pointer;
    }

    .btn-dang-nhap-tt i {
        font-size: 16px;
    }

    .text-login {
        margin-top: 16px;
        text-align: center;
        color: #ffffff;
        font-size: 16px;
    }

    /* dn dki */
    .text-login a {
        color: #FFD700;
        text-decoration: none;
        font-weight: 500;
    }

    .text-login a:hover {
        text-decoration: underline;
    }


    /* end login */
</style>

<body>
<%@ include file="Header.jsp" %>
<div class="main">
    <div class="container">
        <h2>Đăng Ký</h2>


        <form action = "${pageContext.request.contextPath}/register" method="post" class="infor">
            <div class="tt-chitiet" style="grid-column: span 2;">
                <label for="Ho">Họ và Tên:</label>
                <input type="text" id="Ho" name="fullName" placeholder="Họ và Tên" required value="${param.fullName}">

            </div>

            <div class="tt-chitiet">
                <label for="Email">Email cá nhân:</label>
                <input type="email" id="email" name="email" placeholder="Email cá nhân" required value="${param.email}"
                       pattern="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$"
                       title="Email phải đúng định dạng, ví dụ: ten@gmail.com">
                <span id="email-error"
                      style="color: #FFD700; font-size: 14px;"></span>
            </div>

            <div class="tt-chitiet">
                <label for="sdt">Số điện thoại:</label>
                <input type="tel" id="sdt" name="phoneNumber" required maxlength = "10" placeholder="Số điện thoại"
                       value="${param.phoneNumber}" pattern = "^0\d{9}$"
                       title = "Số điện thoại phải gồm 10 chữ số và bắt đầu bằng số 0">
                <span id="phonerror" style="color: #FFD700; font-size: 14px;"></span>

            </div>

            <div class="tt-chitiet"
                 style="grid-column: span 2; position: relative;">
                <label for="Mk">Mật khẩu:</label>
                <input type="password" id="Mk" name="password"
                       placeholder="Mật khẩu" required
                       minlength="8"
                       pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{8,}$"
                       title="Mật khẩu có ít nhất 8 kí tự gồm chữ hoa, chữ thường và các kí tự đặc biệt."  >
                <span id="pwerror" style="color: #FFD700; font-size: 14px;"></span>

                <i class="hide-display" id="togglePassword"
                   style="position:absolute; right:10px; top:38px; cursor:pointer;"></i>
                <c:if test="${not empty error}">
                    <p style="color: red;padding : 10px" >${error}</p>
                </c:if>
            </div>

            <button type="submit" class="btn-DangKy"
                    style="grid-column: span 2;">
                Đăng ký
            </button>
        </form>

        <p class="text-login">
            Bạn đã có tài khoản
            <a href="Login.jsp">Đăng nhập tại đây</a>
        </p>

        <div class="btn-dang-nhap-tt">
            <a class="btn-gg"
               href="${pageContext.request.contextPath}/oauth2/google"
               style="text-align: center">
                <i class="fa-brands fa-google"></i>
                Google
            </a>
        </div>

    </div>
</div>
<%@ include file="Footer.jsp" %>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const params = new URLSearchParams(window.location.search);

        if (params.get("success") === "true") {
            Swal.fire({
                icon: 'success',
                title: 'Đăng ký thành công!',
                text: 'Vui lòng kiểm tra email và bấm link để kích hoạt tài khoản.',
                showConfirmButton: false,
                timer: 3000
            }).then(() => {
                window.location.href = 'login';
            });
        }

    });
</script>
<%--check sdt--%>
<script>
    const phoneInput = document.getElementById("sdt");
    const phoneError = document.getElementById("phonerror");

    phoneInput.addEventListener("input", () => {

        phoneInput.value = phoneInput.value.replace(/\D/g, "").slice(0, 10);

        const ok = /^0\d{9}$/.test(phoneInput.value);
        phoneError.textContent = ok || phoneInput.value.length === 0
            ? ""
            : "SĐT không hợp lệ (vd: 0912345678)";
    });
</script>
<%--check mk--%>

<script>
    const passInput = document.getElementById("Mk");
    const passError = document.getElementById("pwerror");

    passInput.addEventListener("input", () => {
        const pass = passInput.value;

        const okLength = pass.length >= 8;
        const okLower  = /[a-z]/.test(pass);
        const okUpper  = /[A-Z]/.test(pass);
        const okSpecial = /[^A-Za-z0-9]/.test(pass);

        if (okLength && okLower && okUpper && okSpecial) {
            passError.textContent = "";
        } else {
            passError.textContent =
                "Mật khẩu có ít nhất 8 ký tự, có chữ hoa, chữ thường và ký tự đặc biệt.";
        }
    });
</script>
<%--check loi mail--%>
<script>
    const emailInput = document.getElementById("email");
    const emailError = document.getElementById("email-error");

    const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;

    emailInput.addEventListener("input", () => {
        const email = emailInput.value.trim();

        if (email.length === 0) {

            emailError.textContent = "";
            return;
        }

        if (emailRegex.test(email)) {
            emailError.textContent = "";
        } else {
            emailError.textContent = "Email không hợp lệ (vd: ten@gmail.com)";
        }
    });
</script>

</body>

</html>