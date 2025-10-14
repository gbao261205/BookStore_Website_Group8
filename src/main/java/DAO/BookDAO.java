package DAO;

import Model.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BookDAO tương thích với database web_bookstore
 * (sử dụng bảng book, author, book_author, media_file, book_media)
 */
public class BookDAO {

    // ====== TRUY VẤN DANH SÁCH CHO TRANG CHỦ ======

    // Sách mới: theo release_date giảm dần
    private static final String SQL_NEW = """
        SELECT
            b.id,
            b.title,
            COALESCE(GROUP_CONCAT(DISTINCT a.name ORDER BY a.name SEPARATOR ', '), '') AS author,
            b.price,
            b.cover_url
        FROM book b
        LEFT JOIN book_author ba ON ba.book_id = b.id
        LEFT JOIN author a       ON a.id       = ba.author_id
        GROUP BY b.id
        ORDER BY b.release_date DESC, b.id DESC
        LIMIT ?
    """;

    // Sách bán chạy: tạm dựa theo số lượng tồn kho thấp nhất hoặc có thể dùng order_item nếu bạn có
    private static final String SQL_BEST = """
        SELECT
            b.id,
            b.title,
            COALESCE(GROUP_CONCAT(DISTINCT a.name ORDER BY a.name SEPARATOR ', '), '') AS author,
            b.price,
            b.cover_url
        FROM book b
        LEFT JOIN book_author ba ON ba.book_id = b.id
        LEFT JOIN author a       ON a.id       = ba.author_id
        GROUP BY b.id
        ORDER BY b.stock ASC, b.id DESC
        LIMIT ?
    """;

    // Gợi ý: lấy ngẫu nhiên
    private static final String SQL_SUGGEST = """
        SELECT
            b.id,
            b.title,
            COALESCE(GROUP_CONCAT(DISTINCT a.name ORDER BY a.name SEPARATOR ', '), '') AS author,
            b.price,
            b.cover_url
        FROM book b
        LEFT JOIN book_author ba ON ba.book_id = b.id
        LEFT JOIN author a       ON a.id       = ba.author_id
        GROUP BY b.id
        ORDER BY RAND()
        LIMIT ?
    """;

    public List<Book> findNew(int limit) throws SQLException         { return queryList(SQL_NEW, limit); }
    public List<Book> findBestSellers(int limit) throws SQLException { return queryList(SQL_BEST, limit); }
    public List<Book> findSuggest(int limit) throws SQLException     { return queryList(SQL_SUGGEST, limit); }

    private List<Book> queryList(String sql, int limit) throws SQLException {
        List<Book> list = new ArrayList<>();
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    // ====== CHUYỂN RESULTSET -> MODEL ======
    private Book map(ResultSet rs) throws SQLException {
        String cover = rs.getString("cover_url");
        if (cover == null || cover.isBlank()) cover = "";
        return new Book(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getDouble("price"),
                cover
        );
    }
}
