package dao;

import model.Book;
import java.sql.*;
import java.util.*;

public class BookDao {

    // ===== LẤY DANH SÁCH TẤT CẢ SÁCH =====
    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();
        String sql =
            "SELECT b.id, b.isbn, b.title, b.description, b.sellingPrice, b.quantity, "
          + "b.publicationDate, b.edition, "
          + "f.name AS format_name, "
          + "l.name AS language_name, "
          + "a.name AS author_name, "
          + "p.name AS publisher_name, "
          + "m.importPrice "
          + "FROM book b "
          + "LEFT JOIN ebook_format f ON b.format_id = f.id "
          + "LEFT JOIN ebook_language l ON b.language_id = l.id "
          + "LEFT JOIN author a ON b.author_id = a.id "
          + "LEFT JOIN publisher p ON b.publisher_id = p.id "
          + "LEFT JOIN book_metadata m ON b.metadata_id = m.id "
          + "ORDER BY b.id;";

        try (Connection cn = DatabaseConnection.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getLong("id"));
                b.setIsbn(rs.getString("isbn"));
                b.setTitle(rs.getString("title"));
                b.setDescription(rs.getString("description"));
                b.setSellingPrice(rs.getDouble("sellingPrice"));
                b.setQuantity(rs.getInt("quantity"));
                b.setPublicationDate(rs.getDate("publicationDate"));
                b.setEdition(rs.getInt("edition"));
                b.setFormat(rs.getString("format_name"));
                b.setLanguage(rs.getString("language_name"));
                b.setAuthor(rs.getString("author_name"));
                b.setPublisher(rs.getString("publisher_name"));
                b.setImportPrice(rs.getDouble("importPrice"));
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== THÊM SÁCH MỚI =====
    public void insert(Book b) {
        String sql =
            "INSERT INTO book (isbn, title, description, sellingPrice, quantity, publicationDate, edition, "
          + "format_id, height, width, weight, recommendAge_id, language_id, "
          + "author_id, publisher_id, metadata_id) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection cn = DatabaseConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, b.getIsbn());
            ps.setString(2, b.getTitle());
            ps.setString(3, b.getDescription());
            ps.setDouble(4, b.getSellingPrice());
            ps.setInt(5, b.getQuantity());
            ps.setDate(6, new java.sql.Date(b.getPublicationDate().getTime()));
            ps.setInt(7, b.getEdition());
            ps.setInt(8, b.getFormatId());
            ps.setDouble(9, b.getHeight());
            ps.setDouble(10, b.getWidth());
            ps.setDouble(11, b.getWeight());
            ps.setInt(12, b.getRecommendAgeId());
            ps.setInt(13, b.getLanguageId());
            ps.setLong(14, b.getAuthorId());
            ps.setLong(15, b.getPublisherId());
            ps.setLong(16, b.getMetadataId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== CẬP NHẬT SÁCH =====
    public void update(Book b) {
        String sql =
            "UPDATE book "
          + "SET isbn=?, title=?, description=?, sellingPrice=?, quantity=?, publicationDate=?, edition=?, "
          + "format_id=?, height=?, width=?, weight=?, recommendAge_id=?, language_id=?, "
          + "author_id=?, publisher_id=?, metadata_id=? "
          + "WHERE id=?;";

        try (Connection cn = DatabaseConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, b.getIsbn());
            ps.setString(2, b.getTitle());
            ps.setString(3, b.getDescription());
            ps.setDouble(4, b.getSellingPrice());
            ps.setInt(5, b.getQuantity());
            ps.setDate(6, new java.sql.Date(b.getPublicationDate().getTime()));
            ps.setInt(7, b.getEdition());
            ps.setInt(8, b.getFormatId());
            ps.setDouble(9, b.getHeight());
            ps.setDouble(10, b.getWidth());
            ps.setDouble(11, b.getWeight());
            ps.setInt(12, b.getRecommendAgeId());
            ps.setInt(13, b.getLanguageId());
            ps.setLong(14, b.getAuthorId());
            ps.setLong(15, b.getPublisherId());
            ps.setLong(16, b.getMetadataId());
            ps.setLong(17, b.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== XÓA SÁCH =====
    public void delete(long id) {
        String sql = "DELETE FROM book WHERE id=?";
        try (Connection cn = DatabaseConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== THỐNG KÊ =====
    public int countBooks() {
        String sql = "SELECT COUNT(*) FROM book";
        try (Connection cn = DatabaseConnection.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int countOutOfStock() {
        String sql = "SELECT COUNT(*) FROM book WHERE quantity = 0";
        try (Connection cn = DatabaseConnection.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int countLowStock() {
        String sql = "SELECT COUNT(*) FROM book WHERE quantity > 0 AND quantity < 10";
        try (Connection cn = DatabaseConnection.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Book> getLowStockBooks() {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT id, title, quantity FROM book WHERE quantity > 0 AND quantity < 10";
        try (Connection cn = DatabaseConnection.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getLong("id"));
                b.setTitle(rs.getString("title"));
                b.setQuantity(rs.getInt("quantity"));
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
