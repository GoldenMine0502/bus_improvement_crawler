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
    var id: Int = 0,

    @Column(name = "road_name")
    @SerializedName("roadName")
    var roadName: String? = null,

    @Column(name = "pos_x")
    @SerializedName("posX")
    var posX: Double? = null,

    @Column(name = "pos_y")
    @SerializedName("posY")
    var posY: Double? = null,
) {

    override fun toString(): String {
        return "{id=$id,roadName=$roadName,posX=$posX,posY=$posY}"
    }
}