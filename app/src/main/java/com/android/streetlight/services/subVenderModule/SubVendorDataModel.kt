package com.android.streetlight.services.subVenderModule


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubVendorDataModel(
    @Json(name = "data")
    val `data`: Data?,
    @Json(name = "message")
    val message: String?,
    @Json(name = "status")
    val status: Boolean?,
    @Json(name = "statusCode")
    val statusCode: Int?
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "data")
        val `data`: Data?,
        @Json(name = "poleId")
        val poleId: List<PoleId?>?
    ) {
        @JsonClass(generateAdapter = true)
        data class Data(
            @Json(name = "subvendor")
            val subvendor: List<Subvendor?>?,
            @Json(name = "vendor")
            val vendor: List<Vendor?>?
        ) {
            @JsonClass(generateAdapter = true)
            data class Subvendor(
                @Json(name = "_id")
                val id: String?,
                @Json(name = "name")
                val name: String?
            ){
                override fun toString(): String {
                    return name.toString()
                }
            }

            @JsonClass(generateAdapter = true)
            data class Vendor(
                @Json(name = "_id")
                val id: String?,
                @Json(name = "name")
                val name: String?
            )
        }

        @JsonClass(generateAdapter = true)
        data class PoleId(
            @Json(name = "_id")
            val id: String?,
            @Json(name = "poleId")
            val poleId: String?
        )
    }
}