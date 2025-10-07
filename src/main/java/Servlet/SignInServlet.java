/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Data.UserDB;
import User.Account;
import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author POW
 */
//@WebServlet("/signin")
public class SignInServlet extends HttpServlet {
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
        req.getRequestDispatcher("/SignIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || password == null) {
            req.setAttribute("error", "Vui lòng nhập username và mật khẩu.");
            req.getRequestDispatcher("/SignIn.jsp").forward(req, resp);
            return;
        }

        try {
            // Nếu bạn chuyển sang BCrypt: tìm account theo username rồi check hash.
            Account acc = userDB.authenticatePlain(username, password);
            if (acc == null) {
                req.setAttribute("error", "Sai username hoặc mật khẩu.");
                req.getRequestDispatcher("/SignIn.jsp").forward(req, resp);
                return;
            }

            // Lưu session
            HttpSession session = req.getSession();
            session.setAttribute("account", acc);

            // Nếu có tham số redirect, cho quay về trang cũ
            String redirect = req.getParameter("redirect");
            if (redirect != null && !redirect.isEmpty()) {
                resp.sendRedirect(redirect);
            } else {
                resp.sendRedirect(req.getContextPath() + "/home.jsp");
            }
        } catch (ServletException | IOException | SQLException e) {
            req.setAttribute("error", "Đăng nhập thất bại: " + e.getMessage());
            req.getRequestDispatcher("/SignIn.jsp").forward(req, resp);
        }
    }
}
