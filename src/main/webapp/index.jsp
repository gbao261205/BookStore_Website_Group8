<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="vi"><head>
<meta charset="UTF-8"><title>Home</title>
<link rel="stylesheet" href="<c:url value='/partials/navbar.css'/>">
</head><body>
  <%@ include file="/partials/navbar.jsp" %>
  <div style="padding:24px">
    <h2>Xin chào!</h2>
    <c:if test="${not empty sessionScope.currentUser}">
      <p>Đã đăng nhập: <b>${sessionScope.currentUser.fullName}</b></p>
    </c:if>
  </div>
</body></html>
