package org.hoods.forecastly.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
actual fun AppTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if(darkTheme) mediumContrastDarkColorScheme else mediumContrastLightColorScheme,
        typography = AppTypography,
        content = content
    )
}