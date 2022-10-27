package kr.goldenmine.bus_improvement_crawler.requests.naver_map

import kr.goldenmine.bus_improvement_crawler.requests.ICrawlSingleSeleniumRequest
import org.hibernate.Session
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import java.io.File

class RequestNaverSelenium : ICrawlSingleSeleniumRequest {
    override val driver: WebDriver = ChromeDriver()
    override fun getFolder(): File = File("Naver")

    // https://onecellboy.tistory.com/355
    override fun crawlAll(session: Session) {
        val query = session.createSQLQuery("SELECT DISTINCT * FROM (SELECT start_name FROM traffic UNION ALL SELECT finish_name FROM traffic) T")
//        val query = session.createNativeQuery("SELECT DISTINCT start_name FROM TrafficInfo")
        val buses = query.resultList as List<String>


        buses.forEach {

        }
    }

    override fun saveAll(session: Session) {

    }


}