<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Quản Lý Sách</title>
<link rel="stylesheet" href="styles/main.css">
<link
	href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap"
	rel="stylesheet">
</head>
<body>
	<div class="layout">
		<!-- Sidebar -->
		<aside class="sidebar">
			<h3 class="logo">Trang Quản Lý</h3>
			<ul class="menu">
				<li class="active">Quản Lý Sách</li>
				<li>Quản Lý Đơn Hàng</li>
				<li>Cài Đặt</li>
			</ul>
		</aside>

		<!-- Main -->
		<main class="content">
			<div class="content-header">
				<h2>Quản Lý Sách</h2>
				<div class="header-actions">
					<input type="text" placeholder="Tìm kiếm..." class="search-box" />
					<button id="openPopup" class="add-btn">+ Thêm Sách</button>
				</div>
			</div>

			<!-- KHÔNG HIỂN THỊ ALERT NỮA -->
			<!-- <c:if test="${not empty message}">
				<div class="alert">${message}</div>
			</c:if> -->

			<div class="table-container">
				<div class="table-wrapper">
					<table class="book-table">
						<thead>
							<tr>
								<th>Mã Sách (ISBN)</th>
								<th>Tên Sách</th>
								<th>Giá Bán</th>
								<th>Số Lượng</th>
								<th>Ngày Xuất Bản</th>
								<th>Lần Tái Bản</th>
								<th>Định Dạng</th>
								<th>Kích Thước (cm)</th>
								<th>Trọng Lượng (kg)</th>
								<th>Độ Tuổi</th>
								<th>Ngôn Ngữ</th>
								<th>Tác Giả</th>
								<th>Nhà Xuất Bản</th>
								<th>Tình Trạng</th>
								<th>Giá Nhập</th>
								<th>Ngày Nhập</th>
								<th>Mô Tả</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="b" items="${books}">
								<tr>
									<td>${b.isbn}</td>
									<td>${b.title}</td>
									<td><fmt:formatNumber value="${b.sellingPrice}"
											type="number" groupingUsed="true" /></td>
									<td>${b.quantity}</td>
									<td>${b.publicationDate}</td>
									<td>${b.edition}</td>
									<td>${b.formatName}</td>
									<td>${b.height}×${b.width}</td>
									<td>${b.weight}</td>
									<td>${b.ageLabel}</td>
									<td>${b.languageName}</td>
									<td>${b.author}</td>
									<td>${b.publisher}</td>
									<td>${b.stockStatus}</td>
									<td><fmt:formatNumber value="${b.importPrice}"
											type="number" groupingUsed="true" /></td>
									<td>${b.openDate}</td>
									<td class="desc">${b.description}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<!-- Popup thêm sách -->
				<div class="popup" id="popupForm">
					<div class="popup-content">
						<h3>Thêm Sách Mới</h3>
						<form method="post" action="books">
							<label>Mã ISBN:</label> <input type="text" name="isbn" required>

							<label>Tên Sách:</label> <input type="text" name="title" required>

							<label>Giá Bán:</label> <input type="number" step="0.01"
								name="sellingPrice" required> <label>Số Lượng:</label> <input
								type="number" name="quantity" required> <label>Ngày
								Xuất Bản:</label> <input type="date" name="publicationDate" required>

							<label>Lần Tái Bản:</label> <input type="number" name="edition"
								required> <label>Định Dạng:</label> <select
								name="format_id" required>
								<option value="1">EBook</option>
								<option value="2">PrintedBook</option>
							</select> <label>Kích Thước (cm):</label> <input type="text" name="size"
								placeholder="VD: 21.0 x 15.0" required> <label>Trọng
								Lượng (kg):</label> <input type="number" step="0.01" name="weight"
								required> <label>Độ Tuổi:</label> <select
								name="recommendAge_id" required>
								<option value="1">ALL_AGES</option>
								<option value="2">UNDER_3_YEARS_OLD</option>
								<option value="3">3_TO_5_YEARS_OLD</option>
								<option value="4">6_TO_15_YEARS_OLD</option>
								<option value="5">16_TO_18_YEARS_OLD</option>
								<option value="6">OVER_18_YEARS_OLD</option>
							</select> <label>Ngôn Ngữ:</label> <select name="language_id" required>
								<option value="1">VIETNAMESE</option>
								<option value="2">ENGLISH</option>
							</select> <label>Tác Giả:</label> <input type="text" name="author_name"
								placeholder="Nhập tên tác giả" required> <label>Nhà
								Xuất Bản:</label> <input type="text" name="publisher_name"
								placeholder="Nhập tên nhà xuất bản" required> <label>Giá
								Nhập (VNĐ):</label> <input type="number" step="0.01" name="importPrice"
								required> <label>Mô Tả:</label>
							<textarea name="description" placeholder="Nhập mô tả..."></textarea>

							<div class="popup-buttons">
								<button type="button" id="closePopup" class="btn-close">Đóng</button>
								<button type="submit" class="btn-submit">Lưu</button>
							</div>
						</form>
					</div>
				</div>

				<!-- JS mở/đóng popup -->
				<script>
					document.getElementById("openPopup").onclick = function() {
						document.getElementById("popupForm").style.display = "flex";
					};
					document.getElementById("closePopup").onclick = function() {
						document.getElementById("popupForm").style.display = "none";
					};
				</script>

				<!-- Phân trang -->
				<c:set var="pageSize" value="8" />
				<c:set var="totalBooks" value="${requestScope.totalBooks}" />

				<div class="pagination">
					<span> Hiển thị ${(page - 1) * pageSize + 1} – ${page * pageSize > totalBooks ? totalBooks : page * pageSize}
						/ tổng ${totalBooks} </span>

					<c:if test="${totalPages > 1}">
						<div class="page-nav">
							<c:if test="${page > 1}">
								<a href="books?page=${page - 1}">‹</a>
							</c:if>

							<c:forEach begin="1" end="${totalPages}" var="p">
								<a href="books?page=${p}" class="${p == page ? 'active' : ''}">${p}</a>
							</c:forEach>

							<c:if test="${page < totalPages}">
								<a href="books?page=${page + 1}">›</a>
							</c:if>
						</div>
					</c:if>
				</div>


			</div>
		</main>
	</div>
</body>
</html>
