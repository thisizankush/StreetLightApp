package com.android.streetlight.services.serialListModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
class SerialListViewModel : ViewModel() {
    fun getSerialList(): LiveData<SerialListModel> = SerialListRepo().getSerialList()
}