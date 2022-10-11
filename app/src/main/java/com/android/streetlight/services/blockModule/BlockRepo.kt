package com.android.streetlight.services.blockModule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.streetlight.services.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class BlockRepo {
    fun getBlockData(district:String): LiveData<BlockDataModel> {
        val mutableLiveDetailData: MutableLiveData<BlockDataModel> = MutableLiveData()
        val call = RetrofitInstance.create().getBlock(district)
        call?.enqueue(object : Callback, retrofit2.Callback<BlockDataModel?> {
            override fun onResponse(
                call: Call<BlockDataModel?>,
                response: Response<BlockDataModel?>,
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

            override fun onFailure(call: Call<BlockDataModel?>, t: Throwable) {
                Log.d("TAG", "onFailure: ")
            }

        })
        return mutableLiveDetailData
    }
}