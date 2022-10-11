package com.android.streetlight.services

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.streetlight.services.network.RetrofitInstance
import com.android.streetlight.services.poleModule.poleDataModel
import com.android.streetlight.services.poleModule.poleResponseModel
import com.android.streetlight.services.utils.SessionManagementActivity
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ModuleRepo {
    fun getModule(type: String): LiveData<ModuleDataModule> {
        val mutableLiveDetailData: MutableLiveData<ModuleDataModule> = MutableLiveData()
        val call = RetrofitInstance.create().getAllModule(type)
        call.enqueue(object : Callback, retrofit2.Callback<ModuleDataModule?> {
            override fun onResponse(
                call: Call<ModuleDataModule?>,
                response: Response<ModuleDataModule?>,
            ) {
                try {
                    if (response.code() == 200 || response.code() == 201) {
                        mutableLiveDetailData.postValue(response.body())
                    }else if(response.code() == 500){
                        Toast.makeText(BaseApplication.context, "Internal Server Error", Toast.LENGTH_SHORT).show()
                    }else if (response.code() == 401){
                        val sessionManagementActivity = SessionManagementActivity(BaseApplication.instance!!.applicationContext)
                        sessionManagementActivity.logoutUser()
                    } else {
                        mutableLiveDetailData.postValue(null)
                    }
                } catch (e: Exception) {
                    mutableLiveDetailData.postValue(null)
                }
            }

            override fun onFailure(call: Call<ModuleDataModule?>, t: Throwable) {
                Log.d("TAG", "onFailure: ")
            }

        })
        return mutableLiveDetailData
    }
}