package com.android.streetlight.services


import com.google.gson.annotations.SerializedName

data class SolarInventoryModel(
    @SerializedName("data")
    val `data`: List<Data?>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("statusCode")
    val statusCode: Int?
) {
    data class Data(
        @SerializedName("createdAt")
        val createdAt: String?,
        @SerializedName("_id")
        val id: String?,
        @SerializedName("manufacturingDate")
        val manufacturingDate: String?,
        @SerializedName("manufacturingname")
        val manufacturingname: String?,
        @SerializedName("model_number")
        val model_number: String?,
        @SerializedName("SPVType")
        val sPVType: String?,
        @SerializedName("serial_number")
        val serial_number: String?,
        @SerializedName("shadow_free")
        val shadowFree: Boolean?,
        @SerializedName("solarcell")
        val solarcell: String?,
        @SerializedName("status")
        val status: String?,
        @SerializedName("titl_angle")
        val titlAngle: Boolean?,
        @SerializedName("updatedAt")
        val updatedAt: String?,
        @SerializedName("__v")
        val v: Int?,
        @SerializedName("wattage")
        val wattage: String?,
        @SerializedName("wind_loading")
        val windLoading: String?
    )
}