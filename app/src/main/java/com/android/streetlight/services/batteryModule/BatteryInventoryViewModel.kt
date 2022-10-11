package com.android.streetlight.services.batteryModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.streetlight.services.InventoryModuleResponseModel
import com.android.streetlight.services.InventoryRepo

class BatteryInventoryViewModel : ViewModel() {
    fun getBatteryInventory(id:String,ss:String): LiveData<BatteryInventoryResponseModel> = BatteryInventoryRepo().getBatteryInventory(id,ss)
}