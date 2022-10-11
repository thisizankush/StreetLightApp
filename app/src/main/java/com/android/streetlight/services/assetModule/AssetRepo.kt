package com.android.streetlight.services.assetModule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.streetlight.services.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AssetRepo {
    fun createAsset(assetData: AssetDataModel): LiveData<AssetResponseModel> {
        val mutableLiveDetailData: MutableLiveData<AssetResponseModel> = MutableLiveData()
        val call = RetrofitInstance.create().createAsset(assetData)
        call.enqueue(object : Callback, retrofit2.Callback<AssetResponseModel?> {
            override fun onResponse(
                call: Call<AssetResponseModel?>,
                response: Response<AssetResponseModel?>,
            ) {
                try {
                    if (response.code() == 200 || response.code() == 201) {
                        mutableLiveDetailData.postValue(response.body())
                    } else {
                        mutableLiveDetailData.postValue(null)
                    }
                } catch (e: Exception) {
                    mutableLiveDetailData.postValue(null)
                }
            }

            override fun onFailure(call: Call<AssetResponseModel?>, t: Throwable) {
                Log.d("TAG", "onFailure: ")
            }

        })
        return mutableLiveDetailData
    }
}