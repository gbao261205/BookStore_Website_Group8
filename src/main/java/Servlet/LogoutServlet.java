package Servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    HttpSession ss = req.getSession(false);
    if (ss != null) ss.invalidate();
    resp.sendRedirect(req.getContextPath() + "/home.jsp");
  }
}
