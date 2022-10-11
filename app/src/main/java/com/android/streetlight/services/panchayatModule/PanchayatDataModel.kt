package com.android.streetlight.services.panchayatModule


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PanchayatDataModel(
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
        @Json(name = "_id")
        val id: String?,
        @Json(name = "Panchayats")
        val panchayats: Panchayats?
    ) {
        @JsonClass(generateAdapter = true)
        data class Panchayats(
            @Json(name = "_id")
            val id: String?,
            @Json(name = "name")
            val name: String?
        ){
            override fun toString(): String {
                return name.toString()
            }
        }
    }
}