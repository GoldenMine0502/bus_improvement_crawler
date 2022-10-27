package kr.goldenmine.bus_improvement_crawler.requests.bus_card

import kr.goldenmine.bus_improvement_crawler.requests.bus_card.request.BusDetailResponse
import kr.goldenmine.bus_improvement_crawler.requests.bus_card.request.BusSummaryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BusCardService {
    // https://stcis.go.kr/openapi/busroute.json?
    // apikey=20221005142823j70lrh64mcq901lkbkl6o9a3mo&sdCd=28&routeNo=58
    @GET("openapi/busroute.json")
    fun requestBusSummary(
        @Query("apikey") apiKey: String,
        @Query("sdCd") locationId: Int,
        @Query("routeNo") routeNo: String
    ): Call<BusSummaryResponse>

//    @GET("openapi/busroute.json")
//    fun errorBusSummary(
//        @Query("apikey") apiKey: String,
//        @Query("sdCd") locationId: Int,
//        @Query("routeNo") routeNo: String
//    ): Call<String>

    // https://stcis.go.kr/openapi/busroutesttn.json?
    // apikey=(인증받은 API키)&sdCd=(시도코드)&sggCd=(시군구코드)&emdCd=(읍면동코드)&routeId=(노선ID)
    @GET("openapi/busroutesttn.json")
    fun requestBusInDetail(
        @Query("apikey") apiKey: String,
        @Query("sdCd") locationId: Int,
        @Query("routeId") routeId: String
    ): Call<BusDetailResponse>
}