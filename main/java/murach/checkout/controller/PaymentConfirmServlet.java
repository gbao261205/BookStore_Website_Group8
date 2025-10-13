package murach.checkout.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import murach.checkout.service.*;

@WebServlet("/payment/confirm")
public class PaymentConfirmServlet extends HttpServlet {
    private final PaymentService service = new PaymentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        try {
            service.markPaid(token);
            req.setAttribute("msg", "Xác nhận thanh toán thành công. Cảm ơn bạn!");
        } catch (Exception e) {
            req.setAttribute("error", "Xác nhận thất bại: " + e.getMessage());
        }
        req.getRequestDispatcher("/WEB-INF/views/payment_result.jsp").forward(req, resp);
    }
}
