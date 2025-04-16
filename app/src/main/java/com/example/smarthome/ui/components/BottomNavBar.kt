package com.example.smarthome.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf( "things","favourites", "routines", "ideas", "settings")
    val titles = listOf("Things", "Favorites", "Routines", "Ideas", "Settings")

    val iconMap = mapOf(
        "favourites" to Icons.Default.Star,
        "things" to Icons.Default.Home,
        "routines" to Icons.Default.Refresh,
        "ideas" to Icons.Default.Build,
        "settings" to Icons.Default.Settings
    )

    BottomNavigation(
        backgroundColor = Color.White
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEachIndexed { index, item ->
            val isSelected = currentRoute == item
            val icon = iconMap[item] ?: Icons.Default.Star

            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = titles[index],
                        tint = if (isSelected)  Color(0xFFFFD700) else Color.Gray,
                        modifier = Modifier.size(30.dp)
                    )
                },
                label = {
                    Text(
                        text = titles[index],
                        fontSize = 11.sp,
                        color = if (isSelected) Color.Blue else Color.Black
                    )
                },
                selected = isSelected,
                onClick = {
                    // Example: Allow only navigation for specific tabs
                    if (item !in listOf( "ideas", "settings")) {
                        navController.navigate(item) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
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
    BottomNavBar(navController = navController)
}
