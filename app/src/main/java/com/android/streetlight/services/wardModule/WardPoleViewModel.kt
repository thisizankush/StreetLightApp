package com.android.streetlight.services.wardModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class WardPoleViewModel :ViewModel() {
    fun getWardPole(wardPoleid:String): LiveData<WardPoleResponseModel> = WardPoleRepo().getWardPoleData(wardPoleid)
}