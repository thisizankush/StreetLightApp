package com.android.streetlight.services

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ModuleDataModule(
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
        @Json(name = "createdAt")
        val createdAt: String?,
        @Json(name = "_id")
        val id: String?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "type")
        val type: String?,
        @Json(name = "updatedAt")
        val updatedAt: String?,
        @Json(name = "__v")
        val v: Int?
    ){
        override fun toString(): String {
            return name.toString()
        }
    }
}