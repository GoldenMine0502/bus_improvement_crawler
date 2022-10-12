package kr.goldenmine.bus_improvement_crawler.requests.bus_stop

import com.google.gson.Gson
import kr.goldenmine.bus_improvement_crawler.requests.ICrawlRequest
import kr.goldenmine.bus_improvement_crawler.requests.bus_card.RequestBus
import kr.goldenmine.bus_improvement_crawler.util.buses
import org.hibernate.Session
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import retrofit2.Retrofit
import retrofit2.converter.jaxb.JaxbConverterFactory
import java.io.File

class RequestBusStop(
    val serviceKey: String,
    val perPage: Int = 1000
): ICrawlRequest<BusStopService> {
    private val log: Logger = LoggerFactory.getLogger(RequestBus::class.java)
    private val gson = Gson()

    override fun getFolder(): File = File("bus_stop")

    override fun getRetrofitService(): BusStopService = Retrofit.Builder()
        .baseUrl("http://apis.data.go.kr/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(JaxbConverterFactory.create()) // ConverterFactory로 Jaxb converter 추가
//        .addConverterFactory(TikXmlConverterFactory.create())
        .build()
        .create(BusStopService::class.java)

    override fun crawlAll() {
        val request = getRetrofitService()

        val list = crawlList(request)
        crawlSection(request, list)
    }

    private fun crawlList(request: BusStopService): Set<BusStopRouteResponseItem> {
        val list = mutableSetOf<BusStopRouteResponseItem>()

        buses.forEach { routeNo ->
            val response = request.getBusRouteNo(serviceKey, 1, perPage, routeNo).execute().body()

            response?.msgBody?.itemList?.forEach {
                println(it)
                list.add(it)
            }

            Thread.sleep(1000L)
        }

        val file = File("busids.json")
        if(!file.createNewFile()) file.createNewFile()

        file.bufferedWriter().use {
            gson.toJson(list, it)
        }

        return list
    }

    private fun crawlSection(request: BusStopService, items: Set<BusStopRouteResponseItem>) {
        val list = mutableListOf<BusStopSectionResponseItem>()

        items.forEach { busStopRouteResponseItem ->
            println("request: $busStopRouteResponseItem")
            val routeId = busStopRouteResponseItem.ROUTEID

            if(routeId != null) {
                val response = request.getBusRouteSectionList(
                    serviceKey,
                    1,
                    perPage,
                    routeId
                ).execute()

                println(response.body()?.msgBody?.itemList?.size)
            }

            Thread.sleep(1000L)
        }

        val file2 = File("busstops.json")
        if(!file2.exists()) file2.createNewFile()

        file2.bufferedWriter().use {
            gson.toJson(list, it)
        }
    }

    override fun saveAll(session: Session) {

    }
}