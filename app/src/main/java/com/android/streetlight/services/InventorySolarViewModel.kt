package com.android.streetlight.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class InventorySolarViewModel:ViewModel() {
    fun getSolar(id:String,ss:String): LiveData<SolarInventoryModel> = InventorySolarRepo().getSolarInventory(id,ss)
}