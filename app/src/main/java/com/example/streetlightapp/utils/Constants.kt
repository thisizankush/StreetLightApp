package com.example.streetlightapp.utils

object Constants {
    const val BASE_URL = "http://streetlight-backend.msdrms.in/"
    const val DEV_BASE_URL = "http://api-streetlight.antino.ca/"
    const val LOGIN_URL = "api/v1/user/loginWithMobile"
    const val GET_DISTRICT = "api/v1/user/getdataByMobile"
    const val STATE_ID = "62df9f5173c3331b822673fe"
    const val TOKEN_CONSTANT = "Bearer "
    const val API_TOKEN = "TOKEN"
    const val CREATE_LIGHT = "api/v1/lightId/createlightIdandasset"
    const val CREATE_ASSET = "api/v1/asset/create-asset"
    const val CREATE_POLEID = "api/v1/lightId/create-lightId"
    // const val CREATE_ASSET = "api/v1/asset/create-asset"
    const val UPLOAD_IMAGE = "api/v1/lightId/image-upload"
    const val CREATE_IMEINUMBER = "api/v1/luminaryDetails/getimeibysim"
    const val GET_VENDOR = "api/v1/vendor/getdata"
    const val GET_MODULE = "api/v1/manufacturer/get-manufacturer"
    const val GET_BATTERY_INVENTORY= "api/v1/inventory/getBatteryInventoryMobile"
    const val GET_SOLAR_INVENTORY = "api/v1/inventory/getPvmoduleInventoryMobile"
    const val GET_LUMINARY_INVENTORY = "api/v1/inventory/getLuminaryInventoryMobile"
}