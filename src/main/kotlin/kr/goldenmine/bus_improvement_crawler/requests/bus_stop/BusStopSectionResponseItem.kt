package kr.goldenmine.bus_improvement_crawler.requests.bus_stop

import com.google.gson.annotations.SerializedName
import lombok.Data
import javax.persistence.*
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType

/*
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
 */
//@XmlRootElement(name = "itemList")
@XmlAccessorType(XmlAccessType.FIELD)
data class BusStopSectionResponseItem(
    val ADMINNM: String? = null,
    val BSTOPID: Int? = null,
    val BSTOPNM: String? = null,
    val BSTOPSEQ: Int? = null,
    val DIRCD: Int? = null,
    val PATHSEQ: Int? = null,
    val POSX: Double? = null,
    val POSY: Double? = null,
    val SHORT_BSTOPID: Int? = null,
)