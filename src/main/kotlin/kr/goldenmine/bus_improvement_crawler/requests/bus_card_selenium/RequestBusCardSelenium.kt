package kr.goldenmine.bus_improvement_crawler.requests.bus_card_selenium

import kr.goldenmine.bus_improvement_crawler.requests.ICrawlSingleSeleniumRequest
import org.hibernate.Session
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.Select
import java.io.File

class RequestBusCardSelenium: ICrawlSingleSeleniumRequest {
//    val innerDriver: WebDriver
//
//    init {
//
//    }

    override val driver: WebDriver
        get() = ChromeDriver()

    override fun getFolder(): File = File("bus_card_selenium")

    override fun crawlAll(session: Session) {
        driver.get("https://stcis.go.kr/pivotIndi/wpsPivotIndicator.do?siteGb=P&indiClss=IC03&indiSel=IC0303")

        val select = Select(driver.findElement(By.id("searchPopZoneSd")))
        select.selectByVisibleText("인천광역시")
    }

    override fun saveAll(session: Session) {

    }
}