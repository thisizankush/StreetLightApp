package com.android.streetlight.services.wardModule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.streetlight.services.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class WardRepo {
    fun getWardData(panchayat:String): LiveData<WardModel> {
        val mutableLiveDetailData: MutableLiveData<WardModel> = MutableLiveData()
        val call = RetrofitInstance.create().getWards(panchayat)
        call?.enqueue(object : Callback, retrofit2.Callback<WardModel?> {
            override fun onResponse(
                call: Call<WardModel?>,
                response: Response<WardModel?>,
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

            override fun onFailure(call: Call<WardModel?>, t: Throwable) {
                Log.d("TAG", "onFailure: ")
            }

        })
        return mutableLiveDetailData
    }
}