package project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
        "jdbc:mysql://bookstore-bookstore-25.b.aivencloud.com:11273/defaultdb"
        + "?sslMode=REQUIRED&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_sQm2rkmFaSQTEEJiCGe";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Kết nối thành công tới Aiven MySQL!");
            return conn;
        } catch (ClassNotFoundException e) {
            throw new SQLException("Không tìm thấy driver JDBC: " + e.getMessage());
        }
    }

    // Test kết nối khi chạy riêng
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("🎉 Đã kết nối: " + conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
