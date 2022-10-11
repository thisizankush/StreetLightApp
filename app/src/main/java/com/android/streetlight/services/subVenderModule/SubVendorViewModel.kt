package com.android.streetlight.services.subVenderModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class SubVendorViewModel: ViewModel() {
    fun getSubVendor(vendr:String): LiveData<SubVendorDataModel> = SubVendorRepo().getSubVendor(vendr)
}