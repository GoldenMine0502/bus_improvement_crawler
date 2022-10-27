package kr.goldenmine.bus_improvement_crawler.requests.bus_stop.request

import com.google.gson.annotations.SerializedName
import javax.persistence.*
import javax.xml.bind.annotation.*

/*
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
 */
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder = [
//    "ADMINNM", "adminName",
//    "DEST_BSTOPID", "destBusStopId",
//    "DEST_BSTOPNM", "destBusStopName",
//    "FBUS_DEPHMS", "busStartTime",
//    "LBUS_DEPHMS", "busFinishTime",
//    "MAX_ALLOCGAP", "maxAllocationGap",
//    "MIN_ALLOCGAP", "minAllocationGap",
//    "ORIGIN_BSTOPID", "originBusStopId",
//    "ORIGIN_BSTOPNM", "originBusStopName",
//    "ROUTEID", "routeId",
//    "ROUTELEN", "routeLen",
//    "ROUTENO", "routeNo",
//    "ROUTETPCD", "routeTypeCode",
//    "TURN_BSTOPID", "turnBusStopId",
//    "TURN_BSTOPNM", "turnBusStopName",
//])
data class BusStopRouteResponseItem(
//    @XmlType(TypeName="ADMINNM")
//    @XmlElement(field="ADMINNM")
//    @SerializedName("ADMINNM")
//    @XmlElement(name="ADMINNM")
//    @XmlValue
    @SerializedName("adminName")
    @Column(name="admin_name")
    var ADMINNM: String? = null,

    @SerializedName("destBusStopId")
    @Column(name="dest_bus_stop_id")
    var DEST_BSTOPID: String? = null,

    @SerializedName("destBusStopName")
    @Column(name="dest_bus_stop_name")
    var DEST_BSTOPNM: String? = null,

    @SerializedName("busStartTime")
    @Column(name="bus_start_time")
    var FBUS_DEPHMS: String? = null,

    @SerializedName("busFinishTime")
    @Column(name="bus_finish_time")
    var LBUS_DEPHMS: String? = null,

    @SerializedName("maxAllocationGap")
    @Column(name="max_allocation_gap")
    var MAX_ALLOCGAP: Int? = null,

    @SerializedName("minAllocationGap")
    @Column(name="min_allocation_gap")
    var MIN_ALLOCGAP: Int? = null,

    @SerializedName("originBusStopId")
    @Column(name="origin_bus_stop_id")
    var ORIGIN_BSTOPID: String? = null,

    @SerializedName("originBusStopName")
    @Column(name="origin_bus_stop_name")
    var ORIGIN_BSTOPNM: String? = null,

    @SerializedName("routeId")
    @Column(name="route_id")
    var ROUTEID: String? = null,

    @SerializedName("routeLen")
    @Column(name="route_len")
    var ROUTELEN: Int? = null,

    @SerializedName("routeNo")
    @Column(name="route_no")
    var ROUTENO: String? = null,

    @SerializedName("routeTypeCode")
    @Column(name="route_type_code")
    var ROUTETPCD: Int? = null,

    @SerializedName("turnBusStopId")
    @Column(name="turn_bus_stop_id")
    var TURN_BSTOPID: String? = null,

    @SerializedName("turnBusStopName")
    @Column(name="turn_bus_stop_name")
    var TURN_BSTOPNM: String? = null,
) {

    override fun equals(other: Any?): Boolean {
        if(other is BusStopRouteResponseItem) {
            if(ROUTEID == other.ROUTEID && ROUTENO == other.ROUTENO) {
                return true
            }
        }

        return false
    }

    override fun hashCode(): Int {
        return ROUTEID.hashCode()
    }
}