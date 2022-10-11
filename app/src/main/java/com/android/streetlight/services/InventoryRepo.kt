package com.android.streetlight.services

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.streetlight.services.network.RetrofitInstance
import com.android.streetlight.services.panchayatModule.PanchayatDataModel
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class InventoryRepo {
    fun getLuminaryInventory(id:String,serial_number:String): LiveData<InventoryModuleResponseModel> {
        val mutableLiveDetailData: MutableLiveData<InventoryModuleResponseModel> = MutableLiveData()
        val call = RetrofitInstance.create().getLuminaryInventory(id,serial_number)
        call?.enqueue(object : Callback, retrofit2.Callback<InventoryModuleResponseModel?> {
            override fun onResponse(
                call: Call<InventoryModuleResponseModel?>,
                response: Response<InventoryModuleResponseModel?>,
            ) {
                try {
                    if (response.code() == 200) {
                        mutableLiveDetailData.postValue(response.body())
                    } else if (response.code() == 401){
                        Toast.makeText(BaseApplication.context, "unauthorize", Toast.LENGTH_SHORT).show()
                    }else if (response.code() == 500 || response.code() == 502){
                        Toast.makeText(BaseApplication.context, "internal server error", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        mutableLiveDetailData.postValue(null)
                    }
                } catch (e: Exception) {
                    mutableLiveDetailData.postValue(null)
                }
            }

            override fun onFailure(call: Call<InventoryModuleResponseModel?>, t: Throwable) {
                Log.d("TAG", "onFailure: ")
            }

        })
        return mutableLiveDetailData
    }
}