package murach.checkout.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import murach.checkout.util.CartUtil;
import murach.checkout.util.FakeCatalog;

@WebServlet("/cart")
public class CartPageServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // nếu chưa có user demo thì seed luôn
        if (req.getSession().getAttribute("userId") == null) {
            req.getSession().setAttribute("userId", "168");
            req.getSession().setAttribute("userEmail", "ngocthuong.th1327@gmail.com");
        }

        // nếu query ?seed=true → nạp sẵn vài sách cho nhanh
        if ("true".equalsIgnoreCase(req.getParameter("seed")) && CartUtil.isEmpty(req.getSession())) {
            CartUtil.add(req.getSession(), "b1", FakeCatalog.ITEMS.get("b1").name(), FakeCatalog.ITEMS.get("b1").price(), 1);
            CartUtil.add(req.getSession(), "b2", FakeCatalog.ITEMS.get("b2").name(), FakeCatalog.ITEMS.get("b2").price(), 2);
        }

        // catalog để hiển thị nút thêm
        req.setAttribute("catalog", FakeCatalog.ITEMS);
        req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }
}
