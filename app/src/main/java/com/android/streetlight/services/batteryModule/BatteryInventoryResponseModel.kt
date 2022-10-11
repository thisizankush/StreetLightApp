package com.android.streetlight.services.batteryModule

import com.android.streetlight.services.SolarInventoryModel
import com.google.gson.annotations.SerializedName


data class BatteryInventoryResponseModel(

    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("data") val `data`: List<Data?>?,
) {
    data class Data(

        @SerializedName("_id") val Id: String? = null,
        @SerializedName("manufacturingname") val manufacturingname: String? = null,
        @SerializedName("serial_number") val serial_number: String? = null,
        @SerializedName("model_number") val model_number: String? = null,
        @SerializedName("voltage") val voltage: String? = null,
        @SerializedName("current") val current: String? = null,
        @SerializedName("power") val power: String? = null,
        @SerializedName("type") val type: String? = null,
        @SerializedName("details") val details: String? = null,
        @SerializedName("cellVoltage") val cellVoltage: String? = null,
        @SerializedName("manufacturingYear") val manufacturingYear: String? = null,
        @SerializedName("status") val status: String? = null,
        @SerializedName("createdAt") val createdAt: String? = null,
        @SerializedName("updatedAt") val updatedAt: String? = null,
        @SerializedName("__v") val _v: Int? = null

    )
}
