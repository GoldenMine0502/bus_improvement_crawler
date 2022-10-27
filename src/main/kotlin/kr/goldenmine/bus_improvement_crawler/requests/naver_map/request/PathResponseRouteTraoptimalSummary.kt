package kr.goldenmine.bus_improvement_crawler.requests.naver_map.request

import com.google.gson.annotations.SerializedName

data class PathResponseRouteTraoptimalSummary(
    @SerializedName("start")
    private val start: NaverPoint?,

    @SerializedName("goal")
    private val goal: NaverPoint?,

    @SerializedName("distance")
    private val distance: Int?,

    @SerializedName("duration")
    private val duration: Int?,

    @SerializedName("etaServiceType")
    private val etaServiceType: Int?,

    @SerializedName("departureTime")
    private val departureTime: String?,

    @SerializedName("bbox")
    val bbox: List<List<Double>>?,

    @SerializedName("tollFare")
    val tollFare: Int?,

    @SerializedName("taxiFare")
    val taxiFare: Int?,

    @SerializedName("fuelPrice")
    val fuelPrice: Int?,
) {
}