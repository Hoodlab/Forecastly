package org.hoods.forecastly

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.hoods.forecastly.di.initKoin

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun main(){
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Forecastly",
        ) {
            val calculatedScreenSize = calculateWindowSizeClass()
            App(calculatedScreenSize.widthSizeClass, darkTheme = isSystemInDarkTheme(), dynamicColor = false)
        }
    }
}