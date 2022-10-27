package kr.goldenmine.bus_improvement_crawler.requests.kakao_map.database

import com.google.gson.annotations.SerializedName
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/*
CREATE TABLE road_name_info(
	id INT(11) NOT NULL PRIMARY KEY,
    road_name VARCHAR(30),
    pos_x DOUBLE,
    pos_y DOUBLE
);
 */
@Entity
@Table(name = "road_name_info")
class RoadNameInfo(
    @Id
    @Column(name = "id")
    @SerializedName("id")
    val id: Int? = null,

    @Column(name = "road_name")
    @SerializedName("roadName")
    val roadName: String? = null,

    @Column(name = "pos_x")
    @SerializedName("posX")
    val posX: Double? = null,

    @Column(name = "pos_y")
    @SerializedName("posY")
    val posY: Double? = null,
    ) {
}