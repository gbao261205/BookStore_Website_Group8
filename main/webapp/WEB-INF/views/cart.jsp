<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, java.math.BigDecimal, murach.checkout.model.OrderItem, murach.checkout.util.CartUtil" %>
<%
    List<OrderItem> cart = CartUtil.getCart(session);
    BigDecimal total = CartUtil.total(session);
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Giỏ hàng</title>
    <style>
        :root{
            --primary:#2f6ae7;
            --text:#0f172a;
            --muted:#64748b;
            --border:#e2e8f0;
            --bg:#f8fafc;
            --card:#ffffff;
            --accent:#eff6ff;
        }
        *{box-sizing:border-box}
        body{margin:0;font-family:Inter,system-ui,-apple-system,Segoe UI,Roboto,"Helvetica Neue",Arial,sans-serif;color:var(--text);background:var(--bg)}
        a{color:var(--primary);text-decoration:none}
        a:hover{text-decoration:underline}
        .container{max-width:1100px;margin:0 auto;padding:16px}

        /* Header */
        .navbar{background:#fff;border-bottom:1px solid var(--border);padding:10px 16px}
        .navinner{max-width:1100px;margin:0 auto;display:flex;align-items:center;gap:16px}
        .brand{display:flex;align-items:center;gap:8px;font-weight:700;font-size:20px;color:var(--primary)}
        .brand .logo{width:24px;height:24px;border-radius:6px;background:var(--primary);display:inline-block}
        .search{flex:1}
        .search input{width:100%;padding:10px 12px;border:1px solid var(--border);border-radius:8px;background:var(--bg)}
        .navlinks{display:flex;gap:18px;color:var(--muted);font-size:14px}

        /* Layout */
        .grid{display:grid;grid-template-columns: 1fr 320px; gap:16px; margin-top:16px}

        /* Card left */
        .card{background:var(--card);border:1px solid var(--border);border-radius:12px}
        .card-h{padding:12px 16px;border-bottom:1px solid var(--border);font-weight:600}
        .item{display:grid;grid-template-columns:88px 1fr 120px 120px;gap:16px;padding:16px;border-top:1px solid var(--border);align-items:center}
        .item:first-of-type{border-top:none}
        .thumb{width:88px;height:88px;border-radius:10px;overflow:hidden;background:var(--accent);display:flex;align-items:center;justify-content:center}
        .thumb img{max-width:100%;max-height:100%;display:block}
        .title{font-weight:600}
        .price, .sub, .total-row span{font-weight:700}
        .muted{color:var(--muted);font-size:13px;margin-top:6px}

        .qty{display:flex;align-items:center;gap:6px}
        .qty input[type=number]{width:64px;padding:6px 8px;border:1px solid var(--border);border-radius:8px}
        .btn, button{cursor:pointer;border:1px solid var(--border);background:#fff;border-radius:8px;padding:8px 10px}
        .btn-icon{width:36px;height:36px;display:inline-flex;align-items:center;justify-content:center;border-radius:8px}
        .btn-danger{border-color:#fecaca;color:#dc2626;background:#fff}
        .btn-primary{background:var(--primary);color:#fff;border-color:var(--primary)}
        .btn-primary:hover{filter:brightness(0.95)}

        /* Right column */
        .side{display:flex;flex-direction:column;gap:16px}
        .promo .row{padding:14px 16px;border-top:1px solid var(--border)}
        .promo .row:first-child{border-top:none}
        .promo h4{margin:0 0 6px 0;font-size:14px}
        .promo p{margin:0;color:var(--muted);font-size:12px}
        .summary .row{display:flex;justify-content:space-between;padding:12px 16px;border-top:1px solid var(--border)}
        .summary .row:first-child{border-top:none}
        .summary .total-row{font-size:16px}
        .summary .pay{padding:16px}

        /* Empty */
        .empty{padding:32px;text-align:center;color:var(--muted)}
    </style>
</head>
<body>
<!-- Header -->
<div class="navbar">
    <div class="navinner">
        <div class="brand"><span class="logo"></span> Sach50</div>
        <div class="navlinks">Thể Loại</div>
        <div class="search"><input placeholder="Tìm sách"></div>
        <div class="navlinks">
            <span>Thông Báo</span>
            <span style="font-weight:600;color:var(--text)">Giỏ Hàng</span>
            <span>Tài Khoản</span>
        </div>
    </div>
</div>

<div class="container">
    <div class="grid">
        <!-- LEFT: list items -->
        <div class="card">
            <div class="card-h">GIỎ HÀNG (<%= cart.size() %> sản phẩm)</div>

            <% if (cart.isEmpty()) { %>
            <div class="empty">Giỏ hàng đang trống. Hãy thêm vài cuốn sách nhé!</div>
            <% } %>

            <% for (OrderItem it : cart) {
                BigDecimal sub = it.getUnitPrice().multiply(new BigDecimal(it.getQuantity()));
            %>
            <div class="item">
                <div class="thumb">
                    <!-- Nếu bạn có URL ảnh trong OrderItem thì thay bằng it.getImageUrl() -->
                    <img src="<%= ctx %>/images/book-placeholder.png" alt="book">
                </div>

                <div>
                    <div class="title"><%= it.getProductName() %></div>
                    <div class="muted">Mã: <%= it.getProductId() %></div>
                    <div class="muted" style="margin-top:10px">
                        <!-- nút xóa -->
                        <form action="<%=ctx%>/cart/update" method="post" style="display:inline">
                            <button class="btn btn-danger" type="submit" name="remove" value="<%= it.getProductId() %>">Xóa</button>
                        </form>
                    </div>
                </div>

                <!-- Số lượng -->
                <div>
                    <form action="<%=ctx%>/cart/update" method="post" class="qty">
                        <input type="hidden" name="pid" value="<%= it.getProductId() %>"/>
                        <button class="btn-icon" type="button" onclick="this.nextElementSibling.stepDown(); this.form.submit();">−</button>
                        <input type="number" name="qty" min="1" value="<%= it.getQuantity() %>"/>
                        <button class="btn-icon" type="button" onclick="this.previousElementSibling.stepUp(); this.form.submit();">+</button>
                    </form>
                </div>

                <!-- Thành tiền -->
                <div class="sub"><%= sub %> ₫</div>
            </div>
            <% } %>
        </div>

        <!-- RIGHT: promo + summary -->
        <div class="side">
            <!-- Khuyến mãi (tĩnh minh họa) -->
            <div class="card promo">
                <div class="card-h" style="font-weight:700">KHUYẾN MÃI</div>
                <div class="row">
                    <h4>MÃ GIẢM 20%</h4>
                    <p>Cho đơn từ 200k. Không áp dụng sách giảm giá.</p>
                    <div style="margin-top:8px">
                        <button class="btn">Mã: SALE20</button>
                    </div>
                </div>
                <div class="row">
                    <h4>MIỄN PHÍ GIAO HÀNG</h4>
                    <p>Cho đơn từ 500k. Áp dụng nội thành.</p>
                    <div style="margin-top:8px">
                        <button class="btn">Áp dụng</button>
                    </div>
                </div>
            </div>

            <!-- Tổng tiền -->
            <div class="card summary">
                <div class="row">
                    <span>Thành tiền</span>
                    <span class="price"><%= total %> ₫</span>
                </div>
                <div class="row total-row">
                    <span>Tổng số tiền (gồm VAT)</span>
                    <span><%= total %> ₫</span>
                </div>
                <div class="pay">
                    <% if (cart.isEmpty()) { %>
                    <button class="btn btn-primary" style="width:100%" disabled>THANH TOÁN</button>
                    <div class="muted" style="margin-top:8px;text-align:center">Giỏ hàng trống</div>
                    <% } else { %>
                    <a class="btn btn-primary" style="display:block;text-align:center;width:100%" href="<%=ctx%>/checkout_form">THANH TOÁN</a>
                    <% } %>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
