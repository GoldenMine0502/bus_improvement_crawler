package kr.goldenmine.bus_improvement_crawler.requests.bus_stop.request

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType

@XmlAccessorType(XmlAccessType.FIELD)
data class BusStopRouteResponseMsgBody(
    val itemList: List<BusStopRouteResponseItem>? = null
) {

}