package com.example.smarthome.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IdeasScreen(onBackClick: () -> Unit) {
    val categories = listOf(
        "Smart Home Automation Ideas" to listOf(
            "Turn off all lights when leaving home.",
            "Start the coffee maker when the alarm goes off.",
            "Dim the lights and play relaxing music at 9 PM."
        ),
        "Energy-Saving Tips" to listOf(
            "Schedule your thermostat to reduce energy usage at night.",
            "Turn off unused appliances with smart plugs."
        ),
        "Device Setup Guides" to listOf(
            "How to integrate your smart speaker with this app.",
            "How to connect your smart thermostat."
        ),
        "Featured Smart Devices" to listOf(
            "Top-rated smart bulbs for 2025.",
            "Affordable smart cameras under $100."
        ),
        "User Stories and Inspiration" to listOf(
            "John's automated home office setup.",
            "Sarah's energy-efficient smart kitchen."
        ),
        "Seasonal or Thematic Ideas" to listOf(
            "Set up holiday lights with a smart schedule.",
            "Create a spooky ambiance for Halloween."
        ),
        "DIY Projects" to listOf(
            "Build a smart garden with automated watering.",
            "Set up motion sensors for personalized lighting."
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets(0.dp),
                title = {
                    Text(
                        text = "Smart Home Ideas",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = paddingValues.calculateTopPadding(), // Add top padding to avoid overlap with TopAppBar
                        bottom = paddingValues.calculateBottomPadding() // Ensure content scrolls behind BottomNavBar
                    )
            ) {
                categories.forEach { (category, ideas) ->
                    item {
                        IdeaCategoryCard(category = category, ideas = ideas)
                    }
                }
            }
        }
    )
}

@Composable
fun IdeaCategoryCard(category: String, ideas: List<String>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = category,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            ideas.forEach { idea ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Build,
                        contentDescription = "Idea",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = idea,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "ideas") {
        composable("ideas") {
            IdeasScreen(onBackClick = { navController.popBackStack() })
        }
    }
}

@Composable
fun AppContent() {
    val navController = rememberNavController()
    NavGraph(navController = navController)
}
