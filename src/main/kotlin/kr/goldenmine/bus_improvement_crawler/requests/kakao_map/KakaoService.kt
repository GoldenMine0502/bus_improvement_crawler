package kr.goldenmine.bus_improvement_crawler.requests.kakao_map

import kr.goldenmine.bus_improvement_crawler.requests.kakao_map.request.KakaoKeywordResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoService {
    @GET("v2/local/search/keyword.json")
    fun getInfoFromKeyword(
        @Header("Authorization") authorization: String,
        @Query("query") keyword: String,
    ): Call<KakaoKeywordResponse>
}