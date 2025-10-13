package murach.checkout.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import murach.checkout.model.OrderItem;
import murach.checkout.util.CartUtil;

@WebServlet("/cart/update")
public class UpdateCartServlet extends HttpServlet {
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // 1) Nếu nhấn nút "Xóa" từng dòng
        String removePid = req.getParameter("remove");
        if (removePid != null && !removePid.isBlank()) {
            murach.checkout.util.CartUtil.remove(req.getSession(), removePid);
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        // 2) Nếu nhấn "Xóa hết"
        String action = req.getParameter("action");
        if ("clear".equals(action)) {
            murach.checkout.util.CartUtil.clear(req.getSession());
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        // 3) Mặc định: cập nhật số lượng
        String[] pid = req.getParameterValues("pid");
        String[] qty = req.getParameterValues("qty");
        if (pid != null && qty != null && pid.length == qty.length) {
            var cart = murach.checkout.util.CartUtil.getCart(req.getSession());
            for (int i = 0; i < pid.length; i++) {
                for (var it : cart) {
                    if (it.getProductId().equals(pid[i])) {
                        int q = 1;
                        try { q = Integer.parseInt(qty[i]); } catch (Exception ignored) {}
                        it.setQuantity(Math.max(1, q));
                        break;
                    }
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}

