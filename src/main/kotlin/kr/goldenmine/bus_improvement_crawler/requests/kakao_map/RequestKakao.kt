package kr.goldenmine.bus_improvement_crawler.requests.kakao_map

import kr.goldenmine.bus_improvement_crawler.requests.ICrawlRetrofitRequest
import kr.goldenmine.bus_improvement_crawler.requests.kakao_map.request.KakaoKeywordResponse
import kr.goldenmine.bus_improvement_crawler.requests.naver_map.NaverService
import org.hibernate.Session
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File

class RequestKakao(
    val key: String
): ICrawlRetrofitRequest<KakaoService> {
    override val service: KakaoService
        get(): KakaoService = Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(KakaoService::class.java)

    override fun getFolder(): File = File("kakao")

    override fun crawlAll(session: Session) {
//        session.createQuery()
    }

    fun apiTest(keyword: String): KakaoKeywordResponse? {
        return service.getInfoFromKeyword(key, keyword).execute().body()
    }

    override fun saveAll(session: Session) {

    }
}