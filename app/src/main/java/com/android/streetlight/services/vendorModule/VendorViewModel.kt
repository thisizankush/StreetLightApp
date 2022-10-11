package com.android.streetlight.services.vendorModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class VendorViewModel :ViewModel() {
    fun getVendor(): LiveData<VendorDataModel> = VendorRepo().getVendor()
}