package kr.goldenmine.bus_improvement_crawler.requests.naver_map.request

import com.google.gson.annotations.SerializedName

/*
                        "pointIndex": 5,
                        "type": 6,
                        "instructions": "유턴",
                        "distance": 562,
                        "duration": 39060
 */
data class NaverGuide(
    @SerializedName("pointIndex")
    private val pointIndex: Int?,

    @SerializedName("type")
    private val type: Int?,

    @SerializedName("instructions")
    private val instructions: String?,

    @SerializedName("distance")
    private val distance: Int?,

    @SerializedName("duration")
    private val duration: Int?,
) {
}