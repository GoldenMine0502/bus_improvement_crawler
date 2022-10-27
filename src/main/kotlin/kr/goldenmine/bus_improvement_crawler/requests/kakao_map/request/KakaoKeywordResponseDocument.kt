package kr.goldenmine.bus_improvement_crawler.requests.kakao_map.request

import com.google.gson.annotations.SerializedName

/*
        {
            "address_name": "인천 서구 석남동 223-7",
            "category_group_code": "",
            "category_group_name": "",
            "category_name": "교통,수송 > 도로시설 > 교차로",
            "distance": "",
            "id": "15139544",
            "phone": "",
            "place_name": "모래방죽사거리",
            "place_url": "http://place.map.kakao.com/15139544",
            "road_address_name": "",
            "x": "126.661459067706",
            "y": "37.4991930393998"
        }
 */
data class KakaoKeywordResponseDocument(
    @SerializedName("address_name")
    val addressName: String?,

    @SerializedName("category_group_code")
    val categoryGroupCode: String?,

    @SerializedName("category_group_name")
    val categoryGroupName: String?,

    @SerializedName("category_name")
    val categoryName: String?,

    @SerializedName("distance")
    val distance: String?,

    @SerializedName("id")
    val id: String?,

    @SerializedName("phone")
    val phone: String?,

    @SerializedName("place_name")
    val placeName: String?,

    @SerializedName("place_url")
    val placeUrl: String?,

    @SerializedName("road_address_name")
    val roadAddressName: String?,

    @SerializedName("x")
    val x: Double,

    @SerializedName("y")
    val y: Double,
) {
}