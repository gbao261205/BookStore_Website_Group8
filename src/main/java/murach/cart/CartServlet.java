package murach.cart;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

import murach.business.*;
import murach.data.ProductDAOBookstore;  // ⬅️ dùng DAO đọc từ DB

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Ủy quyền GET cho POST để xử lý chung
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletContext sc = getServletContext();
        String action = request.getParameter("action");
        if (action == null) action = "cart";   // tương thích form cũ

        String url = "/index.html";

        // Lấy/khởi tạo giỏ hàng trong session
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        switch (action) {

            case "shop": {
                url = "/index.html";
                break;
            }

            case "add": { // thêm N sp
                String code = request.getParameter("productCode"); // ISBN
                String qtyStr = request.getParameter("quantity");
                int q = 1;
                try { q = Integer.parseInt(qtyStr); if (q < 1) q = 1; } catch (Exception ignored) {}

                Product product = loadProduct(code);   // ⬅️ lấy từ DB
                if (product != null) {
                    LineItem li = new LineItem();
                    li.setProduct(product);
                    li.setQuantity(q);
                    cart.addItem(li);
                }
                url = "/cart.jsp";
                break;
            }

            case "inc": { // tăng 1
                String code = request.getParameter("productCode");
                Product product = loadProduct(code);
                if (product != null) {
                    LineItem li = new LineItem();
                    li.setProduct(product);
                    li.setQuantity(1);
                    cart.addItem(li);
                }
                url = "/cart.jsp";
                break;
            }

            case "dec": { // giảm 1
                String code = request.getParameter("productCode");
                Product product = loadProduct(code);
                if (product != null) {
                    LineItem li = new LineItem();
                    li.setProduct(product);
                    li.setQuantity(1);
                    cart.decreItem(li); // giữ đúng tên hàm theo class của bạn
                }
                url = "/cart.jsp";
                break;
            }

            // đặt số lượng tuyệt đối (form cũ action=cart hoặc set)
            case "set":
            case "cart": {
                String code = request.getParameter("productCode");
                String quantityString = request.getParameter("quantity");

                int q = 1;
                try {
                    q = Integer.parseInt(quantityString);
                    if (q < 0) q = 0;
                } catch (NumberFormatException ignored) {
                    q = 1;
                }

                cart.setQuantity(code, q);
                url = "/cart.jsp";
                break;
            }

            case "remove": { // xóa dòng
                String code = request.getParameter("productCode");
                Product product = loadProduct(code);
                if (product != null) {
                    LineItem li = new LineItem();
                    li.setProduct(product);
                    li.setQuantity(0);
                    cart.removeItem(li);
                }
                url = "/cart.jsp";
                break;
            }

            case "checkout": {
                url = "/checkout.jsp";
                break;
            }

            default: {
                url = "/index.html";
            }
        }

        sc.getRequestDispatcher(url).forward(request, response);
    }

    // ===== Helpers =====

    private double subtotal(Cart cart) {
        double s = 0.0;
        if (cart != null) {
            for (LineItem li : cart.getItems()) {
                s += li.getTotal();
            }
        }
        return s;
    }

    /** Lấy Product theo ISBN từ database (bookstore_db.book) */
    private Product loadProduct(String code) {
        if (code == null || code.isEmpty()) return null;
        try {
            return ProductDAOBookstore.find(code);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
