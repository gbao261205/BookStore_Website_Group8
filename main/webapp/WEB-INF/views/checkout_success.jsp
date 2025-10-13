<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, java.math.BigDecimal, murach.checkout.model.OrderItem, murach.checkout.model.Address" %>
<%
    String ctx = request.getContextPath();

    String orderId    = (String) request.getAttribute("orderId");
    String method     = (String) request.getAttribute("method");     // "COD" | "BANKING"
    BigDecimal amount = (BigDecimal) request.getAttribute("amount"); // tiền hàng
    BigDecimal ship   = (BigDecimal) request.getAttribute("shippingFee"); // phí ship

    if (amount == null) amount = BigDecimal.ZERO;
    if (ship == null)   ship   = BigDecimal.ZERO;
    BigDecimal grand = amount.add(ship);

    @SuppressWarnings("unchecked")
    List<OrderItem> items = (List<OrderItem>) request.getAttribute("items");

    Address address = (Address) request.getAttribute("address");

    String methodText = "COD - Thanh toán khi nhận hàng";
    if ("BANKING".equalsIgnoreCase(method)) methodText = "Chuyển khoản / Internet Banking";

    java.text.NumberFormat nf = java.text.NumberFormat.getInstance(new java.util.Locale("vi","VN"));
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đặt hàng thành công</title>
    <style>
        :root{
            --primary:#6c63ff; --success:#16a34a; --text:#0f172a; --muted:#64748b;
            --border:#e2e8f0; --bg:#f8fafc; --card:#ffffff;
        }
        *{box-sizing:border-box}
        body{margin:0;font-family:Inter,system-ui,Segoe UI,Roboto,Arial,sans-serif;color:var(--text);background:var(--bg)}
        .wrap{max-width:980px;margin:28px auto;padding:0 16px}
        .card{background:var(--card);border:1px solid var(--border);border-radius:14px;overflow:hidden}
        .head{display:flex;align-items:center;gap:12px;padding:18px;border-bottom:1px solid var(--border)}
        .check{width:40px;height:40px;border-radius:50%;background:#dcfce7;color:var(--success);
            display:flex;align-items:center;justify-content:center;font-weight:900;font-size:22px}
        h1{margin:0;font-size:22px}
        .sub{color:var(--muted);font-size:14px}
        .body{padding:18px}
        .grid{display:grid;grid-template-columns:1fr 340px;gap:16px}
        .section{background:#fff;border:1px solid var(--border);border-radius:12px}
        .section .hd{padding:12px 16px;border-bottom:1px solid var(--border);font-weight:700}
        .section .bd{padding:12px 16px}
        .list{display:flex;flex-direction:column;gap:8px}
        table{width:100%;border-collapse:collapse}
        th,td{padding:10px;border-bottom:1px solid var(--border);text-align:left}
        th{font-size:12px;color:var(--muted);text-transform:uppercase}
        .totals .row{display:flex;justify-content:space-between;padding:12px 16px;border-bottom:1px solid var(--border)}
        .totals .row:last-child{border-bottom:none}
        .grand{font-weight:800;font-size:18px}
        .actions{display:flex;gap:12px;margin-top:16px;flex-wrap:wrap}
        .btn{display:inline-block;padding:11px 16px;border-radius:10px;border:1px solid var(--primary);
            background:var(--primary);color:#fff;text-decoration:none;font-weight:700}
        .btn.outline{background:#fff;color:var(--primary)}
        .note{background:#f1f5f9;border:1px solid var(--border);border-radius:10px;padding:12px;font-size:14px}
        .mono{font-family:ui-monospace,SFMono-Regular,Menlo,Consolas,monospace}
    </style>
</head>
<body>
<div class="wrap">
    <div class="card">
        <div class="head">
            <div class="check">✓</div>
            <div>
                <h1>Đặt hàng thành công</h1>
                <div class="sub">Cảm ơn bạn đã mua sắm tại cửa hàng!</div>
            </div>
        </div>

        <div class="body">
            <div class="grid">

                <!-- LEFT -->
                <div>
                    <div class="section">
                        <div class="bd">
                            <div class="list">
                                <div><b>Mã đơn:</b> <span class="mono"><%= orderId %></span></div>
                                <div><b>Phương thức thanh toán:</b> <%= methodText %></div>
                                <% if (address != null) { %>
                                <div>
                                    <b>Địa chỉ giao hàng:</b><br/>
                                    <%= address.getDetail() != null ? address.getDetail() : "" %>
                                    <% if (address.getVillage()!=null && !address.getVillage().isBlank()) { %>,
                                    <%= address.getVillage() %><% } %>
                                    <% if (address.getDistrict()!=null && !address.getDistrict().isBlank()) { %>,
                                    <%= address.getDistrict() %><% } %>
                                    <% if (address.getProvince()!=null && !address.getProvince().isBlank()) { %>,
                                    <%= address.getProvince() %><% } %>
                                    <% if (address.getNation()!=null && !address.getNation().isBlank()) { %>,
                                    <%= address.getNation() %><% } %>
                                </div>
                                <% } %>
                            </div>
                        </div>
                    </div>

                    <% if (items != null && !items.isEmpty()) { %>
                    <div class="section" style="margin-top:12px">
                        <div class="hd">Sản phẩm trong đơn</div>
                        <div class="bd">
                            <table>
                                <thead><tr><th>Sản phẩm</th><th>Đơn giá</th><th>SL</th><th>Thành tiền</th></tr></thead>
                                <tbody>
                                <% for (OrderItem it : items) {
                                    BigDecimal sub = it.getUnitPrice().multiply(new BigDecimal(it.getQuantity()));
                                %>
                                <tr>
                                    <td><%= it.getProductName() %></td>
                                    <td><%= nf.format(it.getUnitPrice()) %> ₫</td>
                                    <td><%= it.getQuantity() %></td>
                                    <td><%= nf.format(sub) %> ₫</td>
                                </tr>
                                <% } %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <% } %>

                    <div class="actions">
                        <a class="btn" href="<%= ctx %>/">Về trang chủ</a>
                        <a class="btn outline" href="<%= ctx %>/cart">Xem giỏ hàng</a>
                        <% if ("BANKING".equalsIgnoreCase(method)) { %>
                        <a class="btn outline" href="<%= ctx %>/payment/confirm?orderId=<%= orderId %>">Hướng dẫn thanh toán</a>
                        <% } %>
                    </div>
                </div>

                <!-- RIGHT -->
                <div>
                    <div class="section totals">
                        <div class="hd">Tóm tắt thanh toán</div>
                        <div class="row"><span>Tiền hàng</span><span><%= nf.format(amount) %> ₫</span></div>
                        <div class="row"><span>Phí vận chuyển</span><span><%= nf.format(ship) %> ₫</span></div>
                        <div class="row grand"><span>Tổng thanh toán</span><span><%= nf.format(grand) %> ₫</span></div>
                    </div>

                    <div class="note" style="margin-top:12px">
                        <% if ("BANKING".equalsIgnoreCase(method)) { %>
                        Đơn hàng đã được tạo. Vui lòng kiểm tra email để hoàn tất chuyển khoản theo hướng dẫn.
                        <% } else { %>
                        Đơn của bạn sẽ được xử lý và giao theo địa chỉ đã cung cấp. Vui lòng chuẩn bị tiền mặt khi nhận hàng.
                        <% } %>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
</body>
</html>
