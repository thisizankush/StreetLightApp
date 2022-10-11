package com.android.streetlight.services.districtModule


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DistrictDataModel(
    @Json(name = "data")
    val `data`: List<Data?>?,
    @Json(name = "message")
    val message: String?,
    @Json(name = "status")
    val status: Boolean?,
    @Json(name = "statusCode")
    val statusCode: Int?
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "district")
        val district: District?,
        @Json(name = "_id")
        val id: String?

    ) {
        @JsonClass(generateAdapter = true)
        data class District(
            @Json(name = "_id")
            val id: String?,
            @Json(name = "name")
            var name: String?

        ){
            override fun toString(): String {
                return name.toString()
            }
        }
    }


}
