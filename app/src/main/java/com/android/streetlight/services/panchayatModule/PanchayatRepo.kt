package com.android.streetlight.services.panchayatModule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.streetlight.services.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class PanchayatRepo {
    fun getPanchayatData(block:String): LiveData<PanchayatDataModel> {
        val mutableLiveDetailData: MutableLiveData<PanchayatDataModel> = MutableLiveData()
        val call = RetrofitInstance.create().getPanchayat(block)
        call?.enqueue(object : Callback, retrofit2.Callback<PanchayatDataModel?> {
            override fun onResponse(
                call: Call<PanchayatDataModel?>,
                response: Response<PanchayatDataModel?>,
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

            override fun onFailure(call: Call<PanchayatDataModel?>, t: Throwable) {
                Log.d("TAG", "onFailure: ")
            }

        })
        return mutableLiveDetailData
    }
}