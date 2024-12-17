package org.hoods.forecastly

import android.app.Application
import org.hoods.forecastly.di.initKoin
import org.koin.android.ext.koin.androidContext

class ForecastlyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin{
            androidContext(this@ForecastlyApp)
        }
    }
}