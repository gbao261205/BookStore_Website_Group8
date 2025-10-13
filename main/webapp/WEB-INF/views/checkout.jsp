<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, java.math.BigDecimal, java.text.NumberFormat, murach.checkout.model.OrderItem, murach.checkout.util.CartUtil" %>
<%
    List<OrderItem> cart = CartUtil.getCart(session);
    BigDecimal itemsTotal = CartUtil.total(session);
    NumberFormat nf = NumberFormat.getInstance(new java.util.Locale("vi", "VN"));
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Checkout</title>
    <style>
        :root{
            --primary:#2f6ae7; --text:#0f172a; --muted:#64748b;
            --border:#e2e8f0; --bg:#f8fafc; --card:#ffffff;
        }
        *{box-sizing:border-box}
        body{margin:0;font-family:Inter,system-ui,Segoe UI,Roboto,Arial,sans-serif;color:var(--text);background:var(--bg)}
        .container{max-width:1100px;margin:24px auto;padding:0 16px}
        h2{font-size:18px;margin:0 0 12px 0}
        .section{background:#fff;border:1px solid var(--border);border-radius:10px;margin-bottom:16px}
        .section .hd{padding:12px 16px;border-bottom:1px solid var(--border);font-weight:700}
        .section .bd{padding:16px}
        .grid{display:grid;grid-template-columns:1fr 360px;gap:16px}
        .row{display:flex;gap:12px}
        .col{flex:1}
        label{display:block;font-size:13px;color:var(--muted);margin-bottom:6px}
        input[type=text], select{width:100%;padding:10px 12px;border:1px solid var(--border);border-radius:8px;background:#fff}
        .hint{font-size:12px;color:var(--muted)}
        .radio{display:flex;align-items:flex-start;gap:10px;padding:12px;border:1px solid var(--border);border-radius:8px;background:#fff}
        .radio + .radio{margin-top:10px}
        .payopt{display:flex;align-items:center;gap:10px;padding:10px 12px;border:1px solid var(--border);border-radius:8px;background:#fff}
        .payopt + .payopt{margin-top:8px}
        table{width:100%;border-collapse:collapse}
        th,td{padding:10px;border-bottom:1px solid var(--border);text-align:left}
        th{text-transform:uppercase;color:var(--muted);font-size:12px}
        .totals{background:#fff;border:1px solid var(--border);border-radius:10px}
        .totals .rowl{display:flex;justify-content:space-between;padding:12px 16px;border-bottom:1px solid var(--border)}
        .totals .rowl:last-child{border-bottom:none}
        .totals .grand{font-weight:800}
        .submitbar{position:sticky;bottom:0;background:#fff;border-top:1px solid var(--border);padding:12px 0;margin-top:12px}
        .btn{display:inline-block;padding:12px 16px;border-radius:8px;border:1px solid var(--primary);background:var(--primary);color:#fff;text-align:center;font-weight:700;cursor:pointer}
        .btn:disabled{opacity:.6;cursor:not-allowed}
        .right{display:flex;flex-direction:column;gap:16px}
    </style>
</head>
<body>
<div class="container">
    <form action="<%=ctx%>/checkout" method="post" id="checkoutForm">
        <div class="grid">
            <!-- LEFT -->
            <div>
                <!-- Địa chỉ giao hàng -->
                <div class="section">
                    <div class="hd">ĐỊA CHỈ GIAO HÀNG</div>
                    <div class="bd">
                        <div class="row">
                            <div class="col">
                                <label>Quốc gia</label>
                                <select name="nation">
                                    <option value="Việt Nam" selected>Việt Nam</option>
                                    <option value="Khác">Khác</option>
                                </select>
                            </div>
                        </div>
                        <div class="row" style="margin-top:10px">
                            <div class="col">
                                <label>Tỉnh/Thành phố</label>
                                <input type="text" name="province" placeholder="Chọn tỉnh/thành phố" required>
                            </div>
                            <div class="col">
                                <label>Quận/Huyện</label>
                                <input type="text" name="district" placeholder="Chọn quận/huyện" required>
                            </div>
                        </div>
                        <div class="row" style="margin-top:10px">
                            <div class="col">
                                <label>Phường/Xã</label>
                                <input type="text" name="village" placeholder="Chọn phường/xã" required>
                            </div>
                        </div>
                        <div class="row" style="margin-top:10px">
                            <div class="col">
                                <label>Địa chỉ nhận hàng</label>
                                <input type="text" name="detail" placeholder="Nhập địa chỉ giao hàng" required>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Phương thức vận chuyển -->
                <div class="section" id="shipBox">
                    <div class="hd">PHƯƠNG THỨC VẬN CHUYỂN</div>
                    <div class="bd">
                        <label class="radio">
                            <input type="radio" name="ship_type" value="inner" checked
                                   onclick="setShipFee(30000)">
                            <div>
                                <div><b>Giao hàng tiêu chuẩn: 30.000 ₫</b> <span class="hint">Nội thành TP.HCM</span></div>
                            </div>
                        </label>
                        <label class="radio">
                            <input type="radio" name="ship_type" value="outer"
                                   onclick="setShipFee(50000)">
                            <div>
                                <div><b>Giao hàng tiêu chuẩn: 50.000 ₫</b> <span class="hint">Ngoại thành TP.HCM</span></div>
                            </div>
                        </label>
                        <input type="hidden" name="shipping_fee" id="shipping_fee" value="30000">
                    </div>
                </div>

                <!-- Phương thức thanh toán -->
                <div class="section">
                    <div class="hd">PHƯƠNG THỨC THANH TOÁN</div>
                    <div class="bd">
                        <label class="payopt">
                            <input type="radio" name="method" value="BANKING">
                            <span>ATM / Internet Banking</span>
                        </label>
                        <label class="payopt">
                            <input type="radio" name="method" value="COD" checked>
                            <span>Thanh toán bằng tiền mặt khi nhận hàng (COD)</span>
                        </label>
                    </div>
                </div>

                <!-- Kiểm tra lại đơn hàng -->
                <div class="section">
                    <div class="hd">KIỂM TRA LẠI ĐƠN HÀNG</div>
                    <div class="bd">
                        <table>
                            <thead>
                            <tr><th>Sản phẩm</th><th>Giá</th><th>SL</th><th>Thành tiền</th></tr>
                            </thead>
                            <tbody>
                            <% for (OrderItem it : cart) {
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
            </div>

            <!-- RIGHT: Tổng tiền -->
            <div class="right">
                <div class="totals" id="summary">
                    <div class="rowl">
                        <span>Thành tiền</span>
                        <span><%= nf.format(itemsTotal) %> ₫</span>
                    </div>
                    <div class="rowl">
                        <span>Phí vận chuyển (tiêu chuẩn)</span>
                        <span id="shipFeeText"><%= nf.format(30000) %> ₫</span>
                    </div>
                    <div class="rowl grand">
                        <span>Tổng số tiền (gồm VAT)</span>
                        <span id="grandText"><%= nf.format(itemsTotal.add(new BigDecimal("30000"))) %> ₫</span>
                    </div>
                </div>

                <div class="submitbar">
                    <button type="submit" class="btn" style="width:100%" <%= cart.isEmpty() ? "disabled" : "" %>>
                        XÁC NHẬN THANH TOÁN
                    </button>
                    <% if (cart.isEmpty()) { %>
                    <div class="hint" style="text-align:center;margin-top:8px">Giỏ hàng trống</div>
                    <% } %>
                </div>
            </div>
        </div>
    </form>
</div>

<script>
    // format VND đơn giản (client)
    function vnd(x){ return x.toLocaleString('vi-VN') + ' ₫'; }

    const shipFeeInput = document.getElementById('shipping_fee');
    const shipFeeText  = document.getElementById('shipFeeText');
    const grandText    = document.getElementById('grandText');
    const itemsTotal   = <%= itemsTotal %>;

    function setShipFee(v){
        shipFeeInput.value = String(v);
        shipFeeText.textContent = v.toLocaleString('vi-VN') + ' ₫';
        const grand = itemsTotal + v;
        grandText.textContent = vnd(grand);
    }
</script>
</body>
</html>
