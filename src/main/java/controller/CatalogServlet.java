package controller;

import dao.BookDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class CatalogServlet extends HttpServlet {

    private final BookDao bookDao = new BookDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy danh sách sách từ DB
        req.setAttribute("books", bookDao.getAllBooks());

        // Chuyển tiếp đến trang hiển thị catalog.jsp
        req.getRequestDispatcher("catalog.jsp").forward(req, resp);
    }
}
