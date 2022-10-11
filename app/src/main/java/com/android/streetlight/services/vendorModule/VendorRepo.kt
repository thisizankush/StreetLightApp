package com.android.streetlight.services.vendorModule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.streetlight.services.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class VendorRepo {
    fun getVendor(): LiveData<VendorDataModel> {
        val mutableLiveDetailData: MutableLiveData<VendorDataModel> = MutableLiveData()
        val call = RetrofitInstance.create().getVendor()
        call?.enqueue(object : Callback, retrofit2.Callback<VendorDataModel?> {
            override fun onResponse(
                call: Call<VendorDataModel?>,
                response: Response<VendorDataModel?>,
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

            override fun onFailure(call: Call<VendorDataModel?>, t: Throwable) {
                Log.d("TAG", "onFailure: ")
            }

        })
        return mutableLiveDetailData
    }
}