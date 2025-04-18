package com.example.smarthome.data

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension property for DataStore
val Context.settingsDataStore by preferencesDataStore(name = "settings")

// Save a string value
suspend fun DataStore<Preferences>.putString(key: Preferences.Key<String>, value: String) {
    this.edit { preferences ->
        preferences[key] = value
    }
}

// Save a boolean value
suspend fun DataStore<Preferences>.putBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
    this.edit { preferences ->
        preferences[key] = value
    }
}

// Save a color value (serialized as ARGB string)
suspend fun DataStore<Preferences>.putColor(key: Preferences.Key<String>, value: Color) {
    this.edit { preferences ->
        preferences[key] = value.toArgb().toString()
    }
}

// Retrieve a string value
fun DataStore<Preferences>.getString(
    key: Preferences.Key<String>,
    defaultValue: String
): Flow<String> {
    return this.data.map { preferences ->
        preferences[key] ?: defaultValue
    }
}

// Retrieve a boolean value
fun DataStore<Preferences>.getBoolean(
    key: Preferences.Key<Boolean>,
    defaultValue: Boolean
): Flow<Boolean> {
    return this.data.map { preferences ->
        preferences[key] ?: defaultValue
    }
}

// Retrieve a color value (deserialized from ARGB string)
fun DataStore<Preferences>.getColor(
    key: Preferences.Key<String>,
    defaultValue: Color
): Flow<Color> {
    return this.data.map { preferences ->
        val colorValue = preferences[key]?.toIntOrNull() ?: defaultValue.toArgb()
        Color(colorValue)
    }
}