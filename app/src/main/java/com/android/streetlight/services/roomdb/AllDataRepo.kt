package com.android.streetlight.services.roomdb

import androidx.lifecycle.LiveData

class AllDataRepo(private val allDao: AllDao) {

    val alldata:LiveData<List<AllData>> = allDao.getAllData()

    suspend fun insert(data: AllData){

        allDao.insert(data)
    }


    suspend fun delete(data: AllData){

        allDao.delete(data)
    }

    suspend fun deleteAll(){
        allDao.deleteAllData()
    }

}