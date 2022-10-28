package kr.goldenmine.bus_improvement_crawler.requests.kakao_map.filter

class FilterPredicateNoFilter(

): IFilterPredicate {
    override val name: String
        get() = "noFilter"

    override fun filter(input: String): Boolean {
        return true
    }

    override fun doReplace(input: String): String {
        return input
    }
}