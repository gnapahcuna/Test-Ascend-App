package com.paiwaddev.paiwadpos.utils

import android.app.Application
import com.paiwaddev.paiwadpos.utils.module.networkModule
import com.paiwaddev.paiwadpos.utils.module.productModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin{
            androidLogger()
            androidContext(this@MyApplication)
            modules(listOf(networkModule, productModule))
        }
    }
}