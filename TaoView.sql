USE BookStore;
-- Ảnh bìa: lấy 1 ảnh đầu tiên của template (nếu có)
-- View kết quả tìm kiếm sách
CREATE OR REPLACE VIEW v_book_search AS
SELECT
  b.id                        AS book_id,
  COALESCE(b.title, bt.title) AS title,            -- ưu tiên Book.title, fallback Template.title
  GROUP_CONCAT(DISTINCT a.fullName SEPARATOR ', ') AS authors,
  p.name                     AS publisher,
  c.name                     AS category,
  sc.name                    AS subcategory,
  b.description              AS description,
  b.sellingPrice             AS price,
  bt.status                  AS template_status,
  bt.language                AS language,
  (SELECT mf.storedCode
     FROM BookTemplateMediaFiles btmf
     JOIN MediaFile mf ON mf.id = btmf.mediaFile_id
    WHERE btmf.bookTemplate_id = bt.id
    ORDER BY mf.id ASC
    LIMIT 1)                 AS cover_url,
  GROUP_CONCAT(DISTINCT tg.name SEPARATOR ', ')    AS tags
FROM Book b
LEFT JOIN SubCategory sc   ON sc.id = b.subCategory_id
LEFT JOIN Category c       ON c.id = sc.category_id
LEFT JOIN BookTemplate bt  ON bt.id = b.template_id
LEFT JOIN Publisher p      ON p.id = bt.publisher_id
LEFT JOIN BookTemplateAuthors bta ON bta.bookTemplate_id = bt.id
LEFT JOIN Author a         ON a.id = bta.author_id
LEFT JOIN BookMetadata bm  ON bm.id = b.metadata_id
LEFT JOIN BookMetadataTags bmt ON bmt.metadata_id = bm.id
LEFT JOIN Tag tg           ON tg.id = bmt.tag_id
GROUP BY
  b.id, title, publisher, category, subcategory, description, price,
  template_status, language, cover_url;

-- Chỉ số gợi ý (tăng tốc LIKE)
CREATE INDEX idx_book_title       ON Book(title);
CREATE INDEX idx_bt_title         ON BookTemplate(title);
CREATE INDEX idx_author_name      ON Author(fullName);
CREATE INDEX idx_cat_name         ON Category(name);
CREATE INDEX idx_subcat_name      ON SubCategory(name);
CREATE INDEX idx_publisher_name   ON Publisher(name);
