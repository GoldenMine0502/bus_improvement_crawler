package kr.goldenmine.bus_improvement_crawler

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.goldenmine.bus_improvement_crawler.requests.kakao_map.RequestKakao
import kr.goldenmine.bus_improvement_crawler.requests.naver_map.RequestNaver
import org.junit.jupiter.api.Test
import java.io.File

class RequestTest {

    private val keys: Keys

    init {
        // read keys
        val type = object : TypeToken<Keys>() {}.type
        val gson = Gson()
        val reader = File("keys.json").reader()

        // keys
        keys = gson.fromJson(reader, type)
    }

    @Test
    fun naverTest() {
        // request
        val request = RequestNaver(keys.requestNaverKeyId, keys.requestNaverKey)
        val start = "126.66532891179264,37.366824870185276"
        val goal = "126.65791852187012,37.365799853663844"

        val result = request.apiTest(start, goal)
        println(result)
        println(result?.route?.traoptimal?.get(0)?.path)
    }

    @Test
    fun kakaoTest() {
        val request = RequestKakao(keys.requestKakaoKey)

        val result = request.apiTest("모래방죽사거리")
        println(result)
        println(result?.documents?.get(0))
    }
}