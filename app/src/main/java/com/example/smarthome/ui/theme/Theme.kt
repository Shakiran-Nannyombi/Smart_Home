package com.example.smarthome.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Define the light theme color scheme
private val LightThemeColors = lightColorScheme(
    primary = Color(0xFFFFD700),
    secondary =  Color(0xFFFFD700),
    tertiary = Color(0xFF0077B6)
)

@Composable
fun SmartHomeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightThemeColors, // Use colorScheme instead of colors
        typography = MaterialTheme.typography, // Optional: Customize typography if needed
        content = content
    )
}
