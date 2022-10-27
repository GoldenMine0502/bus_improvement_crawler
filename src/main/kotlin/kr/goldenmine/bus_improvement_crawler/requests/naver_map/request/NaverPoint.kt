package kr.goldenmine.bus_improvement_crawler.requests.naver_map.request

import com.google.gson.annotations.SerializedName

data class NaverPoint(
    @SerializedName("location")
    private val location: List<Double>?,

    @SerializedName("dir")
    private val dir: Int?,
) {
}