USE WEB_BOOKSTORE;

-- 1) Tạo 1 tài khoản ADMIN
SET @acc_id := NULL;
INSERT INTO account(username, password_hash)
VALUES ('admin', SHA2('admin123', 256));
SET @acc_id := LAST_INSERT_ID();

INSERT INTO user(account_id, full_name, email, phone, gender_id)
VALUES (@acc_id, 'System Admin', 'admin@example.com', '0900000000', 1);

-- Bật vai trò admin (bảng admin FK -> user.id)
INSERT INTO admin(id) SELECT id FROM user WHERE account_id = @acc_id;

-- 2) Tạo 1 Category + Subcategory
INSERT INTO category(name, slug) VALUES ('Sách Kỹ thuật', 'sach-ky-thuat');
SET @cat_id := LAST_INSERT_ID();
INSERT INTO subcategory(category_id, name, slug)
VALUES (@cat_id, 'Lập trình', 'lap-trinh');
SET @subcat_id := LAST_INSERT_ID();

-- 3) Publisher + Author
INSERT INTO publisher(name, website) VALUES ('NXB Khoa Học', 'https://nxbkhoahoc.example');
SET @pub_id := LAST_INSERT_ID();

INSERT INTO author(name, bio) VALUES ('Nguyễn Văn A', 'Tác giả chuyên về lập trình.');
SET @auth_id := LAST_INSERT_ID();

-- 4) 1 SÁCH in (PRINTBOOK), tiếng Việt (có thể để NULL), đủ FK
INSERT INTO book(
  title, slug, sku, isbn13, description,
  price, discount_percent, stock, pages, size,
  format_id, language_id, publisher_id, release_date, cover_url
)
VALUES (
  'Java Cơ Bản',
  'java-co-ban',
  'SKU-JAVA-0001',
  '978-6040000000',
  'Giáo trình nhập môn Java cho sinh viên CNTT.',
  120000, 0.00, 50, 420, '16x24',
  (SELECT id FROM book_format WHERE code='PRINTBOOK'),
  (SELECT id FROM ebook_language WHERE code='VIETNAMESE'),
  @pub_id,
  '2024-09-01',
  'https://example.com/covers/java-co-ban.jpg'
);
SET @book_id := LAST_INSERT_ID();

-- Liên kết SÁCH với tác giả + subcategory
INSERT INTO book_author(book_id, author_id) VALUES (@book_id, @auth_id);
INSERT INTO book_subcategory(book_id, subcategory_id) VALUES (@book_id, @subcat_id);
