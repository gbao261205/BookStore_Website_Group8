package murach.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Kết nối tới MySQL Database bookstore_db
 */
public class BookstoreDB {

    // 🔧 Thông tin kết nối — nhớ sửa user & password cho đúng máy bạn
    private static final String URL =
        "jdbc:mysql://127.0.0.1:3306/bookstore_db?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";
    private static final String USER = "root";           // ← thay bằng user của bạn
    private static final String PASS = "123456";  // ← thay bằng mật khẩu MySQL của bạn

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Không tìm thấy driver MySQL! Hãy thêm mysql-connector-j vào Libraries.", e);
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
