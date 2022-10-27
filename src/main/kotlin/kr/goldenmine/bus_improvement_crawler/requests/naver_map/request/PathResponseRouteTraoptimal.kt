package kr.goldenmine.bus_improvement_crawler.requests.naver_map.request

import com.google.gson.annotations.SerializedName

data class PathResponseRouteTraoptimal(
    @SerializedName("summary")
    val summary: PathResponseRouteTraoptimalSummary?,

    @SerializedName("path")
    val path: List<List<Double>>?,

    @SerializedName("section")
    val sections: List<NaverSection>?,

    @SerializedName("guide")
    val guides: List<NaverGuide>?,
) {
}