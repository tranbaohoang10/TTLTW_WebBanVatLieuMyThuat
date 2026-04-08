<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quên mật khẩu</title>
    <link rel="stylesheet" href="./assets/css/style.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
</head>
<style>
    .container {
        width: 1200px;
        margin: auto;
    }
    .main {
        margin: 0;
        padding: 0;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
        background: linear-gradient(to left, #2659F3, #09BCE4);


    }

    .container {
        background-color: rgba(0, 0, 0, 0.4);
        padding: 45px 45px;
        box-sizing: border-box;
        overflow: hidden;
        position: relative;
        transition: 0.5s ease;
        flex-wrap: wrap;
        border-radius: 10px;
        width: 460px;
        height: 480px;

    }

    h2 {
        color: white;
        text-align: center;
        text-transform: uppercase;
        margin-bottom: 10px;
        font-weight: 700;

    }

    p {
        color: white;
        text-align: center;
        margin-bottom: 20px;
    }

    .form-email .form-1 input {
        width: 100%;
        border: none;
        padding: 8px;
        border-bottom: 1px solid #ccc;
        outline: none;
        font-size: 14px;
        background: none;
        color: white;
        border-radius: 8px;
        padding: 10px;
        transition: border-color 0.3s;
    }

    .form-email .form-1 input::placeholder {
        color: #ccc5c5;
    }

    .forgot-password a {
        color: #ffc107;
        text-decoration: none;

    }

    .forgot-password a:hover {
        color: #6C785B;
    }

    .btn-lay-mk {
        width: 100%;
        background-color: #F5DF4D;
        padding: 10px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        margin-bottom: 5px;
        margin-top: 15px;
        color: #343A40;
        font-size: 1rem;
        font-weight: bold;
    }

    .btn-lay-mk:hover {
        transform: scale(1.05);
    }

    .quay-lai-dang-nhap {
        text-align: center;
        margin-bottom: 15px;
    }

    .link-quay-lai {
        color: white;
        text-decoration: none;
        text-align: center;
    }

    .link-quay-lai:hover {
        color: #6C785B;
    }

    .link-dki {
        color: #FFC107;
        text-decoration: none;
    }

    .link-dki:hover {
        color: #6C785B;
    }


    .btn-login-gg {
        display: flex;
        justify-content: space-between;
    }

    .btn-gg {
        background-color: #DE3F32;
        color: white;
        border: none;
        border-radius: 5px;
        padding: 10px;
        padding-left: 50px;
        padding-right: 50px;
        margin-top: 10px;
        cursor: pointer;
        width: 100%;
    }


    .btn-login-gg i {
        margin-right: 8px;
    }

    p {
        color: white;
    }

    .chuataikhoan {
        color: white;
        text-align: center;
    }


</style>

<body>
<%@ include file="Header.jsp" %>
<div class="main">
    <div class="container">
        <h2>ĐẶT LẠI MẬT KHẨU</h2>
        <p>Chúng tôi sẽ gửi cho bạn một email để kích hoạt việc đặt lại
            mật khẩu</p>
        <form action = "${pageContext.request.contextPath}/forgotpassword" class="form-email" method="post">

            <div class="form-1">
                <input type="email" name = "email" placeholder="Email" id="email" required
                       pattern="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$"
                       title="Email phải đúng định dạng, ví dụ: ten@gmail.com">
                <span id="email-error"
                      style="color: #FFD700; font-size: 14px;"></span>

            </div>

            <p style="color: red; margin-top: 10px">${error}</p>
            <p style="color: #00ff00; margin-top: 10px">${success}</p>

            <button type="submit" class="btn-lay-mk">Lấy lại mật khẩu</button>

            <div class="quay-lai-dang-nhap">
                <a href="Login.jsp" class="link-quay-lai">Quay lại
                </a>
            </div>

            <div class="chuataikhoan">
                Bạn chưa có tài khoản <a href="${pageContext.request.contextPath}/register"
                                         class="link-dki">Đăng ký tại đây</a>
            </div>

            <div class="btn-login-gg">

                <a class="btn-gg" href="${pageContext.request.contextPath}/oauth2/google"
                   style="text-align: center"><i
                        class="fa-brands fa-google"></i>
                    Google
                </a>

            </div>

        </form>
    </div>
</div>


<%@ include file="Footer.jsp" %>


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