package com.example.smarthome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smarthome.ui.components.BottomNavBar
import com.example.smarthome.ui.screens.FavouritesScreen
import com.example.smarthome.ui.screens.ThingsScreen
import com.example.smarthome.ui.theme.SmartHomeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This allows Compose to draw behind system bars (like status bar)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            SmartHomeTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavBar(navController) } // Ensure BottomNavBar is implemented
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "favourites",
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable("favourites") { FavouritesScreen() } // Ensure this screen exists
                        composable("things") { ThingsScreen() } // Ensure this screen exists
                    }
                }
            }
        }
    }
}
