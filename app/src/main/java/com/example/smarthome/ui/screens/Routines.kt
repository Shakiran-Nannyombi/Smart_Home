package com.example.smarthome.ui.screens

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthome.data.entities.Routine
import com.example.smarthome.data.viewmodel.RoutineViewModel
import com.example.smarthome.ui.components.EmptyRoutinesView
import com.example.smarthome.ui.components.RoutineList
import kotlinx.coroutines.launch
import java.util.Calendar

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutinesScreen(
    routineViewModel: RoutineViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val routines by routineViewModel.readAllData.observeAsState(initial = emptyList())
    LaunchedEffect(routines) {
        Log.d("RoutinesScreen", "Routines: $routines")
    }

    // State to control dialog visibility
    val showDialog = remember { mutableStateOf(false) }
    val showUpdateDialog = remember { mutableStateOf(false) }

    // States for user input
    val taskName = remember { mutableStateOf(TextFieldValue("")) }
    val time = remember { mutableStateOf(TextFieldValue("")) }
    val recurrence = remember { mutableStateOf(TextFieldValue("")) }

    // State for the routine being updated
    var routineToUpdate by remember { mutableStateOf<Routine?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets(0.dp),
                title = {
                    Text(
                        text = "My Smart Home",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                actions = {
                    IconButton(onClick = { /* Maybe add filter/sort later */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Filter",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog.value = true }, // Show dialog on FAB click
                containerColor = MaterialTheme.colorScheme.tertiary,
                shape = CircleShape,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Routine",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    ) { padding ->
        if (routines.isEmpty()) {
            EmptyRoutinesView()
        } else {
            RoutineList(
                routines = routines,
                onDeleteRoutine = { routine ->
                    coroutineScope.launch {
                        routineViewModel.deleteRoutine(routine)
                    }
                },
                onUpdateRoutine = { routine ->
                    routineToUpdate = routine
                    taskName.value = TextFieldValue(routine.taskName)
                    time.value = TextFieldValue(routine.time)
                    recurrence.value = TextFieldValue(routine.recurrence)
                    showUpdateDialog.value = true
                },
                modifier = Modifier.padding(padding)
            )
        }

        // Dialog for adding a new routine
        if (showDialog.value) {
            val context = LocalContext.current // Move this outside the Button's onClick
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text(text = "Add New Routine") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = taskName.value,
                            onValueChange = { taskName.value = it },
                            label = { Text("Task Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        // Time Picker Button
                        Button(
                            onClick = {
                                val calendar = Calendar.getInstance()
                                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                                val minute = calendar.get(Calendar.MINUTE)

                                // Show TimePickerDialog
                                TimePickerDialog(
                                    context,
                                    { _, selectedHour, selectedMinute ->
                                        // Update the time state with the selected time
                                        time.value = TextFieldValue(String.format("%02d:%02d", selectedHour, selectedMinute))
                                    },
                                    hour,
                                    minute,
                                    true // Use 24-hour format
                                ).show()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = if (time.value.text.isEmpty()) "Select Time" else "Time: ${time.value.text}"
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = recurrence.value,
                            onValueChange = { recurrence.value = it },
                            label = { Text("Recurrence") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        coroutineScope.launch {
                            // Validate input fields
                            if (taskName.value.text.isBlank() || time.value.text.isBlank() || recurrence.value.text.isBlank()) {
                                Log.e("RoutinesScreen", "All fields must be filled!")
                                return@launch
                            }

                            // Create a new routine with valid input
                            val newRoutine = Routine(
                                taskName = taskName.value.text,
                                time = time.value.text,
                                recurrence = recurrence.value.text
                            )

                            // Add the routine to the database
                            routineViewModel.addRoutine(newRoutine)
                            showDialog.value = false // Close the dialog
                        }
                    }) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        // Reset input fields and close the dialog
                        taskName.value = TextFieldValue("")
                        time.value = TextFieldValue("")
                        recurrence.value = TextFieldValue("")
                        showDialog.value = false
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }

        // Dialog for updating an existing routine
        if (showUpdateDialog.value) {
            AlertDialog(
                onDismissRequest = { showUpdateDialog.value = false },
                title = { Text(text = "Update Routine") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = taskName.value,
                            onValueChange = { taskName.value = it },
                            label = { Text("Task Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = time.value,
                            onValueChange = { time.value = it },
                            label = { Text("Time") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = recurrence.value,
                            onValueChange = { recurrence.value = it },
                            label = { Text("Recurrence") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                routineToUpdate?.let {
                                    routineViewModel.updateRoutine(
                                        it.copy(
                                            taskName = taskName.value.text,
                                            time = time.value.text,
                                            recurrence = recurrence.value.text
                                        )
                                    )
                                }
                            }
                            // Reset input fields and close the dialog
                            taskName.value = TextFieldValue("")
                            time.value = TextFieldValue("")
                            recurrence.value = TextFieldValue("")
                            showUpdateDialog.value = false
                        }
                    ) {
                        Text("Update")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        // Reset input fields and close the dialog
                        taskName.value = TextFieldValue("")
                        time.value = TextFieldValue("")
                        recurrence.value = TextFieldValue("")
                        showUpdateDialog.value = false
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}