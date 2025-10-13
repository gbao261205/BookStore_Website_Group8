package murach.checkout.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DB {
    private static final HikariDataSource ds;

    static {
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl("jdbc:mysql://localhost:3306/BOOKSTORE"
                + "?useUnicode=true&characterEncoding=utf8"
                + "&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true");
        cfg.setUsername("root");          // hoặc user của bạn
        cfg.setPassword("Thuong@123"); // mật khẩu MySQL của bạn
        cfg.setMaximumPoolSize(10);

        try {
            // 👉 DÒNG QUAN TRỌNG
            Class.forName("com.mysql.cj.jdbc.Driver");
            ds = new HikariDataSource(cfg);
        } catch (Exception e) {
            throw new RuntimeException("DB init error", e);
        }
    }

    public static HikariDataSource getDataSource() {
        return ds;
    }
}
