package kr.goldenmine.bus_improvement_crawler.requests.bus_card

import com.google.gson.annotations.SerializedName

//error	문자	에러정보 Root
//level	숫자	에러레벨
//code	문자	에러코드
//text	문자	에러메세지
//status	문자	처리 결과의 상태 표시, 유효값 : OK(성공), NOT_FOUND(결과없음), ERROR(에러)
class BusCardServiceError(
    @SerializedName("error")
    val error: String,

    @SerializedName("level")
    val level: Int,

    @SerializedName("code")
    val code: String,

    @SerializedName("text")
    val text: String
) {
}