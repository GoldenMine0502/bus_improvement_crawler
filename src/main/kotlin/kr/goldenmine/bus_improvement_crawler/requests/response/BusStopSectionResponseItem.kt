package kr.goldenmine.bus_improvement_crawler.requests.response

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
@Table(name = "bus_stop")
@SequenceGenerator(name = "bus_stop_sequence_generator", sequenceName = "bus_stop_sequence")
@Entity
@Data
data class BusStopSectionResponseItem(
    @Id
    @SerializedName("id")
    @Column(name = "id")
    var id: Int? = null,
    @SerializedName("id")
    @Column(name = "id")
    var adminName: String? = null,
    var busStopId: Int? = null,
    var busStopName: String? = null,
    var busStopSequence: Int? = null,
    var directionId: Int? = null,
    var pathSequence: Int? = null,
    var posX: Double? = null,
    var posY: Double? = null,
    var shortBusStopId: Int? = null,
) {
    constructor(): this(null, null, null, null, null, null, null, null, null)
}