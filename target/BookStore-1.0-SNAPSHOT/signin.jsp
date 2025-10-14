<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Đăng nhập</title>
  <link rel="stylesheet" href="<c:url value='/partials/navbar.css'/>">
  <link rel="stylesheet" href="<c:url value='/styles/auth.css'/>">
</head>
<body>
  <%@ include file="/partials/navbar.jsp" %>

  <div class="auth-wrap">
    <div class="auth-card">
      <img class="auth-logo" src="<c:url value='/img/logo.png'/>" alt="Logo">
      <h1 class="auth-title">Đăng nhập tài khoản</h1>
      <p class="auth-sub">Chào mừng bạn quay lại! Vui lòng nhập thông tin bên dưới.</p>

      <c:if test="${not empty error}"><div class="err">${error}</div></c:if>

      <form class="auth-form" method="post" action="${pageContext.request.contextPath}/signin">
        <div>
          <label class="label" for="username">Email hoặc Tên đăng nhập</label>
          <input class="input" id="username" name="username" autocomplete="username" required>
        </div>
        <div>
          <label class="label" for="password">Mật khẩu</label>
          <input class="input" id="password" name="password" type="password" autocomplete="current-password" required>
        </div>

        <button class="btn" type="submit">Đăng nhập</button>
      </form>

      <div class="hr"></div>
      <p class="auth-foot">Chưa có tài khoản?
        <a href="<c:url value='/signup'/>">Đăng ký ngay</a>
      </p>
    </div>
  </div>
</body>
</html>
