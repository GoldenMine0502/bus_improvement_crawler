package kr.goldenmine.bus_improvement_crawler.requests.kakao_map.request

import com.google.gson.annotations.SerializedName

/*
        "is_end": true,
        "pageable_count": 1,
        "same_name": {
            "keyword": "모래방죽사거리",
            "region": [],
            "selected_region": ""
        },
        "total_count": 1
 */
data class KakaoKeywordResponseMeta(
    @SerializedName("is_end")
    val isEnd: Boolean?,

    @SerializedName("pageable_count")
    val pageableCount: Int?,

    @SerializedName("same_name")
    val sameName: KakaoKeywordResponseMetaSameName?,

    @SerializedName("total_count")
    val totalCount: Int?,

) {
}