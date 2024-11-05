package com.example.cryptoapp

import android.app.Application
import com.example.cryptoapp.di.AppModule

class CryptoApp: Application() {

    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModule()
    }
}