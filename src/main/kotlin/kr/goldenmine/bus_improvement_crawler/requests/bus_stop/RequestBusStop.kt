package kr.goldenmine.bus_improvement_crawler.requests.bus_stop

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.goldenmine.bus_improvement_crawler.requests.ICrawlRequest
import kr.goldenmine.bus_improvement_crawler.requests.bus_card.RequestBusCard
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
    private val log: Logger = LoggerFactory.getLogger(RequestBusCard::class.java)
    private val gson = Gson()

    override fun getFolder(): File = File("bus_stop")

    override fun getRetrofitService(): BusStopService = Retrofit.Builder()
        .baseUrl("http://apis.data.go.kr/")
        .addConverterFactory(JaxbConverterFactory.create())
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
                log.info(it.toString())
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
        val map = HashMap<BusStopRouteResponseItem, MutableList<BusStopSectionResponseItem>>()

        items.forEach { busStopRouteResponseItem ->
            log.info("request: $busStopRouteResponseItem")
            val routeId = busStopRouteResponseItem.ROUTEID

            if(routeId != null) {
                val response = request.getBusRouteSectionList(
                    serviceKey,
                    1,
                    perPage,
                    routeId
                ).execute()

                map.computeIfAbsent(busStopRouteResponseItem) { ArrayList() }

                val responseList = response.body()?.msgBody?.itemList
                if(responseList != null) map[busStopRouteResponseItem]?.addAll(responseList)

                log.info(response.body()?.msgBody?.itemList?.size.toString())
            }

            Thread.sleep(1000L)
        }

        val file = File("busstops.json")
        if(!file.exists()) file.createNewFile()

        file.bufferedWriter().use {
            gson.toJson(list, it)
        }

        val mapFile = File(getFolder(), "map.json")
        mapFile.bufferedWriter().use {
            gson.toJson(map, it)
        }
    }

    override fun saveAll(session: Session) {
        val file = File("busstops.json")
        val type = object : TypeToken<Map<BusStopRouteResponseItem, List<BusStopSectionResponseItem>>>() {}.type

        val reader = file.bufferedReader()
        val map = gson.fromJson<Map<BusStopRouteResponseItem, List<BusStopSectionResponseItem>>>(reader, type)
        reader.close()

        val tx = session.beginTransaction()

        var id = 1

        map.forEach { t, u ->
            val busInfo = BusInfo(t.ROUTEID, t.ROUTELEN, t.ROUTENO,
                t.ORIGIN_BSTOPID, t.DEST_BSTOPID,
                t.FBUS_DEPHMS, t.LBUS_DEPHMS,
                t.MAX_ALLOCGAP, t.MIN_ALLOCGAP,
                t.ROUTETPCD, t.TURN_BSTOPID)

            session.save(busInfo)

            u.forEach {
                val busStopStationInfo = BusStopStationInfo(it.BSTOPID, it.BSTOPNM, it.POSX, it.POSY, it.SHORT_BSTOPID, it.ADMINNM)
                val busThroughInfo = BusThroughInfo(id++, t.ROUTEID, it.BSTOPID, it.BSTOPSEQ)

                session.save(busStopStationInfo)
                session.save(busThroughInfo)
            }
        }

        tx.commit()
    }
}