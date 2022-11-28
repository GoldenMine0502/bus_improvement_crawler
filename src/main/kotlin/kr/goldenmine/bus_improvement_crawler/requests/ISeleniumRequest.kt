package kr.goldenmine.bus_improvement_crawler.requests

import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface ISeleniumRequest: ICrawlRequest {
    companion object {
        val log: Logger = LoggerFactory.getLogger(ISeleniumRequest::class.java)
    }

    fun <T> doWhileNotNullOrException(retry: Int = 10, sleep: Long = 1000L, lambda: () -> T?): T {
        repeat(retry) {
            try {
                val result = lambda.invoke()

                if(result != null) return result
            } catch(ex: Exception) {
                log.error(ex.message, ex)
            }
            Thread.sleep(sleep)
        }

        throw NullPointerException("null")
    }
}