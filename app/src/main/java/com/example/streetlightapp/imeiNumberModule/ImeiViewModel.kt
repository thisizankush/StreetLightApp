package com.android.streetlight.services.imeiNumberModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class ImeiViewModel : ViewModel() {
    fun getImeiNumber(simnumber: String) : LiveData<ImeiResponse> = ImeiRepo().getImeiNumber(simnumber)
}