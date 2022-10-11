package com.android.streetlight.services.subVenderModule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.streetlight.services.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class SubVendorRepo {
    fun getSubVendor(vendr:String): LiveData<SubVendorDataModel> {
        val mutableLiveDetailData: MutableLiveData<SubVendorDataModel> = MutableLiveData()
        val call = RetrofitInstance.create().getSubVendor(vendr)
        call?.enqueue(object : Callback, retrofit2.Callback<SubVendorDataModel?> {
            override fun onResponse(
                call: Call<SubVendorDataModel?>,
                response: Response<SubVendorDataModel?>,
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

            override fun onFailure(call: Call<SubVendorDataModel?>, t: Throwable) {
                Log.d("TAG", "onFailure: ")
            }

        })
        return mutableLiveDetailData
    }
}