
-- MySQL 8.0+ DDL for Online Bookstore (Web Bán Sách)
-- Engine: InnoDB, Charset: utf8mb4
-- NOTE: This schema is an implementation based on the provided class/EER diagram.
-- It covers entities, relationships, and lookup enums with reasonable assumptions.

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP DATABASE IF EXISTS WEB_BOOKSTORE;
CREATE DATABASE WEB_BOOKSTORE CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE WEB_BOOKSTORE;

-- =======================
-- Utility / Enum Lookups
-- =======================
CREATE TABLE gender (
  id TINYINT UNSIGNED PRIMARY KEY,
  code VARCHAR(10) NOT NULL UNIQUE
) COMMENT='MALE, FEMALE';

INSERT INTO gender (id, code) VALUES (1,'MALE'),(2,'FEMALE');

CREATE TABLE membership_tier (
  id TINYINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(20) NOT NULL UNIQUE, -- MEMBER, BRONZE, SILVER, GOLD, DIAMOND
  display_name VARCHAR(50) NOT NULL,
  discount_percent DECIMAL(5,2) NOT NULL DEFAULT 0.00,
  required_points INT NOT NULL DEFAULT 0
);

INSERT INTO membership_tier (code, display_name, discount_percent, required_points)
VALUES ('MEMBER','Member',0,0),
       ('BRONZE','Bronze',1,500),
       ('SILVER','Silver',2,1500),
       ('GOLD','Gold',3,3000),
       ('DIAMOND','Diamond',5,6000);

CREATE TABLE book_format (
  id TINYINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(20) NOT NULL UNIQUE -- EBOOK, PRINTBOOK
);
INSERT INTO book_format (code) VALUES ('EBOOK'),('PRINTBOOK');

CREATE TABLE ebook_language (
  id TINYINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(20) NOT NULL UNIQUE -- VIETNAMESE, ENGLISH
);
INSERT INTO ebook_language (code) VALUES ('VIETNAMESE'),('ENGLISH');

CREATE TABLE ebook_status (
  id TINYINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(20) NOT NULL UNIQUE -- IN_STOCK, SOLD
);
INSERT INTO ebook_status (code) VALUES ('IN_STOCK'),('SOLD');

CREATE TABLE order_status (
  id TINYINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(30) NOT NULL UNIQUE -- WAITING_PAYMENT, PAID, WAITING_CONFIRMATION, SHIPPING, COMPLETED, CANCELLED, REQUEST_REFUND, REFUNDED
);
INSERT INTO order_status (code) VALUES
('WAITING_PAYMENT'),('PAID'),('WAITING_CONFIRMATION'),('SHIPPING'),
('COMPLETED'),('CANCELLED'),('REQUEST_REFUND'),('REFUNDED');

CREATE TABLE order_history_flag (
  id TINYINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(30) NOT NULL UNIQUE -- PLACED, CONFIRMED, WAITING_FOR_SHIPPING, DELIVERED
);
INSERT INTO order_history_flag (code) VALUES ('PLACED'),('CONFIRMED'),('WAITING_FOR_SHIPPING'),('DELIVERED');

CREATE TABLE payment_method (
  id TINYINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(30) NOT NULL UNIQUE -- BANK_TRANSFER, COD, VNPAY, MOMO (extendable)
);
INSERT INTO payment_method (code) VALUES ('BANK_TRANSFER');

CREATE TABLE payment_status (
  id TINYINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(20) NOT NULL UNIQUE -- PENDING, SUCCESS, COMPLETED, FAILED
);
INSERT INTO payment_status (code) VALUES ('PENDING'),('SUCCESS'),('COMPLETED'),('FAILED');

CREATE TABLE notification_type (
  id TINYINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(20) NOT NULL UNIQUE -- ORDER, SUPPORT, SYSTEM, OTHER
);
INSERT INTO notification_type (code) VALUES ('ORDER'),('SUPPORT'),('SYSTEM'),('OTHER');

CREATE TABLE return_reason (
  id TINYINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(30) NOT NULL UNIQUE -- DAMAGED, NOT_AS_DESCRIBED, FAKE, NO_NEED
);
INSERT INTO return_reason (code) VALUES ('DAMAGED'),('NOT_AS_DESCRIBED'),('FAKE'),('NO_NEED');

-- ============
-- Core People
-- ============
CREATE TABLE account (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  account_id BIGINT UNSIGNED NOT NULL UNIQUE,
  full_name VARCHAR(120) NOT NULL,
  email VARCHAR(120) NOT NULL UNIQUE,
  phone VARCHAR(20),
  date_of_birth DATE,
  gender_id TINYINT UNSIGNED,
  avatar_url VARCHAR(255),
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_user_account FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE,
  CONSTRAINT fk_user_gender FOREIGN KEY (gender_id) REFERENCES gender(id)
);

CREATE TABLE admin (
  id BIGINT UNSIGNED PRIMARY KEY,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_admin_user FOREIGN KEY (id) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE customer (
  id BIGINT UNSIGNED PRIMARY KEY,
  membership_tier_id TINYINT UNSIGNED,
  points INT NOT NULL DEFAULT 0,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_customer_user FOREIGN KEY (id) REFERENCES user(id) ON DELETE CASCADE,
  CONSTRAINT fk_customer_tier FOREIGN KEY (membership_tier_id) REFERENCES membership_tier(id)
);

CREATE TABLE address (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT UNSIGNED NULL,
  street VARCHAR(255) NOT NULL,
  ward VARCHAR(100),
  district VARCHAR(100),
  city VARCHAR(100),
  province VARCHAR(100),
  country VARCHAR(100) DEFAULT 'Vietnam',
  zipcode VARCHAR(20),
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_address_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE SET NULL
);

-- =======
-- Catalog
-- =======
CREATE TABLE category (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  slug VARCHAR(120) NOT NULL UNIQUE
);

CREATE TABLE subcategory (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  category_id BIGINT UNSIGNED NOT NULL,
  name VARCHAR(100) NOT NULL,
  slug VARCHAR(120) NOT NULL UNIQUE,
  CONSTRAINT fk_subcategory_category FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
);

CREATE TABLE tag (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(60) NOT NULL,
  slug VARCHAR(80) NOT NULL UNIQUE
);

CREATE TABLE author (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(120) NOT NULL,
  bio TEXT
);

CREATE TABLE publisher (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(160) NOT NULL,
  website VARCHAR(160)
);

CREATE TABLE media_file (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  filename VARCHAR(200) NOT NULL,
  storage_url VARCHAR(255) NOT NULL,
  status_id TINYINT UNSIGNED,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_media_status FOREIGN KEY (status_id) REFERENCES ebook_status(id)
);

CREATE TABLE book (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(200) NOT NULL,
  slug VARCHAR(220) NOT NULL UNIQUE,
  sku VARCHAR(50) UNIQUE,
  isbn10 VARCHAR(20),
  isbn13 VARCHAR(20),
  description TEXT,
  price DECIMAL(12,2) NOT NULL DEFAULT 0.00,
  discount_percent DECIMAL(5,2) NOT NULL DEFAULT 0.00,
  stock INT NOT NULL DEFAULT 0,
  weight_grams INT,
  pages INT,
  size VARCHAR(50), -- e.g. 14x20 cm
  format_id TINYINT UNSIGNED NOT NULL,
  language_id TINYINT UNSIGNED,
  publisher_id BIGINT UNSIGNED,
  release_date DATE,
  cover_url VARCHAR(255),
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_book_format FOREIGN KEY (format_id) REFERENCES book_format(id),
  CONSTRAINT fk_book_language FOREIGN KEY (language_id) REFERENCES ebook_language(id),
  CONSTRAINT fk_book_publisher FOREIGN KEY (publisher_id) REFERENCES publisher(id)
);

CREATE TABLE book_author (
  book_id BIGINT UNSIGNED NOT NULL,
  author_id BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (book_id, author_id),
  CONSTRAINT fk_ba_book FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE,
  CONSTRAINT fk_ba_author FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE CASCADE
);

CREATE TABLE book_tag (
  book_id BIGINT UNSIGNED NOT NULL,
  tag_id BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (book_id, tag_id),
  CONSTRAINT fk_bt_book FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE,
  CONSTRAINT fk_bt_tag FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE
);

CREATE TABLE book_subcategory (
  book_id BIGINT UNSIGNED NOT NULL,
  subcategory_id BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (book_id, subcategory_id),
  CONSTRAINT fk_bsc_book FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE,
  CONSTRAINT fk_bsc_subcat FOREIGN KEY (subcategory_id) REFERENCES subcategory(id) ON DELETE CASCADE
);

CREATE TABLE book_media (
  book_id BIGINT UNSIGNED NOT NULL,
  media_file_id BIGINT UNSIGNED NOT NULL,
  is_default BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (book_id, media_file_id),
  CONSTRAINT fk_bm_book FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE,
  CONSTRAINT fk_bm_media FOREIGN KEY (media_file_id) REFERENCES media_file(id) ON DELETE CASCADE
);

-- ==========
-- Cart & UX
-- ==========
CREATE TABLE cart (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  customer_id BIGINT UNSIGNED NOT NULL UNIQUE,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_cart_customer FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE
);

CREATE TABLE cart_item (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  cart_id BIGINT UNSIGNED NOT NULL,
  book_id BIGINT UNSIGNED NOT NULL,
  quantity INT NOT NULL,
  unit_price DECIMAL(12,2) NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_ci_cart FOREIGN KEY (cart_id) REFERENCES cart(id) ON DELETE CASCADE,
  CONSTRAINT fk_ci_book FOREIGN KEY (book_id) REFERENCES book(id),
  UNIQUE KEY uq_cart_book (cart_id, book_id)
);

CREATE TABLE review (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  book_id BIGINT UNSIGNED NOT NULL,
  customer_id BIGINT UNSIGNED NOT NULL,
  rating TINYINT NOT NULL,
  title VARCHAR(160),
  content TEXT,
  is_recommended BOOLEAN NOT NULL DEFAULT FALSE,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_review_book FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE,
  CONSTRAINT fk_review_customer FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE
);

CREATE TABLE response_review (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  review_id BIGINT UNSIGNED NOT NULL,
  creator_admin_id BIGINT UNSIGNED NULL,
  creator_name VARCHAR(120) NULL,
  content TEXT NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_rr_review FOREIGN KEY (review_id) REFERENCES review(id) ON DELETE CASCADE,
  CONSTRAINT fk_rr_admin FOREIGN KEY (creator_admin_id) REFERENCES admin(id) ON DELETE SET NULL
);

-- ======
-- Orders
-- ======
CREATE TABLE promotion (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(40) NOT NULL UNIQUE,
  title VARCHAR(160) NOT NULL,
  description TEXT,
  percent_off DECIMAL(5,2) DEFAULT 0.00,
  amount_off DECIMAL(12,2) DEFAULT 0.00,
  min_order_value DECIMAL(12,2) DEFAULT 0.00,
  starts_at DATETIME,
  ends_at DATETIME,
  active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE `order` (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  customer_id BIGINT UNSIGNED NOT NULL,
  shipping_address_id BIGINT UNSIGNED,
  promotion_id BIGINT UNSIGNED,
  status_id TINYINT UNSIGNED NOT NULL,
  payment_method_id TINYINT UNSIGNED NOT NULL,
  subtotal DECIMAL(12,2) NOT NULL,
  discount_total DECIMAL(12,2) NOT NULL DEFAULT 0.00,
  shipping_fee DECIMAL(12,2) NOT NULL DEFAULT 0.00,
  grand_total DECIMAL(12,2) NOT NULL,
  note VARCHAR(255),
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES customer(id),
  CONSTRAINT fk_order_status FOREIGN KEY (status_id) REFERENCES order_status(id),
  CONSTRAINT fk_order_payment_method FOREIGN KEY (payment_method_id) REFERENCES payment_method(id),
  CONSTRAINT fk_order_promotion FOREIGN KEY (promotion_id) REFERENCES promotion(id),
  CONSTRAINT fk_order_shipping_addr FOREIGN KEY (shipping_address_id) REFERENCES address(id)
);

CREATE TABLE order_item (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  order_id BIGINT UNSIGNED NOT NULL,
  book_id BIGINT UNSIGNED NOT NULL,
  quantity INT NOT NULL,
  unit_price DECIMAL(12,2) NOT NULL,
  line_total DECIMAL(12,2) NOT NULL,
  CONSTRAINT fk_oi_order FOREIGN KEY (order_id) REFERENCES `order`(id) ON DELETE CASCADE,
  CONSTRAINT fk_oi_book FOREIGN KEY (book_id) REFERENCES book(id)
);

CREATE TABLE order_status_history (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  order_id BIGINT UNSIGNED NOT NULL,
  status_id TINYINT UNSIGNED NOT NULL,
  flag_id TINYINT UNSIGNED NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  note VARCHAR(255),
  CONSTRAINT fk_osh_order FOREIGN KEY (order_id) REFERENCES `order`(id) ON DELETE CASCADE,
  CONSTRAINT fk_osh_status FOREIGN KEY (status_id) REFERENCES order_status(id),
  CONSTRAINT fk_osh_flag FOREIGN KEY (flag_id) REFERENCES order_history_flag(id)
);

CREATE TABLE payment (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  order_id BIGINT UNSIGNED NOT NULL,
  method_id TINYINT UNSIGNED NOT NULL,
  status_id TINYINT UNSIGNED NOT NULL,
  amount DECIMAL(12,2) NOT NULL,
  transaction_ref VARCHAR(120),
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_payment_order FOREIGN KEY (order_id) REFERENCES `order`(id) ON DELETE CASCADE,
  CONSTRAINT fk_payment_method FOREIGN KEY (method_id) REFERENCES payment_method(id),
  CONSTRAINT fk_payment_status FOREIGN KEY (status_id) REFERENCES payment_status(id)
);

CREATE TABLE shipping (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  order_id BIGINT UNSIGNED NOT NULL UNIQUE,
  shipping_address_id BIGINT UNSIGNED,
  shipped_at DATETIME,
  delivered_at DATETIME,
  provider VARCHAR(60),
  tracking_no VARCHAR(120),
  status VARCHAR(60),
  CONSTRAINT fk_ship_order FOREIGN KEY (order_id) REFERENCES `order`(id) ON DELETE CASCADE,
  CONSTRAINT fk_ship_addr FOREIGN KEY (shipping_address_id) REFERENCES address(id)
);

-- =======
-- Returns
-- =======
CREATE TABLE return_book (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  order_id BIGINT UNSIGNED NOT NULL,
  reason_id TINYINT UNSIGNED NOT NULL,
  note VARCHAR(255),
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_rb_order FOREIGN KEY (order_id) REFERENCES `order`(id) ON DELETE CASCADE,
  CONSTRAINT fk_rb_reason FOREIGN KEY (reason_id) REFERENCES return_reason(id)
);

CREATE TABLE return_book_item (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  return_book_id BIGINT UNSIGNED NOT NULL,
  order_item_id BIGINT UNSIGNED NOT NULL,
  qty INT NOT NULL,
  price_at_return DECIMAL(12,2) NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_rbi_return FOREIGN KEY (return_book_id) REFERENCES return_book(id) ON DELETE CASCADE,
  CONSTRAINT fk_rbi_order_item FOREIGN KEY (order_item_id) REFERENCES order_item(id) ON DELETE CASCADE
);

-- ============
-- Help Center
-- ============
CREATE TABLE support_ticket (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  customer_id BIGINT UNSIGNED NOT NULL,
  subject VARCHAR(160) NOT NULL,
  content TEXT NOT NULL,
  status VARCHAR(40) NOT NULL DEFAULT 'OPEN',
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_st_customer FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE
);

CREATE TABLE support_response (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  ticket_id BIGINT UNSIGNED NOT NULL,
  responder_admin_id BIGINT UNSIGNED NULL,
  responder_name VARCHAR(120),
  content TEXT NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_sr_ticket FOREIGN KEY (ticket_id) REFERENCES support_ticket(id) ON DELETE CASCADE,
  CONSTRAINT fk_sr_admin FOREIGN KEY (responder_admin_id) REFERENCES admin(id) ON DELETE SET NULL
);

-- ============
-- CMS / Others
-- ============
CREATE TABLE site_content (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  content_key VARCHAR(80) NOT NULL UNIQUE,
  content_value TEXT,
  is_enabled BOOLEAN NOT NULL DEFAULT TRUE,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE notification (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT UNSIGNED NOT NULL,
  type_id TINYINT UNSIGNED NOT NULL,
  title VARCHAR(160) NOT NULL,
  content TEXT NOT NULL,
  is_read BOOLEAN NOT NULL DEFAULT FALSE,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_n_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
  CONSTRAINT fk_n_type FOREIGN KEY (type_id) REFERENCES notification_type(id)
);

-- Helpful indexes
CREATE INDEX idx_user_email ON user(email);
CREATE INDEX idx_book_title ON book(title);
CREATE INDEX idx_book_slug ON book(slug);
CREATE INDEX idx_review_book ON review(book_id);
CREATE INDEX idx_order_customer ON `order`(customer_id);
CREATE INDEX idx_order_status ON `order`(status_id);
CREATE INDEX idx_payment_order ON payment(order_id);

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE messages (
  message_id int NOT NULL AUTO_INCREMENT,
  sender varchar(100) DEFAULT NULL,
  receiver varchar(100) DEFAULT NULL,
  content text,
  file_path varchar(255) DEFAULT NULL,
  file_type varchar(50) DEFAULT NULL,
  sent_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (message_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Đổi tên password_hash -> password (tuỳ bạn chọn A hoặc B, không cần cả hai)
ALTER TABLE account CHANGE password_hash password VARCHAR(255) NOT NULL;

UPDATE account SET password = 'admin123' WHERE username = 'admin';