package kr.goldenmine.bus_improvement_crawler.requests.kakao_map.request

import com.google.gson.annotations.SerializedName

data class KakaoKeywordResponse(
    @SerializedName("documents")
    val documents: List<KakaoKeywordResponseDocument>?,

    @SerializedName("meta")
    val meta: KakaoKeywordResponseMeta?,

    )