package com.android.streetlight.services.batteryModule

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.streetlight.services.BaseApplication
import com.android.streetlight.services.SolarInventoryModel
import com.android.streetlight.services.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback


class BatteryInventoryRepo {
    fun getBatteryInventory(id:String,serial_number:String): LiveData<BatteryInventoryResponseModel> {
        val mutableLiveDetailData: MutableLiveData<BatteryInventoryResponseModel> = MutableLiveData()
        val call = RetrofitInstance.create().getBatteryInventory(id,serial_number)
        call?.enqueue(object : Callback, retrofit2.Callback<BatteryInventoryResponseModel?> {
            override fun onResponse(
                call: Call<BatteryInventoryResponseModel?>,
                response: Response<BatteryInventoryResponseModel?>,
            ) {
                try {
                    if (response.code() == 200 || response.code() == 201) {
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

            override fun onFailure(call: Call<BatteryInventoryResponseModel?>, t: Throwable) {
                Log.d("TAG", "onFailure: ")
            }

        })
        return mutableLiveDetailData
    }
}