package kr.goldenmine.bus_improvement_crawler.requests.kakao_map.filter

class FilterPredicateAlphabet(
): IFilterPredicate {
    private val keywords = mutableListOf<String>()

    init {
        for (eng in 'a'..'z') {
            keywords.add(eng.toString())
        }

        for (eng in 'A'..'Z') {
            keywords.add(eng.toString())
        }
    }

    override val name: String
        get() = "alphabet"



    override fun filter(input: String): Boolean {
        for(keyword in keywords) {
            if(input.contains(keyword)) {
                return true
            }
        }

        return false
    }

    override fun doReplace(input: String): String {
        for(keyword in keywords) {
            if(input.contains(keyword)) {
                return input.replace(keyword, "")
            }
        }

        return input
    }
}