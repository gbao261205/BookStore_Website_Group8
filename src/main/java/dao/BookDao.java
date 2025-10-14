package dao;

import model.Book;
import java.sql.*;
import java.util.*;

public class BookDao {

    // ===== CRUD CƠ BẢN =====
    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM Book";
        try (Connection cn = DatabaseConnection.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getLong("id"));
                b.setTitle(rs.getString("title"));
                b.setDescription(rs.getString("description"));
                b.setSellingPrice(rs.getDouble("sellingPrice"));
                b.setFormat(rs.getString("format"));
                b.setActive(rs.getBoolean("is_active"));
                b.setCoverPath(rs.getString("cover_path"));
                b.setEbookPath(rs.getString("ebook_path"));
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(Book b) {
        String sql = "INSERT INTO Book (title, subCategory_id, description, sellingPrice, format, " +
                     "is_active, slug, meta_title, meta_description, cover_path, ebook_path, quantity) " +
                     "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection cn = DatabaseConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, b.getTitle());
            ps.setLong(2, b.getSubCategoryId());
            ps.setString(3, b.getDescription());
            ps.setDouble(4, b.getSellingPrice());
            ps.setString(5, b.getFormat());
            ps.setBoolean(6, b.isActive());
            ps.setString(7, b.getSlug());
            ps.setString(8, b.getMetaTitle());
            ps.setString(9, b.getMetaDescription());
            ps.setString(10, b.getCoverPath());
            ps.setString(11, b.getEbookPath());
            ps.setInt(12, b.getQuantity());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Book b) {
        String sql = "UPDATE Book SET title=?, description=?, sellingPrice=?, format=?, " +
                     "is_active=?, slug=?, meta_title=?, meta_description=?, cover_path=?, ebook_path=?, quantity=? WHERE id=?";
        try (Connection cn = DatabaseConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getDescription());
            ps.setDouble(3, b.getSellingPrice());
            ps.setString(4, b.getFormat());
            ps.setBoolean(5, b.isActive());
            ps.setString(6, b.getSlug());
            ps.setString(7, b.getMetaTitle());
            ps.setString(8, b.getMetaDescription());
            ps.setString(9, b.getCoverPath());
            ps.setString(10, b.getEbookPath());
            ps.setInt(11, b.getQuantity());
            ps.setLong(12, b.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(long id) {
        String sql = "DELETE FROM Book WHERE id=?";
        try (Connection cn = DatabaseConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== HÀM CHO DASHBOARD =====

    // Tổng số sách
    public int countBooks() {
        String sql = "SELECT COUNT(*) FROM Book";
        try (Connection cn = DatabaseConnection.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Sách hết hàng (quantity = 0)
    public int countOutOfStock() {
        String sql = "SELECT COUNT(*) FROM Book WHERE quantity = 0";
        try (Connection cn = DatabaseConnection.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Sách sắp hết hàng (1 ≤ quantity < 10)
    public int countLowStock() {
        String sql = "SELECT COUNT(*) FROM Book WHERE quantity BETWEEN 1 AND 9";
        try (Connection cn = DatabaseConnection.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public List<Book> getLowStockBooks() 
    {
    List<Book> list = new ArrayList<>();
    String sql = "SELECT * FROM Book WHERE quantity < 2";
    try (Connection cn = DatabaseConnection.getConnection();
         Statement st = cn.createStatement();
         ResultSet rs = st.executeQuery(sql)) 
    {
        while (rs.next()) 
        {
            Book b = new Book();
            b.setId(rs.getLong("id"));
            b.setTitle(rs.getString("title"));
            b.setDescription(rs.getString("description"));
            b.setSellingPrice(rs.getDouble("sellingPrice"));
            b.setFormat(rs.getString("format"));
            b.setActive(rs.getBoolean("is_active"));
            b.setCoverPath(rs.getString("cover_path"));
            b.setEbookPath(rs.getString("ebook_path"));
            b.setQuantity(rs.getInt("quantity"));
            list.add(b);
        }
    } 
    catch (Exception e) 
    {
        e.printStackTrace();
    }
    return list;
    }

}
