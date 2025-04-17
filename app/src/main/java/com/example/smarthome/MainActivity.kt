package com.example.smarthome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smarthome.data.viewmodel.RoutineViewModel
import com.example.smarthome.data.viewmodel.RoutineViewModelFactory
import com.example.smarthome.ui.components.BottomNavBar
import com.example.smarthome.ui.screens.FavouritesScreen
import com.example.smarthome.ui.screens.RoutinesScreen
import com.example.smarthome.ui.screens.ThingsScreen
import com.example.smarthome.ui.theme.SmartHomeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            SmartHomeTheme {
                val navController = rememberNavController()
                val routineViewModel: RoutineViewModel = ViewModelProvider(
                    this,
                    RoutineViewModelFactory(application)
                )[RoutineViewModel::class.java]

                Scaffold(
                    bottomBar = { BottomNavBar(navController) }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "things",
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable("favourites") { FavouritesScreen() }
                        composable("things") { ThingsScreen() }
                        composable("routines") {
                            RoutinesScreen(routineViewModel = routineViewModel)
                        }
                    }
                }
            }
        }
    }
}
