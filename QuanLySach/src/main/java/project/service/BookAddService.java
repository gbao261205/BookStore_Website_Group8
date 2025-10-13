package project.service;

import project.model.Book;
import project.dao.BookDAO;
import project.dao.DBConnection;

import java.sql.*;
import java.util.Date;

public class BookAddService {

    public static boolean addBook(Book b) {
        try {
            long metaId = 0;
            String sqlMeta = "INSERT INTO book_metadata (openDate, importPrice) VALUES (?, ?)";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sqlMeta, Statement.RETURN_GENERATED_KEYS)) {
                ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                ps.setDouble(2, b.getImportPrice());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) metaId = rs.getLong(1);
            }
            b.setMetadataId(metaId);

            BookDAO dao = new BookDAO();
            return dao.insertBook(b);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
