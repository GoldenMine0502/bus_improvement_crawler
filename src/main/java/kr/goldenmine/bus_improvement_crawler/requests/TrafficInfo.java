package kr.goldenmine.bus_improvement_crawler.requests;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "traffic")
//@SequenceGenerator(name="traffic_sequence_generator",
//        sequenceName = "traffic_sequence"   // DB Sequence Name
//        //, initialValue = 1,
//        // allocationSize = 50
//)
public class TrafficInfo {

    @Id
    @SerializedName("id")
    @Column(name = "id")
    int id;

    @SerializedName("00시")
    @Column(name = "time00")
    String time00;

    @SerializedName("01시")
    @Column(name = "time01")
    String time01;

    @SerializedName("02시")
    @Column(name = "time02")
    String time02;

    @SerializedName("03시")
    @Column(name = "time03")
    String time03;

    @SerializedName("04시")
    @Column(name = "time04")
    String time04;

    @SerializedName("05시")
    @Column(name = "time05")
    String time05;

    @SerializedName("06시")
    @Column(name = "time06")
    String time06;

    @SerializedName("07시")
    @Column(name = "time07")
    String time07;

    @SerializedName("08시")
    @Column(name = "time08")
    String time08;

    @SerializedName("09시")
    @Column(name = "time09")
    String time09;

    @SerializedName("10시")
    @Column(name = "time10")
    String time10;

    @SerializedName("11시")
    @Column(name = "time11")
    String time11;

    @SerializedName("12시")
    @Column(name = "time12")
    String time12;

    @SerializedName("13시")
    @Column(name = "time13")
    String time13;

    @SerializedName("14시")
    @Column(name = "time14")
    String time14;

    @SerializedName("15시")
    @Column(name = "time15")
    String time15;

    @SerializedName("16시")
    @Column(name = "time16")
    String time16;

    @SerializedName("17시")
    @Column(name = "time17")
    String time17;

    @SerializedName("18시")
    @Column(name = "time18")
    String time18;

    @SerializedName("19시")
    @Column(name = "time19")
    String time19;

    @SerializedName("20시")
    @Column(name = "time20")
    String time20;

    @SerializedName("21시")
    @Column(name = "time21")
    String time21;

    @SerializedName("22시")
    @Column(name = "time22")
    String time22;

    @SerializedName("23시")
    @Column(name = "time23")
    String time23;

    @SerializedName("거리")
    @Column(name = "distance")
    String distance;

    @SerializedName("기능유형")
    @Column(name = "type")
    String type;

    @SerializedName("도로명")
    @Column(name = "road_name")
    String roadName;

    @SerializedName("링크아이디")
    @Column(name = "link_id")
    String linkId;

    @SerializedName("방향")
    @Column(name = "direction")
    String direction;

    @SerializedName("시점명")
    @Column(name = "start_name")
    String startName;

    @SerializedName("요일")
    @Column(name = "week")
    String week;

    @SerializedName("일자")
    @Column(name = "date")
    String date;

    @SerializedName("종점명")
    @Column(name = "finish_name")
    String finishName;
}
