DROP DATABASE IF EXISTS LsStock;
CREATE DATABASE LsStock;
Use LsStock;

DROP TABLE IF EXISTS User;
CREATE TABLE User(
	user_id INT AUTO_INCREMENT,
    nickname VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    money FLOAT,
    PRIMARY KEY (user_id),
    UNIQUE KEY (nickname, email)
);

DROP TABLE IF EXISTS Company;
CREATE TABLE Company(
	company_id INT AUTO_INCREMENT,
    company_name VARCHAR(255),
    share_price FLOAT,
    PRIMARY KEY (company_id)
);

DROP TABLE IF EXISTS User_Company;
CREATE TABLE User_Company(
	user_company_id INT AUTO_INCREMENT,
    user_id INT,
    company_id INT,
    quantity INT,
    buy_price FLOAT,
    PRIMARY KEY (user_company_id, user_id, company_id),
    FOREIGN KEY (user_id) REFERENCES User (user_id),
    FOREIGN KEY (company_id) REFERENCES Company (company_id)
);

DROP TABLE IF EXISTS Bot;
CREATE TABLE Bot(
	bot_id INT AUTO_INCREMENT,
    buy_percentage FLOAT,
    activation_time FLOAT,
    company_id INT,
    PRIMARY KEY (bot_id),
    FOREIGN KEY (company_id) REFERENCES Company (company_id)
);

DROP TABLE IF EXISTS History;
CREATE TABLE History(
	history_id INT AUTO_INCREMENT,
    max_share_price FLOAT,
    min_share_price FLOAT,
    open_share_price FLOAT,
    close_share_price FLOAT,
    datetime DATE,
    company_id INT,
    PRIMARY KEY (history_id),
    FOREIGN KEY (company_id) REFERENCES Company (company_id)
);