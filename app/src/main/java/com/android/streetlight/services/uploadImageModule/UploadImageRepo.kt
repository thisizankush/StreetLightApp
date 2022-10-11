package com.android.streetlight.services.uploadImageModule

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.streetlight.services.BaseApplication.Companion.context
import com.android.streetlight.services.network.RetrofitInstance
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class UploadImageRepo {

     fun uploadAttachment(filePart: MultipartBody.Part, path: RequestBody): LiveData<UploadImageResponse> {

        val mutableLiveDetailData: MutableLiveData<UploadImageResponse> = MutableLiveData()
        val call = RetrofitInstance.create().uploadAttachment(filePart,path)

            call?.enqueue(object : Callback, retrofit2.Callback<UploadImageResponse?> {
                override fun onResponse(
                    call: Call<UploadImageResponse?>,
                    response: Response<UploadImageResponse?>,
                ) {
                    try {
                        if (response.isSuccessful) {
                            mutableLiveDetailData.postValue(response.body())
                        } else {
                            mutableLiveDetailData.postValue(null)
                        }
                    } catch (e: Exception) {
                        mutableLiveDetailData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<UploadImageResponse?>, t: Throwable) {
                    Log.d("TAG", "onFailure: ")
                }

            })

        return mutableLiveDetailData
    }
}