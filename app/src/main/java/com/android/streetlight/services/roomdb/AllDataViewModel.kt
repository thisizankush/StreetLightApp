package com.android.streetlight.services.roomdb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class AllDataViewModel(application: Application) : AndroidViewModel(application) {

    val allData: LiveData<List<AllData>>
    val repository: AllDataRepo

    init {
        val dao = AllDatabase.MyDB.getInstance(application)?.AllDao()
        repository = AllDataRepo(dao!!)
        allData = repository.alldata
    }

    fun deleteSingleData(data: AllData) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(data)
    }

    fun deleteAllData() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    fun insert(data: AllData) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(data)
    }
}