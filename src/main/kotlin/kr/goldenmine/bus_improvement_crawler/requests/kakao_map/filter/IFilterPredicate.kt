package kr.goldenmine.bus_improvement_crawler.requests.kakao_map.filter

interface IFilterPredicate {
    val name: String
    fun filter(input: String): Boolean
    fun doReplace(input: String): String
}