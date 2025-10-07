<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/partials/navbar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/SignIn.css">

</head>
<body>
    <jsp:include page="/partials/navbar.jsp"/>
    <div class="card">
      <h2>Đăng nhập</h2>

      <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
      %>
        <div class="error"><%= error %></div>
      <% } %>

      <form action="<%= request.getContextPath() %>/signin" method="post" accept-charset="UTF-8">
        <div class="row">
          <label>Username</label>
          <input type="text" name="username" required>
        </div>
        <div class="row">
          <label>Mật khẩu</label>
          <input type="password" name="password" required>
        </div>
        <div class="actions">
          <button type="submit">Đăng nhập</button>
          <a href="<%= request.getContextPath() %>/signup">Tạo tài khoản mới</a>
        </div>
      </form>
    </div>
</body>
</html>
