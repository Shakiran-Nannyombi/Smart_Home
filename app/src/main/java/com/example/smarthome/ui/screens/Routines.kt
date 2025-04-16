package com.example.smarthome.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarthome.data.dao.RoutineDao
import com.example.smarthome.data.entities.Routine
import com.example.smarthome.ui.components.EmptyRoutinesView
import com.example.smarthome.ui.components.RoutineList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutinesScreen(
    routineDao: RoutineDao
) {
    val coroutineScope = rememberCoroutineScope()
    val routines by routineDao.getAllRoutines().observeAsState(initial = emptyList())

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
                onClick = {
                    coroutineScope.launch {
                        try {
                            routineDao.insertRoutine(
                                Routine(taskName = "New Task", time = "08:00 AM", recurrence = "Daily")
                            )
                        } catch (e: Exception) {
                            Log.e("RoutineInsert", "Insert error: ${e.localizedMessage}")
                        }
                    }
                },
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
                        try {
                            routineDao.deleteRoutine(routine)
                        } catch (e: Exception) {
                            Log.e("RoutineDelete", "Delete error: ${e.localizedMessage}")
                        }
                    }
                },
                modifier = Modifier.padding(padding)
            )
        }
    }
}