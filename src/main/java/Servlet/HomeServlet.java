package Servlet;

import DAO.BookDAO;
import Model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Trang chủ: nạp sách mới, bán chạy, gợi ý.
 * Map: context root ("/") + "/home"
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"/", "/home"})
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // UTF-8 cho tham số và render
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        List<Book> newBooks  = Collections.emptyList();
        List<Book> bestBooks = Collections.emptyList();
        List<Book> suggest   = Collections.emptyList();
        String errorMsg = null;

        try {
            BookDAO dao = new BookDAO();
            // GỌI ĐÚNG HÀM THEO DDL web_bookstore
            newBooks  = dao.findNew(12);
            bestBooks = dao.findBestSellers(12);
            suggest   = dao.findSuggest(12);

            // Log nhỏ để kiểm tra trên console
            getServletContext().log(String.format(
                "Home data -> new:%d, best:%d, suggest:%d",
                newBooks.size(), bestBooks.size(), suggest.size()
            ));
        } catch (SQLException e) {
            getServletContext().log("HomeServlet: load data failed", e);
            errorMsg = "Không tải được dữ liệu, vui lòng thử lại.";
        }

        req.setAttribute("newBooks", newBooks);
        req.setAttribute("bestBooks", bestBooks);
        req.setAttribute("suggestBooks", suggest);
        req.setAttribute("error", errorMsg);

        // Forward ra giao diện
        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }
}
