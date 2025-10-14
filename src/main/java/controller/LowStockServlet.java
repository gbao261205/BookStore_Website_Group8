package controller;

import dao.BookDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class LowStockServlet extends HttpServlet {

    private final BookDao bookDao = new BookDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy danh sách sách sắp hết hàng (quantity < 2)
        req.setAttribute("lowStockBooks", bookDao.getLowStockBooks());

        // Forward đến JSP
        RequestDispatcher rd = req.getRequestDispatcher("/lowstock.jsp");
        rd.forward(req, resp);
    }
}
