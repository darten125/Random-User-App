package com.example.randomuser

import android.app.Application
import com.example.randomuser.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RandomUserApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RandomUserApp)
            modules(appModules)
        }
    }
}