<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<header class="navbar">
  <div class="logo">
    <a href="<c:url value='/home.jsp'/>" class="image-box" aria-label="Trang chủ">
      <img src="<c:url value='/img/logo.png'/>" alt="Logo Book Store" class="logo_book"/>
    </a>
  </div>

  <nav class="page" aria-label="Chính">
    <a href="<c:url value='/home.jsp'/>" class="nav-link">Trang chủ</a>
    <a href="<c:url value='/about'/>" class="nav-link">Giới thiệu</a>
    <a href="<c:url value='/categories'/>" class="nav-link">Danh mục</a>
    <a href="<c:url value='/support'/>" class="nav-link">Hỗ trợ</a>
    <a href="<c:url value='/notice'/>" class="nav-link">Thông báo</a>

    <c:choose>
      <c:when test="${not empty sessionScope.account}">
        <div class="dropdown">
          <a href="#" class="nav-link account">
            <c:choose>
              <c:when test="${not empty sessionScope.account.user.fullName}">
                ${sessionScope.account.user.fullName}
              </c:when>
              <c:otherwise>${sessionScope.account.username}</c:otherwise>
            </c:choose>
            &nbsp;▾
          </a>
          <div class="dropdown-content" role="menu">
            <a href="<c:url value='/cart'/>">Giỏ hàng</a>
            <a href="<c:url value='/orders'/>">Đơn hàng</a>
            <div class="dropdown-divider"></div>
            <a href="<c:url value='/logout'/>">Đăng xuất</a>
          </div>
        </div>
      </c:when>

      <c:otherwise>
        <div class="dropdown">
          <a href="<c:url value='/signin'/>" class="nav-link account">Tài khoản ▾</a>
          <div class="dropdown-content" role="menu">
            <a href="<c:url value='/signin'/>">Đăng nhập</a>
            <a href="<c:url value='/signup'/>">Đăng ký</a>
          </div>
        </div>
      </c:otherwise>
    </c:choose>
  </nav>
</header>
