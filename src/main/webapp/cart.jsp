<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="murach.business.*" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Giỏ hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
</head>
<body>

<%
    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null) {
        cart = new Cart();
        session.setAttribute("cart", cart); // đảm bảo ghi lại vào session
    }
    List<LineItem> items = cart.getItems();
    double subtotal = 0;
    for (LineItem it : items) subtotal += it.getTotal();
        
%>

<fmt:setLocale value="vi_VN" />


<div class="cart-layout">
    <!-- Cột trái: danh sách sản phẩm -->
    <div class="cart-main">
        <h1 class="page-title">GIỎ HÀNG (<%= items.size() %> sản phẩm)</h1>

        <div class="cart-head">
            <label class="check-all">
                <input type="checkbox" checked>
                Chọn tất cả (<%= items.size() %> sản phẩm)
            </label>
            <div class="col qty">Số lượng</div>
            <div class="col total">Thành tiền</div>
        </div>

        <% for (LineItem item : items) {
            Product p = item.getProduct();
            String pid = p.getCode().replaceAll("[^a-zA-Z0-9_-]", "_");
        %>

        <div class="cart-row">
            <div class="item-info">
                <div class="title"><%= p.getDescription() %></div>
                <div class="price">
                    Đơn giá:
                    <fmt:formatNumber value="<%= p.getPrice() %>" type="currency" maxFractionDigits="0"/>
                </div>
            </div>

            <!-- Số lượng: nút -  [input]  +  (POST tuyệt đối tới /cart) -->
            <div class="qty-wrap">
                <form id="form-<%=pid%>" action="${pageContext.request.contextPath}/cart" method="post" class="qty-form">
                    <input type="hidden" name="productCode" value="<%= p.getCode() %>">
                    <button name="action" value="dec" type="submit" class="step">-</button>

                    <input id="qty-<%=pid%>" class="qty-input" type="number" name="quantity"
                           value="<%= item.getQuantity() %>" min="0" step="1"
                           inputmode="numeric" pattern="[0-9]*"
                           oninput="this.value=this.value.replace(/[^0-9]/g,'')">

                    <button name="action" value="inc" type="submit" class="step">+</button>
                    <button name="action" value="set" type="submit" class="btn btn-outline btn-sm">Cập nhật</button>
                </form>
            </div>

            <!-- Thành tiền từng dòng + remove -->
            <div class="line-total">
                <fmt:formatNumber value="<%= item.getTotal() %>" type="currency" maxFractionDigits="0"/>
               
                <a class="trash" title="Xoá"
                   href="${pageContext.request.contextPath}/cart?action=remove&productCode=<%= p.getCode() %>">🗑</a>
            </div>
        </div>
        <% } %>

        <!-- Khi giỏ trống -->
        <% if (items.isEmpty()) { %>
        <div class="empty">Giỏ hàng trống.</div>
        <% } %>

        <div class="actions-left">
            <form action="${pageContext.request.contextPath}/index.html" method="get">
                <button class="btn btn-outline">Tiếp tục mua sắm</button>
            </form>
        </div>
    </div>
            
    <!-- Cột phải: tổng tiền -->
    
    <aside class="cart-aside">
        
        <div class="card total">
            <div class="row strong">
                <span>Tổng số tiền (gồm VAT)</span>
                <span>
                    <fmt:formatNumber value="<%= subtotal %>" type="currency" maxFractionDigits="0"/>
                </span>
            </div>
            <form action="${pageContext.request.contextPath}/cart" method="post">
                <input type="hidden" name="action" value="checkout">
                <button type="submit" class="btn btn-primary btn-lg block">THANH TOÁN</button>
            </form>
        </div>
    </aside>
</div>

<script>
    // Enter trong input số sẽ submit "set"
    document.addEventListener('keydown', function (e) {
        if (e.key === 'Enter' && e.target.classList.contains('qty-input')) {
            e.preventDefault();
            const form = e.target.closest('form');
            const hidden = document.createElement('input');
            hidden.type = 'hidden';
            hidden.name = 'action';
            hidden.value = 'set';
            form.appendChild(hidden);
            form.submit();
        }
    });
    // blur cũng submit "set"
    document.addEventListener('blur', function (e) {
        if (e.target && e.target.classList && e.target.classList.contains('qty-input')) {
            const form = e.target.closest('form');
            const hidden = document.createElement('input');
            hidden.type = 'hidden';
            hidden.name = 'action';
            hidden.value = 'set';
            form.appendChild(hidden);
            form.submit();
        }
    }, true);
</script>

</body>
</html>
