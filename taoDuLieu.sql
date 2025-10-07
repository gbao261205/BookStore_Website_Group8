USE BOOKSTORE;

START TRANSACTION;

-- 1) USERS
INSERT INTO users (id, full_name, email, date_of_birth, gender, phone, avatar_url, user_type) VALUES
('U-ADM001','Nguyễn Quản Trị','admin1@bookstore.local','1995-07-10','MALE','0901111001',NULL,'ADMIN'),
('U-ADM002','Trần Điều Hành','admin2@bookstore.local','1992-11-02','FEMALE','0901111002',NULL,'ADMIN'),
('U-CUS001','Lê Minh An','minhan@example.com','2001-03-15','MALE','0912345001',NULL,'CUSTOMER'),
('U-CUS002','Phạm Thu Trang','thutrang@example.com','2000-08-21','FEMALE','0912345002',NULL,'CUSTOMER'),
('U-CUS003','Võ Gia Bảo','giabao@example.com','1999-12-05','MALE','0912345003',NULL,'CUSTOMER');

-- 2) ADDRESSES (id tự tăng)
INSERT INTO addresses (user_id, nation, province, district, village, detail) VALUES
('U-ADM001','Vietnam','Hồ Chí Minh','Quận 1','Bến Nghé','123 Nguyễn Huệ'),
('U-ADM002','Vietnam','Hà Nội','Ba Đình','Phúc Xá','45 Hoàng Hoa Thám'),
('U-CUS001','Vietnam','Đà Nẵng','Hải Châu','Thạch Thang','12 Bạch Đằng'),
('U-CUS002','Vietnam','Hải Phòng','Lê Chân','Dư Hàng','89 Chợ Con'),
('U-CUS003','Vietnam','Cần Thơ','Ninh Kiều','An Khánh','77 30/4');

-- 3) ACCOUNTS (demo: mật khẩu để plain; khi làm thật hãy hash)
INSERT INTO accounts (id, username, password, user_id) VALUES
('A-0001','admin','123456','U-ADM001'),
('A-0002','admin2','123456','U-ADM002'),
('A-0003','minhan','123456','U-CUS001'),
('A-0004','thutrang','123456','U-CUS002'),
('A-0005','giabao','123456','U-CUS003');

COMMIT;

Select * from users