package kr.goldenmine.bus_improvement_crawler.requests.naver_map.request

import com.google.gson.annotations.SerializedName

data class PathResponse(
    @SerializedName("code")
    val code: Int?,

    @SerializedName("message")
    val message: String?,

    @SerializedName("currentDateTime")
    val currentDateTime: String?,

    @SerializedName("route")
    val route: PathResponseRoute?,
) {
}