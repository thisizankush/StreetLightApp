package com.android.streetlight.services.districtModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.streetlight.services.districtModule.DistrictDataModel
import com.android.streetlight.services.districtModule.DistrictRepo

class DistrictViewModel : ViewModel() {
    fun getDistrict(stateId:String): LiveData<DistrictDataModel> = DistrictRepo().getDistrictData(stateId)
}