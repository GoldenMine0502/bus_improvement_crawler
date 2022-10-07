package kr.goldenmine.bus_improvement_crawler.requests

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitServices {
    // https://stcis.go.kr/
    val BUS_CARD_SERVICE: BusCardService = Retrofit.Builder()
        .baseUrl("https://stcis.go.kr/")
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
        .create(BusCardService::class.java)

    // https://api.odcloud.kr/
    // https://api.odcloud.kr/
    // api/15052863/v1/uddi:0c83a2a5-24c6-4a11-8ec4-a842da62fb4f?page=1&perPage=10&serviceKey=Eojpb8YSWns0To%2BoSRzqiOiX8V0eaT7Nnsd%2B9mkWJqTr1vy%2Fw8S3oj8M5v303ljFcVOXMn5cr09BrqaN3fnXSg%3D%3D
    val TRAFFIC_SERVICE: TrafficService = Retrofit.Builder()
        .baseUrl("https://api.odcloud.kr/")
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
        .create(TrafficService::class.java)

    fun getGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

}