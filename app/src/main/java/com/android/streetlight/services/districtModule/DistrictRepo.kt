package com.android.streetlight.services.districtModule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.streetlight.services.districtModule.DistrictDataModel
import com.android.streetlight.services.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class DistrictRepo {
    fun getDistrictData(stateId:String): LiveData<DistrictDataModel> {
        val mutableLiveDetailData: MutableLiveData<DistrictDataModel> = MutableLiveData()
        val call = RetrofitInstance.create().getDistrict(stateId)
        call?.enqueue(object : Callback, retrofit2.Callback<DistrictDataModel?> {
            override fun onResponse(
                call: Call<DistrictDataModel?>,
                response: Response<DistrictDataModel?>,
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

            override fun onFailure(call: Call<DistrictDataModel?>, t: Throwable) {
                Log.d("TAG", "onFailure: ")
            }

        })
        return mutableLiveDetailData
    }
}