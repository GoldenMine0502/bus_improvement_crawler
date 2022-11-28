package kr.goldenmine.bus_improvement_crawler.requests

import org.hibernate.Session
import org.openqa.selenium.WebDriver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.Thread.sleep
import java.util.*

interface ICrawlMultiSeleniumRequest<T>: ISeleniumRequest {
    companion object {
        val log: Logger = LoggerFactory.getLogger(ISeleniumRequest::class.java)
    }

    val threadSize: Int

    fun createNewClient(index: Int): WebDriver
    fun getAllData(session: Session): List<T>
    fun doCrawlOne(session: Session, driver: WebDriver, index: Int, obj: T)
    fun init(session: Session, index: Int)

    override fun crawlAll(session: Session) {
        repeat(threadSize) {
            init(session, it)
        }

        val drivers = mutableListOf<WebDriver>()
        repeat(threadSize) {
            drivers.add(createNewClient(it))
//            sleep(5000L)
            sleep(1000L)
        }

        val queue = LinkedList(getAllData(session))
        val failed = ArrayList<T>()
        val key = Object()
        val keyFailed = Object()

        while(queue.isNotEmpty()) {
            var index = 0
            val threads = drivers.map { driver ->
                val innerIndex = index++
                Thread {
                    while(true) {
                        var data: T? = null
                        synchronized(key) {
                            if(queue.isNotEmpty()) {
                                data = queue.poll()
                            }
                        }
                        if(data == null) break
                        try {
//                            log.info("Thread $index: ${data.toString()}")
                            doCrawlOne(session, driver, innerIndex, data!!)
                        } catch(ex: Exception) {
                            log.error(ex.message, ex)
                            synchronized(keyFailed) {
                                failed.add(data!!)
                            }
                        }
                    }
                }
            }

            threads.forEach {
                it.start()
                sleep(1000L)
            }
            threads.forEach {
                it.join()
            }
        }

        failed.forEach {
            log.info(it.toString())
        }

        log.info("failed ${failed.size}")

        drivers.forEach {
            it.quit()
        }
    }
}