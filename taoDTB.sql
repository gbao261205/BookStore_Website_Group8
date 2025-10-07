CREATE DATABASE BOOKSTORE CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE BOOKSTORE;

-- (nếu chưa tạo bảng) tạo các bảng như đã gửi trước:
CREATE TABLE users (
  id            VARCHAR(50) PRIMARY KEY,
  full_name     VARCHAR(150) NOT NULL,
  email         VARCHAR(150) UNIQUE NOT NULL,
  date_of_birth DATE NULL,
  gender        ENUM('MALE','FEMALE','UNKNOWN') DEFAULT 'UNKNOWN',
  phone         VARCHAR(30) NULL,
  avatar_url    VARCHAR(255) NULL,
  user_type     ENUM('ADMIN','CUSTOMER','SELLER') DEFAULT 'CUSTOMER'
);

CREATE TABLE addresses (
  id        BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id   VARCHAR(50) NOT NULL,
  nation    VARCHAR(80),
  province  VARCHAR(80),
  district  VARCHAR(80),
  village   VARCHAR(120),
  detail    VARCHAR(255),
  CONSTRAINT fk_addr_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE accounts (
  id        VARCHAR(50) PRIMARY KEY,
  username  VARCHAR(80) UNIQUE NOT NULL,
  password  VARCHAR(255) NOT NULL,
  user_id   VARCHAR(50) NOT NULL UNIQUE,
  CONSTRAINT fk_acc_user FOREIGN KEY (user_id) REFERENCES users(id)
);
