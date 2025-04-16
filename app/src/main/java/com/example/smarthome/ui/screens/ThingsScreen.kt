package com.example.smarthome.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarthome.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThingsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets(0.dp), // Removes default padding
                title = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "My Smart Home",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle search action */ }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    IconButton(onClick = { /* Handle filter action */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Filter",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.thingsdefault),
                contentDescription = "Things Grid Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "No things!",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "It looks like we didn’t discover any devices.\nTry an option below",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(20.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 15.sp
            )

            HorizontalDivider(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp),
                horizontalAlignment = Alignment.Start
            ) {
                ActionItem("Run discovery", Icons.Filled.Search)
                ActionItem("Add a cloud account", Icons.Filled.Add)
                ActionItem("View our supported devices", painterResource(id = R.drawable.drag))
                ActionItem("Contact support", Icons.Filled.Email)
            }
        }
    }
}

@Composable
fun ActionItem(text: String, icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(MaterialTheme.colorScheme.tertiary, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(start = 12.dp),
            fontSize = 18.sp
        )
    }
}

@Composable
fun ActionItem(text: String, painter: androidx.compose.ui.graphics.painter.Painter) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(MaterialTheme.colorScheme.tertiary, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painter,
                contentDescription = text,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(start = 12.dp),
            fontSize = 18.sp
        )
    }
}
