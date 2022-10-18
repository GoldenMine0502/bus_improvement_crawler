package kr.goldenmine.bus_improvement_crawler.requests.naver_map

import kr.goldenmine.bus_improvement_crawler.requests.ICrawlSingleSeleniumRequest
import org.hibernate.Session
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import java.io.File

class NaverService : ICrawlSingleSeleniumRequest {
    override val driver: WebDriver = ChromeDriver()
    override fun getFolder(): File = File("Naver")

    // https://onecellboy.tistory.com/355
    override fun crawlAll(session: Session) {

    }

    override fun saveAll(session: Session) {

    }


}