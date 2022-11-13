CREATE DATABASE bus_improvement;

USE sys;
USE bus_improvement;

# [BusDetailResponseSpec(
# routeId=28308002, routeNo=58, routeStartNameToFinishName=송도제2차고지-송도제2차고지, 
# busStopIndex=0, busStopId=2807972, busStopName=송도제2차고지, sdCode=28, sggCode=28185, 
# emdCode=2818510600, sdName=인천광역시, sggName=연수구, emdName=송도동)


# 테이블 목록 보여주기
SHOW TABLES;

DROP TABLE bus_info;
DROP TABLE hibernate_sequence;
DROP TABLE bus_through_info;
DROP TABLE bus_stop_station_info;
DROP TABLE bus_path_info;
DROP TABLE bus_traffic_info;

CREATE TABLE bus_stop_station_info(
	id INT(11) NOT NULL PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
	pos_x DOUBLE,
    pos_y DOUBLE,
    short_id INT(11),
    admin_name VARCHAR(20)
);

CREATE TABLE bus_info(
	route_id VARCHAR(20) NOT NULL PRIMARY KEY,
    route_len INT(11),
    route_no VARCHAR(20),
	origin_bus_stop_id INT(11),
    dest_bus_stop_id INT(11),
    bus_start_time VARCHAR(10),
    bus_finish_time VARCHAR(10),
    max_allocation_gap INT(11),
    min_allocation_gap INT(11),
    route_type INT(11),
    turn_bus_stop_id INT(11)
);

CREATE TABLE bus_through_info(
	id INT(11) NOT NULL PRIMARY KEY,
	route_id VARCHAR(20),
    bus_stop_station_id INT(11),
    bus_stop_sequence INT(11)
);

CREATE TABLE bus_path_info(
	id INT(11) NOT NULL PRIMARY KEY,
    route_no VARCHAR(20), 
    from_id INT(11),
    to_id INT(11),
    sequence INT(11),
    pos_x DOUBLE,
    pos_y DOUBLE
);

CREATE TABLE bus_traffic_info(
	id INT(11) NOT NULL PRIMARY KEY,
    short_id INT(11),
    date VARCHAR(20),
    user_type VARCHAR(20),
    route_no VARCHAR(20),
    time00_on INT(11),
    time00_off INT(11),
    time01_on INT(11),
    time01_off INT(11),
    time02_on INT(11),
    time02_off INT(11),
    time03_on INT(11),
    time03_off INT(11),
    time04_on INT(11),
    time04_off INT(11),
    time05_on INT(11),
    time05_off INT(11),
    time06_on INT(11),
    time06_off INT(11),
    time07_on INT(11),
    time07_off INT(11),
    time08_on INT(11),
    time08_off INT(11),
    time09_on INT(11),
    time09_off INT(11),
    time10_on INT(11),
    time10_off INT(11),
    time11_on INT(11),
    time11_off INT(11),
    time12_on INT(11),
    time12_off INT(11),
    time13_on INT(11),
    time13_off INT(11),
    time14_on INT(11),
    time14_off INT(11),
    time15_on INT(11),
    time15_off INT(11),
    time16_on INT(11),
    time16_off INT(11),
    time17_on INT(11),
    time17_off INT(11),
    time18_on INT(11),
    time18_off INT(11),
    time19_on INT(11),
    time19_off INT(11),
    time20_on INT(11),
    time20_off INT(11),
    time21_on INT(11),
    time21_off INT(11),
    time22_on INT(11),
    time22_off INT(11),
    time23_on INT(11),
    time23_off INT(11)
);

CREATE TABLE hibernate_sequence(
	next_val INT(11) PRIMARY KEY
);

INSERT INTO hibernate_sequence (next_val) VALUES (1);

SELECT * FROM bus_info;
SELECT * FROM bus_traffic_info;
SELECT * FROM bus_stop_station_info;
SELECT * FROM bus_through_info;
SELECT * FROM bus_info ORDER BY route_no ASC;
SELECT * FROM bus_path_info WHERE route_no = '58' AND sequence = 0;
SELECT * FROM hibernate_sequence;
SELECT COUNT(*) FROM bus_info;
SELECT COUNT(*) FROM bus_path_info;
SELECT COUNT(*) FROM bus_stop_station_info;
SELECT COUNT(*) FROM bus_traffic_info;
SELECT * FROM bus_stop_station_info WHERE short_id = '35708';

SELECT * FROM bus_info WHERE route_no = '58';
# 버스 노선이 58번인 노선 구하기
SELECT * FROM bus_through_info WHERE route_id IN (SELECT route_id FROM bus_info WHERE route_no = '58') ORDER BY bus_stop_sequence ASC;
SELECT * FROM bus_through_info B1 INNER JOIN bus_stop_station_info B2 ON B1.route_id = B2.route_id WHERE route_id = '161000007';

SELECT * FROM bus_stop_station_info WHERE id IN (
	SELECT bus_stop_station_id FROM bus_through_info WHERE route_id IN (
		SELECT route_id FROM bus_info WHERE route_no = '58'
	) ORDER BY bus_stop_sequence ASC
);

SELECT * FROM bus_path_info WHERE from_id IN (
	SELECT id FROM bus_stop_station_info WHERE id IN (
		SELECT bus_stop_station_id FROM bus_through_info WHERE route_id IN (
			SELECT route_id FROM bus_info WHERE route_no = '58'
		) ORDER BY bus_stop_sequence ASC
	)
);

SELECT bus_stop_station_id FROM bus_through_info WHERE route_id IN (SELECT route_id FROM bus_info WHERE route_no = '58') ORDER BY bus_stop_sequence ASC;

SELECT * FROM bus_stop_station_info;
SELECT * FROM bus_stop_station_info WHERE admin_name = '연수구';

SELECT through.route_id, through.bus_stop_station_id, through.bus_stop_sequence, station.pos_x, station.pos_y FROM bus_through_info through INNER JOIN bus_stop_station_info station ON through.route_id = station.id; 

ALTER TABLE bus_info MODIFY COLUMN turn_bus_stop_id INT(11);

# safe mode 끄기
SET SQL_SAFE_UPDATES = 0;
DELETE FROM bus_stop;

SELECT * FROM bus_stop WHERE route_no = '2';
SELECT COUNT(DISTINCT bus_stop_id) FROM bus_stop;
#. select * from bus_stop group by DISTINCT bus_stop_id