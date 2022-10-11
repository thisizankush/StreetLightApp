package com.android.streetlight.services.wardModule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.streetlight.services.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class WardPoleRepo {
    fun getWardPoleData(wardPoleid:String): LiveData<WardPoleResponseModel> {
        val mutableLiveDetailData: MutableLiveData<WardPoleResponseModel> = MutableLiveData()
        val call = RetrofitInstance.create().getWardsPole(wardPoleid)
        call?.enqueue(object : Callback, retrofit2.Callback<WardPoleResponseModel?> {
            override fun onResponse(
                call: Call<WardPoleResponseModel?>,
                response: Response<WardPoleResponseModel?>,
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

            override fun onFailure(call: Call<WardPoleResponseModel?>, t: Throwable) {
                Log.d("TAG", "onFailure: ")
            }

        })
        return mutableLiveDetailData
    }
}