package com.android.streetlight.services.wardModule


import com.google.gson.annotations.SerializedName

data class WardPoleResponseModel(
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
        @SerializedName("_id")
        val id: String?,
        @SerializedName("name")
        val name: Int?,
        @SerializedName("poleId")
        val poleId: List<PoleId?>?
    ) {
        data class PoleId(
            @SerializedName("block")
            val block: String?,
            @SerializedName("createdAt")
            val createdAt: String?,
            @SerializedName("district")
            val district: String?,
            @SerializedName("energyConsume")
            val energyConsume: String?,
            @SerializedName("energyHarveste")
            val energyHarveste: String?,
            @SerializedName("IMEINumber")
            val iMEINumber: String?,
            @SerializedName("_id")
            val id: String?,
            @SerializedName("Latitude")
            val latitude: String?,
            @SerializedName("Longitude")
            val longitude: String?,
            @SerializedName("panchayat")
            val panchayat: String?,
            @SerializedName("poleId")
            val poleId: String?,
            @SerializedName("poleImage")
            val poleImage: String?,
            @SerializedName("state")
            val state: String?,
            @SerializedName("updatedAt")
            val updatedAt: String?,
            @SerializedName("__v")
            val v: Int?,
            @SerializedName("vendor")
            val vendor: String?,
            @SerializedName("ward")
            val ward: String?
        )
    }
}