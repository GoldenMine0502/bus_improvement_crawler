package kr.goldenmine.bus_improvement_crawler.requests

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.goldenmine.bus_improvement_crawler.RetrofitServices
import kr.goldenmine.bus_improvement_crawler.requests.response.BusDetailResponse
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import java.io.File

class RequestBus(
    private val serviceKey: String,
    private val locationId: Int
) {

    fun crawlAll() {
        // 나무위키

//    val buses = listOf(
//        "1",
//        "65-1",
//        "300",
//    )

        val failed = mutableSetOf<String>()
        val gson = Gson()
        val busIds = HashMap<String, List<String>>()

        buses.forEach { busName ->
            try {
                val summary = RetrofitServices.BUS_CARD_SERVICE.requestBusSummary(serviceKey, locationId, busName).execute().body()
                println(summary)

                if (summary != null) {
                    val detail =
                        RetrofitServices.BUS_CARD_SERVICE.requestBusInDetail(serviceKey, locationId, summary.results[0].routeId)
                            .execute()
                            .body()

                    println("${detail?.count} ${detail?.results?.get(0)?.routeNameStartToFinish}")
//                println(detail?.results)


                    if (detail?.results != null) {
                        busIds[busName] = summary.results.map { it.routeId }

                        if (detail.results.isEmpty())
                            failed.add(busName)
                        detail.results.forEach {
                            val busStopInfo =
                                BusStopInfo()
                            busStopInfo.routeId = it.routeId
                            busStopInfo.routeNo = it.routeNo
                            busStopInfo.routeNameStartToFinish = it.routeNameStartToFinish
                            busStopInfo.busStopId = it.busStopId
                            busStopInfo.busStopName = it.busStopName
                        }

                        val file = File("data/bus$busName.json")
                        if (!file.exists()) file.createNewFile()
                        file.writeText(gson.toJson(detail))
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                println("exceptionInner $busName")
                failed.add(busName)
            }
            Thread.sleep(1000L)
        }


        failed.forEach {
            println("\"$it\",")
        }

        val busList = File("data/buslist.json")
        if (!busList.exists()) busList.createNewFile()

        busList.writer().use {
            gson.toJson(busIds, it)
        }
//    println(failed)
    }

    fun saveAll() {
        val gson = Gson()

        val registry = StandardServiceRegistryBuilder().configure(File("config/hibernate.cfg.xml")).build()
        val sessionFactory = MetadataSources(registry).buildMetadata().buildSessionFactory()
        val session = sessionFactory.openSession()

//    Runtime.getRuntime().addShutdownHook(Thread {
//        session.close()
//    })

        val type = object : TypeToken<BusDetailResponse>() {}.type

        val tx = session.beginTransaction()

        File("data").listFiles()?.forEach { file ->
            try {
                val busDetail = gson.fromJson<BusDetailResponse>(file.readText(), type)

                println(busDetail.count)
                busDetail.results.forEach {
                    val busStopInfo = BusStopInfo()
                    busStopInfo.routeId = it.routeId
                    busStopInfo.routeNo = it.routeNo
                    busStopInfo.routeNameStartToFinish = it.routeNameStartToFinish
                    busStopInfo.busStopId = it.busStopId
                    busStopInfo.busStopIndex = it.busStopIndex
                    busStopInfo.busStopName = it.busStopName

                    session.save(busStopInfo)
                }

            } catch(ex: Exception) {
                ex.printStackTrace()
                println(file.absolutePath)
            }
        }
        tx.commit()
        session.close()
    }
}