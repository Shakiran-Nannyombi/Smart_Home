package com.example.smarthome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smarthome.data.database.RoutineDatabase
import com.example.smarthome.ui.components.BottomNavBar
import com.example.smarthome.ui.screens.FavouritesScreen
import com.example.smarthome.ui.screens.RoutinesScreen
import com.example.smarthome.ui.screens.ThingsScreen
import com.example.smarthome.ui.theme.SmartHomeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val database = RoutineDatabase.getDatabase(applicationContext)
        val routineDao = database.routineDao()

        setContent {
            SmartHomeTheme {
                val navController = rememberNavController()
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
                            RoutinesScreen(routineDao = routineDao)
                        }
                    }
                }
            }
        }
    }
}
