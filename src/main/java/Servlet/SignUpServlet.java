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
import java.sql.SQLIntegrityConstraintViolationException;
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
            // Kiểm tra trùng trước khi insert (thân thiện hơn)
            if (userDB.usernameExists(username)) {
                req.setAttribute("error", "Tên đăng nhập đã tồn tại. Vui lòng chọn tên khác.");
                req.getRequestDispatcher("/SignUp.jsp").forward(req, resp);
                return;
            }
            if (userDB.emailExists(email)) {   // nếu email của bạn đặt UNIQUE
                req.setAttribute("error", "Email đã được đăng ký. Vui lòng dùng email khác.");
                req.getRequestDispatcher("/SignUp.jsp").forward(req, resp);
                return;
            }

            // --- tạo User + Account như trước (rút gọn) ---
            String userId = "U-" + java.util.UUID.randomUUID().toString().substring(0, 8);
            String accId  = "A-" + java.util.UUID.randomUUID().toString().substring(0, 8);

            User u = new Admin();
            u.setId(userId);
            u.setFullName(fullName);
            u.setEmailAddress(email);
            if (!isBlank(dobStr)) u.setDateOfBirth(java.time.LocalDate.parse(dobStr));
            EGender g = EGender.UNKNOWN;
            if ("MALE".equalsIgnoreCase(genderStr)) g = EGender.MALE;
            if ("FEMALE".equalsIgnoreCase(genderStr)) g = EGender.FEMALE;
            u.setGender(g);
            u.setPhoneNumber(phone);

            userDB.insertUser(u, "CUSTOMER");

            Account acc = new Account(accId, username, password, u);
            userDB.insertAccount(acc);

            // Auto-login -> về home.jsp
            req.getSession().setAttribute("account", acc);
            resp.sendRedirect(req.getContextPath() + "/home.jsp");
        }
        catch (SQLIntegrityConstraintViolationException e) {
            // Phòng khi chạy đua/điểm race còn dính duplicate từ DB
            String msg = e.getMessage();
            String friendly = "Dữ liệu bị trùng. Vui lòng kiểm tra lại.";
            if (msg != null && msg.contains("accounts.username")) {
                friendly = "Tên đăng nhập đã tồn tại. Vui lòng chọn tên khác.";
            } else if (msg != null && msg.contains("users.email")) {
                friendly = "Email đã được đăng ký. Vui lòng dùng email khác.";
            }
            req.setAttribute("error", friendly);
            req.getRequestDispatcher("/SignUp.jsp").forward(req, resp);
        }
        catch (ServletException | IOException | SQLException e) {
            req.setAttribute("error", "Đăng ký thất bại: " + e.getMessage());
            req.getRequestDispatcher("/SignUp.jsp").forward(req, resp);
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
