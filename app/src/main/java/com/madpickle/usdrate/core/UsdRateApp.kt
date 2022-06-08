package com.madpickle.usdrate.core

import android.app.Application
import com.madpickle.usdrate.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by David Madilyan on 31.05.2022.
 */
@HiltAndroidApp
class UsdRateApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}