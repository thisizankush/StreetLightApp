package com.example.streetlightapp.network

import com.example.streetlightapp.BaseApplication
import com.example.streetlightapp.BaseApplication.Companion.context
import com.example.streetlightapp.loginmodule.LoginUserModel
import com.example.streetlightapp.utils.Constants
import com.example.streetlightapp.utils.SessionManagementActivity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface RetrofitInstance {
    @FormUrlEncoded
    @POST(Constants.LOGIN_URL)
    fun userLogin(
        @Field("email") email: String, @Field("password") password: String,
    ): Call<LoginUserModel>




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
                .baseUrl(Constants.DEV_BASE_URL)
                .build()
            return retrofit.create(RetrofitInstance::class.java)

        }
    }
}