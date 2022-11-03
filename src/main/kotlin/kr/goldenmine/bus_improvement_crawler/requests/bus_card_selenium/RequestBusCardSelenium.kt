package kr.goldenmine.bus_improvement_crawler.requests.bus_card_selenium

import kr.goldenmine.bus_improvement_crawler.requests.ICrawlSingleSeleniumRequest
import kr.goldenmine.bus_improvement_crawler.requests.bus_stop.database.BusStopStationInfo
import org.hibernate.Session
import org.openqa.selenium.Alert
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.lang.Thread.sleep
import java.time.Duration


class RequestBusCardSelenium: ICrawlSingleSeleniumRequest {
    private val log: Logger = LoggerFactory.getLogger(RequestBusCardSelenium::class.java)

    override val driver: WebDriver
        get() {
            val prefs: MutableMap<String, Any> = HashMap()
            prefs["download.default_directory"] = getFolder().absolutePath

            val options = ChromeOptions()
            options.setExperimentalOption("prefs", prefs)

            return ChromeDriver(options)
        }

    override fun getFolder(): File = File("bus_card_selenium")

    override fun crawlAll(session: Session) {
        val driver = driver

        Runtime.getRuntime().addShutdownHook(Thread { driver.quit() })

        val dateFromText = "19"
        val middleText = "19"
        val dateToText = "21"
        val busStopStationInfoList = (session.createQuery("FROM BusStopStationInfo").list() as List<BusStopStationInfo?>).filterNotNull()

        val incheonList = setOf("강화군", "계양구", "남동구", "동구", "미추홀구",
            "부평구", "서구", "연수구", "옹진군", "중구")

        val incheonMap = mapOf(
            Pair("강화군", "28710"),
            Pair("계양구", "28245"),
            Pair("남동구", "28200"),
            Pair("동구", "28140"),
            Pair("미추홀구", "28177"),

            Pair("부평구", "28237"),
            Pair("서구", "28260"),
            Pair("연수구", "28185"),
            Pair("옹진군", "28720"),
            Pair("중구", "28110"),
        )

        busStopStationInfoList.filter { incheonList.contains(it.adminName) }.forEach {busStopStationInfo ->
            if(busStopStationInfo.shortId != null) {
                driver.get("https://stcis.go.kr/pivotIndi/wpsPivotIndicator.do?siteGb=P&indiClss=IC03")

                sleep(1000L)

                // 정류장별 이용량 탭 선택
                val tabElement = doWhileNotNullOrException {
                    driver.findElements(By.tagName("li"))
                        .asSequence()
                        .filter { (it.text ?: "").contains("정류장별 이용량") }
                        .firstOrNull()
                }
                tabElement.click()

                sleep(1000L)

                // start date
                setStartDate(driver, middleText)

                // end date
                setEndDate(driver, dateToText)

                // start date
                setStartDate(driver, dateFromText)

                // 인천광역시 선택 searchPopZoneSd
                Select(
                    doWhileNotNullOrException {
                        driver.findElement(By.id("searchPopSttnZoneSd"))
                    }
                ).selectByValue("28")

                sleep(1000L)

                // 구 선택 searchPopSttnZoneSgg
                Select(
                    doWhileNotNullOrException {
                        driver.findElement(By.id("searchPopSttnZoneSgg"))
                    }
                ).selectByValue(incheonMap[busStopStationInfo.adminName])

                sleep(1000L)

                // 값 넣기
                val searchBar =
                    doWhileNotNullOrException { driver.findElements(By.id("popupSearchSttnArsno")).firstOrNull() }
                searchBar.sendKeys(busStopStationInfo.shortId.toString())

                // 검색
                val searchButton = doWhileNotNullOrException {
                    driver.findElements(By.className("btn_sch_in")).filter { it.text.equals("검색") }[1]
                }
                searchButton.click()

                sleep(1000L)

                // 체크
                val popBox = doWhileNotNullOrException {
                    driver.findElements(By.className("pop_box")).firstOrNull()
                }
                val checkBox = doWhileNotNullOrException {
                    popBox.findElements(By.className("check")).firstOrNull()
                }
                checkBox.click()

                sleep(500L)

                // 선택
                val selectButton = doWhileNotNullOrException {
                    driver.findElements(By.tagName("button")).firstOrNull { it.text.contains("선택") }
                }
                selectButton.click()

                sleep(1000L)

                // 검색결과 조회
                val searchButtonTotal = doWhileNotNullOrException {
                    driver.findElements(By.tagName("button")).firstOrNull { it.text.contains("검색결과조회") }
                }
                searchButtonTotal.click()

                // alert check
                val wait = WebDriverWait(driver, Duration.ofSeconds(3))
                wait.until(ExpectedConditions.alertIsPresent())
                val alert: Alert = driver.switchTo().alert()
                alert.accept()

                // download, wait up to 5 min
                val downloadButton = doWhileNotNullOrException(60, 5000L) {
                    driver.findElements(By.id("btnExport")).firstOrNull { it.text.contains("다운로드") }
                }
                val js = driver as JavascriptExecutor
                try {
                    js.executeAsyncScript("rgrstyExcelExport('10')")
                } catch(ex: Exception) {
                    log.info("timeout")
//                    log.error(ex.message, ex)
                }
                sleep(1000L)
            }
        }

    }

    fun setStartDate(driver: WebDriver, text: String) {
        // start date
        val fromDay = doWhileNotNullOrException { driver.findElement(By.id("searchFromDay")) }
        fromDay.click()

        sleep(1000L)

        val datePickerFrom = doWhileNotNullOrException {
            driver.findElement(By.id("ui-datepicker-div"))
        }
        val dateButtonFrom = doWhileNotNullOrException {
            datePickerFrom.findElements(By.tagName("a")).firstOrNull { it.text.trim() == text }
        }
        dateButtonFrom.click()

        sleep(1000L)
    }

    fun setEndDate(driver: WebDriver, text: String) {
        val toDay = doWhileNotNullOrException { driver.findElement(By.id("searchToDay")) }
        toDay.click()

        sleep(1000L)

        val datePickerTo = doWhileNotNullOrException {
            driver.findElement(By.id("ui-datepicker-div"))
        }
        val dateButtonTo = doWhileNotNullOrException {
            datePickerTo.findElements(By.tagName("a")).firstOrNull { it.text.trim() == text }
        }
        dateButtonTo.click()

        sleep(1000L)
    }

    override fun saveAll(session: Session) {

    }
}