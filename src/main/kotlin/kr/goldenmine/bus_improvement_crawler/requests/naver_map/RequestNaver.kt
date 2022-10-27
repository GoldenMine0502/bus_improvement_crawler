package kr.goldenmine.bus_improvement_crawler.requests.naver_map

import kr.goldenmine.bus_improvement_crawler.requests.ICrawlRetrofitRequest
import kr.goldenmine.bus_improvement_crawler.requests.naver_map.request.PathResponse
import kr.goldenmine.bus_improvement_crawler.util.Point
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

    }

    fun convert() {

    }

    fun getStringFromPoint(point: Point) {

    }

    fun apiTest(start: String, goal: String): PathResponse? {
        return service.getPath(serviceKeyId, serviceKey, start, goal).execute().body()
    }

    override fun saveAll(session: Session) {

    }
}