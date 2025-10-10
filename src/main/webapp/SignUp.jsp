<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng ký</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/SignUp.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/partials/navbar.css">
    
</head>
<body>
    <jsp:include page="/partials/navbar.jsp"/>
    <div class="card">
      <h2>Đăng ký tài khoản</h2>

      <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
      %>
        <div class="error"><%= error %></div>
      <% } %>

      <form action="<%= request.getContextPath() %>/signup" method="post" accept-charset="UTF-8">
        <div class="row">
          <div style="flex:1">
            <label>Họ và tên</label>
            <input type="text" name="fullName" required>
          </div>
        </div>

        <div class="row">
          <div style="flex:1">
            <label>Email</label>
            <input type="email" name="email" required>
          </div>
        </div>

        <div class="row">
          <div style="flex:1">
            <label>Ngày sinh</label>
            <input type="date" name="dateOfBirth">
          </div>
          <div style="flex:1">
            <label>Giới tính</label>
            <select name="gender">
              <option value="UNKNOWN">Không rõ</option>
              <option value="MALE">Nam</option>
              <option value="FEMALE">Nữ</option>
            </select>
          </div>
        </div>

        <div class="row">
          <div style="flex:1">
            <label>Số điện thoại</label>
            <input type="tel" name="phone" pattern="0\d{9}" maxlength="10" required title="Số điện thoại phải có 10 chữ số và bắt đầu bằng 0">
          </div>
        </div>

        <div class="row">
          <div style="flex:1">
            <label>Username</label>
            <input type="text" name="username" required>
          </div>
        </div>

        <div class="row">
          <div style="flex:1">
            <label>Mật khẩu</label>
            <input type="password" name="password" required minlength="4">
          </div>
          <div style="flex:1">
            <label>Nhập lại mật khẩu</label>
            <input type="password" name="confirmPassword" required minlength="4">
          </div>
        </div>

        <div class="actions">
          <button type="submit">Đăng ký</button>
          <a href="<%= request.getContextPath() %>/signin">Đã có tài khoản? Đăng nhập</a>
        </div>
      </form>
    </div>
</body>
</html>
