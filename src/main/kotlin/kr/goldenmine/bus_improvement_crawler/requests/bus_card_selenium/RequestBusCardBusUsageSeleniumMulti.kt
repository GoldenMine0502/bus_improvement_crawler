package kr.goldenmine.bus_improvement_crawler.requests.bus_card_selenium

import com.opencsv.CSVReader
import kr.goldenmine.bus_improvement_crawler.requests.ICrawlMultiSeleniumRequest
import kr.goldenmine.bus_improvement_crawler.requests.bus_card_selenium.database.BusTrafficNodeInfo
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
import java.time.Duration
import java.util.*
import kotlin.collections.ArrayList

class RequestBusCardBusUsageSeleniumMulti(
    override val threadSize: Int,
    val maxThreadSize: Int = threadSize,
    private val headless: Boolean = true,
    private val year: Int,
    private val month: Int,
    private val totalPage: Int = 39,
) : ICrawlMultiSeleniumRequest<RequestBusCardBusUsageSeleniumMulti.CrawlRange> {
    private val log: Logger = LoggerFactory.getLogger(RequestBusCardBusUsageSeleniumMulti::class.java)

    private val monthEndDateArray = intArrayOf(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

    // = 초기화, == 비교연산자
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

    override fun getAllData(session: Session): List<CrawlRange> {
        val range = 5
        val monthDates = monthEndDateArray[month - 1]

        val list = ArrayList<CrawlRange>()

        for(date in 1..monthDates) {
            for (index in 1..totalPage step range) {
                list.add(CrawlRange(date, index, index + range - 1))
                log.info(index.toString())
            }
//            if(totalPage % range != 0) {
//                val lastFirstPage = totalPage - totalPage % range + 1
//                list.add(CrawlRange(date, lastFirstPage, totalPage))
//            }
        }

        val filteredList = list.filter { crawlRange ->
            val fileName = crawlRange.fileName
            val count = (0 until maxThreadSize).sumOf { index -> getFileCount(index, fileName) }

            count == 0
        }

        filteredList.forEach {
            log.info(it.toString())
        }

        log.info("crawling ${filteredList.size} of ${list.size} cases.")

        return filteredList
    }

    override fun init(session: Session, index: Int) {
        val innerFolder = File(getFolder(), index.toString())

        innerFolder.listFiles()?.filter { it.name.contains("정류장 지표") }?.forEach {
            it.delete()
            log.info("deleted ${it.name}")
        }
    }

    override fun getFolder(): File = File("bus_card_usage")

    override fun doCrawlOne(session: Session, driver: WebDriver, index: Int, crawlRange: CrawlRange) {
        init(session, index)

        log.info("crawling $crawlRange")

        val js = driver as JavascriptExecutor

        driver.get("https://stcis.go.kr/pivotIndi/wpsPivotIndicator.do?siteGb=P&indiClss=IC03")

        Thread.sleep(1000L)

        // 정류장별 이용량 탭 선택
        val tabElement = doWhileNotNullOrException {
            driver.findElements(By.tagName("li"))
                .asSequence()
                .filter { (it.text ?: "").contains("재차인원") }
                .firstOrNull()
        }
        tabElement.click()

        Thread.sleep(1000L)

//            val tabMonthDateElement = doWhileNotNullOrException {
//                driver.findElements(By.className("searchDateGubun")).firstOrNull { it.text.contains("월") }
//            }
//            tabMonthDateElement.click()
//
//            Thread.sleep(1000L)

        // open datepicker tab
        val tabMonthDateInputElement = doWhileNotNullOrException {
            driver.findElements(By.id("searchFromDay")).firstOrNull()
        }
        tabMonthDateInputElement.click()

        Thread.sleep(2000L)

        // 9월 선택
        val monthSelectUIElement = doWhileNotNullOrException {
            driver.findElements(By.className("ui-datepicker-month")).firstOrNull()
        }
        Select(monthSelectUIElement).selectByValue((month - 1).toString())

        Thread.sleep(2000L)

        val dateButton = doWhileNotNullOrException {
            driver.findElements(By.tagName("td")).firstOrNull { it.text.equals(crawlRange.date.toString()) }
        }
        dateButton.click()

        Thread.sleep(1000L)


//        val confirmButton = doWhileNotNullOrException {
//            driver.findElements(By.className("ui-datepicker-close")).firstOrNull()
//        }
//        confirmButton.click()
//
//        Thread.sleep(1000L)

        // 인천광역시 선택 searchPopSttnZoneSd
        val areaTab = doWhileNotNullOrException {
            driver.findElements(By.className("searchRouteAreaGubun")).firstOrNull { it.text.contains("시도") }
        }
        areaTab.click()
        Select(
            doWhileNotNullOrException {
                driver.findElement(By.id("searchPopZoneSd"))
            }
        ).selectByValue("28")

        Thread.sleep(1000L)

        // 구 선택 searchPopSttnZoneSgg
//            Select(
//                doWhileNotNullOrException {
//                    driver.findElement(By.id("searchPopSttnZoneSgg"))
//                }
//            ).selectByValue(incheonMap[obj.adminName])

//            sleep(1000L)

        // 값 넣기
//                val searchBar =
//                    doWhileNotNullOrException { driver.findElements(By.id("popupSearchSttnArsno")).firstOrNull() }
//                searchBar.sendKeys(obj.routeId.toString())

        // 검색
        val searchButton1 = doWhileNotNullOrException {
            driver.findElements(By.className("btn_sch_in")).firstOrNull { it.text.equals("검색") }
        }
        searchButton1.click()

        Thread.sleep(1000L)

        // 체크
        val popBox1 = doWhileNotNullOrException(20) {
            driver.findElements(By.className("pop_box")).firstOrNull()
        }

//        Thread.sleep(1000L)
//        // page 1
//        val checkBox1 = doWhileNotNullOrException(10) {
//            popBox1.findElements(By.tagName("thead")).firstOrNull()?.findElements(By.className("check"))?.firstOrNull()
//        }
//        checkBox1.click()
//        Thread.sleep(1000L)
//
//        val selectButton1 = doWhileNotNullOrException {
//            driver.findElements(By.tagName("button")).firstOrNull { it.text.contains("선택") }
//        }
//        selectButton1.click()
//
//        Thread.sleep(1000L)

        // busLinePaging(21); return false;
        for (page in crawlRange.start..crawlRange.end) {
//            val searchButton = doWhileNotNullOrException {
//                driver.findElements(By.className("btn_sch_in")).firstOrNull { it.text.equals("검색") }
//            }
//            searchButton.click()
//
//            Thread.sleep(1000L)

            try {
                js.executeScript("busLinePaging($page)")
                Thread.sleep(20000L)

                val checkBoxInner = doWhileNotNullOrException(10) {
                    driver
                        .findElements(By.className("pop_box")).firstOrNull()
                        ?.findElements(By.tagName("thead"))?.firstOrNull()
                        ?.findElements(By.className("check"))?.firstOrNull()
                }
                checkBoxInner.click()
                Thread.sleep(500L)

                val selectButton = doWhileNotNullOrException {
                    driver.findElements(By.tagName("button")).firstOrNull { it.text.contains("선택") }
                }
                selectButton.click()
                Thread.sleep(1000L)

            } catch (ex: Exception) {
                log.info("error while busLinePaging")
                ex.printStackTrace()
                Thread.sleep(10000L)
            }

            try {
                val wait = WebDriverWait(driver, Duration.ofSeconds(5))
                wait.until(ExpectedConditions.alertIsPresent())
                val alert: Alert = driver.switchTo().alert()
                alert.accept()
                log.info("removed the alert")
                break
            } catch(ex: Exception) {
                log.info("no alert")
            }
        }

        // 검색결과 조회
        val searchButtonTotal = doWhileNotNullOrException {
            driver.findElements(By.tagName("button")).firstOrNull { it.text.contains("검색결과조회") }
        }
        searchButtonTotal.click()

        // alert check
//        val wait = WebDriverWait(driver, Duration.ofSeconds(3))
//        wait.until(ExpectedConditions.alertIsPresent())
//        val alert: Alert = driver.switchTo().alert()
//        alert.accept()

        // download, wait up to 5 min
        val downloadButton = doWhileNotNullOrException(60, 5000L) {
            driver.findElements(By.id("btnExport")).firstOrNull { it.text.contains("다운로드") }
        }
        try {
            js.executeAsyncScript(downloadButton.getAttribute("onclick"))
            val wait = WebDriverWait(driver, Duration.ofSeconds(30))
            wait.until(ExpectedConditions.alertIsPresent())
            val alert: Alert = driver.switchTo().alert()
            alert.accept()
        } catch (ex: Exception) {
            log.info("timeout")
//                    log.error(ex.message, ex)
        }
        Thread.sleep(50000L)

        val innerFolder = File(getFolder(), index.toString())

        // rename file
        val file = innerFolder.listFiles()?.firstOrNull {
            it.name.contains("정류장")
        }
        val fileName = crawlRange.fileName
        val count = getFileCount(index, fileName)
        val renameFile = File(innerFolder, "$fileName (${count + 1}).csv")
        file?.renameTo(renameFile)
        file?.delete()

        Thread.sleep(1000L)
    }

    override fun saveAll(session: Session) {
        val folder = getFolder()
        val tx = session.beginTransaction()

        var id = 1
        var errorCount = 0

        for(i in 0 until maxThreadSize) {
            val files = File(folder, i.toString())

            files.listFiles()?.forEach { file ->
                val fileInputStream = FileInputStream(file)
                val inputStreamReader = InputStreamReader(fileInputStream, "MS949")
                val bufferedReader = BufferedReader(inputStreamReader)

                val reader = CSVReader(bufferedReader)
                reader.use {
//                    log.info("${file.name} ${folder.name}")
                    reader.skip(1)
                    reader.readAll().forEach { line ->
                        try {
//                            log.info("$i ${file.name} ${folder.name} ${Arrays.toString(line)}")

                            val busTrafficInfo = makeBusTrafficInfo(id, line)
                            id++
                            session.save(busTrafficInfo)
                        } catch(ex: Exception) {
                            errorCount++
                            ex.printStackTrace()
                            file.delete()
                            log.info("an error occured while saving a file. deleting $i ${file.name} ${folder.name} ${Arrays.toString(line)}. try saving again.")
                        }
                    }
                }
            }
        }

        if(errorCount == 0)
            tx.commit()
    }

    // 노선,                      기종점,정류장순번,    정류장명,04시,05시,06시,07시,08시,09시,10시,11시,12시,13시,14시,15시,16시,17시,18시,19시,20시,21시,22시,23시,00시,01시,02시,03시,
    //  0,                          1, 2,              3,  4,5,6,7, 8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27
    // 58,�۵���2������ - �۵���2������,21,�۵�ǳ�����̿�2����,  0,3,3,8,11,4, 4,14, 7, 7, 9,11,15,18,20,14,15,15,22,10, 0, 0, 0, 0,
    private fun makeBusTrafficInfo(id: Int, line: Array<String>): BusTrafficNodeInfo {
        return BusTrafficNodeInfo(
            id = id,
            routeNo = line[0],
            sequence = line[2].toInt(),

            time04 = line[4].toInt(),
            time05 = line[5].toInt(),
            time06 = line[6].toInt(),
            time07 = line[7].toInt(),
            time08 = line[8].toInt(),
            time09 = line[9].toInt(),

            time10 = line[10].toInt(),
            time11 = line[11].toInt(),
            time12 = line[12].toInt(),
            time13 = line[13].toInt(),
            time14 = line[14].toInt(),
            time15 = line[15].toInt(),

            time16 = line[16].toInt(),
            time17 = line[17].toInt(),
            time18 = line[18].toInt(),
            time19 = line[19].toInt(),
            time20 = line[20].toInt(),
            time21 = line[21].toInt(),

            time22 = line[22].toInt(),
            time23 = line[23].toInt(),
            time00 = line[24].toInt(),
            time01 = line[25].toInt(),
            time02 = line[26].toInt(),
            time03 = line[27].toInt(),
        )
    }


    fun getFileCount(index: Int, fileName: String): Int {
        val innerFolder = File(getFolder(), index.toString())

        val count = (innerFolder.listFiles()?.count { it.name.contains(fileName) } ?: 0)

        return count
    }

    inner class CrawlRange(
        val date: Int,
        val start: Int,
        val end: Int,
    ) {
        val fileName = "2022-$month-$date-$start-$end"

        override fun toString(): String {
            return "{date=$date, start=$start, end=$end}"
        }
    }
}