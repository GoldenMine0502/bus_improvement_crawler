package kr.goldenmine.bus_improvement_crawler.requests.bus_stop

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

/*
<ServiceResult>
    <comMsgHeader/>
    <msgHeader>
        <numOfRows>10</numOfRows>
        <pageNo>1</pageNo>
        <resultCode>0</resultCode>
        <resultMsg>정상적으로 처리되었습니다.</resultMsg>
        <totalCount>95</totalCount>
    </msgHeader>
    <msgBody>
        <itemList>
            <ADMINNM>연수구</ADMINNM>
            <BSTOPID>164000705</BSTOPID>
            <BSTOPNM>송도제2차고지</BSTOPNM>
            <BSTOPSEQ>1</BSTOPSEQ>
            <DIRCD>0</DIRCD>
            <PATHSEQ>2</PATHSEQ>
            <POSX>170286.321777</POSX>
            <POSY>429478.523482</POSY>
            <ROUTEID>161000007</ROUTEID>
            <SHORT_BSTOPID>38703</SHORT_BSTOPID>
        </itemList>
 */
//@Xml(name = "book")
@XmlRootElement(name = "ServiceResult")
@XmlAccessorType(XmlAccessType.FIELD)
data class BusStopSectionResponse(
    val msgHeader: BusStopResponseMsgHeader? = null,
    val msgBody: BusStopSectionResponseMsgBody? = null
) {


}


//data class Coffee(
//    val no: Long? = null,
//
//    val name: String? = null,
//
//    val price: BigDecimal = BigDecimal.ZERO
//
//    @field:XmlElementWrapper(name = "images")
//@field:XmlElement(name = "image")
//val images: List<String>? = arrayListOf(),
//
//@field:XmlElementWrapper(name = "categories")
//@field:XmlElement(name = "category")
//val categories: List<Category>? = arrayListOf(),
//)