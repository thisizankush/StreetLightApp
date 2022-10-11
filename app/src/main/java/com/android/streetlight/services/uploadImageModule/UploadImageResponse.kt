package com.android.streetlight.services.uploadImageModule

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UploadImageResponse(

    @Json(name = "data")
    val `data`: String?,
    @Json(name = "message")
    val message: String?,
    @Json(name = "status")
    val status: Boolean?,
    @Json(name = "statusCode")
    val statusCode: Int?
)