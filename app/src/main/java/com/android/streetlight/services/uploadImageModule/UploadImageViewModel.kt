package com.android.streetlight.services.uploadImageModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadImageViewModel : ViewModel() {
     fun uploadAttachment(filePart: MultipartBody.Part,path : RequestBody) : LiveData<UploadImageResponse> = UploadImageRepo().uploadAttachment(filePart,path)
}