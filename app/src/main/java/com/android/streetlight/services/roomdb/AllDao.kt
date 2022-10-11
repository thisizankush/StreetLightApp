package com.android.streetlight.services.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AllDao {

    @Insert
    fun insert(data: AllData)

    @Update
    fun update(data: AllData)

    @Delete
    fun delete(data: AllData)

    @Query("delete from all_table")
    fun deleteAllData()

    @Query("select * from all_table order by uid ASC")
    fun getAllData(): LiveData<List<AllData>>

}