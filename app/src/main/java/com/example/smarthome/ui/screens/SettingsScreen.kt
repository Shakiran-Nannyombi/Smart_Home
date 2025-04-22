package com.example.smarthome.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
    val viewModelName = viewModel.userName.collectAsState(initial = "").value
    val viewModelEmail = viewModel.userEmail.collectAsState(initial = "").value

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
                        )
                    }
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
                        launcher.launch("image/*")
                    },
                    name = viewModelName,
                    onNameChange = { viewModel.updateUserName(it) }, // Update ViewModel directly
                    email = viewModelEmail,
                    onEmailChange = { viewModel.updateUserEmail(it) } // Update ViewModel directly
                )
            }

            SettingsSection(title = "App Settings") {
                ColorPaletteSetting(
                    selectedColor = appColor,
                    onColorSelected = { viewModel.updateAppColor(it) }
                )
                DividerSection()
                SwitchSettingItem(
                    title = "Auto Arm Security Alarm",
                    isChecked = viewModel.isAutoArmEnabled.collectAsState(initial = false).value,
                    onCheckedChange = { viewModel.updateAutoArmSetting(it) }
                )
                DividerSection()
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
                DividerSection()
                SwitchSettingItem(
                    title = "Camera Access",
                    isChecked = viewModel.isCameraAccessEnabled.collectAsState(initial = false).value,
                    onCheckedChange = { viewModel.updateCameraAccess(it) }
                )
                DividerSection()
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
            .padding(vertical = 8.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF8F8F8)
            ),
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp),
                color = Color.Gray,
                fontSize = 20.sp
            )
        }

        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            content()
        }
    }
}

@Composable
private fun DividerSection() {
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 2.dp,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
    )
    Spacer(modifier = Modifier.height(20.dp))
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
        Color(0xFF6200EE),
        Color(0xFF03DAC6),
        Color(0xFF018786),
        Color(0xFFBB86FC),
        Color(0xFF03A9F4),
        Color(0xFFE91E63)
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
    profileImage: String?,
    onProfileImageClick: () -> Unit,
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
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .clickable { onProfileImageClick() }
        ) {
            if (profileImage != null) {
                Image(
                    painter = rememberAsyncImagePainter(profileImage),
                    contentDescription = "Profile Image",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Default Profile",
                    modifier = Modifier
                        .size(72.dp)
                        .align(Alignment.Center),
                    tint = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text("Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}