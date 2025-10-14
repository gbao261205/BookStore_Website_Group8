package dao;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

public class DatabaseInitializerServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        try {
            // Gọi class DatabaseInitializer để ép khởi tạo static block
            Class.forName("dao.DatabaseInitializer");
            System.out.println(">>> [WEB] DatabaseInitializer đã được load khi server khởi động");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
