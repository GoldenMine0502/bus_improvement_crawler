package kr.goldenmine.bus_improvement_crawler.requests.kakao_map.filter

class FilterPredicateCustomNames: IFilterPredicate {
    val map = mapOf(
        Pair("원인제역삼거리", "원인재역삼거리"),
        Pair("남동로진입", "남동대로"),
        Pair("범말사거리", "벗말사거리"),
        Pair("신동아아케이트", "신동아쇼핑타운"),
        Pair("코오롱유화", "코오롱골프라운지 복합상설점"),
        Pair("가좌1동동부제강서비스센터", "동부제강"),
        Pair("한들3거리", "백석교차로"),



        Pair("APT", "아파트"),
        Pair("고가교", "고가도로"),
        Pair("3거리", "삼거리"),
        Pair("4거리", "사거리"),
    )

    override val name: String
        get() = "customNames"

    override fun filter(input: String): Boolean {
        for(key in map.keys) {
            if(input.contains(key)) return true
        }
        return false
    }

    override fun doReplace(input: String): String {
        for(key in map.keys) {
            if(input.contains(key)) return input.replace(key, map[key]!!)
        }
        return input
    }
}