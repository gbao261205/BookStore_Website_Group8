package murach.checkout.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;                     // NEW
import java.util.List;
import murach.checkout.model.*;
import murach.checkout.service.*;
import murach.checkout.util.CartUtil;          // nếu cần

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private final OrderService orderService = new OrderServiceImpl();
    private final PaymentService paymentService = new PaymentServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String userId = (String) req.getSession().getAttribute("userId");
        String userEmail = (String) req.getSession().getAttribute("userEmail");
        if (userId == null) { resp.sendRedirect(req.getContextPath() + "/cart/seed"); return; }

        @SuppressWarnings("unchecked")
        List<OrderItem> cart = (List<OrderItem>) req.getSession().getAttribute("CART_ITEMS");
        if (cart == null || cart.isEmpty()) {
            req.setAttribute("error", "Giỏ hàng trống");
            req.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(req, resp);
            return;
        }

        Address addr = new Address();
        addr.setUserId(userId);
        addr.setNation(req.getParameter("nation"));
        addr.setProvince(req.getParameter("province"));
        addr.setDistrict(req.getParameter("district"));
        addr.setVillage(req.getParameter("village"));
        addr.setDetail(req.getParameter("detail"));

        String method = req.getParameter("method"); // COD | BANKING

        // NEW: đọc phí vận chuyển từ form
        BigDecimal shippingFee = BigDecimal.ZERO;
        try {
            String sf = req.getParameter("shipping_fee");
            if (sf != null && !sf.isBlank()) shippingFee = new BigDecimal(sf);
        } catch (Exception ignored) {}

        // NEW: tính tiền hàng (không gồm ship) từ cart
        BigDecimal itemsTotal = BigDecimal.ZERO;
        for (OrderItem it : cart) {
            itemsTotal = itemsTotal.add(it.getUnitPrice().multiply(BigDecimal.valueOf(it.getQuantity())));
        }

        try {
            // nếu service của bạn chưa nhận shippingFee vẫn OK
            Order order = orderService.createOrderWithAddress(userId, addr, cart, method);

            // (tùy chọn) nếu muốn lưu tổng thanh toán = hàng + ship -> cập nhật service để cộng shippingFee

            String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
            paymentService.createPaymentAndMaybeSendEmail(order, method, userEmail, baseUrl);

            // clear cart
            req.getSession().removeAttribute("CART_ITEMS");

            // NEW: set dữ liệu cho trang thành công
            req.setAttribute("orderId", order.getId());
            req.setAttribute("method", method);
            req.setAttribute("amount", itemsTotal);      // tiền hàng
            req.setAttribute("shippingFee", shippingFee);
            req.setAttribute("items", cart);
            req.setAttribute("address", addr);

            // thông điệp ngắn (không còn cần thiết nếu dùng JSP mới, nhưng vẫn giữ nếu bạn đang dùng)
            req.setAttribute("msg",
                    "BANKING".equals(method)
                            ? "Đã tạo đơn. Kiểm tra email để hoàn tất thanh toán."
                            : "Đặt hàng thành công. Thanh toán COD khi nhận.");

            req.getRequestDispatcher("/WEB-INF/views/checkout_success.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(req, resp);
        }
    }
}
