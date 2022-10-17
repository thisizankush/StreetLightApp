package com.android.streetlight.services.luminaryWithSerialModule


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.streetlight.services.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class LuminaryWIthSerialRepo {
    fun getLuminaryWithSerial(serial_number: String): LiveData<LuminaryWithSerialResponse> {
        val mutableLiveDetailData: MutableLiveData<LuminaryWithSerialResponse> = MutableLiveData()
        val call = RetrofitInstance.create().getLuminaryWithSerial(serial_number)
        call.enqueue(object : Callback, retrofit2.Callback<LuminaryWithSerialResponse?> {
            override fun onResponse(
                call: Call<LuminaryWithSerialResponse?>,
                response: Response<LuminaryWithSerialResponse?>,
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

            override fun onFailure(call: Call<LuminaryWithSerialResponse?>, t: Throwable) {
                Log.d("TAG", "onFailure: ")
            }

        })
        return mutableLiveDetailData
    }

}