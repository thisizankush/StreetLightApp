package com.android.streetlight.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.streetlight.services.poleModule.poleDataModel
import com.android.streetlight.services.poleModule.poleRepo
import com.android.streetlight.services.poleModule.poleResponseModel

class ModuleViewModel :ViewModel() {
    fun getModule(type: String) : LiveData<ModuleDataModule> = ModuleRepo().getModule(type)
}