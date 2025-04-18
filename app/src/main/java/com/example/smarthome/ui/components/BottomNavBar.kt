package com.example.smarthome.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomNavBar(navController: NavController, appColor: Color) {
    val items = listOf("things", "favourites", "routines", "ideas", "settings")
    val titles = listOf("Things", "Favorites", "Routines", "Ideas", "Settings")

    val iconMap = mapOf(
        "favourites" to Icons.Default.Star,
        "things" to Icons.Default.Home,
        "routines" to Icons.Default.Refresh,
        "ideas" to Icons.Default.Build,
        "settings" to Icons.Default.Settings
    )

    NavigationBar(
        containerColor = Color(0xFFF8F8F8)
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEachIndexed { index, item ->
            val isSelected = currentRoute == item
            val icon = iconMap[item] ?: Icons.Default.Star

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = titles[index],
                        tint = if (isSelected) appColor else Color.Gray,
                        modifier = Modifier.size(30.dp)
                    )
                },
                label = {
                    Text(
                        text = titles[index],
                        fontSize = 11.sp,
                        color = if (isSelected) appColor else Color.Black
                    )
                },
                selected = isSelected,
                onClick = {
                    navController.navigate(item) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    val navController = rememberNavController()
    val appColor = Color(0xFFFFD700)
    BottomNavBar(navController = navController, appColor = appColor)
}
