package kr.goldenmine.bus_improvement_crawler

data class CrawlInfo(
    val requestBusCardKey: String,
    val requestBusStopKey: String,
    val requestBusTrafficKey: String,
    val requestNaverKeyId: String,
    val requestNaverKey: String,
    val requestKakaoKey: String,
    val year: Int,
    val month: Int,
    val totalPage: Int,
)