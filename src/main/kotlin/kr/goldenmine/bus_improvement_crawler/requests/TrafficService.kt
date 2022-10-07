package kr.goldenmine.bus_improvement_crawler.requests

import kr.goldenmine.bus_improvement_crawler.requests.response.TrafficResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TrafficService {

    /*
    https://api.odcloud.kr/api/15052863/v1/uddi:0c83a2a5-24c6-4a11-8ec4-a842da62fb4f?
    serviceKey=Eojpb8YSWns0To%2BoSRzqiOiX8V0eaT7Nnsd%2B9mkWJqTr1vy%2Fw8S3oj8M5v303ljFcVOXMn5cr09BrqaN3fnXSg%3D%3D
    &page=1
    &perPage=1000
    namespace=15052863/v1
     */
    @GET("api/15052863/v1/uddi:0c83a2a5-24c6-4a11-8ec4-a842da62fb4f")
    fun getTraffic(
        @Query("serviceKey", encoded = true) serviceKey: String,
        @Query("page") page: Int,
        @Query("perPage") perPage: Int
    ): Call<TrafficResponse>

    // https://api.odcloud.kr/
    // api/15052863/v1/uddi:0c83a2a5-24c6-4a11-8ec4-a842da62fb4f
    // ?serviceKey=Eojpb8YSWns0To%2BoSRzqiOiX8V0eaT7Nnsd%2B9mkWJqTr1vy%2Fw8S3oj8M5v303ljFcVOXMn5cr09BrqaN3fnXSg%3D%3D&
    // page=1&
    // perPage=10
    @GET("api/15052863/v1/uddi:0c83a2a5-24c6-4a11-8ec4-a842da62fb4f")
    fun getTrafficRaw(
        @Query("namespace") nameSpace: String,
        @Query("serviceKey", encoded = true) serviceKey: String,
        @Query("page") page: Int,
        @Query("perPage") perPage: Int
    ): Call<String>


}