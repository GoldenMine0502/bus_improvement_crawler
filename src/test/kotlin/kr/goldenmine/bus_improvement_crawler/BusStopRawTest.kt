package kr.goldenmine.bus_improvement_crawler

import kr.goldenmine.bus_improvement_crawler.requests.RequestBusStop
import kr.goldenmine.bus_improvement_crawler.requests.response.BusStopRouteResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun main() {
    val serviceKey = "Eojpb8YSWns0To%2BoSRzqiOiX8V0eaT7Nnsd%2B9mkWJqTr1vy%2Fw8S3oj8M5v303ljFcVOXMn5cr09BrqaN3fnXSg%3D%3D"
    val perPage = 100
    val page = 1
    val routeNo = "58"

    RetrofitServices.BUS_STOP_SERVICE_RAW.getBusRouteNoRaw(serviceKey, page, perPage, routeNo).enqueue(object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            println(response.isSuccessful)

            println(response.body())
        }

        override fun onFailure(call: Call<String>, t: Throwable) {
            t.printStackTrace()
        }

    })

}