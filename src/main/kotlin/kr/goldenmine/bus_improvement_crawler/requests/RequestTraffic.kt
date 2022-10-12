package kr.goldenmine.bus_improvement_crawler.requests

import kr.goldenmine.bus_improvement_crawler.RetrofitServices
import kr.goldenmine.bus_improvement_crawler.requests.response.TrafficResponseSpec
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import java.io.File
import kotlin.math.ceil

class RequestTraffic(
    val serviceKey: String,
    val perPage: Int = 1000
) {
    fun requestTraffic() {
        val folder = File("traffic")
        folder.mkdirs()

        val firstRequest = RetrofitServices.TRAFFIC_SERVICE.getTraffic(serviceKey, 1, 10).execute().body()

        if(firstRequest != null) {
            val pages = ceil(firstRequest.matchCount.toDouble() / perPage).toInt()
            val list = mutableListOf<TrafficResponseSpec>()

            repeat(pages) {
                val request = RetrofitServices.TRAFFIC_SERVICE.getTraffic(serviceKey, it + 1, perPage).execute().body()
                if(request != null) {
                    list.addAll(request.data)
                }
                println("page: ${it + 1}, size: ${list.size}")
                Thread.sleep(1000L)
            }

            val registry = StandardServiceRegistryBuilder().configure(File("config/hibernate.cfg.xml")).build()
            val sessionFactory = MetadataSources(registry).buildMetadata().buildSessionFactory()
            val session = sessionFactory.openSession()

            val tx = session.beginTransaction()

            var id = 1
            for(response in list) {
                val trafficInfo = toTrafficInfo(response)
                trafficInfo.id = id++

                session.save(trafficInfo)
            }

            tx.commit()
            session.close()
//            val totalResponse = TrafficResponse(perPage, list, firstRequest.matchCount, firstRequest.page, firstRequest.perPage, firstRequest.totalCount)
//
//            val file = File("traffic/result${System.currentTimeMillis()}.json")
//            file.createNewFile()
//            file.writer().use {
//                val gson = Gson()
//                gson.toJson(totalResponse, it)
//            }
        }
    }

    fun toTrafficInfo(response: TrafficResponseSpec): TrafficInfo {
        val trafficInfo = TrafficInfo()
        trafficInfo.date = response.date
        trafficInfo.direction = response.direction
        trafficInfo.distance = response.distance
        trafficInfo.linkId = response.linkId
        trafficInfo.startName = response.startName
        trafficInfo.finishName = response.finishName
        trafficInfo.roadName = response.roadName
        trafficInfo.time00 = response.time00
        trafficInfo.time01 = response.time01
        trafficInfo.time02 = response.time02
        trafficInfo.time03 = response.time03
        trafficInfo.time04 = response.time04
        trafficInfo.time05 = response.time05
        trafficInfo.time06 = response.time06
        trafficInfo.time07 = response.time07
        trafficInfo.time08 = response.time08
        trafficInfo.time09 = response.time09
        trafficInfo.time10 = response.time10
        trafficInfo.time11 = response.time11
        trafficInfo.time12 = response.time12
        trafficInfo.time13 = response.time13
        trafficInfo.time14 = response.time14
        trafficInfo.time15 = response.time15
        trafficInfo.time16 = response.time16
        trafficInfo.time17 = response.time17
        trafficInfo.time18 = response.time18
        trafficInfo.time19 = response.time19
        trafficInfo.time20 = response.time20
        trafficInfo.time21 = response.time21
        trafficInfo.time22 = response.time22
        trafficInfo.time23 = response.time23

        return trafficInfo
    }
}