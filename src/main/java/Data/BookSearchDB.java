package Data;

import Model.BookCard;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class BookSearchDB {
    private final DataSource ds;
    public BookSearchDB(DataSource ds) { this.ds = ds; }
    private Connection c() throws SQLException { return ds.getConnection(); }

    public Result search(String q, int page, int pageSize) throws SQLException {
        if (page < 1) page = 1;
        int offset = (page - 1) * pageSize;
        String like = "%" + q + "%";

        String WHERE = " WHERE LOWER(title) LIKE LOWER(?) " +
                       "    OR LOWER(authors) LIKE LOWER(?) " +
                       "    OR LOWER(publisher) LIKE LOWER(?) " +
                       "    OR LOWER(category) LIKE LOWER(?) " +
                       "    OR LOWER(subcategory) LIKE LOWER(?) " +
                       "    OR LOWER(tags) LIKE LOWER(?) " +
                       "    OR LOWER(description) LIKE LOWER(?) ";

        long total = 0;
        try (Connection cn = c();
             PreparedStatement ps = cn.prepareStatement("SELECT COUNT(*) FROM v_book_search" + WHERE)) {
            for (int i=1;i<=7;i++) ps.setString(i, like);
            try (ResultSet rs = ps.executeQuery()) { rs.next(); total = rs.getLong(1); }
        }

        List<BookCard> items = new ArrayList<>();
        String SQL = "SELECT book_id,title,authors,publisher,category,subcategory,price,cover_url " +
                     "FROM v_book_search" + WHERE + " ORDER BY book_id DESC LIMIT ? OFFSET ?";

        try (Connection cn = c(); PreparedStatement ps = cn.prepareStatement(SQL)) {
            int idx=1;
            for (; idx<=7; idx++) ps.setString(idx, like);
            ps.setInt(idx++, pageSize);
            ps.setInt(idx  , offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BookCard b = new BookCard();
                    b.setId(rs.getLong("book_id"));
                    b.setTitle(rs.getString("title"));
                    b.setAuthors(rs.getString("authors"));
                    b.setPublisher(rs.getString("publisher"));
                    b.setCategory(rs.getString("category"));
                    b.setSubcategory(rs.getString("subcategory"));
                    b.setPrice(rs.getDouble("price"));
                    b.setCoverUrl(rs.getString("cover_url"));
                    items.add(b);
                }
            }
        }

        return new Result(items, total, page, pageSize, q);
    }

    public static class Result {
        public final List<BookCard> items;
        public final long total;
        public final int page, pageSize;
        public final String q;
        public Result(List<BookCard> items, long total, int page, int pageSize, String q) {
            this.items = items; this.total = total; this.page = page; this.pageSize = pageSize; this.q = q;
        }
        public int totalPages() { return (int)Math.max(1, Math.ceil(total / (double)pageSize)); }
    }
}
