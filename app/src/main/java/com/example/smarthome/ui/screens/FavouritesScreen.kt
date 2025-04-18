package com.example.smarthome.ui.screens

import android.app.TimePickerDialog
import android.content.Context
import android.view.ContextThemeWrapper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarthome.R
import com.example.smarthome.data.entities.Routine
import com.example.smarthome.data.viewmodel.RoutineViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(viewModel: RoutineViewModel) {
    // Observe routines from the database using readAllData
    val allRoutines by viewModel.readAllData.observeAsState(initial = emptyList())
    val favoriteRoutines = remember { mutableStateListOf<Routine>() }

    // State to show/hide the dialog
    var showDialog by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) } // State to toggle edit mode
    val time = remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets(0),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Box(
                        modifier = Modifier.padding(start = 80.dp)
                    ) {
                        Text(
                            text = "My Smart Home",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { isEditing = !isEditing }) { // Toggle edit mode
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            if (!isEditing) { // Show FAB only when not in edit mode
                FloatingActionButton(
                    onClick = { showDialog = true }, // Show the dialog to add favorites
                    containerColor = Color(0xFF0077B6),
                    shape = CircleShape,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = Color.White
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (favoriteRoutines.isEmpty()) {
                // Show empty state
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.stardefault),
                        contentDescription = "Star Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
                    )

                    Spacer(modifier = Modifier.height(0.dp))

                    Text(
                        text = "No Favorites!",
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Add your favorite routines for easy\naccess here.",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 20.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Tap the '+' button below to add your\nfavorite routines.",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 20.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                // Show list of favorite routines
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(favoriteRoutines) { routine ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .background(MaterialTheme.colorScheme.surface)
                                .clickable(enabled = isEditing) { // Enable click only in edit mode
                                    favoriteRoutines.remove(routine) // Remove the routine
                                },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = routine.taskName,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            if (isEditing) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Delete",
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.clickable {
                                        favoriteRoutines.remove(routine) // Remove the routine
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        // Dialog to select routines
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Add Favorite Routines") },
                text = {
                    LazyColumn {
                        items(allRoutines) { routine ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        if (!favoriteRoutines.contains(routine)) {
                                            favoriteRoutines.add(routine)
                                        }
                                    }
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = routine.taskName)
                                if (favoriteRoutines.contains(routine)) {
                                    Text(text = "✓", color = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Done")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
