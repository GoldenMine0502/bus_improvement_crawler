package kr.goldenmine.bus_improvement_crawler.requests.bus_stop

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BusStopService {
    @GET("6280000/busRouteService/getBusRouteSectionList")
    fun getBusRouteSectionList(
        @Query("ServiceKey", encoded = true) serviceKey:String,
        @Query("pageNo") page: Int,
        @Query("numOfRows") perPage: Int,
        @Query("routeId") routeId: String
    ): Call<BusStopSectionResponse>

    @GET("6280000/busRouteService/getBusRouteNo")
    fun getBusRouteNo(
        @Query("ServiceKey", encoded = true) serviceKey:String,
        @Query("pageNo") page: Int,
        @Query("numOfRows") perPage: Int,
        @Query("routeNo") routeNo: String
    ): Call<BusStopRouteResponse>

    @GET("6280000/busRouteService/getBusRouteNo")
    fun getBusRouteNoRaw(
        @Query("ServiceKey", encoded = true) serviceKey:String,
        @Query("pageNo") page: Int,
        @Query("numOfRows") perPage: Int,
        @Query("routeNo") routeNo: String
    ): Call<String>
}