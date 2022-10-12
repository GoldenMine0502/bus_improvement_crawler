package kr.goldenmine.bus_improvement_crawler

import com.google.gson.Gson
import kr.goldenmine.bus_improvement_crawler.requests.RequestBusStop
import kr.goldenmine.bus_improvement_crawler.requests.buses
import kr.goldenmine.bus_improvement_crawler.requests.response.BusStopRouteResponse
import kr.goldenmine.bus_improvement_crawler.requests.response.BusStopRouteResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.Thread.sleep

fun main() {
    val serviceKey = "Eojpb8YSWns0To%2BoSRzqiOiX8V0eaT7Nnsd%2B9mkWJqTr1vy%2Fw8S3oj8M5v303ljFcVOXMn5cr09BrqaN3fnXSg%3D%3D"
    val perPage = 1000
    val page = 1
    val list = mutableSetOf<BusStopRouteResponseItem>()

    println("crawling...")
    buses.forEach { routeNo ->
        val response = RetrofitServices.BUS_STOP_SERVICE.getBusRouteNo(serviceKey, page, perPage, routeNo).execute().body()

        response?.msgBody?.itemList?.forEach {
            println(it)
            list.add(it)
        }

        sleep(1000L)
    }

    println("printing...")
    list.forEach {
        println(it)
    }

    val gson = Gson()
    val file = File("busids.json")
    if(!file.createNewFile()) file.createNewFile()

    file.bufferedWriter().use {
        gson.toJson(list, it)
    }

//    RetrofitServices.BUS_STOP_SERVICE.getBusRouteNo(serviceKey, page, perPage, routeNo).enqueue(object : Callback<BusStopRouteResponse> {
//        override fun onResponse(call: Call<BusStopRouteResponse>, response: Response<BusStopRouteResponse>) {
//            println(response.isSuccessful)
//
//            response.body()?.msgBody?.itemList?.forEach {
//                println(it)
//                list.add(it)
//            }
//        }
//
//        override fun onFailure(call: Call<BusStopRouteResponse>, t: Throwable) {
//            t.printStackTrace()
//        }
//
//    })

}