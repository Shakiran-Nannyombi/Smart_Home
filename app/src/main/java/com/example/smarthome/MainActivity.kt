package com.example.smarthome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smarthome.data.viewmodel.RoutineViewModel
import com.example.smarthome.data.viewmodel.RoutineViewModelFactory
import com.example.smarthome.data.viewmodel.SettingsViewModel
import com.example.smarthome.data.viewmodel.SettingsViewModelFactory
import com.example.smarthome.ui.components.BottomNavBar
import com.example.smarthome.ui.screens.FavouritesScreen
import com.example.smarthome.ui.screens.RoutinesScreen
import com.example.smarthome.ui.screens.SettingsScreen
import com.example.smarthome.ui.screens.ThingsScreen
import com.example.smarthome.ui.screens.IdeasScreen
import com.example.smarthome.ui.theme.SmartHomeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberNavController()

            // ViewModel Setup
            val routineViewModel: RoutineViewModel = ViewModelProvider(
                this,
                RoutineViewModelFactory(application)
            )[RoutineViewModel::class.java]
            val settingsViewModel: SettingsViewModel = ViewModelProvider(
                this,
                SettingsViewModelFactory(application)
            )[SettingsViewModel::class.java]

            // Collect appColor from SettingsViewModel
            val appColor = settingsViewModel.appColor.collectAsState(initial = Color(0xFFFFD700)).value // Default to Gold if not available

            SmartHomeTheme(
                appColor = appColor, // Pass the appColor from SettingsViewModel
                darkTheme = false
            ) {
                Scaffold(
                    bottomBar = { BottomNavBar(navController = navController, appColor = appColor) } // Pass appColor here
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "things",
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable("favourites") {
                            FavouritesScreen(viewModel = routineViewModel) // Pass the routineViewModel here
                        }
                        composable("things") {
                            ThingsScreen()
                        }
                        composable("routines") {
                            RoutinesScreen(routineViewModel = routineViewModel, appColor = appColor) // Pass appColor to RoutinesScreen
                        }
                        composable("settings") {
                            SettingsScreen(
                                navController = navController, // Use the same navController
                                onBackClick = { /* Handle back click */ }
                            )
                        }
                        composable("ideas") {
                            IdeasScreen(onBackClick = { navController.popBackStack() }) // Add IdeasScreen
                        }
                    }
                }
            }
        }
    }
}
