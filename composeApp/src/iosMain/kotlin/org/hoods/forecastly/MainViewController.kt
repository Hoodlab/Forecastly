package org.hoods.forecastly

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.window.ComposeUIViewController
import org.hoods.forecastly.di.initKoin

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    val calculatedScreenSize = calculateWindowSizeClass()
    App(calculatedScreenSize.widthSizeClass,dynamicColor = false, darkTheme = isSystemInDarkTheme())
}