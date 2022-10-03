package com.example.streetlightapp.loginmodule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.streetlightapp.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class LoginRepo {
    fun userLogin(email:String,pass:String): LiveData<LoginUserModel> {
        val mutableLiveDetailData: MutableLiveData<LoginUserModel> = MutableLiveData()
        val call = RetrofitInstance.create().userLogin(email,pass)
        call.enqueue(object : Callback, retrofit2.Callback<LoginUserModel?> {
            override fun onResponse(
                call: Call<LoginUserModel?>,
                response: Response<LoginUserModel?>,
            ) {
                try {
                    if (response.code() == 200) {
                        mutableLiveDetailData.postValue(response.body())
                    } else {
                        mutableLiveDetailData.postValue(null)
                    }
                } catch (e: Exception) {
                    mutableLiveDetailData.postValue(null)
                }
            }

            override fun onFailure(call: Call<LoginUserModel?>, t: Throwable) {
                Log.d("Hello", "onFailure:$t")
            }

        })
        return mutableLiveDetailData
    }
}