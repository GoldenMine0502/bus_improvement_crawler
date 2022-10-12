package kr.goldenmine.bus_improvement_crawler.requests

import kr.goldenmine.bus_improvement_crawler.RetrofitServices

class RequestBusStop(
    val serviceKey: String,
    val perPage: Int
) {

    fun busStop(routeNo: String, page: Int = 1) {
        val response = RetrofitServices.BUS_STOP_SERVICE.getBusRouteNo(serviceKey, page, perPage, routeNo).execute().body()

        response?.msgBody?.itemList?.forEach {

        }
    }
}