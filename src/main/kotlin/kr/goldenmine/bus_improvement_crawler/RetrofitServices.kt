package kr.goldenmine.bus_improvement_crawler

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitServices {
    //https://stcis.go.kr/
    val BUS_CARD_SERVICE: BusCardService = Retrofit.Builder()
        .baseUrl("https://stcis.go.kr/")
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
        .create(BusCardService::class.java)
}