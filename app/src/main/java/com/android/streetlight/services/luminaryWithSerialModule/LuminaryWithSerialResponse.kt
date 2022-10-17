package com.android.streetlight.services.luminaryWithSerialModule

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LuminaryWithSerialResponse (

    @Json(name = "status") var status     : Boolean? = null,
    @Json(name = "statusCode") var statusCode : Int?     = null,
    @Json(name = "message") var message    : String?  = null,
    @Json(name = "data") var data       : Data?

)

    data class LuminaryDetails (

        @Json(name = "_id") var Id           : String? = null,
        @Json(name = "serial_number") var serial_number : String? = null,
        @Json(name = "IMEINumber") var IMEINumber   : String? = null,
        @Json(name = "status") var status       : String? = null,
        @Json(name = "createdAt") var createdAt    : String? = null,
        @Json(name = "updatedAt") var updatedAt    : String? = null,
        @Json(name = "___v") var ___v           : Int?    = null

    )



    data class LuminaryInventory (

        @Json(name = "_id") var _id           : String? = null,
        @Json(name = "manufacturer") var manufacturer : String? = null,
        @Json(name = "casing") var casing       : String? = null,
        @Json(name = "model_number") var model_number  : String? = null,
        @Json(name = "serial_number") var serial_number : String? = null,
        @Json(name = "height") var height       : String? = null,
        @Json(name = "wattage") var wattage      : String? = null,
        @Json(name = "numberOfLed") var numberOfLed  : Int?    = null,
        @Json(name = "lux_measured") var lux_measured  : String? = null,
        @Json(name = "status") var status       : String? = null,
        @Json(name = "createdAt") var createdAt    : String? = null,
        @Json(name = "updatedAt") var updatedAt    : String? = null,
        @Json(name = "__v") var __v           : Int?    = null

    )


    data class Data (

        @Json(name = "luminaryDetails") var luminaryDetails   : List<LuminaryDetails>?,
        @Json(name = "luminaryInventory") var luminaryInventory : List<LuminaryInventory>?

    )
