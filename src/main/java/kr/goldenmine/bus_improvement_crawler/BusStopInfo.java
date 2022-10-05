package kr.goldenmine.bus_improvement_crawler;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bus_stop")
@SequenceGenerator(name="bus_stop_sequence_generator",
        sequenceName = "bus_stop_sequence"   // DB Sequence Name
        //, initialValue = 1,
        // allocationSize = 50
)
public class BusStopInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SerializedName("id")
    @Column(name = "id")
    int id;

    @SerializedName("route_id")
    @Column(name = "route_id")
    String routeId;

    @SerializedName("route_no")
    @Column(name = "route_no")
    String routeNo;

    @SerializedName("routeNm")
    @Column(name = "route_name_start_to_finish")
    String routeNameStartToFinish;

    /*
        bus_stop_index INT(11),
    bus_stop_id VARCHAR(20) UNIQUE KEY,
    bus_stop_name VARCHAR(40)
     */
    @SerializedName("bus_stop_index")
    @Column(name = "bus_stop_index")
    int busStopIndex;

    @SerializedName("bus_stop_id")
    @Column(name = "bus_stop_id")
    String busStopId;

    @SerializedName("bus_stop_name")
    @Column(name = "bus_stop_name")
    String busStopName;
}
