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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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
    routineViewModel: RoutineViewModel = viewModel(),
    appColor: Color // Add appColor as a parameter
) {
    val coroutineScope = rememberCoroutineScope()
    val routines by routineViewModel.readAllData.observeAsState(initial = emptyList())

    val showDialog = remember { mutableStateOf(false) }
    val showUpdateDialog = remember { mutableStateOf(false) }

    val updateTaskName = remember { mutableStateOf("") }
    val updateTime = remember { mutableStateOf("") }
    val updateRecurrence = remember { mutableStateOf("") }

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
                onClick = { showDialog.value = true },
                containerColor = appColor, // Use appColor here
                shape = CircleShape,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Routine",
                    tint = Color.White
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
                    updateTaskName.value = routine.taskName
                    updateTime.value = routine.time
                    updateRecurrence.value = routine.recurrence
                    showUpdateDialog.value = true
                },
                modifier = Modifier.padding(padding)
            )
        }

        if (showDialog.value) {
            AddRoutineDialog(
                onDismiss = { showDialog.value = false },
                onAdd = { newRoutine ->
                    coroutineScope.launch {
                        if (newRoutine.taskName.isBlank() || newRoutine.time.isBlank() || newRoutine.recurrence.isBlank()) {
                            Log.e("RoutinesScreen", "All fields must be filled!")
                            return@launch
                        }
                        routineViewModel.addRoutine(newRoutine)
                        showDialog.value = false
                    }
                },
                appColor = appColor // Pass appColor to AddRoutineDialog
            )
        }

        if (showUpdateDialog.value) {
            UpdateRoutineDialog(
                routine = routineToUpdate,
                taskName = updateTaskName,
                time = updateTime,
                recurrence = updateRecurrence,
                onDismiss = { showUpdateDialog.value = false },
                onUpdate = { updatedRoutine ->
                    coroutineScope.launch {
                        routineViewModel.updateRoutine(updatedRoutine)
                    }
                    updateTaskName.value = ""
                    updateTime.value = ""
                    updateRecurrence.value = ""
                    showUpdateDialog.value = false
                },
                appColor = appColor // Pass appColor to UpdateRoutineDialog
            )
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun AddRoutineDialog(
    onDismiss: () -> Unit,
    onAdd: (Routine) -> Unit,
    appColor: Color
) {
    val taskName = remember { mutableStateOf("") }
    val time = remember { mutableStateOf("") }
    val recurrence = remember { mutableStateOf("") }
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Add New Routine") },
        text = {
            Column {
                OutlinedTextField(
                    value = taskName.value,
                    onValueChange = { taskName.value = it },
                    label = { Text("Task Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        val calendar = Calendar.getInstance()
                        val hour = calendar.get(Calendar.HOUR_OF_DAY)
                        val minute = calendar.get(Calendar.MINUTE)

                        val timePickerDialog = TimePickerDialog(
                            context,
                            { _, selectedHour, selectedMinute ->
                                val isPM = selectedHour >= 12
                                val formattedHour = if (selectedHour % 12 == 0) 12 else selectedHour % 12
                                val amPm = if (isPM) "PM" else "AM"
                                time.value = String.format("%02d:%02d %s", formattedHour, selectedMinute, amPm)
                            },
                            hour,
                            minute,
                            false
                        )

                        timePickerDialog.setOnShowListener {
                            val positiveButton = timePickerDialog.getButton(TimePickerDialog.BUTTON_POSITIVE)
                            val negativeButton = timePickerDialog.getButton(TimePickerDialog.BUTTON_NEGATIVE)
                            positiveButton.setTextColor(appColor.toArgb())
                            negativeButton.setTextColor(appColor.toArgb())
                        }

                        timePickerDialog.show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (time.value.isEmpty()) "Select Time" else "Time: ${time.value}")
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
                onAdd(
                    Routine(
                        taskName = taskName.value,
                        time = time.value,
                        recurrence = recurrence.value
                    )
                )
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}

@SuppressLint("DefaultLocale")
@Composable
fun UpdateRoutineDialog(
    routine: Routine?,
    taskName: MutableState<String>,
    time: MutableState<String>,
    recurrence: MutableState<String>,
    onDismiss: () -> Unit,
    onUpdate: (Routine) -> Unit,
    appColor: Color
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Update Routine") },
        text = {
            Column {
                OutlinedTextField(
                    value = taskName.value,
                    onValueChange = { taskName.value = it },
                    label = { Text("Task Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        val calendar = Calendar.getInstance()
                        val hour = calendar.get(Calendar.HOUR_OF_DAY)
                        val minute = calendar.get(Calendar.MINUTE)

                        val timePickerDialog = TimePickerDialog(
                            context,
                            { _, selectedHour, selectedMinute ->
                                val isPM = selectedHour >= 12
                                val formattedHour = if (selectedHour % 12 == 0) 12 else selectedHour % 12
                                val amPm = if (isPM) "PM" else "AM"
                                time.value = String.format("%02d:%02d %s", formattedHour, selectedMinute, amPm)
                            },
                            hour,
                            minute,
                            false
                        )

                        timePickerDialog.setOnShowListener {
                            val positiveButton = timePickerDialog.getButton(TimePickerDialog.BUTTON_POSITIVE)
                            val negativeButton = timePickerDialog.getButton(TimePickerDialog.BUTTON_NEGATIVE)
                            positiveButton.setTextColor(appColor.toArgb())
                            negativeButton.setTextColor(appColor.toArgb())
                        }

                        timePickerDialog.show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (time.value.isEmpty()) "Select Time" else "Time: ${time.value}")
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
                onUpdate(
                    routine?.copy(
                        taskName = taskName.value,
                        time = time.value,
                        recurrence = recurrence.value
                    ) ?: return@Button
                )
            }) {
                Text("Update")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}