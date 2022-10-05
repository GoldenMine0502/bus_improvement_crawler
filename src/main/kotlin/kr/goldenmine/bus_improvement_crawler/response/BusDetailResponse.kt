package kr.goldenmine.bus_improvement_crawler.response

import com.google.gson.annotations.SerializedName

class BusDetailResponse(
    @SerializedName("count")
    val count: Int,

    @SerializedName("result")
    val results: List<BusDetailResponseSpec>,
) {

}