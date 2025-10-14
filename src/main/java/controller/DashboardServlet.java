package controller;

import dao.BookDao;
import dao.OrderDao;
import dao.CustomerDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class DashboardServlet extends HttpServlet {
    private final BookDao bookDao = new BookDao();
    private final OrderDao orderDao = new OrderDao();
    private final CustomerDao customerDao = new CustomerDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Thống kê sách
        int totalBooks = bookDao.countBooks();
        int outOfStock = bookDao.countOutOfStock();
        int lowStock = bookDao.countLowStock();  // < 10 cuốn
        int inStock = totalBooks - outOfStock;

        // Thống kê doanh thu và đơn
        double totalRevenue = orderDao.getTotalRevenue();
        int totalOrders = orderDao.countOrders();

        // Thống kê khách hàng
        int totalCustomers = customerDao.countCustomers(); 
        // Gửi dữ liệu lên JSP
        req.setAttribute("totalBooks", totalBooks);
        req.setAttribute("outOfStock", outOfStock);
        req.setAttribute("lowStock", lowStock);
        req.setAttribute("inStock", inStock);
        req.setAttribute("totalRevenue", totalRevenue);
        req.setAttribute("totalOrders", totalOrders);
        req.setAttribute("totalCustomers", totalCustomers);

        req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
    }
}
