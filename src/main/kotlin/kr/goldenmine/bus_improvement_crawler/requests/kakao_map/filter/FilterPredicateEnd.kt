package kr.goldenmine.bus_improvement_crawler.requests.kakao_map.filter

class FilterPredicateEnd(
    private val keywords: List<String>
): IFilterPredicate {
    override val name: String
        get() = "end"

    override fun filter(input: String): Boolean {
        for(keyword in keywords) {
            if(input.endsWith(keyword)) {
                return true
            }
        }

        return false
    }

    override fun doReplace(input: String): String {
        for(keyword in keywords) {
            if(input.endsWith(keyword)) {
                return input.substring(0, input.length - keyword.length)
            }
        }

        return input
    }
}