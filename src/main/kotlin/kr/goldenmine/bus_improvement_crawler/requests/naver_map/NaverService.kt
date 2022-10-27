package kr.goldenmine.bus_improvement_crawler.requests.naver_map

import kr.goldenmine.bus_improvement_crawler.requests.naver_map.request.PathResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverService {
    @GET("map-direction/v1/driving")
    fun getPath(
        @Query("X-NCP-APIGW-API-KEY-ID") apiKeyId: String,
        @Query("X-NCP-APIGW-API-KEY") apiKey: String,
        @Query("start") start: String,
        @Query("goal") goal: String,
    ): Call<PathResponse>
}