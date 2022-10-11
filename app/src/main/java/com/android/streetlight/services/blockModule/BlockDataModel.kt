package com.android.streetlight.services.blockModule


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BlockDataModel(
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
        @Json(name = "Blocks")
        val blocks: Blocks?,
        @Json(name = "_id")
        val id: String?
    ) {
        @JsonClass(generateAdapter = true)
        data class Blocks(
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