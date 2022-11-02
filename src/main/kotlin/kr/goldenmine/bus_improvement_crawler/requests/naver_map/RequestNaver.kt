package kr.goldenmine.bus_improvement_crawler.requests.naver_map

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.goldenmine.bus_improvement_crawler.requests.ICrawlRetrofitRequest
import kr.goldenmine.bus_improvement_crawler.requests.bus_stop.database.BusStopInfo
import kr.goldenmine.bus_improvement_crawler.requests.bus_stop.database.BusStopStationInfo
import kr.goldenmine.bus_improvement_crawler.requests.naver_map.database.BusPathInfo
import kr.goldenmine.bus_improvement_crawler.requests.naver_map.request.PathResponse
import kr.goldenmine.bus_improvement_crawler.util.Point
import kr.goldenmine.bus_improvement_crawler.util.convertTM127toWGS84
import org.hibernate.Session
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.lang.Thread.sleep

class RequestNaver(
    private val serviceKeyId: String,
    private val serviceKey: String,
) : ICrawlRetrofitRequest<NaverService> {
    private val gson = Gson()
    private val log: Logger = LoggerFactory.getLogger(RequestNaver::class.java)

    override val service: NaverService
        get(): NaverService = Retrofit.Builder()
            .baseUrl("https://naveropenapi.apigw.ntruss.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(NaverService::class.java)

    // 파일 포맷
    // 노선번호-sequence.json

    override fun getFolder() = File("naver")

    private fun executeBusQuery(session: Session): List<BusStopInfo> {
        val busInfoList = (session.createQuery("FROM BusStopInfo").list() as List<BusStopInfo?>).filterNotNull()

        return busInfoList
    }

    private fun executeRouteQuery(session: Session, routeId: String): List<BusStopStationInfo> {
        val routes = (session.createQuery(
            "FROM BusStopStationInfo busStopStationInfo WHERE busStopStationInfo.id IN (" +
                    "SELECT busStopStationId FROM BusThroughInfo busThroughInfo WHERE busThroughInfo.routeId = '$routeId' ORDER BY busThroughInfo.busStopSequence ASC" +
                    ")"
        ).list() as List<BusStopStationInfo?>).filterNotNull()

        return routes
    }

    override fun crawlAll(session: Session) {
        getFolder().mkdirs()

        val busInfoList = executeBusQuery(session)

        log.info("bus size: ${busInfoList.size}")
//        log.info(busInfoList.toString())

        val totalSize = busInfoList
            .asSequence()
            .map { it.routeId }
            .filterNotNull()
            .map { executeRouteQuery(session, it) }
            .map { it.size }
            .reduce { acc, i -> acc + i }

        log.info("station path size: $totalSize")

        var totalIndex = 0
        busInfoList
            .asSequence()
            .forEach { busInfo ->
                val routeNo = busInfo.routeNo
                val routeId = busInfo.routeId
                if (routeNo != null && routeId != null) {
                    val routes = executeRouteQuery(session, routeId)

                    // 빈파일이면 안됨. 50byte는 넘겠지.
                    val fileSize = getFolder().listFiles()?.count {
                        it.name.startsWith("$routeNo-") && it.length() > 50 && !it.name.substring(routeNo.length + 1).contains("-")
                    } ?: 0

                    log.info("routes of $totalIndex $routeNo $routeId: ${routes.size} $fileSize")
                    totalIndex++


                    if (fileSize < routes.size - 1) {
                        for (index in 0 until routes.size - 1) {
                            val tm127Start = routes[index]
                            val tm127Finish = routes[index + 1]

                            if (tm127Start.posX != null && tm127Start.posY != null && tm127Finish.posX != null && tm127Finish.posY != null) {
                                val start = convertTM127toWGS84(Point(tm127Start.posX, tm127Start.posY))
                                val finish = convertTM127toWGS84(Point(tm127Finish.posX, tm127Finish.posY))

                                val queryStart = "${start.x},${start.y}"
                                val queryFinish = "${finish.x},${finish.y}"

                                val response =
                                    service.getPath(serviceKeyId, serviceKey, queryStart, queryFinish).execute().body()

                                if (response != null) {
                                    val file = File(getFolder(), "$routeNo-$index.json")
                                    if (!file.exists()) file.createNewFile()
                                    file.bufferedWriter().use {
                                        gson.toJson(response, it)
                                    }

                                    log.info("$routeNo-$index.json written")
//                                val path = response.route?.traoptimal?.get(0)?.path?.asSequence()
//                                    ?.map { Point(it[0], it[1]) }
//                                    ?.toList() ?: listOf()

//                                for(pathIndex in path.indices) {
//                                    val busPathInfo = BusPathInfo(0, )
//                                }
                                }
                            }

                            sleep(500L)
                        }
                    }
                }

                sleep(500L)
            }
    }

    override fun saveAll(session: Session) {
        val type = object : TypeToken<PathResponse>() {}.type

        val tx = session.beginTransaction()
        val busList = executeBusQuery(session)
        var totalId = 0
        busList.forEach {busStopInfo ->
            val routeId = busStopInfo.routeId
            val routeNo = busStopInfo.routeNo

            if(routeNo != null && routeId != null) {
                val busStopInfoList = executeRouteQuery(session, routeId)

                for(index in 0 until busStopInfoList.size - 1) {
                    val from = busStopInfoList[index]
                    val to = busStopInfoList[index + 1]

                    val file = File(getFolder(), "$routeNo-$index.json")
                    val response = gson.fromJson<PathResponse>(file.readText(), type)

                    if(from.id != null && to.id != null) {
                        response?.route?.traoptimal?.get(0)?.path?.forEach {
                            val pathInfo = BusPathInfo(totalId++, from.id, to.id, index, it[0], it[1])

                            session.save(pathInfo)
                        }
                    }
                }
            }
            log.info("$routeNo written")
        }

        tx.commit()
    }

    fun apiTest(start: String, goal: String): PathResponse? {
        return service.getPath(serviceKeyId, serviceKey, start, goal).execute().body()
    }
}