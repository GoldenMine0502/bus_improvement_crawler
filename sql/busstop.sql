CREATE DATABASE bus_improvement;

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
	origin_bus_stop_id VARCHAR(20),
    dest_bus_stop_id VARCHAR(20),
    bus_start_time VARCHAR(10),
    bus_finish_time VARCHAR(10),
    max_allocation_gap INT(11),
    min_allocation_gap INT(11),
    route_type INT(11),
    turn_bus_stop_id VARCHAR(20)
);

CREATE TABLE bus_through_info(
	id INT(11) NOT NULL PRIMARY KEY,
	route_id VARCHAR(20),
    bus_stop_station_id INT(11),
    bus_stop_sequence INT(11)
);

CREATE TABLE hibernate_sequence(
	next_val INT(11) PRIMARY KEY
);

INSERT INTO hibernate_sequence (next_val) VALUES (1);

SELECT * FROM bus_info;
SELECT * FROM bus_stop_station_info;
SELECT * FROM bus_through_info;
SELECT * FROM bus_info ORDER BY route_no ASC;
SELECT COUNT(*) FROM bus_info;

SELECT * FROM bus_info WHERE route_no = '58';
# 버스 노선이 58번인 노선 구하기
SELECT * FROM bus_through_info WHERE route_id IN (SELECT route_id FROM bus_info WHERE route_no = '58') ORDER BY bus_stop_sequence ASC;
SELECT * FROM bus_through_info B1 INNER JOIN bus_stop_station_info B2 ON B1.route_id = B2.route_id WHERE route_id = '161000007';


SELECT * FROM bus_stop_station_info WHERE id IN (
	SELECT bus_stop_station_id FROM bus_through_info WHERE route_id IN (
		SELECT route_id FROM bus_info WHERE route_no = '58'
	) ORDER BY bus_stop_sequence ASC
);
SELECT bus_stop_station_id FROM bus_through_info WHERE route_id IN (SELECT route_id FROM bus_info WHERE route_no = '58') ORDER BY bus_stop_sequence ASC;

SELECT COUNT(*) FROM bus_stop_station_info;

# safe mode 끄기
SET SQL_SAFE_UPDATES = 0;
DELETE FROM bus_stop;

SELECT * FROM bus_stop WHERE route_no = '2';
SELECT COUNT(DISTINCT bus_stop_id) FROM bus_stop;
#. select * from bus_stop group by DISTINCT bus_stop_id