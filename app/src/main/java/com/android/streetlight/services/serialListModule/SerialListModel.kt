package com.android.streetlight.services.serialListModule


import com.squareup.moshi.Json

data class SerialListModel(

    @Json(name = "status")
    var status: Boolean? = null,
    @Json(name = "statusCode")
    var statusCode: Int? = null,
    @Json(name = "message")
    var message: String? = null,
    @Json(name = "data")
    var data: List<Data>

) {

    data class Sim(

        @Json(name = "simNumber")
        var simNumber: String? = null,
        @Json(name = "mobileNumber")
        var mobileNumber: String? = null

    )

    data class Data(

        @Json(name = "_id")
        var _id: String? = null,
        @Json(name = "sim")
        var sim: Sim? = Sim(),
        @Json(name = "serial_number")
        var serial_number: String? = null,
        @Json(name = "IMEINumber")
        var IMEINumber: String? = null,
        @Json(name = "status")
        var status: String? = null,
        @Json(name = "createdAt")
        var createdAt: String? = null,
        @Json(name = "updatedAt")
        var updatedAt: String? = null,
        @Json(name = "__v")
        var __v: Int? = null

    )
}