<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<header class="navbar">
  <div class="logo">
    <a href="<c:url value='/'/>" class="image-box" aria-label="Trang chủ">
      <img src="<c:url value='/img/logo.png'/>" alt="Logo Book Store" class="logo_book"/>
    </a>
  </div>

  <nav class="page" aria-label="Chính">
    <a href="<c:url value='/'/>" class="nav-link">Trang chủ</a>
    <a href="<c:url value='/about'/>" class="nav-link">Giới thiệu</a>
    <a href="<c:url value='/categories'/>" class="nav-link">Danh mục</a>
    <a href="<c:url value='/support'/>" class="nav-link">Hỗ trợ</a>

    <div class="dropdown notify">
      <a href="#" class="nav-link" aria-haspopup="true" aria-expanded="false">Thông báo</a>
      <div class="popover" role="dialog" aria-label="Thông báo">
        <div class="popover-header">
          <div class="avatar"></div>
          <div>Đăng nhập để xem Thông báo</div>
        </div>
        <div class="popover-actions">
          <a href="<c:url value='/register'/>">Đăng ký</a>
          <a href="<c:url value='/login'/>">Đăng nhập</a>
        </div>
      </div>
    </div>

    <div class="dropdown">
      <a href="<c:url value='/login'/>" class="nav-link account" aria-haspopup="true" aria-expanded="false">Tài khoản ▾</a>
      <div class="dropdown-content" role="menu">
        <a href="<c:url value='/login'/>">Đăng nhập</a>
        <a href="<c:url value='/register'/>">Đăng ký</a>
      </div>
    </div>
  </nav>
</header>
