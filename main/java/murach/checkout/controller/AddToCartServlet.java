package murach.checkout.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.math.BigDecimal;
import murach.checkout.util.CartUtil;
import murach.checkout.util.FakeCatalog;

@WebServlet("/cart/add")
public class AddToCartServlet extends HttpServlet {
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id  = req.getParameter("id");
        int qty    = Math.max(1, parseInt(req.getParameter("qty"), 1));

        FakeCatalog.Item it = FakeCatalog.ITEMS.get(id);
        if (it != null) {
            CartUtil.add(req.getSession(), id, it.name(), it.price(), qty);
        }
        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    private int parseInt(String s, int d) { try { return Integer.parseInt(s); } catch (Exception e) { return d; } }
}
