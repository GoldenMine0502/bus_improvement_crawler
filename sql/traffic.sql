CREATE DATABASE bus_improvement;

USE bus_improvement;

CREATE TABLE traffic(
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    time00 VARCHAR(10),
    time01 VARCHAR(10),
    time02 VARCHAR(10),
    time03 VARCHAR(10),
    time04 VARCHAR(10),
    time05 VARCHAR(10),
    time06 VARCHAR(10),
    time07 VARCHAR(10),
    time08 VARCHAR(10),
    time09 VARCHAR(10),
    time10 VARCHAR(10),
    time11 VARCHAR(10),
    time12 VARCHAR(10),
    time13 VARCHAR(10),
    time14 VARCHAR(10),
    time15 VARCHAR(10),
    time16 VARCHAR(10),
    time17 VARCHAR(10),
    time18 VARCHAR(10),
    time19 VARCHAR(10),
    time20 VARCHAR(10),
    time21 VARCHAR(10),
    time22 VARCHAR(10),
    time23 VARCHAR(10),
	distance VARCHAR(10),
	type VARCHAR(30),
	road_name VARCHAR(30),
	link_id VARCHAR(30),
	direction VARCHAR(30),
	start_name VARCHAR(30),
	week VARCHAR(10),
	date VARCHAR(30),
	finish_name VARCHAR(30)
);

CREATE TABLE road_name_info(
	id INT(11) NOT NULL PRIMARY KEY,
    road_name VARCHAR(30),
    pos_x DOUBLE,
    pos_y DOUBLE
);

SELECT * FROM traffic;
SELECT * FROM road_name_info;
SELECT COUNT(*) FROM traffic;

SELECT * FROM traffic WHERE start_name = '제3산용4거리';
SELECT DISTINCT(road_name) FROM traffic;

SELECT start_name FROM (SELECT start_name FROM traffic UNION ALL SELECT finish_name FROM traffic) T;
SELECT COUNT(start_name) FROM (SELECT start_name FROM traffic UNION ALL SELECT finish_name FROM traffic) T;
SELECT DISTINCT start_name FROM traffic UNION ALL SELECT DISTINCT finish_name FROM traffic;

DROP TABLE traffic;