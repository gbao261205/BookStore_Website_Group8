package project.dao;

import java.sql.*;
import java.util.*;
import project.model.Book;

public class BookDAO {

    /**
     * Lấy danh sách tất cả sách từ cơ sở dữ liệu
     * (không còn cột status trong book_metadata)
     * Trạng thái được xác định theo quantity.
     */
    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();

        String sql = """
            SELECT 
                b.id, b.isbn, b.title, b.description,
                b.sellingPrice, b.quantity,
                b.publicationDate, b.edition,
                f.name AS format_name,
                b.height, b.width, b.weight,
                r.name AS age_label,
                l.name AS language_name,
                a.name AS author_name,
                p.name AS publisher_name,
                m.importPrice,
                m.openDate
            FROM book b
            LEFT JOIN ebook_format f ON b.format_id = f.id
            LEFT JOIN ebook_language l ON b.language_id = l.id
            LEFT JOIN ebook_age_recommend r ON b.recommendAge_id = r.id
            LEFT JOIN author a ON b.author_id = a.id
            LEFT JOIN publisher p ON b.publisher_id = p.id
            LEFT JOIN book_metadata m ON b.metadata_id = m.id
            ORDER BY b.id ASC
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
                b.setFormatName(rs.getString("format_name"));
                b.setHeight(rs.getDouble("height"));
                b.setWidth(rs.getDouble("width"));
                b.setWeight(rs.getDouble("weight"));
                b.setAgeLabel(rs.getString("age_label"));
                b.setLanguageName(rs.getString("language_name"));
                b.setAuthor(rs.getString("author_name"));
                b.setPublisher(rs.getString("publisher_name"));
                b.setImportPrice(rs.getDouble("importPrice"));
                b.setOpenDate(rs.getDate("openDate"));

                // ✅ Tự xác định trạng thái dựa vào quantity
                if (b.getQuantity() == 0) {
                    b.setStockStatus("SOLD");
                } else if (b.getQuantity() < 5) {
                    b.setStockStatus("LOW");
                } else {
                    b.setStockStatus("IN_STOCK");
                }

                list.add(b);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean insertBook(Book b) {
        String sql = """
            INSERT INTO book (
                isbn, title, description, sellingPrice, quantity,
                publicationDate, edition, format_id, height, width, weight,
                recommendAge_id, language_id, author_id, publisher_id, metadata_id
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

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

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
