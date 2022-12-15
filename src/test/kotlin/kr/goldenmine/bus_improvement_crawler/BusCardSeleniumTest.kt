package kr.goldenmine.bus_improvement_crawler

import kr.goldenmine.bus_improvement_crawler.requests.bus_card_selenium.RequestBusCardBusStopSelenium
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.junit.jupiter.api.Test
import java.io.File

class BusCardSeleniumTest { //
//    @Test
//    fun test() {
//        val registry = StandardServiceRegistryBuilder().configure(File("config/hibernate.cfg.xml")).build()
//        val sessionFactory = MetadataSources(registry).buildMetadata().buildSessionFactory()
//
//        val session = sessionFactory.openSession()
//
//        val request = RequestBusCardBusStopSelenium()
//        request.crawlAll(session)
//    } // robots.txt 체크 API 제한
}