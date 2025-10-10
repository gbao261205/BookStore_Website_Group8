package Servlet;

import Data.BookSearchDB;
import Data.BookSearchDB.Result;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private BookSearchDB searchDB;

    @Override
    public void init() {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/YourDB");
            searchDB = new BookSearchDB(ds);
        } catch (NamingException e) { throw new RuntimeException("Cannot init DataSource", e); }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String q = req.getParameter("q");
        int page = 1;
        try { page = Integer.parseInt(req.getParameter("page")); } catch (NumberFormatException ignore) {}

        if (q == null || q.isBlank()) {
            req.setAttribute("result", new BookSearchDB.Result(java.util.List.of(), 0, 1, 12, ""));
        } else {
            try {
                Result r = searchDB.search(q.trim(), page, 12);
                req.setAttribute("result", r);
            } catch (SQLException e) {
                req.setAttribute("error", "Lỗi tìm kiếm: " + e.getMessage());
            }
        }
        req.getRequestDispatcher("/search.jsp").forward(req, resp);
    }
}
