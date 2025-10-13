package project.controller;

import project.dao.BookDAO;
import project.model.Book;
import project.dao.DBConnection;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private BookDAO bookDAO;

    @Override
    public void init() {
        bookDAO = new BookDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Book> allBooks = bookDAO.getAllBooks();

        int pageSize = 8;
        int page = 1;
        if (req.getParameter("page") != null) {
            try {
                page = Integer.parseInt(req.getParameter("page"));
            } catch (NumberFormatException ignored) {}
        }

        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, allBooks.size());
        List<Book> books = allBooks.subList(start, end);

        req.setAttribute("books", books);
        req.setAttribute("page", page);
        req.setAttribute("totalPages", (int) Math.ceil(allBooks.size() / (double) pageSize));
        req.setAttribute("totalBooks", allBooks.size());

        // Lấy message nếu có
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("message") != null) {
            req.setAttribute("message", session.getAttribute("message"));
            session.removeAttribute("message");
        }

        RequestDispatcher rd = req.getRequestDispatcher("books.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            req.setCharacterEncoding("UTF-8");

            // ===== LẤY DỮ LIỆU =====
            String isbn = req.getParameter("isbn");
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            double sellingPrice = Double.parseDouble(req.getParameter("sellingPrice"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            java.sql.Date pubDate = java.sql.Date.valueOf(req.getParameter("publicationDate"));
            int edition = Integer.parseInt(req.getParameter("edition"));
            int formatId = Integer.parseInt(req.getParameter("format_id"));
            String size = req.getParameter("size");
            double weight = Double.parseDouble(req.getParameter("weight"));
            int recommendAgeId = Integer.parseInt(req.getParameter("recommendAge_id"));
            int languageId = Integer.parseInt(req.getParameter("language_id"));
            double importPrice = Double.parseDouble(req.getParameter("importPrice"));

            String authorName = req.getParameter("author_name");
            String publisherName = req.getParameter("publisher_name");

            // ===== KÍCH THƯỚC =====
            double height = 0, width = 0;
            if (size != null && size.contains("x")) {
                try {
                    String[] parts = size.toLowerCase().replace(" ", "").split("x");
                    height = Double.parseDouble(parts[0]);
                    width = Double.parseDouble(parts[1]);
                } catch (Exception ignored) {}
            }

            long authorId = 0, publisherId = 0, metadataId = 0;

            try (Connection conn = DBConnection.getConnection()) {
                // Author
                PreparedStatement psA = conn.prepareStatement("SELECT id FROM author WHERE name=? LIMIT 1");
                psA.setString(1, authorName);
                ResultSet rsA = psA.executeQuery();
                if (rsA.next()) {
                    authorId = rsA.getLong("id");
                } else {
                    PreparedStatement insA = conn.prepareStatement(
                        "INSERT INTO author (name, joinAt) VALUES (?, CURDATE())",
                        Statement.RETURN_GENERATED_KEYS);
                    insA.setString(1, authorName);
                    insA.executeUpdate();
                    ResultSet genA = insA.getGeneratedKeys();
                    if (genA.next()) authorId = genA.getLong(1);
                }

                // Publisher
                PreparedStatement psP = conn.prepareStatement("SELECT id FROM publisher WHERE name=? LIMIT 1");
                psP.setString(1, publisherName);
                ResultSet rsP = psP.executeQuery();
                if (rsP.next()) {
                    publisherId = rsP.getLong("id");
                } else {
                    PreparedStatement insP = conn.prepareStatement(
                        "INSERT INTO publisher (name, joinAt) VALUES (?, CURDATE())",
                        Statement.RETURN_GENERATED_KEYS);
                    insP.setString(1, publisherName);
                    insP.executeUpdate();
                    ResultSet genP = insP.getGeneratedKeys();
                    if (genP.next()) publisherId = genP.getLong(1);
                }

                // Metadata
                PreparedStatement psMeta = conn.prepareStatement(
                    "INSERT INTO book_metadata (openDate, importPrice) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
                psMeta.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
                psMeta.setDouble(2, importPrice);
                psMeta.executeUpdate();
                ResultSet genM = psMeta.getGeneratedKeys();
                if (genM.next()) metadataId = genM.getLong(1);
            }

            // ===== TẠO BOOK =====
            Book b = new Book();
            b.setIsbn(isbn);
            b.setTitle(title);
            b.setDescription(description);
            b.setSellingPrice(sellingPrice);
            b.setQuantity(quantity);
            b.setPublicationDate(new java.util.Date(pubDate.getTime()));
            b.setEdition(edition);
            b.setFormatId(formatId);
            b.setHeight(height);
            b.setWidth(width);
            b.setWeight(weight);
            b.setRecommendAgeId(recommendAgeId);
            b.setLanguageId(languageId);
            b.setAuthorId(authorId);
            b.setPublisherId(publisherId);
            b.setMetadataId(metadataId);
            b.setImportPrice(importPrice);
            b.setOpenDate(new java.util.Date());

            boolean success = bookDAO.insertBook(b);

            // ===== THÔNG BÁO SESSION =====
            HttpSession session = req.getSession();
            if (success)
                session.setAttribute("message", "✅ Thêm sách thành công!");
            else
                session.setAttribute("message", "❌ Có lỗi khi thêm sách!");

            resp.sendRedirect("books");

        } catch (Exception e) {
            e.printStackTrace();
            HttpSession session = req.getSession();
            session.setAttribute("message", "⚠️ Lỗi: " + e.getMessage());
            resp.sendRedirect("books");
        }
    }
}
