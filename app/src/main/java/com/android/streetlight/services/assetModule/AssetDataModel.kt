package com.android.streetlight.services.assetModule

import com.google.gson.annotations.SerializedName


data class AssetDataModel(

    @SerializedName("ssl")
    var ssl: String? = null,
    @SerializedName("imeinumber")
    var imeinumber: String? = null,
    @SerializedName("poleId")
    var poleId: String? = null,
    @SerializedName("joinig_date")
    var joinigDate: String? = null,
    @SerializedName("manufacturingDate")
    var manufacturingDate: String? = null,
    @SerializedName("installationDetails")
    var installationDetails: InstallationDetails? = InstallationDetails(),
    @SerializedName("replacementDate")
    var replacementDate: String? = null,
    @SerializedName("replacementReasion")
    var replacementReasion: String? = null,
    @SerializedName("pvmoduleDetails")
    var pvmoduleDetails: PvmoduleDetails? = PvmoduleDetails(),
    @SerializedName("luminaiDetails")
    var luminaiDetails: LuminaiDetails? = LuminaiDetails(),
    @SerializedName("batteryDetails")
    var batteryDetails: BatteryDetails? = BatteryDetails(),

)

data class InstallationDetails(

    @SerializedName("installationPersion")
    var installationPersion: String? = null,
    @SerializedName("installationDate")
    var installationDate: String? = null

)

data class PvmoduleDetails(

    @SerializedName("serial_number")
    var serialNumber: String? = null,
    @SerializedName("model_number")
    var modelNumber: String? = null,
    @SerializedName("manufacturingname")
    var manufacturingname: String? = null,
    @SerializedName("manufacturingDate")
    var manufacturingDate: String? = null,
    @SerializedName("wattage")
    var wattage: String? = null,
    @SerializedName("solarcell")
    var solarcell: String? = null,
    @SerializedName("SPVType")
    var SPVType: String? = null,
    @SerializedName("titl_free")
    var titlFree: Boolean? = null,
    @SerializedName("wind_loading")
    var windLoading: String? = null

)

data class LuminaiDetails(

    @SerializedName("manufacturingname")
    var manufacturingname: String? = null,
    @SerializedName("casing")
    var casing: String? = null,
    @SerializedName("model_number")
    var modelNumber: String? = null,
    @SerializedName("height")
    var height: String? = null,
    @SerializedName("wattage")
    var wattage: String? = null,
    @SerializedName("numberOfLed")
    var numberOfLed: Int? = null,
    @SerializedName("lux_measured")
    var luxMeasured: String? = null

)

data class BatteryDetails(

    @SerializedName("serial_number")
    var serialNumber: String? = null,
    @SerializedName("model_number")
    var modelNumber: String? = null,
    @SerializedName("manufacturingname")
    var manufacturingname: String? = null,
    @SerializedName("manufacturingDate")
    var manufacturingDate: String? = null,
    @SerializedName("voltage")
    var voltage: String? = null,
    @SerializedName("current")
    var current: String? = null,
    @SerializedName("power")
    var power: String? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("details")
    var details: String? = null,
    @SerializedName("cellVoltage")
    var cellVoltage: String? = null,
    @SerializedName("manufacturingYear")
    var manufacturingYear: String? = null

)

