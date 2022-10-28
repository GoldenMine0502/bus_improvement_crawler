package kr.goldenmine.bus_improvement_crawler.requests.kakao_map

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.goldenmine.bus_improvement_crawler.requests.ICrawlRetrofitRequest
import kr.goldenmine.bus_improvement_crawler.requests.kakao_map.database.RoadNameInfo
import kr.goldenmine.bus_improvement_crawler.requests.kakao_map.filter.*
import kr.goldenmine.bus_improvement_crawler.requests.kakao_map.request.KakaoKeywordResponse
import kr.goldenmine.bus_improvement_crawler.requests.kakao_map.request.KakaoKeywordResponseDocument
import org.hibernate.Session
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.lang.Thread.sleep

class RequestKakao(
    val key: String
) : ICrawlRetrofitRequest<KakaoService> {
    private val log: Logger = LoggerFactory.getLogger(RequestKakao::class.java)
    private val gson = Gson()
    private val keywords = mutableListOf("북측", "남측", "삼거리", "사거리", "오거리", "입구", "진입", "공업", "물류", "거리", "(신)", "앞")
    private val keywordPredicates = listOf(
        FilterPredicateNoFilter(),
        FilterPredicateCustomNames(),
        FilterPredicateEnd(keywords),
        FilterPredicateStart(keywords),
        FilterPredicateAlphabet(),
    )

    init {
        for (i in 0..9) {
            keywords.add(i.toString())
        }

        for (eng in 'a'..'z') {
            keywords.add(eng.toString())
        }

        for (eng in 'A'..'Z') {
            keywords.add(eng.toString())
        }
    }

    override val service: KakaoService
        get(): KakaoService = Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(KakaoService::class.java)

    override fun getFolder(): File = File("kakao")

    override fun crawlAll(session: Session) {
        val query =
            session.createSQLQuery("SELECT start_name FROM traffic UNION ALL SELECT finish_name FROM traffic")
        val roadNames = (query.list() as List<String?>).distinct()
        val results = ArrayList<RoadNameInfo>()

        sleep(1000L)
        log.info("size: ${roadNames.size}")
        sleep(1000L)

        for (index in roadNames.indices) {
            var roadName = roadNames[index]

            var added = false

            for (predicate in keywordPredicates) {
                if(added) break

                while (roadName != null && predicate.filter(roadName)) {
                    val previous = roadName
                    roadName = predicate.doReplace(roadName)

                    try {
                        val pre = service.getInfoFromKeyword(key, roadName).execute().body()?.documents
                            ?.filter { it.addressName?.contains("인천") == true || it.roadAddressName?.contains("인천") == true }
                            ?.sortedWith { o1, o2 ->
                            fun compareTransport(): Int {
                                return if(o1.categoryName?.contains("교통") == true) {
                                    if(o2.categoryName?.contains("교통") == true) {
                                        0
                                    } else {
                                        -1 // o1이 앞섬
                                    }
                                } else {
                                    if(o2.categoryName?.contains("교통") == true) {
                                        1 // o2가 앞섬
                                    } else {
                                        0
                                    }
                                }
                            }

                            fun compareRoadAddress(): Int {
                                return if(o1.roadAddressName?.contains("인천") == true) {
                                    if(o2.roadAddressName?.contains("인천") == true) {
                                        // 인천 내에선 교통인지 아닌지로 구분
                                        compareTransport()
                                    } else { // o1이 우세
                                        -1
                                    }
                                } else {
                                    if(o2.roadAddressName?.contains("인천") == true) { // o2이 우세
                                        1
                                    } else { // 둘다 인천이 아니면.. 그냥 뒤로 밀어
                                        1
                                    }
                                }
                            }

                            compareTransport()
                        }
                        val result = pre?.firstOrNull()

                        if (result != null) {
                            val roadNameInfo = RoadNameInfo(index + 1, roadNames[index], result.x, result.y)

                            log.info("$index, $roadName, $roadNameInfo, $result \n$pre\n")
                            results.add(roadNameInfo)

                            added = true
                            break
                        }
                    } catch (ex: IndexOutOfBoundsException) {
//                        log.warn("no road from ${predicate.name} $roadName")
                    } catch (ex: Exception) {
                        log.error(ex.message, ex)
                    }

                    if(previous == roadName) break

                    sleep(100L)
                }
            }

            if (!added) {
                val nullInfo = RoadNameInfo(index + 1, roadName, 0.0, 0.0)
                results.add(nullInfo)
                log.info("null data added $roadName ${roadNames[index]}")
            }

            sleep(500L)
        }

        results.forEach { roadNameInfo ->
            log.info("saving: $roadNameInfo")
            val file = File(getFolder(), "${roadNameInfo.roadName}.json")
            if (!file.exists()) file.createNewFile()

            file.bufferedWriter().use { writer ->
                gson.toJson(roadNameInfo, writer)
            }
        }
    }

    fun filterKeyword(input: String) {
        var result = input
        var lastChanged: Int

        do {
            lastChanged = 0

            for (keyword in keywords) {
                if (result.endsWith(keyword)) {
                    result = result.substring(0, result.length - keyword.length)
                    lastChanged++
                }

                if (result.startsWith(keyword)) {
                    result = result.substring(keyword.length, result.length)
                    lastChanged++
                }
            }
        } while (lastChanged > 0)
    }

    fun apiTest(keyword: String): KakaoKeywordResponse? {
        return service.getInfoFromKeyword(key, keyword).execute().body()
    }

    override fun saveAll(session: Session) {
        val type = object : TypeToken<RoadNameInfo>() {}.type

        val tx = session.beginTransaction()

        getFolder().listFiles()?.forEach {
            it.bufferedReader().use { reader ->
                val roadNameInfo = gson.fromJson<RoadNameInfo>(reader, type)
                log.info("saving: $roadNameInfo")
                session.save(roadNameInfo)
            }
        }

        tx.commit()
    }
}