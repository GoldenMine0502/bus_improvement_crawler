package kr.goldenmine.bus_improvement_crawler.requests

import com.google.gson.Gson
import org.hibernate.Session
import java.io.File

interface ICrawlRequest<T> {
    fun getFolder(): File
    fun getRetrofitService(): T

    fun crawlAll()
    fun saveAll(session: Session)
}