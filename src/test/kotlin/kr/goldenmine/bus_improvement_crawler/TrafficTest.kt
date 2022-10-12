package kr.goldenmine.bus_improvement_crawler

import kr.goldenmine.bus_improvement_crawler.requests.RequestTraffic
import java.io.File

fun main() {
    val defaultApiKey = File("config/trafficapi.txt").readText()
    println(defaultApiKey)

    val requestTraffic = RequestTraffic(defaultApiKey)

    requestTraffic.requestTraffic()
}