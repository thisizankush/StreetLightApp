package com.android.streetlight.services.poleModule

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.streetlight.services.BaseApplication
import com.android.streetlight.services.CreatePoleResponseModel
import com.android.streetlight.services.network.RetrofitInstance
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class poleRepo {
    fun createPoleId(poledata: CreatePoleResponseModel): LiveData<poleResponseModel> {
        val mutableLiveDetailData: MutableLiveData<poleResponseModel> = MutableLiveData()
        val call = RetrofitInstance.create().createPoleId(poledata)
        call.enqueue(object : Callback, retrofit2.Callback<poleResponseModel?> {
            override fun onResponse(
                call: Call<poleResponseModel?>,
                response: Response<poleResponseModel?>,
            ) {
                try {
                    if (response.isSuccessful) {
                        mutableLiveDetailData.postValue(response.body())

                    } else if (response.code() == 400){
                        //Toast.makeText(BaseApplication.context, "bad request", Toast.LENGTH_SHORT).show()
                        try {

                            val jObjError = JSONObject(response.errorBody()!!.string())
                            mutableLiveDetailData.postValue(poleResponseModel(data = null,
                                message = null,
                                status = null,
                                error = jObjError.getString("message").toString(),
                                statusCode = null

                            ))
                        } catch (e: Exception) {
                            //Toast.makeText(BaseApplication.context, e.message, Toast.LENGTH_LONG).show()
                        }
                    }else if (response.code() == 500 || response.code() == 502){
                        Toast.makeText(BaseApplication.context, "internal server error", Toast.LENGTH_SHORT).show()
                    }else {
                        mutableLiveDetailData.postValue(null)
                    }
                } catch (e: Exception) {
                    mutableLiveDetailData.postValue(null)
                }
            }

            override fun onFailure(call: Call<poleResponseModel?>, t: Throwable) {
                Log.d("TAG", "onFailure: ")
            }

        })
        return mutableLiveDetailData
    }
}