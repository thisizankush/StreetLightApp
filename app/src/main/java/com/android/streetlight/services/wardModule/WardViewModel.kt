package com.android.streetlight.services.wardModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class WardViewModel: ViewModel() {
    fun getWards(panchayat:String): LiveData<WardModel> = WardRepo().getWardData(panchayat)
}