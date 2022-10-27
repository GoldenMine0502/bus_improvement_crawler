import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.goldenmine.bus_improvement_crawler.Keys
import kr.goldenmine.bus_improvement_crawler.requests.naver_map.RequestNaver
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import java.io.File

fun main() {
    // read keys
    val type = object : TypeToken<Keys>() {}.type
    val gson = Gson()
    val reader = File("keys.json").reader()

    // keys
    val key = gson.fromJson<Keys>(reader, type)

    // request
    val request = RequestNaver(key.requestNaverKeyId, key.requestNaverKey)

    // session
    val registry = StandardServiceRegistryBuilder().configure(File("config/hibernate.cfg.xml")).build()
    val sessionFactory = MetadataSources(registry).buildMetadata().buildSessionFactory()
    val session = sessionFactory.openSession()

    // crawl
    request.crawlAll(session)

    // close
    session.close()
}