package kr.goldenmine.bus_improvement_crawler.requests.bus_stop.database

import com.google.gson.annotations.SerializedName
import lombok.Data
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

//@Data
@Entity
@Table(name = "bus_stop_station_info")
class BusStopStationInfo(
    /*
	id VARCHAR(20) NOT NULL PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
	pos_x FLOAT,
    pos_y FLOAT,
    short_id INT(11),
    admin_name VARCHAR(20)
     */
    @Id
    @SerializedName("id")
    @Column(name = "id")
    val id: Int? = null,

    @SerializedName("name")
    @Column(name = "name")
    val name: String? = null,

    @SerializedName("pos_x")
    @Column(name = "pos_x")
    val posX: Double? = null,

    @SerializedName("pos_y")
    @Column(name = "pos_y")
    val posY: Double? = null,

    @SerializedName("short_id")
    @Column(name = "short_id")
    val shortId: Int? = null,

    @SerializedName("admin_name")
    @Column(name = "admin_name")
    val adminName: String? = null,
) {

}