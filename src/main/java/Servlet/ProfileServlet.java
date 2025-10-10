package Servlet;

import Data.UserDB;
import User.Account;
import User.EGender;
import User.User;
import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ProfileServlet extends HttpServlet {
    private UserDB userDB;

    @Override
    public void init() {
        try {
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/YourDB");
            userDB = new UserDB(ds);
        } catch (NamingException e) {
            throw new RuntimeException("Cannot init DataSource", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Account acc = (Account) req.getSession().getAttribute("account");
        if (acc == null) {
            resp.sendRedirect(req.getContextPath() + "/signin");
            return;
        }
        // Hiển thị form với dữ liệu đang có trong session
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        HttpSession ss = req.getSession();
        Account acc = (Account) ss.getAttribute("account");
        if (acc == null) {
            resp.sendRedirect(req.getContextPath() + "/signin");
            return;
        }

        // Lấy dữ liệu từ form
        String fullName = req.getParameter("fullName");
        String email    = req.getParameter("email");
        String phone    = req.getParameter("phone");
        String gender   = req.getParameter("gender");
        String dobStr   = req.getParameter("dateOfBirth");

        try {
            User u = acc.getUser();
            u.setFullName(fullName);
            u.setEmailAddress(email);
            u.setPhoneNumber(phone);
            // gender
            EGender g = EGender.UNKNOWN;
            if ("MALE".equalsIgnoreCase(gender)) g = EGender.MALE;
            else if ("FEMALE".equalsIgnoreCase(gender)) g = EGender.FEMALE;
            u.setGender(g);
            // dob
            if (dobStr != null && !dobStr.trim().isEmpty())
                u.setDateOfBirth(LocalDate.parse(dobStr));
            else
                u.setDateOfBirth(null);

            // Cập nhật DB
            userDB.updateUserProfile(u);

            // Cập nhật session
            ss.setAttribute("account", acc);
            req.setAttribute("success", "Đã lưu thay đổi hồ sơ.");

        } catch (SQLException e) {
            req.setAttribute("error", "Lưu thất bại: " + e.getMessage());
        }

        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }
}
