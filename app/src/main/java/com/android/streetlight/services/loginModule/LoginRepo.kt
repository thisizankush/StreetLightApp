package com.android.streetlight.services.loginModule

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.streetlight.services.BaseApplication
import com.android.streetlight.services.network.RetrofitInstance
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback


class LoginRepo {
    fun userLogin(email:String,pass:String): LiveData<LoginUserModel> {
        val mutableLiveDetailData: MutableLiveData<LoginUserModel> = MutableLiveData()
        val call = RetrofitInstance.create().userLogin(email,pass)
        call?.enqueue(object : Callback, retrofit2.Callback<LoginUserModel?> {
            override fun onResponse(
                call: Call<LoginUserModel?>,
                response: Response<LoginUserModel?>,
            ) {
                try {
                    if (response.code() == 200) {
                        mutableLiveDetailData.postValue(response.body())
                    } else {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            Toast.makeText(BaseApplication.context,
                                jObjError.getJSONObject("error").getString("message"),
                                Toast.LENGTH_LONG).show()
                            mutableLiveDetailData.postValue(null)
                        } catch (e: Exception) {
                            Toast.makeText(BaseApplication.context, e.message, Toast.LENGTH_LONG).show()
                        }
                        //Toast.makeText(BaseApplication.context, "${response.errorBody().toString()}", Toast.LENGTH_SHORT).show()
                       // mutableLiveDetailData.postValue(null)
                    }
                } catch (e: Exception) {
                    mutableLiveDetailData.postValue(null)
                }
            }

            override fun onFailure(call: Call<LoginUserModel?>, t: Throwable) {
                Log.d("TAG", "onFailure: ")
            }

        })
        return mutableLiveDetailData
    }
}