package com.android.streetlight.services


import com.google.gson.annotations.SerializedName

data class CreatePoleResponseModel(
    @SerializedName("asset")
    val asset: Asset?,
    @SerializedName("poleId")
    val poleId: PoleId?
) {
    data class Asset(
        @SerializedName("batteryDetails")
        val batteryDetails: BatteryDetails?,
        @SerializedName("IMEINumber")
        val IMEINumber: String?,
        @SerializedName("installationDetails")
        val installationDetails: InstallationDetails?,
        @SerializedName("joinigDate")
        val joinigDate: String?,
        @SerializedName("luminaryDetails")
        val luminaryDetails: LuminaiDetails?,
        @SerializedName("manufacturingDate")
        val manufacturingDate: String?,
        @SerializedName("pvmoduleDetails")
        val pvmoduleDetails: PvmoduleDetails?,
        @SerializedName("replacementDate")
        val replacementDate: String?,
        @SerializedName("replacementReasion")
        val replacementReasion: String?,
        @SerializedName("ssl")
        val ssl: Any?
    ) {
        data class BatteryDetails(
            @SerializedName("cellVoltage")
            val cellVoltage: Any?,
            @SerializedName("current")
            val current: String?,
            @SerializedName("details")
            val details: Any?,
            @SerializedName("manufacturingDate")
            val manufacturingDate: String?,
            @SerializedName("manufacturingYear")
            val manufacturingYear: Any?,
            @SerializedName("manufacturingname")
            val manufacturingname: String?,
            @SerializedName("model_number")
            val model_number: String?,
            @SerializedName("power")
            val power: String?,
            @SerializedName("serial_number")
            val serial_number: String?,
            @SerializedName("type")
            val type: String?,
            @SerializedName("voltage")
            val voltage: String?
        )

        data class InstallationDetails(
            @SerializedName("installationDate")
            val installationDate: String?,
            @SerializedName("installationPersion")
            val installationPersion: String?
        )

        data class LuminaiDetails(
            @SerializedName("casing")
            val casing: String?,
            @SerializedName("height")
            val height: String?,
            @SerializedName("lux_measured")
            val lux_measured: Any?,
            @SerializedName("serial_number")
            val serial_number: String?,
            @SerializedName("manufacturingname")
            val manufacturingname: String?,
            @SerializedName("model_number")
            val model_number: String?,
            @SerializedName("numberOfLed")
            val numberOfLed: String?,
            @SerializedName("wattage")
            val wattage: String?
        )

        data class PvmoduleDetails(
            @SerializedName("manufacturingDate")
            val manufacturingDate: String?,
            @SerializedName("manufacturingname")
            val manufacturingname: String?,
            @SerializedName("shadow_free")
            val shadow_free: Boolean,
            @SerializedName("model_number")
            val model_number: String?,
            @SerializedName("SPVType")
            val SPVType: String?,
            @SerializedName("serial_number")
            val serial_number: String?,
            @SerializedName("solarcell")
            val solarcell: String?,
            @SerializedName("titl_angle")
            val titl_angle: Boolean?,
            @SerializedName("wattage")
            val wattage: String?,
            @SerializedName("wind_loading")
            val wind_loading: Any?
        )
    }

    data class PoleId(
        @SerializedName("block")
        val block: String?,
        @SerializedName("createdBy")
        val createdBy: String?,
        @SerializedName("district")
        val district: String?,
        @SerializedName("IMEINumber")
        val IMEINumber: String?,
        @SerializedName("installationInfo")
        val installationInfo: InstallationInfo?,
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
        @SerializedName("systemDetails")
        val systemDetails: SystemDetails?,
        @SerializedName("vendor")
        val vendor: String?,
        @SerializedName("ward")
        val ward: String?
    ) {
        data class InstallationInfo(
            @SerializedName("cableProper")
            val cableProper: Boolean?,
            @SerializedName("fixing")
            val fixing: Boolean?,
            @SerializedName("grouting")
            val grouting: Boolean?,
            @SerializedName("language")
            val language: Boolean?,
            @SerializedName("length")
            val length: String?,
            @SerializedName("operationMaintenance")
            val operationMaintenance: Boolean?,
            @SerializedName("remarks")
            val remarks: String?,
            @SerializedName("signBoard")
            val signBoard: Boolean?,
            @SerializedName("size")
            val size: String?,
            @SerializedName("systemInstalled")
            val systemInstalled: Boolean?,
            @SerializedName("uidNumber")
            val uidNumber: Boolean?
        )

        data class SystemDetails(
            @SerializedName("AgencyAddress")
            val agencyAddress: Any?,
            @SerializedName("agencyName")
            val agencyName: String?,
            @SerializedName("agreementDate")
            val agreementDate: Any?,
            @SerializedName("agreementNumber")
            val agreementNumber: Any?,
            @SerializedName("beneficiaryAddress")
            val beneficiaryAddress: String?,
            @SerializedName("beneficiaryName")
            val beneficiaryName: String?,
            @SerializedName("details")
            val details: String?,
            @SerializedName("installationDate")
            val installationDate: String?,
            @SerializedName("installationLocation")
            val installationLocation: String?,
            @SerializedName("orderDate")
            val orderDate: String?,
            @SerializedName("orderNumber")
            val orderNumber: Any?,
            @SerializedName("systemCapacity")
            val systemCapacity: Any?,
            @SerializedName("warranteeExpire")
            val warranteeExpire: String?
        )
    }
}