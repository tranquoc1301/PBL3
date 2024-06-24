CREATE DATABASE CAFEMANAGEMENT;

DROP TABLE IF EXISTS USER;
CREATE TABLE IF NOT EXISTS `USER` (
	ID INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    ROLE VARCHAR(10),
	USERNAME VARCHAR(100) NOT NULL,
    `PASSWORD` VARCHAR(100) NOT NULL,
    `FULLNAME` VARCHAR(100),
    DATEOFBIRTH DATE,
    `EMAIL` VARCHAR(100),
    `PHONENUMBER` VARCHAR(12)
);

DROP TABLE IF EXISTS PRODUCT;
CREATE TABLE IF NOT EXISTS PRODUCT (
	ID VARCHAR(5) NOT NULL PRIMARY KEY,
    `NAME` VARCHAR(50),
    `TYPE` VARCHAR(10),
    `STOCK` INT,
    PRICE DOUBLE,
    IMAGE TEXT
);

DROP TABLE IF EXISTS CUSTOMER;
CREATE TABLE IF NOT EXISTS CUSTOMER (
	ID INT NOT NULL PRIMARY KEY,
    FULLNAME VARCHAR(20) NOT NULL,
    CCCD VARCHAR(12) NOT NULL,
    TOTAL_TRANSACTION_AMOUNT DOUBLE,
    LATEST_TRANSACTION_DATE DATETIME
);

DROP TABLE IF EXISTS RECEIPT; 
CREATE TABLE IF NOT EXISTS RECEIPT(
	ID VARCHAR(10) NOT NULL PRIMARY KEY,
    CUSTOMER_ID INT NOT NULL,
    USER_ID INT NOT NULL,
    TOTAL_AMOUNT DOUBLE,
    TRANSACTION_DATE DATETIME,
    CONSTRAINT FOREIGN KEY Customer_FK (CUSTOMER_ID) REFERENCES CUSTOMER(ID),
	CONSTRAINT FOREIGN KEY User_FK (USER_ID) REFERENCES USER(ID)
);


