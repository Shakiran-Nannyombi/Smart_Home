package com.example.smarthome.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.data.settingsDataStore
import com.example.smarthome.data.getColor
import com.example.smarthome.data.getBoolean
import com.example.smarthome.data.getString
import com.example.smarthome.data.putColor
import com.example.smarthome.data.putBoolean
import com.example.smarthome.data.putString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.Color
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStore = application.applicationContext.settingsDataStore

    // Keys for DataStore
    companion object {
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        private val APP_COLOR_KEY = stringPreferencesKey("app_color") // Use String for color
        private val AUTO_ARM_KEY = booleanPreferencesKey("auto_arm")
        private val NOTIFICATIONS_KEY = booleanPreferencesKey("notifications")
        private val GOOGLE_ASSISTANT_KEY = booleanPreferencesKey("google_assistant") // New key
        private val LOCATION_ACCESS_KEY = booleanPreferencesKey("location_access")
        private val CAMERA_ACCESS_KEY = booleanPreferencesKey("camera_access")
        private val MICROPHONE_ACCESS_KEY = booleanPreferencesKey("microphone_access")
    }

    // Expose Flows
    val userName: Flow<String> = dataStore.getString(USER_NAME_KEY, "")
    val userEmail: Flow<String> = dataStore.getString(USER_EMAIL_KEY, "")
    val appColor: Flow<Color> = dataStore.getColor(APP_COLOR_KEY, Color(0xFFFFD700)) // Default Color
    val isAutoArmEnabled: Flow<Boolean> = dataStore.getBoolean(AUTO_ARM_KEY, false)
    val isNotificationsEnabled: Flow<Boolean> = dataStore.getBoolean(NOTIFICATIONS_KEY, true)
    val isGoogleAssistantEnabled: Flow<Boolean> = dataStore.getBoolean(GOOGLE_ASSISTANT_KEY, false) // New flow
    val isLocationAccessEnabled: Flow<Boolean> = dataStore.getBoolean(LOCATION_ACCESS_KEY, false)
    val isCameraAccessEnabled: Flow<Boolean> = dataStore.getBoolean(CAMERA_ACCESS_KEY, false)
    val isMicrophoneAccessEnabled: Flow<Boolean> = dataStore.getBoolean(MICROPHONE_ACCESS_KEY, false)

    // Update methods
    fun updateUserName(name: String) {
        viewModelScope.launch {
            dataStore.putString(USER_NAME_KEY, name)
        }
    }

    fun updateUserEmail(email: String) {
        viewModelScope.launch {
            dataStore.putString(USER_EMAIL_KEY, email)
        }
    }

    fun updateAppColor(color: Color) {
        viewModelScope.launch {
            dataStore.putColor(APP_COLOR_KEY, color) // Use putColor for storing the color
        }
    }

    fun updateAutoArmSetting(enabled: Boolean) {
        viewModelScope.launch {
            dataStore.putBoolean(AUTO_ARM_KEY, enabled)
        }
    }

    fun updateNotificationsSetting(enabled: Boolean) {
        viewModelScope.launch {
            dataStore.putBoolean(NOTIFICATIONS_KEY, enabled)
        }
    }

    // New method for updating Google Assistant setting
    fun updateGoogleAssistantSetting(enabled: Boolean) {
        viewModelScope.launch {
            dataStore.putBoolean(GOOGLE_ASSISTANT_KEY, enabled)
        }
    }

    fun updateLocationAccess(enabled: Boolean) {
        viewModelScope.launch {
            dataStore.putBoolean(LOCATION_ACCESS_KEY, enabled)
        }
    }

    fun updateCameraAccess(enabled: Boolean) {
        viewModelScope.launch {
            dataStore.putBoolean(CAMERA_ACCESS_KEY, enabled)
        }
    }

    fun updateMicrophoneAccess(enabled: Boolean) {
        viewModelScope.launch {
            dataStore.putBoolean(MICROPHONE_ACCESS_KEY, enabled)
        }
    }
}
