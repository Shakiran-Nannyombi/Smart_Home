package com.example.smarthome.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.smarthome.R // Import your resources

@Composable
fun BottomNavBar(navController: NavController, appColor: Color) {
    val items = listOf("favourites","things", "routines", "ideas", "settings")
    val titles = listOf("Favorites","Things", "Routines", "Ideas", "Settings")

    NavigationBar(
        containerColor = Color(0xFFF8F8F8)
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEachIndexed { index, item ->
            val isSelected = currentRoute == item

            NavigationBarItem(
                icon = {
                    when (item) {
                        "things" -> Icon(
                            painter = painterResource(id = R.drawable.thingsdefault), // Use PNG for "Things"
                            contentDescription = "Things",
                            tint = if (isSelected) appColor else Color.Black,
                            modifier = Modifier.size(25.dp)
                        )
                        "favourites" -> Icon(
                            imageVector = Icons.Filled.Star, // Material Icon for "Favorites"
                            contentDescription = "Favorites",
                            tint = if (isSelected) appColor else Color.Black,
                            modifier = Modifier.size(25.dp)
                        )
                        "routines" -> Icon(
                            imageVector = Icons.Filled.Refresh, // Material Icon for "Routines"
                            contentDescription = "Routines",
                            tint = if (isSelected) appColor else Color.Black,
                            modifier = Modifier.size(25.dp)
                        )
                        "ideas" -> Icon(
                            painter = painterResource(id = R.drawable.ideas), // Material Icon for "Ideas"
                            contentDescription = "Ideas",
                            tint = if (isSelected) appColor else Color.Black,
                            modifier = Modifier.size(25.dp)
                        )
                        "settings" -> Icon(
                            painter = painterResource(id = R.drawable.settings), // Use PNG for "Settings"
                            contentDescription = "Settings",
                            tint = if (isSelected) appColor else Color.Black,
                            modifier = Modifier.size(25.dp)
                        )

                    }
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
