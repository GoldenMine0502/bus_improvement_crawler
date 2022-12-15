package kr.goldenmine.bus_improvement_crawler

import kr.goldenmine.bus_improvement_crawler.requests.bus_stop.database.BusStopStationInfo
import kr.goldenmine.bus_improvement_crawler.util.Point
import kr.goldenmine.bus_improvement_crawler.util.convertTM127toWGS84
import kr.goldenmine.bus_improvement_crawler.util.distance
import org.apache.commons.exec.util.MapUtils
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.junit.jupiter.api.Test
import java.io.File

class CalculationTest {
//    @Test
//    fun timeComplexity() {
////        val min = 166
//        val MIN = 1
//        val MAX = 1600
//        val registry = StandardServiceRegistryBuilder().configure(File("config/hibernate.cfg.xml")).build()
//        val sessionFactory = MetadataSources(registry).buildMetadata().buildSessionFactory()
//
//        val session = sessionFactory.openSession()
//        val query = session.createQuery("FROM BusStopStationInfo")
//
////        println(query.list())
//        val list = query.list() as List<BusStopStationInfo>
//        val result = mutableListOf<BusStopStationInfo>()
//
//        val cache = HashMap<BusStopStationInfo, Point?>()
//
//        var count = 0
//
//        list.forEach {first ->
//            if(!cache.containsKey(first))
//                cache[first] = convert(first)
//            val firstPos = cache[first]
//
//            val others = list.asSequence().filter { second ->
////                if(first == second) false
//
//                if(!cache.containsKey(second))
//                    cache[second] = convert(second)
//                val secondPos = cache[second]
//
//                if(firstPos != null && secondPos != null) {
//                    val distance = distance(firstPos.x, secondPos.x, firstPos.y, secondPos.y, 0.0, 0.0)
////                    if(distance in 1.0..1600.0)
////                        println(distance)
//                    distance in 1.0..1600.0
//                } else {
//                    false
//                }
//            }.toList()
//
//            result.addAll(others)
//
//            count++
//            if(count % 100 == 0) {
//                println(count)
//            }
//        }
//
//        println(result.size)
//    }
//
//    fun convert(busStopStationInfo: BusStopStationInfo): Point? {
//        val tm127X = busStopStationInfo.posX
//        val tm127Y = busStopStationInfo.posY
//
//        if(tm127X != null && tm127Y != null) {
//            return convertTM127toWGS84(Point(tm127X, tm127Y))
//        }
//        return null
//    }
}