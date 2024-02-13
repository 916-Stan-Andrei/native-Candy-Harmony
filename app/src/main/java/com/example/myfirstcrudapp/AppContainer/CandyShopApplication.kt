package com.example.myfirstcrudapp.AppContainer

import android.app.Application

class CandyShopApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}