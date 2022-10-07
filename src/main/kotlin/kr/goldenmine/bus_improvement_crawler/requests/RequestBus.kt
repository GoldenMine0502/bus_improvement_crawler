package kr.goldenmine.bus_improvement_crawler.requests

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
        val buses = listOf(
            // 간선버스
            "1", "2", "2-1", "3-2", "4",
            "5", "5-1", "6", "6-1", "7",
            "8", "9", "10", "11", "12",
            "13", "14", "14-1", "15", "16",
            "16-1", "17-1", "20", "21", "22",

            "23", "24", "24-1", "27", "28",
            "28-1", "29", "30", "33", "34",
            "35", "36", "37", "38", "42",
            "43", "43-1", "44", "45", "46",
            "47", "58", "62", "63", "65",

            "65-1", "66", "67-1", "70", "72",
            "75", "76", "77", "78", "79",
            "80", "81", "82", "87", "92",
            "93", "98", "103", "103-1", "111-2",
            "112", "202", "203", "204", "205",

            "206", "222", "223", "300", "700-1",

            // 좌석버스
            "60-5", "790", "800", "111", "117",
            "302", "303", "303-1", "304", "306",
            "307", "308", "310", "320", "330",

            // 지선버스
            //"e음11", "e음12", "e음13", "e음15", "e음16",
            //"e음17", "e음21", "e음22",
            "506",
            //"e음31",
            "510", "511", "512", "514-1", "515",
            "515-1", "516", "517", "518", "519",
            "520", "566", "순환41", "순환42", "순환43",

            "순환44",
            //"e음45",
            "521", "522", "523",
            "525", "순환51", "순환52",
            //"e음53", "순환54",
            //"e음55",
            "순환56", "532", "535", "536",
            "537", "538", "539", "540",
            //"e음61",
            "526", "530", "551", "555", "556",

            "557", "557A", "558", "560", "561",
            "562", "564", "564-1", "565", "567",
            "568", "569", "570", "571", "574",
            "579",
            //"e음71",
            "581", "582", "583",
            "584", "584-1", "585", "586", "587",

            "588", "590", "순환83",
            //"e음84", "e음85",
            //"e음86", "e음87", "e음88", "e음89",
            "591",
            "592", "593", "594", "595", "701",
            "702",

            // 마을버스
            //"무의1",
            "533", "534",
            //"공영버스",
        )

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