package com.example.voxel_review.ui.screens.settings


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.voxel_review.ui.theme.Voxel_ReviewTheme

@Composable
fun SettingsRoute(
    darkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    modifier: Modifier
) {
    var notifications by rememberSaveable {
        mutableStateOf(true)
    }

    var accentName by rememberSaveable {
        mutableStateOf(AccentOption.CYAN.name)
    }

    SettingsScreen(
        darkMode = darkMode,
        notificationsEnabled = notifications,
        selectedAccent = AccentOption.valueOf(accentName),
        onDarkModeChange = onDarkModeChange,
        onNotificationsChange = {
            notifications = it
        },
        onAccentChange = {
            accentName = it.name
        }
    )
}



