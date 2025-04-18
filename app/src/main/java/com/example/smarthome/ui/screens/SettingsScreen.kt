package com.example.smarthome.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.smarthome.data.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    val appColor = viewModel.appColor.collectAsState(initial = Color(0xFFFFD700)).value
    val userName = viewModel.userName.collectAsState(initial = "").value
    val userEmail = viewModel.userEmail.collectAsState(initial = "").value

    // State to hold the profile image URI
    var profileImageUri by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri?.toString()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets(0),
                title = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                    Text(
                        text = "My Smart Home",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ) }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = appColor
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(
                    start = padding.calculateStartPadding(LayoutDirection.Ltr),
                    top = padding.calculateTopPadding(),
                    end = padding.calculateEndPadding(LayoutDirection.Ltr)
                )
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            SettingsSection(title = "User Settings") {
                UserSettingsSection(
                    profileImage = profileImageUri,
                    onProfileImageClick = {
                        launcher.launch("image/*") // Open image picker
                    },
                    name = userName,
                    onNameChange = { viewModel.updateUserName(it) },
                    email = userEmail,
                    onEmailChange = { viewModel.updateUserEmail(it) }
                )
            }

            // App Appearance Section
            SettingsSection(title = "App Settings") {
                ColorPaletteSetting(
                    selectedColor = viewModel.appColor.collectAsState(initial = Color(0xFF6200EE)).value,
                    onColorSelected = { viewModel.updateAppColor(it) }
                )

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )

                Spacer(modifier = Modifier.height(20.dp))

                SwitchSettingItem(
                    title = "Auto Arm Security Alarm",
                    isChecked = viewModel.isAutoArmEnabled.collectAsState(initial = false).value,
                    onCheckedChange = { viewModel.updateAutoArmSetting(it) }
                )

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )

                Spacer(modifier = Modifier.height(20.dp))

                SwitchSettingItem(
                    title = "App Notifications",
                    isChecked = viewModel.isNotificationsEnabled.collectAsState(initial = false).value,
                    onCheckedChange = { viewModel.updateNotificationsSetting(it) }
                )
            }

            SettingsSection(title = "Voice Assistants") {
                SwitchSettingItem(
                    title = "Google Assistant",
                    isChecked = viewModel.isGoogleAssistantEnabled.collectAsState(initial = false).value,
                    onCheckedChange = { viewModel.updateGoogleAssistantSetting(it) }
                )
            }

            SettingsSection(title = "App Permissions") {
                SwitchSettingItem(
                    title = "Location Access",
                    isChecked = viewModel.isLocationAccessEnabled.collectAsState(initial = false).value,
                    onCheckedChange = { viewModel.updateLocationAccess(it) }
                )

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )

                Spacer(modifier = Modifier.height(20.dp))

                SwitchSettingItem(
                    title = "Camera Access",
                    isChecked = viewModel.isCameraAccessEnabled.collectAsState(initial = false).value,
                    onCheckedChange = { viewModel.updateCameraAccess(it) }
                )

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )

                Spacer(modifier = Modifier.height(20.dp))

                SwitchSettingItem(
                    title = "Microphone Access",
                    isChecked = viewModel.isMicrophoneAccessEnabled.collectAsState(initial = false).value,
                    onCheckedChange = { viewModel.updateMicrophoneAccess(it) }
                )
            }

        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp) // Add vertical spacing between sections
    ) {
        // Title inside a card
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF8F8F8) // Off-white background color
            ),
           modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(16.dp),
                color = Color.Gray,
                fontSize = 20.sp
            )
        }

        // Content outside the card
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            content()
        }
    }
}

@Composable
private fun SettingItem(
    title: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp), // Directly on the app's background
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            modifier = Modifier.weight(1f),
            textStyle = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun SwitchSettingItem(
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            enabled = enabled
        )
    }
}

@Composable
private fun ColorPaletteSetting(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit
) {
    val colors = listOf(
        Color(0xFFFFD700),
        Color(0xFF6200EE), // Purple
        Color(0xFF03DAC6), // Teal
        Color(0xFF018786), // Dark Teal
        Color(0xFFBB86FC), // Light Purple
        Color(0xFF03A9F4), // Light Blue
        Color(0xFFE91E63)  // Pink
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "App Color",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            colors.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color)
                        .border(
                            width = if (color == selectedColor) 3.dp else 0.dp,
                            color = Color.Gray,
                            shape = CircleShape
                        )
                        .clickable { onColorSelected(color) }
                )
            }
        }
    }
}

@Composable
private fun UserSettingsSection(
    profileImage: String?, // URL or null for the profile image
    onProfileImageClick: () -> Unit, // Callback for when the profile image is clicked
    name: String,
    onNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Image
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Color.Gray) // Placeholder background color
                .clickable { onProfileImageClick() }, // Handle image click
            contentAlignment = Alignment.Center
        ) {
            if (profileImage != null) {
                // Load the profile image using Coil
                Image(
                    painter = rememberAsyncImagePainter(profileImage),
                    contentDescription = "Profile Image",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                // Placeholder icon for no image
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Add Profile Image",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp) // Adjust icon size
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp)) // Space between image and text fields

        // Name and Email Fields
        Column(
            modifier = Modifier.weight(1f)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { newValue -> onNameChange(newValue) },
                label = { Text("Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.Start // Force left-to-right text direction
                )
            )

            Spacer(modifier = Modifier.height(8.dp)) // Space between fields

            OutlinedTextField(
                value = email,
                onValueChange = { newValue -> onEmailChange(newValue) },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodySmall.copy(
                    textAlign = TextAlign.Start // Force left-to-right text direction
                )
            )
        }
    }
}