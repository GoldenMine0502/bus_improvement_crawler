package kr.goldenmine.bus_improvement_crawler

import kr.goldenmine.bus_improvement_crawler.requests.RequestBus

fun main() {
    val requestBus = RequestBus()
    requestBus.crawlAll()
    requestBus.saveAll()
}