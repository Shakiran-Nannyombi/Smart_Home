package com.example.smarthome.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarthome.R
import com.example.smarthome.data.entities.Routine
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.Color

@Composable
fun EmptyRoutinesView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.routines),
                contentDescription = "No Routines",
                modifier = Modifier.size(200.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No Routines!",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Click the '+' button below to get started.",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun RoutineList(
    routines: List<Routine>,
    onDeleteRoutine: (Routine) -> Unit,
    onUpdateRoutine: (Routine) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(routines) { routine ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(
                        text = "Task Name: ${routine.taskName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black // Explicitly set text color to black
                    )
                    Text(
                        text = "Timing: ${routine.time}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black // Explicitly set text color to black
                    )
                    Text(
                        text = "Recurrence: ${routine.recurrence}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black // Explicitly set text color to black
                    )
                }
                IconButton(onClick = { onUpdateRoutine(routine) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Routine",
                        tint = Color(0xFF0077B6) // Icon color
                    )
                }
                IconButton(onClick = { onDeleteRoutine(routine) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Routine",
                        tint = Color(0xFF0077B6) // Icon color
                    )
                }
            }
        }
    }
}

@Composable
fun RoutineItem(routine: Routine, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = "Task Name: ${routine.taskName}", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Timing: ${routine.time}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Recurrence: ${routine.recurrence}")
        }
        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Routine",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}
