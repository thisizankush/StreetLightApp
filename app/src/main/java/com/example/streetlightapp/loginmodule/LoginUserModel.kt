package com.example.streetlightapp.loginmodule


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginUserModel(
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
        @Json(name = "token")
        val token: String?,
        @Json(name = "user")
        val user: User?
    ) {
        @JsonClass(generateAdapter = true)
        data class User(
            @Json(name = "address")
            val address: String?,
            @Json(name = "country_code")
            val countryCode: Int?,
            @Json(name = "createdAt")
            val createdAt: String?,
            @Json(name = "datetime")
            val datetime: String?,
            @Json(name = "district")
            val district: String?,
            @Json(name = "email")
            val email: String?,
            @Json(name = "_id")
            val id: String?,
            @Json(name = "level")
            val level: String?,
            @Json(name = "mobileNumber")
            val mobileNumber: Long?,
            @Json(name = "name")
            val name: String?,
            @Json(name = "password")
            val password: String?,
            @Json(name = "permission")
            val permission: String?,
            @Json(name = "role")
            val role: String?,
            @Json(name = "username")
            val username: String?,
            @Json(name = "__v")
            val v: Int?
        )
    }
}