CREATE DATABASE ck_database;
DROP DATABASE ck_database;
CREATE TABLE ICPC (
	TeamName varchar(20) NOT NULL,
    UniversityName varchar(125) NOT NULL,
    ProblemID varchar(10) NOT NULL,
    `Time` INT NOT NULL,
    Result varchar(5) NOT NULL
);

SELECT TeamName, UniversityName, COUNT(ProblemID) AS Solved, MAX(Time) AS SolveTime 
		                           FROM icpc WHERE Result = 'AC'
		                           GROUP BY TeamName, UniversityName 
		                           ORDER BY Solved DESC, SolveTime ASC;