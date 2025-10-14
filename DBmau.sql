USE web_bookstore;
-- =========================
-- SAMPLE DATA (10 BOOKS)
-- Run AFTER your DDL
-- =========================
START TRANSACTION;

-- -------- Catalog: Category / Subcategory / Tag / Author / Publisher --------
INSERT INTO category (name, slug) VALUES
('Fiction', 'fiction'),
('Non-Fiction', 'non-fiction'),
('Technology', 'technology'),
('Children', 'children');

INSERT INTO subcategory (category_id, name, slug) VALUES
((SELECT id FROM category WHERE slug='fiction'), 'Science Fiction', 'science-fiction'),
((SELECT id FROM category WHERE slug='fiction'), 'Mystery & Thriller', 'mystery-thriller'),
((SELECT id FROM category WHERE slug='fiction'), 'Romance', 'romance'),
((SELECT id FROM category WHERE slug='non-fiction'), 'History', 'history'),
((SELECT id FROM category WHERE slug='non-fiction'), 'Business', 'business'),
((SELECT id FROM category WHERE slug='non-fiction'), 'Self-Help', 'self-help'),
((SELECT id FROM category WHERE slug='technology'), 'Programming', 'programming'),
((SELECT id FROM category WHERE slug='technology'), 'Data Science', 'data-science'),
((SELECT id FROM category WHERE slug='children'), 'Picture Books', 'picture-books'),
((SELECT id FROM category WHERE slug='children'), 'Young Adult', 'young-adult');

INSERT INTO tag (name, slug) VALUES
('Bestseller', 'bestseller'),
('New Release', 'new-release'),
('Award Winning', 'award-winning'),
('Vietnamese Author', 'vietnamese-author'),
('Translated', 'translated'),
('Classic', 'classic');

INSERT INTO author (name, bio) VALUES
('Nguyen Nhat Anh', 'Vietnamese author of children and YA literature.'),
('Haruki Murakami', 'Japanese novelist, essays, and translator.'),
('Yuval Noah Harari', 'Historian and author of popular science books.'),
('Robert C. Martin', 'Software engineer and author (Uncle Bob).'),
('Martin Fowler', 'Software developer, author, speaker.'),
('Colleen Hoover', 'American author of romance.'),
('Agatha Christie', 'Queen of mystery.'),
('Andy Weir', 'Science fiction author.'),
('Eric Matthes', 'Programming educator.'),
('Bui Anh Tuan', 'Vietnamese writer.');

INSERT INTO publisher (name, website) VALUES
('NXB Tre', 'https://nxbtre.vn'),
('Alfred A. Knopf', 'https://knopfdoubleday.com'),
('HarperCollins', 'https://www.harpercollins.com'),
('Pearson', 'https://www.pearson.com'),
('O''Reilly Media', 'https://www.oreilly.com'),
('NXB Kim Dong', 'https://nxbkimdong.com.vn');

-- -------- Media files (placeholder) --------
INSERT INTO media_file (filename, storage_url, status_id) VALUES
('mat-biec.jpg',       'https://cdn.example.com/book/mat-biec.jpg',       (SELECT id FROM ebook_status WHERE code='IN_STOCK')),
('norwegian-wood.jpg', 'https://cdn.example.com/book/norwegian-wood.jpg', (SELECT id FROM ebook_status WHERE code='IN_STOCK')),
('sapiens.jpg',        'https://cdn.example.com/book/sapiens.jpg',        (SELECT id FROM ebook_status WHERE code='IN_STOCK')),
('clean-code.jpg',     'https://cdn.example.com/book/clean-code.jpg',     (SELECT id FROM ebook_status WHERE code='IN_STOCK')),
('refactoring.jpg',    'https://cdn.example.com/book/refactoring.jpg',    (SELECT id FROM ebook_status WHERE code='IN_STOCK')),
('verity.jpg',         'https://cdn.example.com/book/verity.jpg',         (SELECT id FROM ebook_status WHERE code='IN_STOCK')),
('and-then.jpg',       'https://cdn.example.com/book/and-then.jpg',       (SELECT id FROM ebook_status WHERE code='IN_STOCK')),
('project-hail.jpg',   'https://cdn.example.com/book/project-hail.jpg',   (SELECT id FROM ebook_status WHERE code='IN_STOCK')),
('python-crash.jpg',   'https://cdn.example.com/book/python-crash.jpg',   (SELECT id FROM ebook_status WHERE code='IN_STOCK')),
('cho-toi-xin.jpg',    'https://cdn.example.com/book/cho-toi-xin.jpg',    (SELECT id FROM ebook_status WHERE code='IN_STOCK'));

-- -------- 10 Books --------
INSERT INTO book
(title, slug, sku, isbn10, isbn13, description, price, discount_percent, stock, weight_grams, pages, size, format_id, language_id, publisher_id, release_date, cover_url)
VALUES
('Mắt Biếc', 'mat-biec', 'SKU-MB-001', '6041111111', '9786041111111',
 'Tác phẩm nổi tiếng của Nguyễn Nhật Ánh.', 90000, 0.00, 120, 300, 320, '14x20 cm',
 (SELECT id FROM book_format WHERE code='PRINTBOOK'),
 (SELECT id FROM ebook_language WHERE code='VIETNAMESE'),
 (SELECT id FROM publisher WHERE name='NXB Tre'), '1990-06-01',
 'https://cdn.example.com/book/mat-biec.jpg'),

('Norwegian Wood', 'norwegian-wood', 'SKU-NW-002', '0375704027', '9780375704024',
 'A coming-of-age novel by Haruki Murakami.', 220000, 10.00, 80, 350, 296, '13x20 cm',
 (SELECT id FROM book_format WHERE code='PRINTBOOK'),
 (SELECT id FROM ebook_language WHERE code='ENGLISH'),
 (SELECT id FROM publisher WHERE name='Alfred A. Knopf'), '1987-09-04',
 'https://cdn.example.com/book/norwegian-wood.jpg'),

('Sapiens: A Brief History of Humankind', 'sapiens', 'SKU-SA-003', '0062316095', '9780062316097',
 'How Homo sapiens conquered the world.', 320000, 5.00, 60, 500, 498, '16x24 cm',
 (SELECT id FROM book_format WHERE code='PRINTBOOK'),
 (SELECT id FROM ebook_language WHERE code='ENGLISH'),
 (SELECT id FROM publisher WHERE name='HarperCollins'), '2011-01-01',
 'https://cdn.example.com/book/sapiens.jpg'),

('Clean Code', 'clean-code', 'SKU-CC-004', '0132350882', '9780132350884',
 'A Handbook of Agile Software Craftsmanship.', 650000, 0.00, 40, 700, 464, '17x24 cm',
 (SELECT id FROM book_format WHERE code='PRINTBOOK'),
 (SELECT id FROM ebook_language WHERE code='ENGLISH'),
 (SELECT id FROM publisher WHERE name='Pearson'), '2008-08-01',
 'https://cdn.example.com/book/clean-code.jpg'),

('Refactoring (2nd Edition)', 'refactoring-2e', 'SKU-RF-005', '0134757599', '9780134757599',
 'Improving the Design of Existing Code.', 790000, 0.00, 35, 800, 448, '17x24 cm',
 (SELECT id FROM book_format WHERE code='PRINTBOOK'),
 (SELECT id FROM ebook_language WHERE code='ENGLISH'),
 (SELECT id FROM publisher WHERE name='Pearson'), '2018-11-19',
 'https://cdn.example.com/book/refactoring.jpg'),

('Verity', 'verity', 'SKU-VE-006', '1791392792', '9781791392796',
 'A romantic thriller by Colleen Hoover.', 250000, 0.00, 75, 350, 336, '13x20 cm',
 (SELECT id FROM book_format WHERE code='PRINTBOOK'),
 (SELECT id FROM ebook_language WHERE code='ENGLISH'),
 (SELECT id FROM publisher WHERE name='HarperCollins'), '2018-12-07',
 'https://cdn.example.com/book/verity.jpg'),

('And Then There Were None', 'and-then-there-were-none', 'SKU-AT-007', '0062073486', '9780062073488',
 'Classic mystery by Agatha Christie.', 180000, 0.00, 90, 280, 272, '13x20 cm',
 (SELECT id FROM book_format WHERE code='PRINTBOOK'),
 (SELECT id FROM ebook_language WHERE code='ENGLISH'),
 (SELECT id FROM publisher WHERE name='HarperCollins'), '1939-11-06',
 'https://cdn.example.com/book/and-then.jpg'),

('Project Hail Mary', 'project-hail-mary', 'SKU-PH-008', '0593135202', '9780593135204',
 'Sci-fi survival by Andy Weir.', 420000, 0.00, 55, 600, 496, '16x24 cm',
 (SELECT id FROM book_format WHERE code='PRINTBOOK'),
 (SELECT id FROM ebook_language WHERE code='ENGLISH'),
 (SELECT id FROM publisher WHERE name='Alfred A. Knopf'), '2021-05-04',
 'https://cdn.example.com/book/project-hail.jpg'),

('Python Crash Course (3rd Ed.)', 'python-crash-course-3e', 'SKU-PC-009', '1718502702', '9781718502703',
 'A fast-paced, no-nonsense guide to programming in Python.', 950000, 0.00, 50, 900, 544, '18x25 cm',
 (SELECT id FROM book_format WHERE code='PRINTBOOK'),
 (SELECT id FROM ebook_language WHERE code='ENGLISH'),
 (SELECT id FROM publisher WHERE name='O''Reilly Media'), '2023-05-01',
 'https://cdn.example.com/book/python-crash.jpg'),

('Cho Tôi Xin Một Vé Đi Tuổi Thơ', 'cho-toi-xin-mot-ve-di-tuoi-tho', 'SKU-CT-010', '6042111111', '9786042111111',
 'Tác phẩm thiếu nhi đặc sắc của Nguyễn Nhật Ánh.', 85000, 0.00, 150, 280, 208, '14x20 cm',
 (SELECT id FROM book_format WHERE code='PRINTBOOK'),
 (SELECT id FROM ebook_language WHERE code='VIETNAMESE'),
 (SELECT id FROM publisher WHERE name='NXB Kim Dong'), '2007-05-20',
 'https://cdn.example.com/book/cho-toi-xin.jpg');

-- -------- Book ↔ Author --------
INSERT INTO book_author (book_id, author_id)
SELECT b.id, a.id FROM book b JOIN author a
WHERE b.slug='mat-biec' AND a.name='Nguyen Nhat Anh';
INSERT INTO book_author (book_id, author_id)
SELECT b.id, a.id FROM book b JOIN author a
WHERE b.slug='norwegian-wood' AND a.name='Haruki Murakami';
INSERT INTO book_author (book_id, author_id)
SELECT b.id, a.id FROM book b JOIN author a
WHERE b.slug='sapiens' AND a.name='Yuval Noah Harari';
INSERT INTO book_author (book_id, author_id)
SELECT b.id, a.id FROM book b JOIN author a
WHERE b.slug='clean-code' AND a.name='Robert C. Martin';
INSERT INTO book_author (book_id, author_id)
SELECT b.id, a.id FROM book b JOIN author a
WHERE b.slug='refactoring-2e' AND a.name='Martin Fowler';
INSERT INTO book_author (book_id, author_id)
SELECT b.id, a.id FROM book b JOIN author a
WHERE b.slug='verity' AND a.name='Colleen Hoover';
INSERT INTO book_author (book_id, author_id)
SELECT b.id, a.id FROM book b JOIN author a
WHERE b.slug='and-then-there-were-none' AND a.name='Agatha Christie';
INSERT INTO book_author (book_id, author_id)
SELECT b.id, a.id FROM book b JOIN author a
WHERE b.slug='project-hail-mary' AND a.name='Andy Weir';
INSERT INTO book_author (book_id, author_id)
SELECT b.id, a.id FROM book b JOIN author a
WHERE b.slug='python-crash-course-3e' AND a.name='Eric Matthes';
INSERT INTO book_author (book_id, author_id)
SELECT b.id, a.id FROM book b JOIN author a
WHERE b.slug='cho-toi-xin-mot-ve-di-tuoi-tho' AND a.name='Nguyen Nhat Anh';

-- -------- Book ↔ Subcategory (classification) --------
-- Mắt Biếc & Cho Tôi Xin... -> Children/YA
INSERT INTO book_subcategory (book_id, subcategory_id)
SELECT b.id, s.id FROM book b JOIN subcategory s
WHERE b.slug='mat-biec' AND s.slug='young-adult';
INSERT INTO book_subcategory (book_id, subcategory_id)
SELECT b.id, s.id FROM book b JOIN subcategory s
WHERE b.slug='cho-toi-xin-mot-ve-di-tuoi-tho' AND s.slug='young-adult';

-- Norwegian Wood -> Romance
INSERT INTO book_subcategory (book_id, subcategory_id)
SELECT b.id, s.id FROM book b JOIN subcategory s
WHERE b.slug='norwegian-wood' AND s.slug='romance';

-- Sapiens -> History
INSERT INTO book_subcategory (book_id, subcategory_id)
SELECT b.id, s.id FROM book b JOIN subcategory s
WHERE b.slug='sapiens' AND s.slug='history';

-- Clean Code, Refactoring, Python Crash -> Programming
INSERT INTO book_subcategory (book_id, subcategory_id)
SELECT b.id, s.id FROM book b JOIN subcategory s
WHERE b.slug='clean-code' AND s.slug='programming';
INSERT INTO book_subcategory (book_id, subcategory_id)
SELECT b.id, s.id FROM book b JOIN subcategory s
WHERE b.slug='refactoring-2e' AND s.slug='programming';
INSERT INTO book_subcategory (book_id, subcategory_id)
SELECT b.id, s.id FROM book b JOIN subcategory s
WHERE b.slug='python-crash-course-3e' AND s.slug='programming';

-- Verity -> Romance (thriller blend)
INSERT INTO book_subcategory (book_id, subcategory_id)
SELECT b.id, s.id FROM book b JOIN subcategory s
WHERE b.slug='verity' AND s.slug='romance';

-- And Then There Were None -> Mystery & Thriller
INSERT INTO book_subcategory (book_id, subcategory_id)
SELECT b.id, s.id FROM book b JOIN subcategory s
WHERE b.slug='and-then-there-were-none' AND s.slug='mystery-thriller';

-- Project Hail Mary -> Science Fiction
INSERT INTO book_subcategory (book_id, subcategory_id)
SELECT b.id, s.id FROM book b JOIN subcategory s
WHERE b.slug='project-hail-mary' AND s.slug='science-fiction';

-- -------- Book ↔ Tag --------
-- A few examples per book
INSERT INTO book_tag (book_id, tag_id)
SELECT b.id, t.id FROM book b JOIN tag t
WHERE b.slug='mat-biec' AND t.slug='vietnamese-author';
INSERT INTO book_tag (book_id, tag_id)
SELECT b.id, t.id FROM book b JOIN tag t
WHERE b.slug='mat-biec' AND t.slug='classic';

INSERT INTO book_tag (book_id, tag_id)
SELECT b.id, t.id FROM book b JOIN tag t
WHERE b.slug='norwegian-wood' AND t.slug='translated';

INSERT INTO book_tag (book_id, tag_id)
SELECT b.id, t.id FROM book b JOIN tag t
WHERE b.slug='sapiens' AND t.slug='bestseller';

INSERT INTO book_tag (book_id, tag_id)
SELECT b.id, t.id FROM book b JOIN tag t
WHERE b.slug='clean-code' AND t.slug='award-winning';

INSERT INTO book_tag (book_id, tag_id)
SELECT b.id, t.id FROM book b JOIN tag t
WHERE b.slug='refactoring-2e' AND t.slug='award-winning';

INSERT INTO book_tag (book_id, tag_id)
SELECT b.id, t.id FROM book b JOIN tag t
WHERE b.slug='verity' AND t.slug='new-release';

INSERT INTO book_tag (book_id, tag_id)
SELECT b.id, t.id FROM book b JOIN tag t
WHERE b.slug='and-then-there-were-none' AND t.slug='classic';

INSERT INTO book_tag (book_id, tag_id)
SELECT b.id, t.id FROM book b JOIN tag t
WHERE b.slug='project-hail-mary' AND t.slug='bestseller';

INSERT INTO book_tag (book_id, tag_id)
SELECT b.id, t.id FROM book b JOIN tag t
WHERE b.slug='python-crash-course-3e' AND t.slug='bestseller';

-- -------- Book ↔ Media (cover) --------
INSERT INTO book_media (book_id, media_file_id, is_default)
SELECT b.id, m.id, TRUE FROM book b JOIN media_file m
WHERE b.slug='mat-biec' AND m.filename='mat-biec.jpg';
INSERT INTO book_media (book_id, media_file_id, is_default)
SELECT b.id, m.id, TRUE FROM book b JOIN media_file m
WHERE b.slug='norwegian-wood' AND m.filename='norwegian-wood.jpg';
INSERT INTO book_media (book_id, media_file_id, is_default)
SELECT b.id, m.id, TRUE FROM book b JOIN media_file m
WHERE b.slug='sapiens' AND m.filename='sapiens.jpg';
INSERT INTO book_media (book_id, media_file_id, is_default)
SELECT b.id, m.id, TRUE FROM book b JOIN media_file m
WHERE b.slug='clean-code' AND m.filename='clean-code.jpg';
INSERT INTO book_media (book_id, media_file_id, is_default)
SELECT b.id, m.id, TRUE FROM book b JOIN media_file m
WHERE b.slug='refactoring-2e' AND m.filename='refactoring.jpg';
INSERT INTO book_media (book_id, media_file_id, is_default)
SELECT b.id, m.id, TRUE FROM book b JOIN media_file m
WHERE b.slug='verity' AND m.filename='verity.jpg';
INSERT INTO book_media (book_id, media_file_id, is_default)
SELECT b.id, m.id, TRUE FROM book b JOIN media_file m
WHERE b.slug='and-then-there-were-none' AND m.filename='and-then.jpg';
INSERT INTO book_media (book_id, media_file_id, is_default)
SELECT b.id, m.id, TRUE FROM book b JOIN media_file m
WHERE b.slug='project-hail-mary' AND m.filename='project-hail.jpg';
INSERT INTO book_media (book_id, media_file_id, is_default)
SELECT b.id, m.id, TRUE FROM book b JOIN media_file m
WHERE b.slug='python-crash-course-3e' AND m.filename='python-crash.jpg';
INSERT INTO book_media (book_id, media_file_id, is_default)
SELECT b.id, m.id, TRUE FROM book b JOIN media_file m
WHERE b.slug='cho-toi-xin-mot-ve-di-tuoi-tho' AND m.filename='cho-toi-xin.jpg';

COMMIT;
