<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sách sắp hết hàng</title>
    <link rel="stylesheet" href="style_catalog/catalog.css">
</head>
<body>
    <div class="container">
        <header class="header">
            <div class="header-content">
                <div class="logo-placeholder">⚠️ SẮP HẾT HÀNG</div>
                <div class="title-section">
                    <h1>Danh sách sách sắp hết hàng</h1>
                </div>
            </div>
        </header>

        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Tiêu đề</th>
                        <th>Giá</th>
                        <th>Số lượng còn</th>
                        <th>Trạng thái</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="b" items="${lowStockBooks}">
                        <tr>
                            <td>${b.id}</td>
                            <td>${b.title}</td>
                            <td>${b.sellingPrice}₫</td>
                            <td>${b.quantity}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${b.active}">
                                        <span class="status available">Hiển thị</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="status out-of-stock">Ẩn</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="footer">
            <a href="dashboard">← Quay lại Bảng điều khiển</a>
        </div>
    </div>
</body>
</html>
