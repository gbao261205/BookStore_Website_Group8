<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Đăng ký</title>
  <link rel="stylesheet" href="<c:url value='/partials/navbar.css'/>">
  <link rel="stylesheet" href="<c:url value='/styles/auth.css'/>">
</head>
<body>
  <%@ include file="/partials/navbar.jsp" %>

  <div class="auth-wrap">
    <div class="auth-card" style="max-width:720px">
        <img class="auth-logo" src="<c:url value='/img/logo.png'/>" alt="Logo">
        <h1 class="auth-title">Tạo tài khoản</h1>

        <c:if test="${not empty error}"><div class="err">${error}</div></c:if>

        <form class="auth-form" method="post" action="${pageContext.request.contextPath}/signup" id="signupForm" novalidate>
        <!-- Tài khoản -->
            <div class="grid grid-2">
                <div>
                  <label class="label" for="username">Tên đăng nhập *</label>
                  <input class="input" id="username" name="username" required minlength="4" maxlength="40">
                </div>
                <div>
                  <label class="label" for="email">Email *</label>
                  <input class="input" id="email" name="email" type="email" required>
                </div>
                <div>
                  <label class="label" for="password">Mật khẩu *</label>
                  <input class="input" id="password" name="password" type="password" required minlength="6">
                </div>
                <div>
                  <label class="label" for="confirm">Nhập lại mật khẩu *</label>
                  <input class="input" id="confirm" name="confirm" type="password" required minlength="6">
                </div>
            </div>

        <!-- Thông tin cá nhân -->
        <div class="grid grid-2">
          <div>
            <label class="label" for="fullName">Họ và tên *</label>
            <input class="input" id="fullName" name="fullName" required>
          </div>
          <div>
            <label class="label" for="phone">Số điện thoại</label>
            <input class="input" id="phone" name="phone" pattern="^[0-9]{9,15}$" placeholder="0912345678">
          </div>
          <div>
            <label class="label" for="dob">Ngày sinh</label>
            <input class="input" id="dob" name="dob" type="date">
          </div>
          <div>
            <label class="label" for="gender">Giới tính</label>
            <select class="input" id="gender" name="genderCode">
              <option value="">— Chọn —</option>
              <option value="MALE">Nam</option>
              <option value="FEMALE">Nữ</option>
            </select>
          </div>
          <div class="grid-col-2">
            <label class="label" for="avatar">Ảnh đại diện (URL)</label>
            <input class="input" id="avatar" name="avatar">
          </div>
        </div>

        <!-- Địa chỉ -->
        <div class="grid grid-2">
          <div>
            <label class="label" for="street">Số nhà, đường</label>
            <input class="input" id="street" name="street" placeholder="12 Nguyễn Huệ">
          </div>
          <div>
            <label class="label" for="ward">Phường/Xã</label>
            <input class="input" id="ward" name="ward">
          </div>
          <div>
            <label class="label" for="district">Quận/Huyện</label>
            <input class="input" id="district" name="district">
          </div>
          <div>
            <label class="label" for="city">Thành phố</label>
            <input class="input" id="city" name="city">
          </div>
          <div>
            <label class="label" for="province">Tỉnh/TP</label>
            <input class="input" id="province" name="province">
          </div>
          <div>
            <label class="label" for="zipcode">Mã bưu chính</label>
            <input class="input" id="zipcode" name="zipcode">
          </div>
        </div>

        <button class="btn" type="submit">Đăng ký</button>

        <div class="hr"></div>
        <p class="auth-foot">Đã có tài khoản?
          <a href="<c:url value='/signin'/>">Đăng nhập tại đây</a>
        </p>
      </form>
    </div>
  </div>

  <script>
    // Kiểm tra trùng mật khẩu (client-side)
    const pwd=document.getElementById('password'), cf=document.getElementById('confirm');
    function check(){ if(pwd.value && cf.value && pwd.value!==cf.value){ cf.setCustomValidity('Không khớp'); } else { cf.setCustomValidity(''); } }
    pwd.addEventListener('input',check); cf.addEventListener('input',check);
    document.getElementById('signupForm').addEventListener('submit',e=>{ if(!e.target.checkValidity()){ e.preventDefault(); e.target.reportValidity(); } });
  </script>
</body>
</html>
