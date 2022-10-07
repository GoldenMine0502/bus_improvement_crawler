package kr.goldenmine.bus_improvement_crawler.requests.response

import com.google.gson.annotations.SerializedName

/*
{
    "currentCount": 1000,
    "data": [
    "matchCount": 30084,
    "page": 1,
    "perPage": 10,
    "totalCount": 30084
 */
class TrafficResponse(
    @SerializedName("currentCount")
    val count: Int,

    @SerializedName("data")
    val data: List<TrafficResponseSpec>,

    @SerializedName("matchCount")
    val matchCount: Int,

    @SerializedName("page")
    val page: Int,

    @SerializedName("perPage")
    val perPage: Int,

    @SerializedName("totalCount")
    val totalCount: Int
)