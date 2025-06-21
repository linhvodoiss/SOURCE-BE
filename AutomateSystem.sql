-- Drop the database if it already exists
DROP DATABASE IF EXISTS AutomateSystem;

-- Create database
CREATE DATABASE IF NOT EXISTS AutomateSystem;
USE AutomateSystem;

-- Create table user
DROP TABLE IF EXISTS `User`;
CREATE TABLE IF NOT EXISTS `User` (
                                      id              SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                      username        CHAR(50) NOT NULL UNIQUE,
    email           CHAR(50) NOT NULL UNIQUE,
    `password`        VARCHAR(800) NOT NULL,
    firstName       NVARCHAR(50) NOT NULL,
    lastName        NVARCHAR(50) NOT NULL,
    phoneNumber		VARCHAR(20) NOT NULL UNIQUE,
    `role`            ENUM('ADMIN', 'CUSTOMER') DEFAULT 'CUSTOMER',
    `status`          TINYINT DEFAULT 0, -- 0: Not Active, 1: Active
    avatarUrl       VARCHAR(500),
    created_at      DATETIME DEFAULT NOW(),
    updated_at      DATETIME DEFAULT NOW()
    );

-- Create table Registration_User_Token
DROP TABLE IF EXISTS `Registration_User_Token`;
CREATE TABLE IF NOT EXISTS `Registration_User_Token` (
                                                         id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                                         token CHAR(36) NOT NULL UNIQUE,
    user_id SMALLINT UNSIGNED NOT NULL,
    expiryDate DATETIME NOT NULL
    );

-- Create table Reset_Password_Token
DROP TABLE IF EXISTS `Reset_Password_Token`;
CREATE TABLE IF NOT EXISTS `Reset_Password_Token` (
                                                      id              INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                                      token           CHAR(36) NOT NULL UNIQUE,
    user_id         SMALLINT UNSIGNED NOT NULL,
    expiryDate      DATETIME NOT NULL
    );

-- Create table Version
DROP TABLE IF EXISTS Version;
CREATE TABLE IF NOT EXISTS Version (
                                       id              SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                       version         VARCHAR(50) NOT NULL UNIQUE,
    description     VARCHAR(255),
    created_at      DATETIME DEFAULT NOW(),
    updated_at      DATETIME DEFAULT NOW()
    );


-- Create table category
DROP TABLE IF EXISTS Category;
CREATE TABLE IF NOT EXISTS Category (
                                        id              SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                        version_id      SMALLINT UNSIGNED NOT NULL,
                                        name            NVARCHAR(50) NOT NULL,
    slug            VARCHAR(100) NOT NULL UNIQUE,
    `order`         INT DEFAULT 0,
    isActive        TINYINT DEFAULT 1,
    created_at      DATETIME DEFAULT NOW(),
    updated_at      DATETIME DEFAULT NOW(),
    FOREIGN KEY (version_id) REFERENCES Version(id)
    );


-- Create table Doc
DROP TABLE IF EXISTS Doc;
CREATE TABLE IF NOT EXISTS Doc (
                                   id              INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                   version_id      SMALLINT UNSIGNED NOT NULL,
                                   category_id     SMALLINT UNSIGNED NOT NULL,
                                   title           NVARCHAR(255) NOT NULL,
    slug            VARCHAR(150) NOT NULL UNIQUE,
    content         TEXT,
    `order`         INT DEFAULT 0,
    isActive        TINYINT DEFAULT 1,
    created_at      DATETIME DEFAULT NOW(),
    updated_at      DATETIME DEFAULT NOW(),
    FOREIGN KEY (version_id) REFERENCES Version(id),
    FOREIGN KEY (category_id) REFERENCES Category(id)
    );



DROP TABLE IF EXISTS SubscriptionPackage;
CREATE TABLE IF NOT EXISTS SubscriptionPackage (
                                                   id              SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                                   name            NVARCHAR(100) NOT NULL,
    price           FLOAT NOT NULL CHECK (price >= 0),
    discount        FLOAT DEFAULT 0 CHECK (discount >= 0),
    billingCycle    ENUM('MONTHLY', 'YEARLY') DEFAULT 'MONTHLY',
    isActive        TINYINT DEFAULT 1, -- 1: hoạt động, 0: ngừng cung cấp
    options         VARCHAR(500),
    simulatedCount  INT DEFAULT 0,
    created_at      DATETIME DEFAULT NOW(),
    updated_at      DATETIME DEFAULT NOW()
    );
DROP TABLE IF EXISTS PaymentOrder;
CREATE TABLE IF NOT EXISTS PaymentOrder (
                                            id              INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                            user_id         SMALLINT UNSIGNED NOT NULL,
                                            subscription_id SMALLINT UNSIGNED NOT NULL,
                                            orderId         CHAR(36) NOT NULL UNIQUE,
    paymentLink     VARCHAR(500),
    amount          FLOAT NOT NULL,
    billingCycle    ENUM('MONTHLY', 'YEARLY') NOT NULL,
    target          VARCHAR(100),
    paymentStatus   ENUM('PENDING', 'SUCCESS', 'FAILED') DEFAULT 'PENDING',
    created_at      DATETIME DEFAULT NOW(),
    updated_at      DATETIME DEFAULT NOW(),
    FOREIGN KEY (user_id) REFERENCES `User`(id),
    FOREIGN KEY (subscription_id) REFERENCES SubscriptionPackage(id)
    );
DROP TABLE IF EXISTS License;
CREATE TABLE IF NOT EXISTS License (
                                       id              INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                       user_id         SMALLINT UNSIGNED NOT NULL,
                                       subscription_id SMALLINT UNSIGNED NOT NULL,
                                       licenseKey      CHAR(36) NOT NULL UNIQUE,
    expiryDate      DATETIME NOT NULL,
    ip              VARCHAR(50),
    created_at      DATETIME DEFAULT NOW(),
    updated_at      DATETIME DEFAULT NOW(),
    FOREIGN KEY (user_id) REFERENCES `User`(id),
    FOREIGN KEY (subscription_id) REFERENCES SubscriptionPackage(id)
    );


insert into `user`(username, email, `password`, firstName, lastName, phoneNumber, `role`, `status`, avatarUrl)
    value	('admin',	'admin@gmail.com', '$2a$10$RAU5Vl1A6Iheyeg2MSBlVeLRLpH2kRSpredJkzJIm72ZscI6pg/62', 'Nguyen Van', 'A', '0987654321', 'ADMIN', 1, ''),
                    ('CaoVanBay',	'Bay@gmail.com', '$2a$10$RAU5Vl1A6Iheyeg2MSBlVeLRLpH2kRSpredJkzJIm72ZscI6pg/62', 'Cao Van', 'Bay', '0999999999', 'CUSTOMER', 1, ''),
                    ('LeThiTam',	'Tam@gmail.com', '$2a$10$RAU5Vl1A6Iheyeg2MSBlVeLRLpH2kRSpredJkzJIm72ZscI6pg/62', 'Le Thi', 'Tam', '0912345678', 'CUSTOMER', 1, '');

INSERT INTO SubscriptionPackage (name, price, discount, billingCycle, isActive, options)
VALUES
    ('Basic Plan', 9.99, 0, 'MONTHLY', 1, '5 documents per month'),
    ('Pro Plan', 19.99, 10, 'MONTHLY', 1, 'Unlimited access');

INSERT INTO PaymentOrder (user_id, subscription_id, orderId, paymentLink, amount, billingCycle, target, paymentStatus)
VALUES
    (1, 2, 'ORDER-123456', 'https://payment.example.com/checkout/ORDER-123456', 17.99, 'MONTHLY', 'web', 'SUCCESS');

INSERT INTO License (user_id, subscription_id, licenseKey, expiryDate, ip)
VALUES
    (1, 2, 'LIC-ABCDEF-123456', '2025-07-21 23:59:59', '203.113.78.9');

INSERT INTO Version (version, description)
VALUES
    ('v1.0', 'Phiên bản đầu tiên của hệ thống'),
    ('v1.1', 'Bổ sung thêm chức năng tìm kiếm');

INSERT INTO Category (version_id, name, slug, `order`, isActive)
VALUES
    (1, 'Hướng dẫn sử dụng', 'huong-dan-su-dung', 1, 1),
    (1, 'FAQ', 'cau-hoi-thuong-gap', 2, 1),
    (2, 'Changelog', 'thay-doi-phien-ban', 1, 1);

INSERT INTO Doc (version_id, category_id, title, slug, content, `order`, isActive)
VALUES
    (1, 1, 'Cách đăng ký tài khoản', 'dang-ky-tai-khoan', 'Bạn cần điền email và mật khẩu...', 1, 1),
    (1, 2, 'Tôi quên mật khẩu, làm sao lấy lại?', 'quen-mat-khau', 'Bạn có thể nhấn vào “Quên mật khẩu”.', 1, 1),
    (2, 3, 'v1.1 - Thêm chức năng tìm kiếm', 'v1-1-search-update', 'Chúng tôi đã thêm chức năng tìm kiếm...', 1, 1);
