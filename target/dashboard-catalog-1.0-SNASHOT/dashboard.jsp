<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="style_dashboard/dashboard.css">
</head>
<body>
<div class="dashboard-container">

    <!-- SIDEBAR -->
    <div class="sidebar">
        <div class="sidebar-nav">
            <ul>
                <li class="active"><a href="dashboard">📊 Báo cáo chi tiết</a></li>
                <li><a href="transactionList">📦 Lịch sử giao dịch</a></li>
                <li><a href="books">📚 Danh mục sách</a></li>
                <li><a href="order">🚚 Fulfillment</a></li>
                <li><a href="logout">🚪 Đăng xuất</a></li>
            </ul>
        </div>
    </div>

    <!-- MAIN -->
    <div class="main-content">
        <div class="top-bar">
            <h2>Bảng điều khiển quản trị</h2>
            <span class="language">🌐 Tiếng Việt</span>
        </div>

        <!-- ALERT -->
        <c:if test="${lowStock > 0}">
            <div class="alert-banner" style="background-color:#f8d7da;color:#721c24;padding:10px;margin:10px 0;">
                ⚠️ Có <strong>${lowStock}</strong> sản phẩm sắp hết hàng! 
                <a href="lowstock" style="color:#004085;text-decoration:underline;">Xem ngay</a>
            </div>
        </c:if>

        <!-- KPI -->
        <div class="metrics-cards">
            <div class="metrics-row">
                <a href="books" class="metric-card-link">
                    <div class="metric-card">
                        <span class="metric-title">Tổng số sách</span>
                        <div class="metric-count">${totalBooks}</div>
                    </div>
                </a>

                <a href="catalog" class="metric-card-link">
                    <div class="metric-card">
                        <span class="metric-title">Sách còn hàng</span>
                        <div class="metric-count">${inStock}</div>
                    </div>
                </a>

                <a href="lowstock" class="metric-card-link">
                    <div class="metric-card">
                        <span class="metric-title">Sách hết hàng</span>
                        <div class="metric-count">${outOfStock}</div>
                    </div>
                </a>
            </div>

            <div class="metrics-row">
                <a href="revenue" class="metric-card-link">
                    <div class="metric-card">
                        <span class="metric-title">Tổng doanh thu</span>
                        <div class="metric-count">
                            <fmt:formatNumber value="${totalRevenue}" type="currency" currencySymbol="₫"/>
                        </div>
                    </div>
                </a>

                <a href="order" class="metric-card-link">
                    <div class="metric-card">
                        <span class="metric-title">Tổng số đơn</span>
                        <div class="metric-count">${totalOrders}</div>
                    </div>
                </a>

                <a href="customerList" class="metric-card-link">
                    <div class="metric-card">
                        <span class="metric-title">Số lượng khách hàng</span>
                        <div class="metric-count">${totalCustomers}</div>
                    </div>
                </a>
            </div>
        </div>

        <!-- SHORTCUTS -->
        <div class="stats-card">
            <h3>🚀 Lối tắt nhanh</h3>
            <div class="shortcut-row">
                <a href="catalog" class="shortcut-btn">📚 Quản lý Catalog</a>
                <a href="fulfillment" class="shortcut-btn">🚚 Quản lý Fulfillment</a>
            </div>
        </div>

    </div>
</div>
</body>
</html>
