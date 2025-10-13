package murach.checkout.service;

import murach.checkout.util.DB;
import murach.checkout.util.Ids;
import murach.checkout.util.MailUtil;
import murach.checkout.model.Order;
import murach.checkout.model.Payment;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;

public class PaymentServiceImpl implements PaymentService {

    @Override
    public Payment createPaymentAndMaybeSendEmail(Order order, String method, String userEmail, String baseUrl) throws Exception {
        Payment p = new Payment();
        p.setId(Ids.oid());
        p.setOrderId(order.getId());
        p.setMethod(method);
        p.setStatus("BANKING".equals(method) ? "WAITING" : "INIT");
        p.setAmount(order.getTotalAmount());

        String token = null;
        LocalDateTime expires = null;

        if ("BANKING".equals(method)) {
            token = Ids.token();
            expires = LocalDateTime.now().plusHours(24);
        }

        // insert payment
        try (Connection c = DB.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "INSERT INTO payments(id,order_id,method,status,amount,token,expires_at) VALUES(?,?,?,?,?,?,?)")) {
            ps.setString(1, p.getId());
            ps.setString(2, p.getOrderId());
            ps.setString(3, p.getMethod());
            ps.setString(4, p.getStatus());
            ps.setBigDecimal(5, p.getAmount());
            ps.setString(6, token);
            if (expires != null) ps.setTimestamp(7, Timestamp.valueOf(expires)); else ps.setNull(7, Types.TIMESTAMP);
            ps.executeUpdate();
        }

        // gửi mail cho Banking
        if ("BANKING".equals(method)) {
            String confirmUrl = baseUrl + "/payment/confirm?token=" + token;

            // bạn có thể thay body này thành nội dung chuyển khoản + mã đơn để đối soát
            String html = """
        <h2>Hoàn tất thanh toán cho đơn hàng</h2>
        <p>Số tiền: <b>%s</b></p>
        <p>Vui lòng chuyển khoản theo thông tin dưới đây và bấm xác nhận:</p>
        <ul>
          <li>Ngân hàng: MB Bank</li>
          <li>STK: 0123456789</li>
          <li>Chủ TK: BOOKSTORE CO.</li>
          <li>Nội dung chuyển khoản: <b>ORDER-%s</b></li>
        </ul>
        <p>Sau khi chuyển, bấm vào liên kết xác nhận: <a href="%s">%s</a></p>
        <p>Link hết hạn sau 24h.</p>
      """.formatted(p.getAmount().toPlainString(), order.getId(), confirmUrl, confirmUrl);

            MailUtil.send(userEmail, "Hoàn tất thanh toán đơn " + order.getId(), html);
        }

        p.setToken(token);
        p.setExpiresAt(expires);
        return p;
    }

    @Override
    public void markPaid(String token) throws Exception {
        // 1) tìm payment theo token còn hạn
        String orderId = null;
        try (Connection c = DB.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT order_id FROM payments WHERE token=? AND expires_at > NOW() AND status='WAITING'")) {
            ps.setString(1, token);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) orderId = rs.getString(1);
            }
        }
        if (orderId == null) throw new IllegalStateException("Token không hợp lệ hoặc đã hết hạn");

        // 2) update payment + order
        try (Connection c = DB.getDataSource().getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps1 = c.prepareStatement("UPDATE payments SET status='PAID' WHERE token=?");
                 PreparedStatement ps2 = c.prepareStatement("UPDATE orders SET status='PAID' WHERE id=?")) {
                ps1.setString(1, token);
                ps1.executeUpdate();
                ps2.setString(1, orderId);
                ps2.executeUpdate();
            }
            c.commit();
        }
    }
}
