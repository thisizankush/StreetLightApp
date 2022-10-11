package com.android.streetlight.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.streetlight.services.panchayatModule.PanchayatDataModel
import com.android.streetlight.services.panchayatModule.PanchayatRepo

class InventoryViewModel :ViewModel() {
    fun getLuminary(id:String,serial:String): LiveData<InventoryModuleResponseModel> = InventoryRepo().getLuminaryInventory(id,serial)
}