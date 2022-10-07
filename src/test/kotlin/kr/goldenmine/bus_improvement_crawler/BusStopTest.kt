package kr.goldenmine.bus_improvement_crawler

import kr.goldenmine.bus_improvement_crawler.requests.RequestBus
import java.io.File

fun main() {
    val defaultApiKey = File("config/busapi.txt").readText()
    val defaultLocationId = 28

    println(defaultApiKey)
    println(defaultLocationId)

    val requestBus = RequestBus(defaultApiKey, defaultLocationId)
    requestBus.crawlAll()
    requestBus.saveAll()
}