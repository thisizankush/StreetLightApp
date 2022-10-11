package com.android.streetlight.services.panchayatModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class PanchayatViewModel : ViewModel() {
    fun getPanchayat(block:String): LiveData<PanchayatDataModel> = PanchayatRepo().getPanchayatData(block)

}