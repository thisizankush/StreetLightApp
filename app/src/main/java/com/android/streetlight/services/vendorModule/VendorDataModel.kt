package com.android.streetlight.services.vendorModule


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VendorDataModel(
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
        val poleId: List<Any?>?
    ) {
        @JsonClass(generateAdapter = true)
        data class Data(
            @Json(name = "subvendor")
            val subvendor: List<Any?>?,
            @Json(name = "vendor")
            val vendor: List<Vendor?>?
        ) {
            @JsonClass(generateAdapter = true)
            data class Vendor(
                @Json(name = "active")
                val active: Boolean?,
                @Json(name = "address")
                val address: String?,
                @Json(name = "aggrementnumer")
                val aggrementnumer: List<Any?>?,
                @Json(name = "country_code")
                val countryCode: Int?,
                @Json(name = "createdAt")
                val createdAt: String?,
                @Json(name = "email")
                val email: String?,
                @Json(name = "logo")
                val logo: String?,
                @Json(name = "_id")
                val id: String?,
                @Json(name = "mobileNumber")
                val mobileNumber: Long?,
                @Json(name = "name")
                val name: String?,
                @Json(name = "updatedAt")
                val updatedAt: String?,
                @Json(name = "__v")
                val v: Int?,
                @Json(name = "workorder")
                val workorder: List<Any?>?
            ){
                override fun toString(): String {
                    return name.toString()
                }
            }
        }
    }
}