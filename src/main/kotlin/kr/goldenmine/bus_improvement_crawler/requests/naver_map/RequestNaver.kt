package kr.goldenmine.bus_improvement_crawler.requests.naver_map

import kr.goldenmine.bus_improvement_crawler.requests.ICrawlRetrofitRequest
import kr.goldenmine.bus_improvement_crawler.requests.naver_map.request.PathResponse
import org.hibernate.Session
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File

class RequestNaver(
    private val serviceKeyId: String,
    private val serviceKey: String,
): ICrawlRetrofitRequest<NaverService> {
    override val service: NaverService
        get(): NaverService = Retrofit.Builder()
    .baseUrl("https://naveropenapi.apigw.ntruss.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .addConverterFactory(ScalarsConverterFactory.create())
    .build()
    .create(NaverService::class.java)

    override fun getFolder() = File("naver")

    override fun crawlAll(session: Session) {
        val start = "126.66532891179264,37.366824870185276"
        val goal = "126.65791852187012,37.365799853663844"

        service.getPath(serviceKeyId, serviceKey, start, goal).enqueue(object : Callback<PathResponse> {
            override fun onResponse(call: Call<PathResponse>, response: Response<PathResponse>) {
//                println(response.body())
                println(response.isSuccessful)

                println(response.body()?.route?.traoptimal?.get(0)?.path)
            }

            override fun onFailure(call: Call<PathResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    override fun saveAll(session: Session) {

    }
}