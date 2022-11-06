package kr.goldenmine.bus_improvement_crawler.requests.bus_card_selenium

import com.opencsv.CSVReader
import kr.goldenmine.bus_improvement_crawler.requests.ICrawlMultiSeleniumRequest
import kr.goldenmine.bus_improvement_crawler.requests.bus_card_selenium.database.BusTrafficInfo
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
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.Thread.sleep
import java.time.Duration
import java.util.*


class RequestBusCardSeleniumMulti(
    override val threadSize: Int,
    val maxThreadSize: Int = threadSize,
    private val headless: Boolean = true
) :
    ICrawlMultiSeleniumRequest<BusStopStationInfo> {
    private val log: Logger = LoggerFactory.getLogger(RequestBusCardSeleniumMulti::class.java)

    private val incheonList = setOf(
        "강화군", "계양구", "남동구", "동구", "미추홀구",
        "부평구", "서구", "연수구", "옹진군", "중구"
    )

    private val incheonMap = mapOf(
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

    private val monthValue = "8" // 9월
//    private val dateFromText = "19"
//    private val middleText = "19"
//    private val dateToText = "21"

    override fun createNewClient(index: Int): WebDriver {
        val innerFolder = File(getFolder(), index.toString())
        innerFolder.mkdirs()

        val prefs: MutableMap<String, Any> = HashMap()
        prefs["download.default_directory"] = innerFolder.absolutePath

        val options = ChromeOptions()
        options.setExperimentalOption("prefs", prefs)

        if (headless)
            options.addArguments("--headless")

        return ChromeDriver(options)
    }

    override fun getAllData(session: Session): List<BusStopStationInfo> {
        val list = (session.createQuery("FROM BusStopStationInfo").list() as List<BusStopStationInfo?>)

        val exclude = mutableSetOf<Int>()

        repeat(maxThreadSize) { index ->
            val innerFolder = File(getFolder(), index.toString())

            innerFolder.listFiles()?.filterNotNull()?.forEach { file ->
                val shortId = file.name.substring(0, file.name.lastIndexOf("."))

                exclude.add(shortId.toInt())
            }
        }

        val returnList = list
            .asSequence()
            .filterNotNull()
            .filter { !exclude.contains(it.shortId) }
            .filter { incheonList.contains(it.adminName) }
            .toList()

        log.info("total files: ${list.size}")
        log.info("downloaded files: ${exclude.size}")
        log.info("files to download: ${returnList.size}")

        return returnList
    }

    override fun init(session: Session) {
        repeat(maxThreadSize) { index ->
            val innerFolder = File(getFolder(), index.toString())

            innerFolder.listFiles()?.filterNotNull()?.forEach { src ->
                try {
                    // 일단 short id를 읽을 수 있는 지 확인
                    val reader = CSVReader(src.bufferedReader())
                    val data: String
                    val dest: File
                    reader.use {
                        reader.skip(1)
                        data = reader.readNext()[1]
                        dest = File(getFolder(), "$index/${data}.csv")
                    }

                    if (!src.name.startsWith(data)) {
                        val success = src.renameTo(dest)
                        if (success) {
                            println("Renaming ${dest.name} succeed")
                        }
                    }

                    // 데이터 전체 보존 되어있는 지 확인
                    val reader2 = CSVReader(src.bufferedReader())
                    reader2.use {
                        reader2.skip(1)
                        reader2.readAll().forEach { line ->
                            val busTrafficInfo = makeBusTrafficInfo(0, line)
                        }
                    }
                } catch (ex: Exception) {
                    log.error(if(src.exists()) src.readText() else "null")
                    log.error(ex.message, ex)
                    log.error(src.path)
                    src.delete()
                }

            }
        }
    }

    override fun getFolder(): File = File("bus_card_selenium_multi")

    override fun doCrawlOne(session: Session, driver: WebDriver, obj: BusStopStationInfo) {
        if (obj.shortId != null) {
            log.info(obj.shortId.toString())
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

            val tabMonthDateElement = doWhileNotNullOrException {
                driver.findElements(By.className("searchDateGubun")).firstOrNull { it.text.contains("월") }
            }
            tabMonthDateElement.click()

            sleep(1000L)

            val tabMonthDateInputElement = doWhileNotNullOrException {
                driver.findElements(By.id("searchFromMonth")).firstOrNull()
            }
            tabMonthDateInputElement.click()

            val monthSelectUIElement = doWhileNotNullOrException {
                driver.findElements(By.className("ui-datepicker-month")).firstOrNull()
            }

            // 9월 선택
            Select(monthSelectUIElement).selectByValue(monthValue)

            sleep(1000L)

            val confirmButton = doWhileNotNullOrException {
                driver.findElements(By.className("ui-datepicker-close")).firstOrNull()
            }

            confirmButton.click()

            sleep(1000L)
            // start date
//            setStartDate(driver, middleText)

            // end date
//            setEndDate(driver, dateToText)

            // start date
//            setStartDate(driver, dateFromText)

            val areaTab = doWhileNotNullOrException {
                driver.findElements(By.className("searchSttnAreaGubun")).firstOrNull { it.text.contains("시도") }
            }
            areaTab.click()

            // 인천광역시 선택 searchPopSttnZoneSd
            Select(
                doWhileNotNullOrException {
                    driver.findElement(By.id("searchPopSttnZoneSd"))
                }
            ).selectByValue("28")

            sleep(1000L)

            // 구 선택 searchPopSttnZoneSgg
//            Select(
//                doWhileNotNullOrException {
//                    driver.findElement(By.id("searchPopSttnZoneSgg"))
//                }
//            ).selectByValue(incheonMap[obj.adminName])

//            sleep(1000L)

            // 값 넣기
            val searchBar =
                doWhileNotNullOrException { driver.findElements(By.id("popupSearchSttnArsno")).firstOrNull() }
            searchBar.sendKeys(obj.shortId.toString())

            // 검색
            val searchButton = doWhileNotNullOrException {
                driver.findElements(By.className("btn_sch_in")).firstOrNull { it.text.equals("검색") }
            }
            searchButton.click()

            sleep(1000L)

            // 체크
            val popBox = doWhileNotNullOrException(10) {
                driver.findElements(By.className("pop_box")).firstOrNull()
            }
            val checkBox = doWhileNotNullOrException(10) {
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
            } catch (ex: Exception) {
                log.info("timeout")
//                    log.error(ex.message, ex)
            }
            sleep(1000L)
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

    fun makeBusTrafficInfo(id: Int, line: Array<String>): BusTrafficInfo {
        val busTrafficInfo = BusTrafficInfo(
            id = id,
            shortId = line[1].toInt(),
            date = line[4],
            userType = line[5],
            routeNo = line[2],

            time04On = line[6].toInt(),
            time04Off = line[7].toInt(),

            time05On = line[8].toInt(),
            time05Off = line[9].toInt(),
            time06On = line[10].toInt(),
            time06Off = line[11].toInt(),
            time07On = line[12].toInt(),
            time07Off = line[13].toInt(),
            time08On = line[14].toInt(),
            time08Off = line[15].toInt(),

            time09On = line[16].toInt(),
            time09Off = line[17].toInt(),
            time10On = line[18].toInt(),
            time10Off = line[19].toInt(),
            time11On = line[20].toInt(),
            time11Off = line[21].toInt(),
            time12On = line[22].toInt(),
            time12Off = line[23].toInt(),

            time13On = line[24].toInt(),
            time13Off = line[25].toInt(),
            time14On = line[26].toInt(),
            time14Off = line[27].toInt(),
            time15On = line[28].toInt(),
            time15Off = line[29].toInt(),
            time16On = line[30].toInt(),
            time16Off = line[31].toInt(),

            time17On = line[32].toInt(),
            time17Off = line[33].toInt(),
            time18On = line[34].toInt(),
            time18Off = line[35].toInt(),
            time19On = line[36].toInt(),
            time19Off = line[37].toInt(),
            time20On = line[38].toInt(),
            time20Off = line[39].toInt(),

            time21On = line[40].toInt(),
            time21Off = line[41].toInt(),
            time22On = line[42].toInt(),
            time22Off = line[43].toInt(),
            time23On = line[44].toInt(),
            time23Off = line[45].toInt(),
            time00On = line[46].toInt(),
            time00Off = line[47].toInt(),

            time01On = line[48].toInt(),
            time01Off = line[49].toInt(),
            time02On = line[50].toInt(),
            time02Off = line[51].toInt(),
            time03On = line[52].toInt(),
            time03Off = line[53].toInt(),
        )

        return busTrafficInfo
    }

    override fun saveAll(session: Session) {
        init(session)
        val tx = session.beginTransaction()

        var id = 0
        getFolder().listFiles()?.forEach { folder ->
            folder.listFiles()?.forEach { file ->
                try {
                    val fileInputStream = FileInputStream(file)
                    val inputStreamReader = InputStreamReader(fileInputStream, "MS949")
                    val bufferedReader = BufferedReader(inputStreamReader)

                    val reader = CSVReader(bufferedReader)
                    reader.use {
                        log.info("${file.name} ${folder.name}")
                        reader.skip(1)
                        reader.readAll().forEach { line ->
//                            log.info(Arrays.toString(line))

                            val busTrafficInfo = makeBusTrafficInfo(id++, line)

                            session.save(busTrafficInfo)
                        }
                    }
                } catch(ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }

        tx.commit()
    }
}