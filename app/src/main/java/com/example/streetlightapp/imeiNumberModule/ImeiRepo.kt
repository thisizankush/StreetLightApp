package com.android.streetlight.services.imeiNumberModule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.streetlight.services.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ImeiRepo {
    fun getImeiNumber(simnumber: String): LiveData<ImeiResponse> {
        val mutableLiveDetailData: MutableLiveData<ImeiResponse> = MutableLiveData()
        val call = RetrofitInstance.create().getImeiNumber(simnumber)
        call?.enqueue(object : Callback, retrofit2.Callback<ImeiResponse?> {
            override fun onResponse(
                call: Call<ImeiResponse?>,
                response: Response<ImeiResponse?>,
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

            override fun onFailure(call: Call<ImeiResponse?>, t: Throwable) {
                Log.d("TAG", "onFailure: ")
            }

        })
        return mutableLiveDetailData
    }

}