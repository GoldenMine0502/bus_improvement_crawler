package kr.goldenmine.bus_improvement_crawler

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.goldenmine.bus_improvement_crawler.requests.ICrawlRequest
import kr.goldenmine.bus_improvement_crawler.requests.bus_card.RequestBus
import kr.goldenmine.bus_improvement_crawler.requests.bus_stop.RequestBusStop
import kr.goldenmine.bus_improvement_crawler.requests.bus_traffic.RequestTraffic
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
    val log = LoggerFactory.getLogger(Main::class.java)
    val gson = Gson()
    val reader = File("keys.json").reader()

    val type = object : TypeToken<Keys>() {}.type
    val keys = gson.fromJson<Keys>(reader, type)
    reader.close()

    val toCrawl = listOf(
//        RequestBus(keys.requestBusCardKey, LOCATION_ID_INCHEON),
        RequestTraffic(keys.requestBusTrafficKey),
        RequestBusStop(keys.requestBusStopKey),
    )

    val registry = StandardServiceRegistryBuilder().configure(File("config/hibernate.cfg.xml")).build()
    val sessionFactory = MetadataSources(registry).buildMetadata().buildSessionFactory()

    toCrawl.forEach {
        log.info("${it.getFolder().path} started")
        it.progress(sessionFactory)
        log.info("${it.getFolder().path} finished")
    }
}
