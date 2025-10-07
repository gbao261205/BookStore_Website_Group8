/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Data.UserDB;
import User.*;
import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
//import jakarta.naming.InitialContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;
import javax.naming.InitialContext;

/**
 *
 * @author POW
 */
//@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    private UserDB userDB;

    @Override
    public void init() {
        try {
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/YourDB");
            userDB = new UserDB(ds);
        } catch (Exception e) {
            throw new RuntimeException("Cannot init DataSource", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/SignUp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String fullName  = req.getParameter("fullName");
        String email     = req.getParameter("email");
        String username  = req.getParameter("username");
        String password  = req.getParameter("password");
        String confirm   = req.getParameter("confirmPassword");
        String dobStr    = req.getParameter("dateOfBirth");
        String genderStr = req.getParameter("gender");
        String phone     = req.getParameter("phone");

        // Validate cơ bản
        if (isBlank(fullName) || isBlank(email) || isBlank(username) || isBlank(password)) {
            req.setAttribute("error", "Vui lòng nhập đầy đủ họ tên, email, username và mật khẩu.");
            req.getRequestDispatcher("/SignUp.jsp").forward(req, resp);
            return;
        }
        if (!password.equals(confirm)) {
            req.setAttribute("error", "Mật khẩu xác nhận không khớp.");
            req.getRequestDispatcher("/SignUp.jsp").forward(req, resp);
            return;
        }

        try {
            // Tạo ID
            String userId = "U-" + UUID.randomUUID().toString().substring(0, 8);
            String accId  = "A-" + UUID.randomUUID().toString().substring(0, 8);

            // Tạo user (tạm dùng Admin làm lớp cụ thể)
            User u = new Admin();
            u.setId(userId);
            u.setFullName(fullName);
            u.setEmailAddress(email);
            if (!isBlank(dobStr)) {
                u.setDateOfBirth(LocalDate.parse(dobStr)); // yyyy-MM-dd
            }
            EGender gender = EGender.UNKNOWN;
            if ("MALE".equalsIgnoreCase(genderStr)) gender = EGender.MALE;
            if ("FEMALE".equalsIgnoreCase(genderStr)) gender = EGender.FEMALE;
            u.setGender(gender);
            u.setPhoneNumber(phone);

            // Lưu users (user_type = CUSTOMER)
            userDB.insertUser(u, "CUSTOMER");

            // Lưu accounts (mật khẩu: demo dùng plain; thực tế hãy hash)
            Account acc = new Account(accId, username, password, u);
            userDB.insertAccount(acc);

            // Auto-login: lưu session & chuyển về trang chủ
            req.getSession().setAttribute("account", acc);
            resp.sendRedirect(req.getContextPath() + "/home.jsp");
        } catch (IOException | SQLException e) {
            req.setAttribute("error", "Đăng ký thất bại: " + e.getMessage());
            req.getRequestDispatcher("/SignUp.jsp").forward(req, resp);
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
