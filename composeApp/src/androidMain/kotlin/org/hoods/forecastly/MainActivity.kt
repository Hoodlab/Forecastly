package org.hoods.forecastly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val calculatedScreenSizes = calculateWindowSizeClass(this)
            App(calculatedScreenSizes.widthSizeClass,dynamicColor = true, darkTheme = isSystemInDarkTheme())
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(
        WindowWidthSizeClass.Medium,
        dynamicColor = true, darkTheme = isSystemInDarkTheme()
    )
}