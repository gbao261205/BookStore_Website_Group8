<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Hồ sơ của tôi</title>
  <link rel="stylesheet" href="<c:url value='/partials/navbar.css'/>">
  <link rel="stylesheet" href="<c:url value='/styles/profile.css'/>">
</head>
<body>
  <jsp:include page="/partials/navbar.jsp"/>

  <main class="profile-page">
    <div class="profile-card">
      <div class="title">Hồ Sơ Của Tôi</div>
      <div class="subtitle">Quản lý thông tin hồ sơ để bảo mật tài khoản</div>

      <c:if test="${not empty error}"><div class="msg-error">${error}</div></c:if>
      <c:if test="${not empty success}"><div class="msg-ok">${success}</div></c:if>

      <form method="post" action="<c:url value='/profile'/>" accept-charset="UTF-8">
        <div class="profile-row">
          <div class="label">Tên đăng nhập</div>
          <div class="field"><div class="muted">${sessionScope.account.username}</div></div>
        </div>

        <div class="profile-row">
          <div class="label">Tên</div>
          <div class="field"><input type="text" name="fullName" value="${sessionScope.account.user.fullName}"></div>
        </div>

        <div class="profile-row">
          <div class="label">Email</div>
          <div class="field"><input type="email" name="email" value="${sessionScope.account.user.emailAddress}"></div>
        </div>

        <div class="profile-row">
          <div class="label">Số điện thoại</div>
          <div class="field"><input type="text" name="phone" value="${sessionScope.account.user.phoneNumber}"></div>
        </div>

        <div class="profile-row">
          <div class="label">Giới tính</div>
          <div class="field">
            <label><input type="radio" name="gender" value="MALE"   <c:if test="${sessionScope.account.user.gender == 'MALE'}">checked</c:if>> Nam</label>
            <label><input type="radio" name="gender" value="FEMALE" <c:if test="${sessionScope.account.user.gender == 'FEMALE'}">checked</c:if>> Nữ</label>
            <label><input type="radio" name="gender" value="UNKNOWN" <c:if test="${sessionScope.account.user.gender == 'UNKNOWN' || empty sessionScope.account.user.gender}">checked</c:if>> Khác</label>
          </div>
        </div>

        <div class="profile-row">
          <div class="label">Ngày sinh</div>
          <div class="field"><input type="date" name="dateOfBirth" value="<c:out value='${sessionScope.account.user.dateOfBirth}'/>"></div>
        </div>

        <div class="actions"><button class="btn" type="submit">Lưu</button></div>
      </form>
    </div>
  </main>
</body>
</html>
