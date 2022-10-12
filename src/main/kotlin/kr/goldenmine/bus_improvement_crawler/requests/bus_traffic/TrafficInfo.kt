package kr.goldenmine.bus_improvement_crawler.requests.bus_traffic

import com.google.gson.annotations.SerializedName
import lombok.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Data
@Entity
@Table(name = "traffic")
class TrafficInfo {
    @Id
    @SerializedName("id")
    @Column(name = "id")
    var id = 0

    @SerializedName("00시")
    @Column(name = "time00")
    var time00: String? = null

    @SerializedName("01시")
    @Column(name = "time01")
    var time01: String? = null

    @SerializedName("02시")
    @Column(name = "time02")
    var time02: String? = null

    @SerializedName("03시")
    @Column(name = "time03")
    var time03: String? = null

    @SerializedName("04시")
    @Column(name = "time04")
    var time04: String? = null

    @SerializedName("05시")
    @Column(name = "time05")
    var time05: String? = null

    @SerializedName("06시")
    @Column(name = "time06")
    var time06: String? = null

    @SerializedName("07시")
    @Column(name = "time07")
    var time07: String? = null

    @SerializedName("08시")
    @Column(name = "time08")
    var time08: String? = null

    @SerializedName("09시")
    @Column(name = "time09")
    var time09: String? = null

    @SerializedName("10시")
    @Column(name = "time10")
    var time10: String? = null

    @SerializedName("11시")
    @Column(name = "time11")
    var time11: String? = null

    @SerializedName("12시")
    @Column(name = "time12")
    var time12: String? = null

    @SerializedName("13시")
    @Column(name = "time13")
    var time13: String? = null

    @SerializedName("14시")
    @Column(name = "time14")
    var time14: String? = null

    @SerializedName("15시")
    @Column(name = "time15")
    var time15: String? = null

    @SerializedName("16시")
    @Column(name = "time16")
    var time16: String? = null

    @SerializedName("17시")
    @Column(name = "time17")
    var time17: String? = null

    @SerializedName("18시")
    @Column(name = "time18")
    var time18: String? = null

    @SerializedName("19시")
    @Column(name = "time19")
    var time19: String? = null

    @SerializedName("20시")
    @Column(name = "time20")
    var time20: String? = null

    @SerializedName("21시")
    @Column(name = "time21")
    var time21: String? = null

    @SerializedName("22시")
    @Column(name = "time22")
    var time22: String? = null

    @SerializedName("23시")
    @Column(name = "time23")
    var time23: String? = null

    @SerializedName("거리")
    @Column(name = "distance")
    var distance: String? = null

    @SerializedName("기능유형")
    @Column(name = "type")
    var type: String? = null

    @SerializedName("도로명")
    @Column(name = "road_name")
    var roadName: String? = null

    @SerializedName("링크아이디")
    @Column(name = "link_id")
    var linkId: String? = null

    @SerializedName("방향")
    @Column(name = "direction")
    var direction: String? = null

    @SerializedName("시점명")
    @Column(name = "start_name")
    var startName: String? = null

    @SerializedName("요일")
    @Column(name = "week")
    var week: String? = null

    @SerializedName("일자")
    @Column(name = "date")
    var date: String? = null

    @SerializedName("종점명")
    @Column(name = "finish_name")
    var finishName: String? = null
}