package kr.goldenmine.bus_improvement_crawler.requests.kakao_map.request

import com.google.gson.annotations.SerializedName

/*
            "keyword": "모래방죽사거리",
            "region": [],
            "selected_region": ""
 */
data class KakaoKeywordResponseMetaSameName(
    @SerializedName("keyword")
    val keyword: String?,

//    @SerializedName("region"),
//    val region: List<>,

    @SerializedName("selected_region")
    val selectedRegion: String?,

) {
}