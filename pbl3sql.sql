DROP DATABASE IF EXISTS PBL3_DATABASE;
CREATE DATABASE IF NOT EXISTS PBL3_DATABASE;
USE PBL3_DATABASE;

DROP TABLE IF EXISTS PRIVILEGE;
CREATE TABLE PRIVILEGE (
	ID INT NOT NULL,
    PRIVILEGE_DESC VARCHAR(100) NOT NULL,
    PRIMARY KEY(ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `USER`;
CREATE TABLE `USER` (
  ID INT NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100),
  EMAIL varchar(100) NOT NULL,
  `PASSWORD` varchar(100) NOT NULL,
  GENDER varchar(5),
  DATE_OF_BIRTH DATE,
  PRIVILEGE_ID INT NOT NULL,
  AVATAR varchar(100) DEFAULT NULL,
  CREATE_AT timestamp NULL DEFAULT NULL,
  UPDATED_AT timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT FK_PRIVILEGE FOREIGN KEY (PRIVILEGE_ID) REFERENCES PRIVILEGE(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



DROP TABLE IF EXISTS INDUSTRY;
CREATE TABLE INDUSTRY (
  `INDUSTRY_ID` int NOT NULL AUTO_INCREMENT,
  `INDUSTRY_NAME` varchar(100) NOT NULL,
  PRIMARY KEY (`INDUSTRY_ID`),
  CONSTRAINT UNQ_INDUSTRY_NAME UNIQUE(`INDUSTRY_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS COMPANY;
CREATE TABLE IF NOT EXISTS COMPANY (
	ID INT AUTO_INCREMENT NOT NULL,
    COMPANY_NAME VARCHAR(255) NOT NULL,
    LOCATION VARCHAR(255) NOT NULL,
    STAFF_SIZE VARCHAR(50) NOT NULL,
	COMPANY_DESCRIPTION TEXT DEFAULT NULL,
	PRIMARY KEY(ID),
    CONSTRAINT UNQ_COMPANY_NAME UNIQUE(COMPANY_NAME)
)ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS WORK_LOCATION;
CREATE TABLE IF NOT EXISTS WORK_LOCATION
(
	ID INT AUTO_INCREMENT,
    LOCATION VARCHAR(200) NOT NULL,
    PRIMARY KEY(ID),
    CONSTRAINT UNQ_LOCATION UNIQUE(LOCATION)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS JOB;
CREATE TABLE IF NOT EXISTS JOB (
	JOB_ID INT AUTO_INCREMENT NOT NULL,
    JOB_NAME varchar(255) DEFAULT NULL,
    COMPANY_ID INT NOT NULL,
    INDUSTRY varchar(255) DEFAULT NULL, 
    COMPANY_LOCATIONS TEXT,
    POSTED_DATE varchar(20) NOT NULL,
    ENROLLMENT_LOCATION VARCHAR(255) DEFAULT NULL,
	`ROLE` VARCHAR(100) DEFAULT NULL,
    SALARY VARCHAR(100) DEFAULT NULL,
    GENDER_REQUIREMENT VARCHAR(10),
    NUMBER_OF_RECRUITMENT VARCHAR(10),
    AGE_REQUIREMENT VARCHAR(50),
	PROBATION_TIME VARCHAR(20) DEFAULT NULL,
    WORKWAY VARCHAR(100) DEFAULT NULL,
    EXPERIENCE_REQUIREMENT VARCHAR(100) DEFAULT NULL,
    DEGREE_REQUIREMENT VARCHAR(100)  DEFAULT NULL,
    BENEFITS TEXT,
    JOB_DESCRIPTION TEXT,
    JOB_REQUIREMENT TEXT,
    DEADLINE VARCHAR(20) NOT NULL,
	SOURCE_PICTURE varchar(255) DEFAULT NULL,
    PRIMARY KEY(JOB_ID),
	CONSTRAINT FK_COMPANY FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)
	-- CONSTRAINT FK_INDUSTRY FOREIGN KEY (INDUSTRY_ID) REFERENCES INDUSTRY(INDUSTRY_ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SELECT* FROM job;
DELETE FROM job;
SELECT* from company;
DELETE FROM company;
SELECT ID
FROM company;
USE pbl3_database;