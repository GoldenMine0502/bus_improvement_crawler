package kr.goldenmine.bus_improvement_crawler.requests.bus_stop.request

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

/*
    <msgHeader>
        <numOfRows>12</numOfRows>
        <pageNo>1</pageNo>
        <resultCode>0</resultCode>
        <resultMsg>정상적으로 처리되었습니다.</resultMsg>
        <totalCount>12</totalCount>
    </msgHeader>
    <msgBody>
        <itemList>
            <ADMINNM>계양구</ADMINNM>
            <DEST_BSTOPID>166000239</DEST_BSTOPID>
            <DEST_BSTOPNM>부평역</DEST_BSTOPNM>
            <FBUS_DEPHMS>0510</FBUS_DEPHMS>
            <LBUS_DEPHMS>2320</LBUS_DEPHMS>
            <MAX_ALLOCGAP>12</MAX_ALLOCGAP>
            <MIN_ALLOCGAP>12</MIN_ALLOCGAP>
            <ORIGIN_BSTOPID>167000391</ORIGIN_BSTOPID>
            <ORIGIN_BSTOPNM>롯데마트</ORIGIN_BSTOPNM>
            <ROUTEID>165000121</ROUTEID>
            <ROUTELEN>18340</ROUTELEN>
            <ROUTENO>581</ROUTENO>
            <ROUTETPCD>1</ROUTETPCD>
            <TURN_BSTOPID>166000239</TURN_BSTOPID>
            <TURN_BSTOPNM>부평역</TURN_BSTOPNM>
        </itemList>
 */
@XmlRootElement(name = "ServiceResult")
@XmlAccessorType(XmlAccessType.FIELD)
data class BusStopRouteResponse(
    val msgHeader: BusStopResponseMsgHeader? = null,
    val msgBody: BusStopRouteResponseMsgBody? = null,
) {

}