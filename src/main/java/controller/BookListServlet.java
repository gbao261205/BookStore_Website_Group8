package controller;

import dao.BookDao;
import model.Book;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class BookListServlet extends HttpServlet {
    private final BookDao dao = new BookDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Book> books = dao.getAllBooks();
        req.setAttribute("books", books);
        req.getRequestDispatcher("catalog.jsp").forward(req, resp);
    }
}
