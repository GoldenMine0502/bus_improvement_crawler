package kr.goldenmine.bus_improvement_crawler.requests.bus_card.request

import com.google.gson.annotations.SerializedName

data class BusDetailResponse(
    @SerializedName("count")
    val count: Int,

    @SerializedName("result")
    val results: List<BusDetailResponseSpec>,
) {

}