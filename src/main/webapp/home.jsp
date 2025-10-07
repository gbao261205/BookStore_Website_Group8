<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Trang chủ</title>
  <link rel="stylesheet" href="<c:url value='/partials/navbar.css'/>">
</head>
<body>
  <jsp:include page="/partials/navbar.jsp"/>

  <main class="page">
    <c:choose>
      <c:when test="${not empty sessionScope.account}">
        <h2>
          Xin chào
          <c:choose>
            <c:when test="${not empty sessionScope.account.user.fullName}">
              ${sessionScope.account.user.fullName}
            </c:when>
            <c:otherwise>
              ${sessionScope.account.username}
            </c:otherwise>
          </c:choose>
          !
        </h2>
        <p>Đây là trang chủ demo sau khi đăng nhập.</p>
      </c:when>
      <c:otherwise>
        <h2>Đây là trang chủ của BOOKSTORE.</h2>
      </c:otherwise>
    </c:choose>
  </main>
</body>
</html>
