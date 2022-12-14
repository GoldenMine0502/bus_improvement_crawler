package kr.goldenmine.bus_improvement_crawler

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.bonigarcia.wdm.WebDriverManager
import kr.goldenmine.bus_improvement_crawler.CrawlInfo
import kr.goldenmine.bus_improvement_crawler.requests.bus_card_selenium.RequestBusCardBusStopSeleniumMulti
import kr.goldenmine.bus_improvement_crawler.requests.bus_card_selenium.RequestBusCardBusUsageSeleniumMulti
import kr.goldenmine.bus_improvement_crawler.requests.bus_stop.RequestBusStop
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.slf4j.LoggerFactory
import java.io.File

/*
당시 mysql 세팅을 다시 설정해보고 연결 확인을 몇번을 해봤지만 문제가 없어서 몇시간을 해맨 끝에 알아낸 원인은
패스워드는 대문자, 소문자, 숫자, 특수문자를 포함한 암호 길이 8자 이상으로 설정해야 연결이 제대로 된다였다!!!!
(원인 파악 후 매우 허탈하였다!!!)
 */
const val LOCATION_ID_INCHEON = 28

// https://stackoverflow.com/questions/59365267/intellij-idea-how-to-specify-main-class-in-kotlin
class Main {
    companion object {
        @JvmStatic fun main(args : Array<String>) {
            try {
                WebDriverManager.chromedriver().setup()
            } catch(ex: Exception) {
                ex.printStackTrace()
            }
            val log = LoggerFactory.getLogger(Main::class.java)
            val gson = Gson()
            val reader = File("crawlinfo.json").reader()

            // final + 변수타입
            val type = object : TypeToken<CrawlInfo>() {}.type
            val crawlInfo = gson.fromJson<CrawlInfo>(reader, type)
            reader.close()

            val toCrawl = listOf(
            //        RequestTraffic(keys.requestBusTrafficKey),
                RequestBusStop(crawlInfo.requestBusStopKey),
            //        RequestKakao(keys.requestKakaoKey),
            //        RequestNaver(keys.requestNaverKeyId, keys.requestNaverKey),
                RequestBusCardBusStopSeleniumMulti(8, 16, false, crawlInfo.month),
                RequestBusCardBusUsageSeleniumMulti(4, 16, false, crawlInfo.year, crawlInfo.month),
            )

            val registry = StandardServiceRegistryBuilder().configure(File("config/hibernate.cfg.xml")).build()
            val sessionFactory = MetadataSources(registry).buildMetadata().buildSessionFactory()

            val session = sessionFactory.openSession()
            //    RequestNaver(keys.requestNaverKeyId, keys.requestNaverKey).saveAll(session)
            //    RequestBusCardBusUsageSeleniumMulti(0, 16, false, crawlInfo.year, crawlInfo.month).saveAll(session)
            toCrawl.forEach {
                log.info("${it.getFolder().path} started")
                try {
                    it.saveAll(session)
                    // it.progress(sessionFactory)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
                log.info("${it.getFolder().path} finished")
            }

            session.close()
        }
    }
}
