package kr.goldenmine.bus_improvement_crawler.requests.kakao_map.filter

class FilterPredicateStart(
    private val keywords: List<String>
): IFilterPredicate {
    override val name: String
        get() = "start"

    override fun filter(input: String): Boolean {
        for(keyword in keywords) {
            if(input.startsWith(keyword)) {
                return true
            }
        }

        return false
    }

    override fun doReplace(input: String): String {
        for(keyword in keywords) {
            if(input.startsWith(keyword)) {
                return input.substring(keyword.length, input.length)
            }
        }

        return input
    }
}