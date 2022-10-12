package kr.goldenmine.bus_improvement_crawler.requests.response

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType

/*
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
//@XmlRootElement(name = "msgBody")
@XmlAccessorType(XmlAccessType.FIELD)
class BusStopSectionResponseMsgBody(
    val itemList: List<BusStopSectionResponseItem>? = null
) {
}