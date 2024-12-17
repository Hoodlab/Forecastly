package org.hoods.forecastly

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import org.hoods.forecastly.ui.components.getNavigationType
import org.hoods.forecastly.ui.home.HomeScreen
import org.hoods.forecastly.ui.navigation.ForecastNavigation
import org.hoods.forecastly.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    windowWidthSizeClass: WindowWidthSizeClass,
    dynamicColor:Boolean,
    darkTheme:Boolean
) {
    AppTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {
        ForecastNavigation(navigationType = getNavigationType(windowWidthSizeClass))
    }
}