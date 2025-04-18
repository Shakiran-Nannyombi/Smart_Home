package com.example.smarthome.ui.theme

import androidx.compose.material3.Shapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography as M3Typography
import androidx.compose.material3.darkColorScheme as m3DarkColorScheme
import androidx.compose.material3.lightColorScheme as m3LightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle // Import the correct TextStyle

// Default color values
private val DefaultPrimaryColor = Color(0xFFFFD700) // Gold
private val DefaultSecondaryColor = Color(0xFFFFD700) // Gold
private val DefaultTertiaryColor = Color(0xFF0077B6) // Blue

// Function to generate light color scheme based on primary color
private fun customLightColorScheme(
    primary: Color = DefaultPrimaryColor,
    secondary: Color = DefaultSecondaryColor,
    tertiary: Color = DefaultTertiaryColor
) = m3LightColorScheme(
    primary = primary,
    onPrimary = Color.White,
    primaryContainer = primary.copy(alpha = 0.2f),
    onPrimaryContainer = Color.Black,

    secondary = secondary,
    onSecondary = Color.Black,
    secondaryContainer = secondary.copy(alpha = 0.2f),
    onSecondaryContainer = Color.Black,

    tertiary = tertiary,
    onTertiary = Color.White,
    tertiaryContainer = tertiary.copy(alpha = 0.2f),
    onTertiaryContainer = Color.White,

    // Keep other colors consistent
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFEEEEEE),
    onSurfaceVariant = Color(0xFF444444),
    error = Color(0xFFB00020),
    onError = Color.White
)

// Function to generate dark color scheme based on primary color
private fun customDarkColorScheme(
    primary: Color = DefaultPrimaryColor,
    secondary: Color = DefaultSecondaryColor,
    tertiary: Color = DefaultTertiaryColor
) = m3DarkColorScheme(
    primary = primary,
    onPrimary = Color.White,
    primaryContainer = primary.copy(alpha = 0.2f),
    onPrimaryContainer = Color.Black,

    secondary = secondary,
    onSecondary = Color.Black,
    secondaryContainer = secondary.copy(alpha = 0.2f),
    onSecondaryContainer = Color.Black,

    tertiary = tertiary,
    onTertiary = Color.White,
    tertiaryContainer = tertiary.copy(alpha = 0.2f),
    onTertiaryContainer = Color.White,

    // Dark mode specific colors
    background = Color(0xFFFFD700), // Gold background for dark mode
    onBackground = Color.White,
    surface = Color(0xFFFFD700), // Gold surface for dark mode
    onSurface = Color.White,
    surfaceVariant = Color(0xFF2D2D2D), // Darker surface variant
    onSurfaceVariant = Color(0xFFCCCCCC),
    error = Color(0xFFCF6679),
    onError = Color.Black
)

@Composable
fun SmartHomeTheme(
    appColor: Color? = null, // Dynamic color from settings
    darkTheme: Boolean = false, // Support for dark mode
    content: @Composable () -> Unit
) {
    // Use the appColor if provided, otherwise use default
    val primaryColor = appColor ?: DefaultPrimaryColor

    // Choose the color scheme based on the darkTheme flag
    val colorScheme = if (darkTheme) {
        customDarkColorScheme(
            primary = primaryColor,
            secondary = primaryColor, // Or you can keep DefaultSecondaryColor
            tertiary = DefaultTertiaryColor
        )
    } else {
        customLightColorScheme(
            primary = primaryColor,
            secondary = primaryColor, // Or you can keep DefaultSecondaryColor
            tertiary = DefaultTertiaryColor
        )
    }

    // Apply the MaterialTheme with the chosen color scheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography, // Reference the renamed custom typography
        shapes = Shapes, // Your custom shapes if defined
        content = content
    )
}

// Define custom typography styles here
// Renamed the Typography object to AppTypography to avoid conflict
val AppTypography = M3Typography(
    displayLarge = TextStyle(/* Define the style here */), // Edit this to customize displayLarge
    displayMedium = TextStyle(/* Define the style here */), // Edit this to customize displayMedium
    // Define more text styles as needed
)

// Define custom shapes here
val Shapes = Shapes(
    small = RoundedCornerShape(4.dp), // Edit this to customize small shapes
    medium = RoundedCornerShape(8.dp), // Edit this to customize medium shapes
    large = RoundedCornerShape(16.dp) // Edit this to customize large shapes
)
