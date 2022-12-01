package kr.goldenmine.bus_improvement_crawler.requests.bus_stop

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.goldenmine.bus_improvement_crawler.requests.ICrawlRetrofitRequest
import kr.goldenmine.bus_improvement_crawler.requests.bus_stop.database.BusInfo
import kr.goldenmine.bus_improvement_crawler.requests.bus_stop.database.BusStopStationInfo
import kr.goldenmine.bus_improvement_crawler.requests.bus_stop.database.BusThroughInfo
import kr.goldenmine.bus_improvement_crawler.requests.bus_stop.request.BusStopRouteResponseItem
import kr.goldenmine.bus_improvement_crawler.requests.bus_stop.request.BusStopSectionResponseItem
import kr.goldenmine.bus_improvement_crawler.util.buses
import org.hibernate.Session
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import retrofit2.Retrofit
import retrofit2.converter.jaxb.JaxbConverterFactory
import java.io.File

class RequestBusStop(
    private val serviceKey: String,
    private val perPage: Int = 1000
) : ICrawlRetrofitRequest<BusStopService> {
    private val log: Logger = LoggerFactory.getLogger(RequestBusStop::class.java)
    private val gson = Gson()

    override val service: BusStopService = Retrofit.Builder()
        .baseUrl("http://apis.data.go.kr/")
        .addConverterFactory(JaxbConverterFactory.create())
        .build()
        .create(BusStopService::class.java)

    override fun getFolder(): File = File("bus_stop")

    override fun crawlAll(session: Session) {
        val list = crawlList()
        crawlSection(list)
    }

    private fun crawlList(): Set<BusStopRouteResponseItem> {
        val list = mutableSetOf<BusStopRouteResponseItem>()

        val busTemp = buses.toMutableSet()
//        busTemp.add("0")
//        busTemp.add("1")
//        busTemp.add("2")
//        busTemp.add("3")
//        busTemp.add("4")
//        busTemp.add("5")
//        busTemp.add("6")
//        busTemp.add("7")
//        busTemp.add("8")
//        busTemp.add("9")


        buses.forEach { routeNo ->
            if (busTemp.contains(routeNo)) {
                val response = service.getBusRouteNo(serviceKey, 1, perPage, routeNo).execute().body()

                log.info("${response?.msgBody?.itemList?.size} ${response?.msgBody?.itemList?.map { it.ROUTENO }?.joinToString()}")
                response?.msgBody?.itemList?.forEach {
                    list.add(it)
                    busTemp.remove(it.ROUTENO)
                }

                Thread.sleep(1000L)
            }
        }

        log.info("buses count: ${list.size}")
        val file = File(getFolder(), "busids.json")
        if (!file.createNewFile()) file.createNewFile()

        file.bufferedWriter().use {
            gson.toJson(list, it)
        }

        return list
    }


    private fun crawlSection(items: Set<BusStopRouteResponseItem>) {
        val list = mutableListOf<BusStopSectionResponseItem>()

        val map = mutableListOf<Pair<BusStopRouteResponseItem, BusStopSectionResponseItem>>()

        items.forEach { busStopRouteResponseItem ->
            log.info("request: $busStopRouteResponseItem")
            val routeId = busStopRouteResponseItem.ROUTEID

            if (routeId != null) {
                val response = service.getBusRouteSectionList(
                    serviceKey,
                    1,
                    perPage,
                    routeId
                ).execute()

                val body = response.body()

                if (body != null) {
                    body.msgBody?.itemList?.forEach {
                        map.add(Pair(busStopRouteResponseItem, it))
                    }
                    log.info(response.body()?.msgBody?.itemList?.size.toString())
                } else {
                    log.warn("body is null")
                }
            }

            Thread.sleep(1000L)
        }

        val file = File(getFolder(), "busstops.json")
        if (!file.exists()) file.createNewFile()

        file.bufferedWriter().use {
            gson.toJson(list, it)
        }

        val mapFile = File(getFolder(), "map.json")
        if (!mapFile.exists()) mapFile.createNewFile()

        mapFile.bufferedWriter().use {
            gson.toJson(map, it)
        }
    }

    override fun saveAll(session: Session) {
        val file = File(getFolder(), "map.json")
        val type = object : TypeToken<ArrayList<Pair<BusStopRouteResponseItem, BusStopSectionResponseItem>>>() {}.type
        val reader = file.bufferedReader()
        val map = gson.fromJson<ArrayList<Pair<BusStopRouteResponseItem, BusStopSectionResponseItem>>>(reader, type)
        reader.close()

        val tx = session.beginTransaction()

        var id = 1

        map.distinctBy { it.first }
            .map { it.first }
            .forEach { t ->
                val busInfo = BusInfo(
                    t.ROUTEID, t.ROUTELEN, t.ROUTENO,
                    t.ORIGIN_BSTOPID, t.DEST_BSTOPID,
                    t.FBUS_DEPHMS, t.LBUS_DEPHMS,
                    t.MAX_ALLOCGAP, t.MIN_ALLOCGAP,
                    t.ROUTETPCD, t.TURN_BSTOPID
                )

                session.save(busInfo)
            }

        map.distinctBy { it.second.BSTOPID }
            .map { it.second }
            .forEach { u ->
                val busStopStationInfo = BusStopStationInfo(
                    u.BSTOPID, u.BSTOPNM,
                    u.POSX, u.POSY,
                    u.SHORT_BSTOPID, u.ADMINNM
                )
                session.save(busStopStationInfo)
            }

        map.forEach { (t, u) ->
            val busThroughInfo = BusThroughInfo(id++, t.ROUTEID, u.BSTOPID, u.BSTOPSEQ)

            session.save(busThroughInfo)
        }

        tx.commit()
    }
}