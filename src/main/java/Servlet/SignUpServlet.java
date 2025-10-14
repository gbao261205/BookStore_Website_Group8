// src/main/java/Servlet/SignUpServlet.java
package Servlet;

import dao.AccountDAO;
import dao.impl.AccountDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import static org.apache.taglibs.standard.functions.Functions.trim;

public class SignUpServlet extends HttpServlet {
    private final AccountDAO accountDAO = new AccountDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");

        // account
        String username = t(req.getParameter("username"));
        String password = t(req.getParameter("password"));

        // user
        String fullName = t(req.getParameter("fullName"));
        String email    = t(req.getParameter("email"));
        String phone    = t(req.getParameter("phone"));
        String dob      = t(req.getParameter("dob"));       // yyyy-MM-dd or ""
        String genderCode = trim(req.getParameter("genderCode"));  // "1" (MALE) / "2" (FEMALE) hoặc ""
        String avatar   = t(req.getParameter("avatar"));

        // address
        String street   = t(req.getParameter("street"));
        String ward     = t(req.getParameter("ward"));
        String district = t(req.getParameter("district"));
        String city     = t(req.getParameter("city"));
        String province = t(req.getParameter("province"));
        String zipcode  = t(req.getParameter("zipcode"));
        String country  = t(req.getParameter("country"));

        // validate tối thiểu
        if (isEmpty(username) || isEmpty(password) || isEmpty(fullName) || isEmpty(email)) {
            req.setAttribute("error", "Họ tên, email, username, password là bắt buộc.");
            req.getRequestDispatcher("/signup.jsp").forward(req, resp);
            return;
        }
        if (accountDAO.usernameExists(username)) {
            req.setAttribute("error", "Tên đăng nhập đã tồn tại.");
            req.getRequestDispatcher("/signup.jsp").forward(req, resp);
            return;
        }
        if (accountDAO.emailExists(email)) {
            req.setAttribute("error", "Email đã được sử dụng.");
            req.getRequestDispatcher("/signup.jsp").forward(req, resp);
            return;
        }

        var created = accountDAO.signUp(
            fullName, email, phone, dob, genderCode, avatar,
            username, password,
            street, ward, district, city, province, zipcode, country
        );

        if (created.isPresent()) {
            req.getSession(true).setAttribute("currentUser", created.get());
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.setAttribute("error", "Không tạo được tài khoản. Vui lòng thử lại.");
            req.getRequestDispatcher("/signup.jsp").forward(req, resp);
        }
    }

    private static String t(String s){ return s==null? null : s.trim(); }
    private static boolean isEmpty(String s){ return s==null || s.isEmpty(); }
}
