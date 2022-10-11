package com.android.streetlight.services.loginModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    fun getUserLogin(email:String,pass:String): LiveData<LoginUserModel> = LoginRepo().userLogin(email,pass)
}