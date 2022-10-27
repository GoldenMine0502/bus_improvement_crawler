package kr.goldenmine.bus_improvement_crawler.requests.bus_traffic

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.goldenmine.bus_improvement_crawler.requests.ICrawlRetrofitRequest
import kr.goldenmine.bus_improvement_crawler.requests.bus_traffic.database.TrafficInfo
import kr.goldenmine.bus_improvement_crawler.requests.bus_traffic.request.TrafficResponseSpec
import org.hibernate.Session
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import kotlin.math.ceil

class RequestTraffic(
    private val serviceKey: String,
    private val perPage: Int = 1000
) : ICrawlRetrofitRequest<TrafficService> {
    private val log: Logger = LoggerFactory.getLogger(RequestTraffic::class.java)
    private val gson = Gson()

    override val service: TrafficService = Retrofit.Builder()
        .baseUrl("https://api.odcloud.kr/")
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
        .create(TrafficService::class.java)

    override fun getFolder(): File = File("bus_traffic")

    override fun saveAll(session: Session) {
        val type = object : TypeToken<List<TrafficResponseSpec>>() {}.type
        val reader = File(getFolder(), "data.json").bufferedReader()
        val list = gson.fromJson<List<TrafficResponseSpec>>(reader, type)
        reader.close()

        val tx = session.beginTransaction()

        var id = 1
        for (response in list) {
            val trafficInfo = toTrafficInfo(response)
            trafficInfo.id = id++

            session.save(trafficInfo)
        }

        tx.commit()
    }

    override fun crawlAll(session: Session) {
        val firstRequest = service.getTraffic(serviceKey, 1, 10).execute().body()

        if (firstRequest != null) {
            val pages = ceil(firstRequest.matchCount.toDouble() / perPage).toInt()
            val list = mutableListOf<TrafficResponseSpec>()

            repeat(pages) {
                val response = service.getTraffic(serviceKey, it + 1, perPage).execute().body()
                if (response != null) {
                    list.addAll(response.data)
                }
                log.info("page: ${it + 1}, size: ${list.size}")
                Thread.sleep(1000L)
            }

            File(getFolder(), "data.json").bufferedWriter().use {
                gson.toJson(list, it)
            }
        } else {
            log.error("error while getting first request.")
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