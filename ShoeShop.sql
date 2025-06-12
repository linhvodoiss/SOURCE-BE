-- Drop the database if it already exists
DROP DATABASE IF EXISTS TestingSystem;

-- Create database
CREATE DATABASE IF NOT EXISTS TestingSystem;
USE TestingSystem;

-- Create table user
DROP TABLE IF EXISTS `User`;
CREATE TABLE IF NOT EXISTS `User` ( 
    id              SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username        CHAR(50) NOT NULL UNIQUE,
    email           CHAR(50) NOT NULL UNIQUE,
    `password`        VARCHAR(800) NOT NULL,
    firstName       NVARCHAR(50) NOT NULL,
    lastName        NVARCHAR(50) NOT NULL,
    phoneNumber		CHAR(10) NOT NULL UNIQUE,
    `role`            ENUM('Admin', 'Saler', 'Customer') DEFAULT 'Customer',
    `status`          TINYINT DEFAULT 0, -- 0: Not Active, 1: Active
    avatarUrl       VARCHAR(500)
);

-- Create table Registration_User_Token
DROP TABLE IF EXISTS `Registration_User_Token`;
CREATE TABLE IF NOT EXISTS `Registration_User_Token` ( 
    id              INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    token           CHAR(36) NOT NULL UNIQUE,
    user_id         SMALLINT UNSIGNED NOT NULL,
    expiryDate      DATETIME NOT NULL
);

-- Create table Reset_Password_Token
DROP TABLE IF EXISTS `Reset_Password_Token`;
CREATE TABLE IF NOT EXISTS `Reset_Password_Token` ( 
    id              INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    token           CHAR(36) NOT NULL UNIQUE,
    user_id         SMALLINT UNSIGNED NOT NULL,
    expiryDate      DATETIME NOT NULL
);

-- Create table category
DROP TABLE IF EXISTS `Category`;
CREATE TABLE IF NOT EXISTS `Category` ( 
    id                SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `name`            NVARCHAR(50) NOT NULL UNIQUE,
    `description`     VARCHAR(500)
);

-- Create table product
DROP TABLE IF EXISTS `Product`;
CREATE TABLE IF NOT EXISTS `Product`(
    id              SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `name`          NVARCHAR(50) NOT NULL UNIQUE KEY,
    category_id     SMALLINT UNSIGNED NOT NULL,
    number_of_products INT NOT NULL CHECK(number_of_products >= 0),
    price           FLOAT NOT NULL,
    thumbnailUrl    VARCHAR(500) DEFAULT '',
    `description`   VARCHAR(500) DEFAULT '',
    created_at      DATETIME DEFAULT NOW(),
    updated_at      DATETIME DEFAULT NOW(),
    FOREIGN KEY (category_id) REFERENCES `Category`(id)
);

-- Create table cart
DROP TABLE IF EXISTS `Cart`;
CREATE TABLE IF NOT EXISTS `Cart`(
    id              INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id         SMALLINT UNSIGNED NOT NULL,
    created_at      DATETIME DEFAULT NOW(),
    FOREIGN KEY (user_id) REFERENCES `User`(id)
);

-- Create table cart item
DROP TABLE IF EXISTS `CartItem`;
CREATE TABLE IF NOT EXISTS `CartItem`(
    id              INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    cart_id         INT UNSIGNED NOT NULL,
    product_id      SMALLINT UNSIGNED NOT NULL,
    quantity        INT NOT NULL CHECK(quantity > 0),
    FOREIGN KEY (cart_id) REFERENCES `Cart`(id),
    FOREIGN KEY (product_id) REFERENCES `Product`(id)
);

insert into `user`(username, email, `password`, firstName, lastName, phoneNumber, `role`, `status`, avatarUrl)
			value	('NguyenVanA',	'A@gmail.com', '123456', 'Nguyen Van', 'A', '0987654321', 'Admin', 1, ''),
					('NguyenThiB',	'B@gmail.com', '123456', 'Nguyen Thi', 'B', '0987654322', 'Customer', 1, ''),
                    ('NguyenTienMot',	'Mot@gmail.com', '123456', 'Nguyen Tien', 'Mot', '0987654333', 'Customer', 1, ''),
                    ('NguyenThiHai',	'Hai@gmail.com', '123456', 'Nguyen Thi', 'Hai', '0987654444', 'Customer', 1, ''),
                    ('DoQuangBa',	'Ba@gmail.com', '123456', 'Do Quang', 'Ba', '0987655555', 'Customer', 1, ''),
                    ('BanhThiBon',	'Bon@gmail.com', '123456', 'Banh Thi', 'Bon', '0987666666', 'Customer', 1, ''),
                    ('DangTienNam',	'Nam@gmail.com', '123456', 'Dang Tien', 'Nam', '0987777777', 'Customer', 1, ''),
                    ('LoThiSau',	'Sau@gmail.com', '123456', 'Lo Thi', 'Sau', '0988888888', 'Customer', 1, ''),
                    ('CaoVanBay',	'Bay@gmail.com', '123456', 'Cao Van', 'Bay', '0999999999', 'Customer', 1, ''),
                    ('LeThiTam',	'Tam@gmail.com', '123456', 'Le Thi', 'Tam', '0912345678', 'Customer', 1, '');
                    
insert into category (`name`, `description`)
			   value ('Nike', 'Nike là một trong những thương hiệu thể thao nổi tiếng nhất trên thế giới. Từ học sinh tiểu học cho đến các vận động viên chuyên nghiệp, không ai có thể phủ nhận sức hấp dẫn của Nike. Nếu bạn khảo sát xem có bao nhiêu người đã hoặc đang sở hữu các sản phẩm của Nike, thì con số này sẽ khiến bạn bất ngờ.'),
					 ('ADIDAS', 'Adidas là một công ty đa quốc gia đến từ Đức, chuyên sản xuất giày dép, quần áo và phụ kiện. Tiền thân của công ty là Gebruder Dassler Schuhfabrik, được thành lập vào năm 1924 bởi anh em nhà Dassler là Adi Dassler và Rudolf.'),
                     ('FILA', 'Giày Fila là một thương hiệu giày nổi tiếng với chất lượng cao và thiết kế thời trang. Những đôi giày Fila không chỉ mang lại sự thoải mái khi sử dụng mà còn giúp người mang thể hiện phong cách cá tính riêng. Tuy nhiên, việc chọn kích thước giày Fila phù hợp không phải lúc nào cũng dễ dàng.');
                     
insert into product (`name`, category_id, number_of_products, price, thumbnailUrl, `description`)
			  value ('Giày Chạy Bộ Nam Nike Reactx Infinity Run 4', '1', '100', '3471300', '','Đôi giày Nike InfinityRN 4 ReactX không chỉ mang lại sự hỗ trợ tối ưu trong quá trình chạy bộ, mà còn mang đến cảm giác mới lạ trong mỗi bước chân.'),
					('Giày Chạy Bộ Nam Nike Air Zoom Structure 26', '1', '100', '4039000', '','Tận hưởng những bước chạy nhẹ nhàng, thoải mái để giúp bạn hoàn thành mục tiêu của mình. '),
                    ('Giày Chạy Bộ Nam Nike Flex Experience Rn 12', '1', '100', '2179000', '','Đặt những mục tiêu chạy bộ đầy tham vọng và chinh phục chúng cùng Giày Chạy Bộ Nam Nike Flex Experience Run 12! Được thiết kế với phong cách tối giản nhưng đầy tính năng,được thiết kế với sự tối giản và linh hoạt tối ưu, giúp bạn cảm nhận trọn vẹn từng chuyển động trên mọi cung đường. '),
                    ('Giày Sneaker Unisex Adidas Avryn', '2', '100', '3040000', '','Hãy bước vào kỷ nguyên mới của sự thoải mái và linh hoạt cùng Giày Sneaker Unisex Adidas Avryn. Đây không chỉ là một đôi giày, mà là sự kết hợp tinh hoa công nghệ đỉnh cao từ adidas, đưa trải nghiệm thể thao chuyên nghiệp vào phong cách hàng ngày của bạn'),
                    ('Giày Sneaker Nam Adidas Osade', '2', '100', '1920000', '','Trong thế giới sneaker luôn biến đổi, đâu là đôi giày vượt thoát khỏi dòng chảy xu hướng, tỏa sáng bất kể thời gian? Giày Sneaker Nam Adidas Osade chính là lời giải đáp, nơi sự tinh tế vượt thời gian kết hợp cùng năng lượng hiện đại, đưa từng bước chân của bạn trở thành tuyên ngôn phong cách mạnh mẽ'),
                    ('Giày Thể Thao Nam Adidas X_Plrboost', '2', '100', '4200000', '','Mang đến sự đa năng, thoải mái và phong cách cho mọi hành trình phiêu lưu. Đôi giày adidas này biến công nghệ sneaker tiên tiến nhất trở nên thật nhẹ nhàng. Kết cấu thoáng khí giúp đôi chân bạn luôn mát mẻ, cùng đệm BOOST đàn hồi cho cảm giác thoải mái suốt ngày dài.'),
                    ('Giày Sneaker Unisex Fila Targa Classic X Dragon', '3', '100', '2295000', '','Giày Sneaker Unisex Fila Targa Classic X Dragon - Sự hòa quyện tinh tế giữa di sản và thiết kế đương đại. Chất liệu cao cấp đảm bảo thoải mái và bền bỉ. Chi tiết rồng độc đáo là điểm nhấn nổi bật chắc chắn sẽ biến bạn trở thành tâm điểm của đám đông.'),
                    ('Giày Sneakers Unisex Fila Ray Bumper', '3', '100', '1996000', '','Hãy chinh phục mọi ánh nhìn với đôi Giày Sneaker Unisex Fila Ray Bumper, sự kết hợp hoàn hảo giữa thiết kế thời thượng và sự thoải mái tối ưu. Được sản xuất từ những chất liệu cao cấp, Fila Ray Bumper không chỉ mang đến vẻ ngoài bắt mắt mà còn đảm bảo sự bền bỉ và êm ái trong từng bước chân.'),
                    ('Giày Sneakers Unisex Fila Taurus V3', '3', '100', '920500', '','Trải nghiệm đỉnh cao của sự thoải mái và phong cách với Giày Sneaker Unisex Fila Taurus V3, phiên bản mới nhất của mẫu giày bán chạy đình đám FILA TAURUS. Được thiết kế với sự tỉ mỉ và tinh xảo, Giày Sneaker Unisex Fila Taurus V3 hứa hẹn sẽ nâng tầm diện mạo của bạn, đồng hành cùng bạn trên mọi hành trình.');
