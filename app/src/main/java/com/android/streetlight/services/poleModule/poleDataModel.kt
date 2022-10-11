package com.android.streetlight.services.poleModule

import com.google.gson.annotations.SerializedName


data class poleDataModel(

    @SerializedName("poleId")
    var poleId: String? = null,
    @SerializedName("IMEINumber")
    var IMEINumber: String? = null,
    @SerializedName("district")
    var district: String? = null,
    @SerializedName("block")
    var block: String? = null,
    @SerializedName("panchayat")
    var panchayat: String? = null,
    @SerializedName("ward")
    var ward: String? = null,
    @SerializedName("state")
    var state: String? = null,
    @SerializedName("vendor")
    var vendor: String? = null,
    @SerializedName("subvendor")
    var subvendor: String? = null,
    @SerializedName("Latitude")
    var Latitude: String? = null,
    @SerializedName("Longitude")
    var Longitude: String? = null,
    @SerializedName("poleImage")
    var poleImage: String? = null,
    @SerializedName("installationInfo")
    var installationInfo: InstallationInfo? = InstallationInfo(),
    @SerializedName("systemDetails")
    var systemDetails: SystemDetails? = SystemDetails(),
    @SerializedName("createdBy")
    var createdBy: String? = null

)

data class InstallationInfo (

    @SerializedName("length"               ) var length               : String?  = null,
    @SerializedName("size"                 ) var size                 : String?  = null,
    @SerializedName("grouting"             ) var grouting             : Boolean? = null,
    @SerializedName("fixing"               ) var fixing               : Boolean? = null,
    @SerializedName("uidNumber"            ) var uidNumber            : Boolean? = null,
    @SerializedName("signBoard"            ) var signBoard            : Boolean? = null,
    @SerializedName("systemInstalled"      ) var systemInstalled      : Boolean? = null,
    @SerializedName("cableProper"          ) var cableProper          : Boolean? = null,
    @SerializedName("operationMaintenance" ) var operationMaintenance : Boolean? = null,
    @SerializedName("language"             ) var language             : Boolean? = null,
    @SerializedName("remarks"              ) var remarks              : String?  = null

)



data class SystemDetails(

    @SerializedName("agencyName")
    var agencyName: String? = null,
    @SerializedName("orderNumber")
    var orderNumber: String? = null,
    @SerializedName("orderDate")
    var orderDate: String? = null,
    @SerializedName("details")
    var details: String? = null,
    @SerializedName("agreementNumber")
    var agreementNumber: String? = null,
    @SerializedName("agreementDate")
    var agreementDate: String? = null,
    @SerializedName("systemCapacity")
    var systemCapacity: String? = null,
    @SerializedName("AgencyAddress")
    var AgencyAddress: String? = null,
    @SerializedName("beneficiaryName")
    var beneficiaryName: String? = null,
    @SerializedName("beneficiaryAddress")
    var beneficiaryAddress: String? = null,
    @SerializedName("installationLocation")
    var installationLocation: String? = null,
    @SerializedName("installationDate")
    var installationDate: String? = null,
    @SerializedName("warranteeExpire")
    var warranteeExpire: String? = null

)