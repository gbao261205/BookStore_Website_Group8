package murach.checkout.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.math.BigDecimal;
import murach.checkout.util.CartUtil;

@WebServlet("/cart/fake")
public class QuickCartServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        // nếu chưa có user giả lập thì seed luôn
        if (req.getSession().getAttribute("userId") == null) {
            req.getSession().setAttribute("userId", "168");
            req.getSession().setAttribute("userEmail", "ngocthuong.th1327@gmail.com");
        }

        // thêm vài sản phẩm mẫu
        CartUtil.add(req.getSession(), "p1", "Sách A", new BigDecimal("120000"), 1);
        CartUtil.add(req.getSession(), "p2", "Sách B", new BigDecimal("95000"), 2);

        String next = req.getParameter("next");
        if (next == null || next.isBlank()) next = req.getContextPath() + "/checkout_form";

        resp.getWriter().println("OK: Đã tạo giỏ tạm. Vào <a href='" + next + "'>form checkout</a>.");
    }
}
