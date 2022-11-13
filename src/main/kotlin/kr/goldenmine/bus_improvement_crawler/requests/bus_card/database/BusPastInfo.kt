package kr.goldenmine.bus_improvement_crawler.requests.bus_card.database

import com.google.gson.annotations.SerializedName
import lombok.*
import javax.persistence.*

@Data
@Entity
@Table(name = "bus_stop")
@SequenceGenerator(name = "bus_stop_sequence_generator", sequenceName = "bus_stop_sequence")
class BusPastInfo {
    @Id //    @GeneratedValue(strategy = GenerationType.AUTO)
    @SerializedName("id")
    @Column(name = "id")
    var id = 0

    @SerializedName("route_id")
    @Column(name = "route_id")
    var routeId: String? = null

    @SerializedName("route_no")
    @Column(name = "route_no")
    var routeNo: String? = null

    @SerializedName("routeNm")
    @Column(name = "route_name_start_to_finish")
    var routeNameStartToFinish: String? = null

    /*
        bus_stop_index INT(11),
    bus_stop_id VARCHAR(20) UNIQUE KEY,
    bus_stop_name VARCHAR(40)
     */
    @SerializedName("bus_stop_index")
    @Column(name = "bus_stop_index")
    var busStopIndex = 0

    @SerializedName("bus_stop_id")
    @Column(name = "bus_stop_id")
    var busStopId: String? = null

    @SerializedName("bus_stop_name")
    @Column(name = "bus_stop_name")
    var busStopName: String? = null
}