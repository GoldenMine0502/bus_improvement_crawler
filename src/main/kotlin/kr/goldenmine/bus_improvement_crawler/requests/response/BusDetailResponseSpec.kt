package kr.goldenmine.bus_improvement_crawler.requests.response

import com.google.gson.annotations.SerializedName

//       "routeId": "28308002",
//      "routeNo": "58",
//      "routeNm": "송도제2차고지-송도제2차고지",
//      "sttnSeq": 0,
//      "sttnId": "2807972",
//      "sttnNm": "송도제2차고지",
//      "sdCd": "28",
//      "sggCd": "28185",
//      "emdCd": "2818510600",
//      "sdNm": "인천광역시",
//      "sggNm": "연수구",
//      "emdNm": "송도동"
data class BusDetailResponseSpec(
    @SerializedName("routeId")
    val routeId: String,

    @SerializedName("routeNo")
    val routeNo: String,

    @SerializedName("routeNm")
    val routeNameStartToFinish: String,

    @SerializedName("sttnSeq")
    val busStopIndex: Int,

    @SerializedName("sttnId")
    val busStopId: String,

    @SerializedName("sttnNm")
    val busStopName: String,

    @SerializedName("sdCd")
    val sdCode: String,

    @SerializedName("sggCd")
    val sggCode: String,

    @SerializedName("emdCd")
    val emdCode: String,

    @SerializedName("sdNm")
    val sdName: String,

    @SerializedName("sggNm")
    val sggName: String,

    @SerializedName("emdNm")
    val emdName: String
) {

}