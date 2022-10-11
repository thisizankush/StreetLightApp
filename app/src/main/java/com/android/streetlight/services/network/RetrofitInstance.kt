package com.android.streetlight.services.network

import android.content.Context.MODE_PRIVATE
import com.android.streetlight.services.*
import com.android.streetlight.services.BaseApplication.Companion.context
import com.android.streetlight.services.assetModule.AssetDataModel
import com.android.streetlight.services.assetModule.AssetResponseModel
import com.android.streetlight.services.batteryModule.BatteryInventoryResponseModel
import com.android.streetlight.services.blockModule.BlockDataModel
import com.android.streetlight.services.districtModule.DistrictDataModel
import com.android.streetlight.services.imeiNumberModule.ImeiResponse
import com.android.streetlight.services.loginModule.LoginUserModel
import com.android.streetlight.services.loginModule.LoginUserModel.Data.User
import com.android.streetlight.services.panchayatModule.PanchayatDataModel
import com.android.streetlight.services.poleModule.poleDataModel
import com.android.streetlight.services.poleModule.poleResponseModel
import com.android.streetlight.services.subVenderModule.SubVendorDataModel
import com.android.streetlight.services.uploadImageModule.UploadImageResponse
import com.android.streetlight.services.utils.Constants
import com.android.streetlight.services.utils.SessionManagementActivity
import com.android.streetlight.services.vendorModule.VendorDataModel
import com.android.streetlight.services.wardModule.WardModel
import com.android.streetlight.services.wardModule.WardPoleResponseModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


interface RetrofitInstance {
    @FormUrlEncoded
    @POST(Constants.LOGIN_URL)
    fun userLogin(
        @Field("email") email: String, @Field("password") password: String,
    ): Call<LoginUserModel>

    @FormUrlEncoded
    @POST(Constants.GET_DISTRICT)
    fun getDistrict(
        @Field("state") stateId: String
    ): Call<DistrictDataModel>

    @FormUrlEncoded
    @POST(Constants.GET_DISTRICT)
    fun getBlock(
        @Field("district") stateId: String
    ): Call<BlockDataModel>

    @FormUrlEncoded
    @POST(Constants.GET_DISTRICT)
    fun getPanchayat(
        @Field("block") stateId: String
    ): Call<PanchayatDataModel>

    @FormUrlEncoded
    @POST(Constants.GET_DISTRICT)
    fun getWards(
        @Field("panchayat") stateId: String
    ): Call<WardModel>

    @FormUrlEncoded
    @POST(Constants.GET_DISTRICT)
    fun getWardsPole(
        @Field("ward") stateId: String
    ): Call<WardPoleResponseModel>


    @POST(Constants.CREATE_ASSET)
    fun createAsset(
        @Body assetData: AssetDataModel
    ): Call<AssetResponseModel>

    @POST(Constants.CREATE_LIGHT)
    fun createPoleId(
        @Body poleData: CreatePoleResponseModel
    ): Call<poleResponseModel>

    @POST(Constants.GET_VENDOR)
    fun getVendor(
    ): Call<VendorDataModel>


    @FormUrlEncoded
    @POST(Constants.GET_VENDOR)
    fun getSubVendor(
        @Field("vendor") vendorId: String
    ): Call<SubVendorDataModel>


    @POST(Constants.GET_MODULE)
    fun getAllModule(
        @Query("type") moduleType: String
    ): Call<ModuleDataModule>


    @GET(Constants.CREATE_IMEINUMBER)
    fun getImeiNumber(
        @Query("simnumber") simnumber: String
    ): Call<ImeiResponse>

    @GET(Constants.GET_BATTERY_INVENTORY)
    fun getBatteryInventory(
        @Query("id") id: String, @Query("serial_number") serial_number: String
    ): Call<BatteryInventoryResponseModel>

    @GET(Constants.GET_SOLAR_INVENTORY)
    fun getSolarInventory(
        @Query("id") id: String, @Query("serial_number") serial_number: String
    ): Call<SolarInventoryModel>

    @GET(Constants.GET_LUMINARY_INVENTORY)
    fun getLuminaryInventory(
        @Query("id") id: String, @Query("serial_number") serial_number: String
    ): Call<InventoryModuleResponseModel>





    @Multipart
    @POST(Constants.UPLOAD_IMAGE)
     fun uploadAttachment(
        @Part file: MultipartBody.Part, @Part("path") path :RequestBody
    ): Call<UploadImageResponse?>?


    companion object {
       val session = BaseApplication.context?.let { SessionManagementActivity(it) }
        var client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader(
                    "Authorization",
                    Constants.TOKEN_CONSTANT + session?.retriveData(context,Constants.API_TOKEN)
                )
                .build()
            chain.proceed(newRequest)
        }.build()
        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        fun create(): RetrofitInstance {

            val retrofit = Retrofit.Builder()
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(Constants.BASE_URL)
                .build()
            return retrofit.create(RetrofitInstance::class.java)

        }
    }
}