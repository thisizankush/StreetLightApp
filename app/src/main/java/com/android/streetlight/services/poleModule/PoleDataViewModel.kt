package com.android.streetlight.services.poleModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.streetlight.services.CreatePoleResponseModel

class PoleDataViewModel : ViewModel() {
    fun createPoleId(poleData: CreatePoleResponseModel) : LiveData<poleResponseModel> = poleRepo().createPoleId(poleData)
}