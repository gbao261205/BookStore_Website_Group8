<%-- /main/webapp/partials/navbar.jsp --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<header class="navbar">
  <!-- Logo -->
  <div class="logo">
    <a href="<c:url value='/'/>" class="image-box" aria-label="Trang chủ">
      <img src="<c:url value='img/logo.png'/>" alt="BookStore">
    </a>
  </div>

  <!-- Search -->
  <form class="nav-search" action="<c:url value='/search'/>" method="get">
    <input type="text" name="q" placeholder="Tìm sách, tác giả...">
    <button type="submit">Tìm</button>
  </form>

  <!-- Menu -->
  <nav class="menu">
    <a class="nav-link" href="<c:url value='/'/>">Trang chủ</a>
    <a class="nav-link" href="<c:url value='/about'/>">Giới thiệu</a>
    <a class="nav-link" href="<c:url value='/category'/>">Danh mục</a>
    <a class="nav-link" href="<c:url value='/help'/>">Liên hệ</a>
  </nav>

  <!-- Account dropdown -->
  <div class="dropdown">
    <c:choose>
      <c:when test="${not empty sessionScope.currentUser}">
        <a href="#" class="account" aria-haspopup="true" aria-expanded="false">
          ${sessionScope.currentUser.fullName}
        </a>
        <div class="dropdown-content" role="menu">
          <a href="<c:url value='/profile'/>">Hồ sơ</a>
          <a href="<c:url value='/orders'/>">Đơn hàng</a>
          <c:if test="${sessionScope.currentUser.admin}">
            <div class="dropdown-divider"></div>
            <a href="<c:url value='/admin/dashboard'/>">Quản trị</a>
          </c:if>
          <div class="dropdown-divider"></div>
          <a href="<c:url value='/logout'/>">Đăng xuất</a>
        </div>
      </c:when>
      <c:otherwise>
        <a href="#" class="account" aria-haspopup="true" aria-expanded="false">Tài khoản</a>
        <div class="dropdown-content" role="menu">
          <a href="<c:url value='/signin'/>">Đăng nhập</a>
          <a href="<c:url value='/signup'/>">Đăng ký</a>
        </div>
      </c:otherwise>
    </c:choose>
  </div>
</header>
<link rel="stylesheet" href="<c:url value='/partials/navbar.css'/>">
