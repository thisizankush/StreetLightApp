package com.android.streetlight.services.imeiNumberModule

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImeiResponse(
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
        @Json(name = "IMEINumber")
        val iMEINumber: String?
    )
}