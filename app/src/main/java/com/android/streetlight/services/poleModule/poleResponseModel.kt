package com.android.streetlight.services.poleModule

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class poleResponseModel(
    @Json(name = "data")
    val `data`: Data?,
    @Json(name = "message")
    val message: String?,
    @Json(name = "status")
    val status: Boolean?,
    @Json(name = "error")
    val error: String?,
    @Json(name = "statusCode")
    val statusCode: Int?
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "block")
        val block: String?,
        @Json(name = "createdAt")
        val createdAt: String?,
        @Json(name = "district")
        val district: String?,
        @Json(name = "IMEINumber")
        val iMEINumber: String?,
        @Json(name = "_id")
        val id: String?,
        @Json(name = "installationInfo")
        val installationInfo: InstallationInfo?,
        @Json(name = "Latitude")
        val latitude: String?,
        @Json(name = "Longitude")
        val longitude: String?,
        @Json(name = "panchayat")
        val panchayat: String?,
        @Json(name = "poleId")
        val poleId: String?,
        @Json(name = "poleImage")
        val poleImage: String?,
        @Json(name = "state")
        val state: String?,
        @Json(name = "subvendor")
        val subvendor: String?,
        @Json(name = "systemDetails")
        val systemDetails: SystemDetails?,
        @Json(name = "updatedAt")
        val updatedAt: String?,
        @Json(name = "__v")
        val v: Int?,
        @Json(name = "vendor")
        val vendor: String?,
        @Json(name = "ward")
        val ward: String?
    ) {
        @JsonClass(generateAdapter = true)
        data class InstallationInfo(
            @Json(name = "cableProper")
            val cableProper: Boolean?,
            @Json(name = "fixing")
            val fixing: Boolean?,
            @Json(name = "grouting")
            val grouting: Boolean?,
            @Json(name = "language")
            val language: Boolean?,
            @Json(name = "length")
            val length: String?,
            @Json(name = "operationMaintenance")
            val operationMaintenance: Boolean?,
            @Json(name = "remarks")
            val remarks: String?,
            @Json(name = "signBoard")
            val signBoard: Boolean?,
            @Json(name = "size")
            val size: String?,
            @Json(name = "systemInstalled")
            val systemInstalled: Boolean?,
            @Json(name = "uidNumber")
            val uidNumber: Boolean?
        )

        @JsonClass(generateAdapter = true)
        data class SystemDetails(
            @Json(name = "AgencyAddress")
            val agencyAddress: String?,
            @Json(name = "agencyName")
            val agencyName: String?,
            @Json(name = "agreementDate")
            val agreementDate: String?,
            @Json(name = "agreementNumber")
            val agreementNumber: String?,
            @Json(name = "beneficiaryAddress")
            val beneficiaryAddress: String?,
            @Json(name = "beneficiaryName")
            val beneficiaryName: String?,
            @Json(name = "details")
            val details: String?,
            @Json(name = "installationDate")
            val installationDate: Any?,
            @Json(name = "installationLocation")
            val installationLocation: String?,
            @Json(name = "orderDate")
            val orderDate: Any?,
            @Json(name = "orderNumber")
            val orderNumber: String?,
            @Json(name = "systemCapacity")
            val systemCapacity: String?,
            @Json(name = "warranteeExpire")
            val warranteeExpire: String?
        )
    }
}