package kr.goldenmine.bus_improvement_crawler.requests.bus_stop

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType

/*
    <msgHeader>
        <numOfRows>10</numOfRows>
        <pageNo>1</pageNo>
        <resultCode>0</resultCode>
        <resultMsg>정상적으로 처리되었습니다.</resultMsg>
        <totalCount>95</totalCount>
    </msgHeader>

    <msgHeader>
        <numOfRows>12</numOfRows>
        <pageNo>1</pageNo>
        <resultCode>0</resultCode>
        <resultMsg>정상적으로 처리되었습니다.</resultMsg>
        <totalCount>12</totalCount>
    </msgHeader>
 */
//@XmlRootElement(name = "msgHeader")
@XmlAccessorType(XmlAccessType.FIELD)
data class BusStopResponseMsgHeader(
    val numOfRows: Int? = null,
    val pageNo: Int? = null,
    val resultCode: Int? = null,
    val resultMsg: String? = null,
    val totalCount: Int? = null,
) {


}