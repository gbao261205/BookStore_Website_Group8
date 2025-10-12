package murach.data;

import murach.business.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOBookstore {
    private static Product map(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setCode(rs.getString("isbn"));          // Product.code  ← book.isbn
        p.setDescription(rs.getString("title"));  // Product.desc  ← book.title
        p.setPrice(rs.getDouble("sellingPrice")); // Product.price ← book.sellingPrice
        return p;
    }

    /** Lấy 1 sản phẩm theo ISBN (dùng cho Add to Cart) */
    public static Product find(String isbn) {
        String sql = """
            SELECT isbn, title, sellingPrice
            FROM book
            WHERE isbn = ?
        """;
        try (Connection c = BookstoreDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, isbn);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Lấy list còn hàng để hiển thị catalog (tùy chọn) */
    public static List<Product> findAllInStock() {
        String sql = """
            SELECT isbn, title, sellingPrice
            FROM book
            WHERE quantity > 0
            ORDER BY title
        """;
        List<Product> list = new ArrayList<>();
        try (Connection c = BookstoreDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
