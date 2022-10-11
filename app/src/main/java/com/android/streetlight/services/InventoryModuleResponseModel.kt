package com.android.streetlight.services


import com.google.gson.annotations.SerializedName

data class InventoryModuleResponseModel(
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
        @SerializedName("casing")
        val casing: String?,
        @SerializedName("createdAt")
        val createdAt: String?,
        @SerializedName("height")
        val height: String?,
        @SerializedName("_id")
        val id: String?,
        @SerializedName("luxMeasured")
        val luxMeasured: String?,
        @SerializedName("manufacturingname")
        val manufacturingname: String?,
        @SerializedName("modelNumber")
        val model_number: String?,
        @SerializedName("serial_number")
        val serial_number: String?,
        @SerializedName("numberOfLed")
        val numberOfLed: String?,
        @SerializedName("status")
        val status: String?,
        @SerializedName("updatedAt")
        val updatedAt: String?,
        @SerializedName("__v")
        val v: Int?,
        @SerializedName("wattage")
        val wattage: String?
    )
}