package kr.goldenmine.bus_improvement_crawler.requests.bus_card

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.goldenmine.bus_improvement_crawler.requests.ICrawlRequest
import kr.goldenmine.bus_improvement_crawler.util.buses
import org.hibernate.Session
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.lang.Thread.sleep

class RequestBus(
    private val serviceKey: String,
    private val locationId: Int
): ICrawlRequest<BusCardService> {
    private val log: Logger = LoggerFactory.getLogger(RequestBus::class.java)

    override fun getFolder() = File("bus_card")

    override fun getRetrofitService(): BusCardService = Retrofit.Builder()
        .baseUrl("https://stcis.go.kr/")
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
        .create(BusCardService::class.java)

    override fun crawlAll() {
        val request = getRetrofitService()

        val failed = mutableSetOf<String>()
        val gson = Gson()
        val busIds = HashMap<String, List<String>>()

        buses.forEach { busName ->
            try {
                val summary = request.requestBusSummary(serviceKey, locationId, busName).execute().body()
                log.info(summary.toString())

                if (summary != null) {
                    sleep(1000L)
                    val detail = request.requestBusInDetail(serviceKey, locationId, summary.results[0].routeId).execute().body()

                    log.info("${detail?.count} ${detail?.results?.get(0)?.routeNameStartToFinish}")

                    if (detail?.results != null) {
                        busIds[busName] = summary.results.map { it.routeId }

                        if (detail.results.isEmpty())
                            failed.add(busName)
                        detail.results.forEach {
                            val busInfo =
                                BusInfo()
                            busInfo.routeId = it.routeId
                            busInfo.routeNo = it.routeNo
                            busInfo.routeNameStartToFinish = it.routeNameStartToFinish
                            busInfo.busStopId = it.busStopId
                            busInfo.busStopName = it.busStopName
                        }

                        val file = File(getFolder(), "bus$busName.json")
                        if (!file.exists()) file.createNewFile()
                        file.writeText(gson.toJson(detail))
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                println("exceptionInner $busName")
                failed.add(busName)
            }
            sleep(1000L)
        }


        failed.forEach {
            println("\"$it\",")
        }

        val busList = File(getFolder(), "buslist.json")
        if (!busList.exists()) busList.createNewFile()

        busList.writer().use {
            gson.toJson(busIds, it)
        }
    }

    override fun saveAll(session: Session) {
        val gson = Gson()
        val type = object : TypeToken<BusDetailResponse>() {}.type
        val tx = session.beginTransaction()

        getFolder().listFiles()?.forEach { file ->
            try {
                val busDetail = gson.fromJson<BusDetailResponse>(file.readText(), type)

                println(busDetail.count)
                busDetail.results.forEach {
                    val busInfo =
                        BusInfo()
                    busInfo.routeId = it.routeId
                    busInfo.routeNo = it.routeNo
                    busInfo.routeNameStartToFinish = it.routeNameStartToFinish
                    busInfo.busStopId = it.busStopId
                    busInfo.busStopIndex = it.busStopIndex
                    busInfo.busStopName = it.busStopName

                    session.save(busInfo)
                }

            } catch(ex: Exception) {
                ex.printStackTrace()
                println(file.absolutePath)
            }
        }

        tx.commit()
    }
}