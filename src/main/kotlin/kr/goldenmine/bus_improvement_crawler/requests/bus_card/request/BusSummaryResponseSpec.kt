package kr.goldenmine.bus_improvement_crawler.requests.bus_card.request

import com.google.gson.annotations.SerializedName

/*
응답결과
항목명	타입	설명
count	숫자	응답결과 건수
status	문자	상태값: OK(성공), NOT_FOUND(결과없음)
result
routeId	문자	응답결과 노선ID
routeNo	문자	응답결과 노선번호
stgSttnNma	문자	응답결과 기점
arrSttnNma	문자	응답결과 종점
 */
data class BusSummaryResponseSpec(
    @SerializedName("routeId")
    val routeId: String,

    @SerializedName("routeNo")
    val routeNo: String,

    @SerializedName("stgSttnNma")
    val startLocation: String,

    @SerializedName("arrSttnNma")
    val finishLocation: String
)