package kr.goldenmine.bus_improvement_crawler.requests.naver_map.request

import com.google.gson.annotations.SerializedName

data class PathResponseRoute(
    @SerializedName("traoptimal")
    val traoptimal: List<PathResponseRouteTraoptimal>?,
) {
}