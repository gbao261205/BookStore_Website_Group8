<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh mục sách</title>
    <link rel="stylesheet" href="style_catalog/catalog.css">
</head>
<body>
    <div class="container">
        <header class="header">
            <div class="header-content">
                <div class="logo-placeholder">📚 SÁCH</div>
                <div class="title-section">
                    <h1>Danh mục - Kho sách</h1>
                </div>
                <div class="search-section">
                    <input type="text" id="searchInput" placeholder="Tìm sách theo tiêu đề hoặc danh mục...">
                </div>
            </div>
        </header>

        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Tiêu đề</th>
                        <th>Danh mục</th>
                        <th>Giá</th>
                        <th>Trạng thái</th>
                    </tr>
                </thead>
                <tbody id="bookTableBody">
                    <c:forEach var="b" items="${books}">
                        <tr>
                            <td>${b.id}</td>
                            <td>${b.title}</td>
                            <td>${b.category}</td>
                            <td>${b.sellingPrice}₫</td>
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

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const searchInput = document.getElementById('searchInput');
            const tableBody = document.getElementById('bookTableBody');
            const rows = tableBody.getElementsByTagName('tr');
            searchInput.addEventListener('keyup', function() {
                const filter = searchInput.value.toLowerCase();
                for (let i = 0; i < rows.length; i++) {
                    let titleCell = rows[i].getElementsByTagName('td')[1];
                    let categoryCell = rows[i].getElementsByTagName('td')[2];
                    if (titleCell || categoryCell) {
                        let textValue = (titleCell.textContent || titleCell.innerText) + " " + (categoryCell.textContent || categoryCell.innerText);
                        rows[i].style.display = textValue.toLowerCase().indexOf(filter) > -1 ? "" : "none";
                    }
                }
            });
        });
    </script>
</body>
</html>
