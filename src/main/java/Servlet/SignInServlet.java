// Servlet/SignInServlet.java
package Servlet;

import dao.AccountDAO;
import dao.impl.AccountDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;

public class SignInServlet extends HttpServlet {
    private final AccountDAO accountDAO = new AccountDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/signin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        String u = req.getParameter("username");
        String p = req.getParameter("password");
        var found = accountDAO.signIn(u == null ? null : u.trim(), p == null ? null : p.trim());
        if (found.isPresent()) {
            req.getSession(true).setAttribute("currentUser", found.get());
            resp.sendRedirect(req.getContextPath() + (found.get().isAdmin() ? "/admin/dashboard" : "/"));
        } else {
            req.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu");
            req.getRequestDispatcher("/signin.jsp").forward(req, resp);
        }
    }
}
