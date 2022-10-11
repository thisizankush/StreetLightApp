package com.android.streetlight.services.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class AllDatabase {

    @Database(entities = [AllData::class], version = 1)
    abstract class MyDB : RoomDatabase() {

        abstract fun AllDao(): AllDao

        companion object {
            private var INSTANCE: MyDB? = null

            fun getInstance(context: Context): MyDB? {
                if (INSTANCE == null) {
                    synchronized(MyDB::class) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext, MyDB::class.java, "my.db").build()
                    }
                }
                return INSTANCE
            }
            fun destroyInstance() {
                INSTANCE = null
            }
        }
    }
}