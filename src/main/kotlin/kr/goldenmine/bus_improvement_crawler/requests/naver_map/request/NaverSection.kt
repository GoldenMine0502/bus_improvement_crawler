package kr.goldenmine.bus_improvement_crawler.requests.naver_map.request

import com.google.gson.annotations.SerializedName

/*
                        "pointIndex": 0,
                        "pointCount": 6,
                        "distance": 562,
                        "name": "송도바이오대로",
                        "congestion": 1,
                        "speed": 51
 */
data class NaverSection(
    @SerializedName("pointIndex")
    private val pointIndex: Int?,

    @SerializedName("pointCount")
    private val pointCount: Int?,

    @SerializedName("distance")
    private val distance: Int?,

    @SerializedName("name")
    private val name: String?,

    @SerializedName("congestion")
    private val congestion: Int?,

    @SerializedName("speed")
    private val speed: Int?,
) {
}