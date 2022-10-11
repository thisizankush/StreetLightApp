package com.android.streetlight.services.wardModule

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WardModel(
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
        @Json(name = "Wards")
        val wards: Wards?
    ) {
        @JsonClass(generateAdapter = true)
        data class Wards(
            @Json(name = "_id")
            val id: String?,
            @Json(name = "name")
            val name: Int?
        ){
            override fun toString(): String {
                return name.toString()
            }
        }
    }
}