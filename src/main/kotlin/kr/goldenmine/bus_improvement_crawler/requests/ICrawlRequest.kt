package kr.goldenmine.bus_improvement_crawler.requests

import com.google.gson.Gson
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

private val log = LoggerFactory.getLogger(ICrawlRequest::class.java)

interface ICrawlRequest {
    fun getFolder(): File

    fun crawlAll(session: Session)
    fun saveAll(session: Session)

    fun progress(sessionFactory: SessionFactory) {
        val session = sessionFactory.openSession()

        getFolder().mkdirs()
        crawlAll(session)

        try {
            saveAll(session)
        } catch (ex: Exception) {
            log.error(ex.message, ex)
        } finally {
            if (session.isOpen)
                session.close()
        }
    }
}