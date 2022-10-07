CREATE DATABASE bus_improvement;

USE bus_improvement;

# [BusDetailResponseSpec(
# routeId=28308002, routeNo=58, routeStartNameToFinishName=송도제2차고지-송도제2차고지, 
# busStopIndex=0, busStopId=2807972, busStopName=송도제2차고지, sdCode=28, sggCode=28185, 
# emdCode=2818510600, sdName=인천광역시, sggName=연수구, emdName=송도동)


# 테이블 목록 보여주기
SHOW TABLES;

DROP TABLE bus_stop;
DROP TABLE hibernate_sequence;
CREATE TABLE bus_stop(
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    route_id VARCHAR(20), 
    route_no VARCHAR(10),
    route_name_start_to_finish VARCHAR(100),
    bus_stop_index INT(11),
    bus_stop_id VARCHAR(20),
    bus_stop_name VARCHAR(40)
);
CREATE TABLE hibernate_sequence(
	next_val INT(11) PRIMARY KEY
);
INSERT INTO hibernate_sequence (next_val) VALUES (1);

SELECT * FROM bus_stop;
SELECT COUNT(*) FROM bus_stop;

SELECT * FROM bus_stop WHERE route_no = '58';

# safe mode 끄기
SET SQL_SAFE_UPDATES = 0;
DELETE FROM bus_stop;

SELECT * FROM bus_stop WHERE route_no = '2';
SELECT COUNT(DISTINCT bus_stop_id) FROM bus_stop;
#. select * from bus_stop group by DISTINCT bus_stop_id