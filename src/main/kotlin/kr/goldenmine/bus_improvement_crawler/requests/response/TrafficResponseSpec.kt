package kr.goldenmine.bus_improvement_crawler.requests.response

import com.google.gson.annotations.SerializedName
import kr.goldenmine.bus_improvement_crawler.requests.TrafficInfo

/*
            "00시": "0",
            "01시": "0",
            "02시": "0",
            "03시": "0",
            "04시": "0",
            "05시": "0",
            "06시": "0",
            "07시": "0",
            "08시": "0",
            "09시": "0",
            "10시": "0",
            "11시": "0",
            "12시": "0",
            "13시": "0",
            "14시": "0",
            "15시": "0",
            "16시": "0",
            "17시": "0",
            "18시": "0",
            "19시": "0",
            "20시": "0",
            "21시": "0",
            "22시": "0",
            "23시": "0"
 */
class TrafficResponseSpec(
    @SerializedName("00시")
    val time00: String,

    @SerializedName("01시")
    val time01: String,

    @SerializedName("02시")
    val time02: String,

    @SerializedName("03시")
    val time03: String,

    @SerializedName("04시")
    val time04: String,

    @SerializedName("05시")
    val time05: String,

    @SerializedName("06시")
    val time06: String,

    @SerializedName("07시")
    val time07: String,

    @SerializedName("08시")
    val time08: String,

    @SerializedName("09시")
    val time09: String,

    @SerializedName("10시")
    val time10: String,

    @SerializedName("11시")
    val time11: String,

    @SerializedName("12시")
    val time12: String,

    @SerializedName("13시")
    val time13: String,

    @SerializedName("14시")
    val time14: String,

    @SerializedName("15시")
    val time15: String,

    @SerializedName("16시")
    val time16: String,

    @SerializedName("17시")
    val time17: String,

    @SerializedName("18시")
    val time18: String,

    @SerializedName("19시")
    val time19: String,

    @SerializedName("20시")
    val time20: String,

    @SerializedName("21시")
    val time21: String,

    @SerializedName("22시")
    val time22: String,

    @SerializedName("23시")
    val time23: String,

    @SerializedName("거리")
    val distance: String,

    @SerializedName("기능유형")
    val type: String,

    @SerializedName("도로명")
    val roadName: String,

    @SerializedName("링크아이디")
    val linkId: String,

    @SerializedName("방향")
    val direction: String,

    @SerializedName("시점명")
    val startName: String,

    @SerializedName("요일")
    val week: String,

    @SerializedName("일자")
    val date: String,

    @SerializedName("종점명")
    val finishName: String
) {
    val trafficSum = time00 + time01 + time02 + time03 + time04 + time05 +
            time06 + time07 + time08 + time09 + time10 + time11 +
            time12 + time13 + time14 + time15 + time16 + time17 +
            time18 + time19 + time20 + time21 + time22 + time23
}