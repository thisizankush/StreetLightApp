package com.android.streetlight.services.serialListModule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.streetlight.services.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class SerialListRepo {
    fun getSerialList(): LiveData<SerialListModel> {
        val mutableLiveDetailData: MutableLiveData<SerialListModel> = MutableLiveData()
        val call = RetrofitInstance.create().getSerialList()
        call.enqueue(object : Callback, retrofit2.Callback<SerialListModel?> {
            override fun onResponse(
                call: Call<SerialListModel?>,
                response: Response<SerialListModel?>,
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

            override fun onFailure(call: Call<SerialListModel?>, t: Throwable) {
                Log.d("TAG", "onFailure: ")
            }

        })
        return mutableLiveDetailData
    }
}