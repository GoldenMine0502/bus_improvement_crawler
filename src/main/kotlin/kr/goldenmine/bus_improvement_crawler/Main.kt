package kr.goldenmine.bus_improvement_crawler

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.goldenmine.bus_improvement_crawler.requests.RequestBus
import java.io.File

/*
당시 mysql 세팅을 다시 설정해보고 연결 확인을 몇번을 해봤지만 문제가 없어서 몇시간을 해맨 끝에 알아낸 원인은
패스워드는 대문자, 소문자, 숫자, 특수문자를 포함한 암호 길이 8자 이상으로 설정해야 연결이 제대로 된다였다!!!!
(원인 파악 후 매우 허탈하였다!!!)
 */

fun main(args: Array<String>) {
    val type = object : TypeToken<Map<String, ArrayList<String>>>() {}.type
    val gson = Gson()

    File("data/buslist.json").reader().use {
//        val busIds = gson.fromJson<Map<String, ArrayList<String>>>(it, type)

//        saveAll(busIds)
    }

   RequestBus().saveAll()
}
