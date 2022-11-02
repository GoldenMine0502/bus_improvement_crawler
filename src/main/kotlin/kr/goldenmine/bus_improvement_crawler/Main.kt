package kr.goldenmine.bus_improvement_crawler

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.bonigarcia.wdm.WebDriverManager
import kr.goldenmine.bus_improvement_crawler.requests.bus_card_selenium.RequestBusCardSelenium
import kr.goldenmine.bus_improvement_crawler.requests.bus_stop.RequestBusStop
import kr.goldenmine.bus_improvement_crawler.requests.bus_traffic.RequestTraffic
import kr.goldenmine.bus_improvement_crawler.requests.kakao_map.RequestKakao
import kr.goldenmine.bus_improvement_crawler.requests.naver_map.RequestNaver
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.slf4j.LoggerFactory
import java.io.File

/*
당시 mysql 세팅을 다시 설정해보고 연결 확인을 몇번을 해봤지만 문제가 없어서 몇시간을 해맨 끝에 알아낸 원인은
패스워드는 대문자, 소문자, 숫자, 특수문자를 포함한 암호 길이 8자 이상으로 설정해야 연결이 제대로 된다였다!!!!
(원인 파악 후 매우 허탈하였다!!!)
 */

class Main

const val LOCATION_ID_INCHEON = 28

fun main() {
    WebDriverManager.chromedriver().setup()

    val log = LoggerFactory.getLogger(Main::class.java)
    val gson = Gson()
    val reader = File("keys.json").reader()

    // final + 변수타입
    val type = object : TypeToken<Keys>() {}.type
    val keys = gson.fromJson<Keys>(reader, type)
    reader.close()

    val toCrawl = listOf(
//        RequestBus(keys.requestBusCardKey, LOCATION_ID_INCHEON),
//        RequestTraffic(keys.requestBusTrafficKey),
//        RequestBusStop(keys.requestBusStopKey),
//        RequestKakao(keys.requestKakaoKey),
        RequestNaver(keys.requestNaverKeyId, keys.requestNaverKey)
    )

    val registry = StandardServiceRegistryBuilder().configure(File("config/hibernate.cfg.xml")).build()
    val sessionFactory = MetadataSources(registry).buildMetadata().buildSessionFactory()

    val session = sessionFactory.openSession()
//    RequestNaver(keys.requestNaverKeyId, keys.requestNaverKey).crawlAll(session)
    RequestBusCardSelenium().crawlAll(session)
//    toCrawl.forEach {
//        log.info("${it.getFolder().path} started")
//        try {
//            it.progress(sessionFactory)
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//        }
//        log.info("${it.getFolder().path} finished")
//    }

    session.close()
}