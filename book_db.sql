-- ======================================================
-- ENUM TABLES (format, language, age)
-- ======================================================

CREATE TABLE ebook_format (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50)
);

CREATE TABLE ebook_language (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50)
);

CREATE TABLE ebook_age_recommend (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50)
);

-- ======================================================
-- AUTHOR & PUBLISHER
-- ======================================================

CREATE TABLE author (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    avatar VARCHAR(255),
    introduction TEXT,
    joinAt DATE
);

CREATE TABLE publisher (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    avatar VARCHAR(255),
    introduction TEXT,
    joinAt DATE
);

-- ======================================================
-- BOOK METADATA (đã bỏ cột status)
-- ======================================================

CREATE TABLE book_metadata (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    openDate DATETIME,
    importPrice DOUBLE
);

-- ======================================================
-- MAIN BOOK TABLE
-- ======================================================

CREATE TABLE book (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    isbn VARCHAR(20),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    sellingPrice DOUBLE,
    quantity INT DEFAULT 0,
    publicationDate DATE,
    edition INT,
    format_id INT,
    height DOUBLE,
    width DOUBLE,
    weight DOUBLE,
    recommendAge_id INT,
    language_id INT,
    author_id BIGINT,
    publisher_id BIGINT,
    metadata_id BIGINT,

    FOREIGN KEY (format_id) REFERENCES ebook_format(id),
    FOREIGN KEY (recommendAge_id) REFERENCES ebook_age_recommend(id),
    FOREIGN KEY (language_id) REFERENCES ebook_language(id),
    FOREIGN KEY (author_id) REFERENCES author(id),
    FOREIGN KEY (publisher_id) REFERENCES publisher(id),
    FOREIGN KEY (metadata_id) REFERENCES book_metadata(id)
);

-- ======================================================
-- ENUM DATA
-- ======================================================

INSERT INTO ebook_format (name) VALUES ('EBook'), ('PrintedBook');
INSERT INTO ebook_language (name) VALUES ('VIETNAMESE'), ('ENGLISH');
INSERT INTO ebook_age_recommend (name) VALUES
('ALL_AGES'), ('UNDER_3_YEARS_OLD'), ('3_TO_5_YEARS_OLD'),
('6_TO_15_YEARS_OLD'), ('16_TO_18_YEARS_OLD'), ('OVER_18_YEARS_OLD');

-- ======================================================
-- AUTHOR & PUBLISHER
-- ======================================================

INSERT INTO author (name, introduction, joinAt)
VALUES ('REKI KAWAHARA', 'Tác giả Nhật Bản, nổi tiếng với Sword Art Online.', '2020-01-01');

INSERT INTO publisher (name, introduction, joinAt)
VALUES ('IPM, Hà Nội', 'Nhà xuất bản tại Việt Nam chuyên mảng tiểu thuyết Nhật.', '2019-05-01');

-- ======================================================
-- BOOK METADATA
-- ======================================================

INSERT INTO book_metadata (openDate, importPrice)
VALUES 
('2023-01-01', 90000),
('2023-01-05', 95000),
('2023-01-10', 100000),
('2023-02-01', 105000),
('2023-03-01', 110000);

-- ======================================================
-- BOOK DATA
-- ======================================================

INSERT INTO book (
    isbn, title, description, sellingPrice, quantity, publicationDate, edition, 
    format_id, height, width, weight, recommendAge_id, language_id, 
    author_id, publisher_id, metadata_id
)
VALUES
('8935250707640', 'Sword Art Online Progressive Vol 7',
 'Để thấy Sword Art Online có không gian kể chuyện rất rộng, lại lạ mà rất thu hút.',
 120000, 10, '2022-12-01', 1, 2, 21.0, 15.0, 0.4, 1, 2, 1, 1, 1),

('8935250707641', 'Sword Art Online Progressive Vol 8',
 'Cuộc phiêu lưu của Kirito và Asuna trong tầng 7 đầy nguy hiểm.',
 125000, 12, '2023-01-10', 1, 2, 21.0, 15.0, 0.42, 1, 2, 1, 1, 2),

('8935250707642', 'Sword Art Online Progressive Vol 9',
 'Tầng 9 mở ra nhiều bí ẩn mới giữa hai nhân vật chính.',
 130000, 9, '2023-02-05', 1, 2, 21.0, 15.0, 0.43, 1, 2, 1, 1, 3),

('8935250707650', 'Sword Art Online Vol 1',
 'Mở đầu câu chuyện sinh tồn trong thế giới SAO.',
 130000, 0, '2023-03-01', 2, 2, 21.0, 15.0, 0.45, 1, 2, 1, 1, 4),

('8935250707651', 'Sword Art Online Vol 2',
 'Tiếp nối hành trình giải phóng tầng cao hơn của Aincrad.',
 130000, 7, '2023-03-15', 2, 2, 21.0, 15.0, 0.46, 1, 2, 1, 1, 5),

('8934974170010', 'Doraemon Tập 1',
 'Những cuộc phiêu lưu đầu tiên của chú mèo máy Doraemon cùng Nobita.',
 35000, 30, '2023-04-01', 1, 2, 18.0, 13.0, 0.3, 1, 1, 1, 1, 1);

-- Kiểm tra kết quả
ALTER TABLE book AUTO_INCREMENT = 12;
select * from book;


