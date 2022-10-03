package com.example.streetlightapp

import android.app.Application
import android.content.Context

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

    }
    companion object {
        var instance: BaseApplication? = null
            private set

        @JvmStatic
        val context: Context?
            get() = instance
    }

}