package com.example.mycontacts.app

import android.app.Application
import com.example.mycontacts.data.preferences.LocalStorage
import com.example.mycontacts.data.source.room.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        LocalStorage.init(this)
    }

    companion object {
        lateinit var instance: App
    }
}