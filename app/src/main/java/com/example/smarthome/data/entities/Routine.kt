package com.example.smarthome.data.entities
import androidx.room.Entity
import androidx.room.PrimaryKey

// Define the entity for the "routines" table
@Entity(tableName = "routines")
data class Routine(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val taskName: String,
    val time: String,
    val recurrence: String
)
