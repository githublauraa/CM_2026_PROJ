package com.example.voxel_review.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.VoxelAccentPurple
import com.example.voxel_review.ui.theme.VoxelAccentYellow
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSecondary
import com.example.voxel_review.ui.theme.Voxel_ReviewTheme

enum class AccentOption(val color: Color) {
    CYAN(VoxelPrimary),
    PINK(VoxelSecondary),
    AQUA(Color(0xFF10E7CD)),
    YELLOW(VoxelAccentYellow),
    PURPLE(VoxelAccentPurple)
}

@Composable
fun SettingsRoute(
    darkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    var notifications by rememberSaveable { mutableStateOf(true) }
    var accentName by rememberSaveable { mutableStateOf(AccentOption.CYAN.name) }

    SettingsScreen(
        darkMode = darkMode,
        notificationsEnabled = notifications,
        selectedAccent = AccentOption.valueOf(accentName),
        onDarkModeChange = onDarkModeChange,
        onNotificationsChange = { notifications = it },
        onAccentChange = { accentName = it.name },
        modifier = modifier
    )
}

@Composable
fun SettingsScreen(
    darkMode: Boolean,
    notificationsEnabled: Boolean,
    selectedAccent: AccentOption,
    onDarkModeChange: (Boolean) -> Unit,
    onNotificationsChange: (Boolean) -> Unit,
    onAccentChange: (AccentOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Configuración",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Image(
                painter = painterResource(R.drawable.settings),
                contentDescription = "Configuración",
                modifier = Modifier.size(25.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
        }

        Spacer(Modifier.height(2.dp))
        SectionTitle("AJUSTES GENERALES")
        SettingSwitch(
            symbol = "☾",
            title = "Modo Oscuro",
            checked = darkMode,
            accentColor = VoxelPrimary,
            onCheckedChange = onDarkModeChange
        )
        SettingSwitch(
            iconRes = R.drawable.campana_notificaciones,
            title = "Notificaciones",
            checked = notificationsEnabled,
            accentColor = VoxelSecondary,
            onCheckedChange = onNotificationsChange
        )

        Spacer(Modifier.height(4.dp))
        SectionTitle("PREFERENCIAS GAMER")
        SettingsCard {
            Text(
                text = "Cambiar color de tema de la aplicación:",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 13.sp
            )
            Spacer(Modifier.height(18.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                AccentOption.entries.forEach { option ->
                    AccentCircle(
                        option = option,
                        selected = option == selectedAccent,
                        onClick = { onAccentChange(option) }
                    )
                }
            }
        }

        SettingsCard {
            Text(
                text = "Términos de servicio y privacidad",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Voxel Review Versión 1.4.2 · Hecho por y para apasionados del gaming",
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.55f),
                fontSize = 11.sp,
                lineHeight = 15.sp
            )
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun SettingSwitch(
    symbol: String? = null,
    iconRes: Int? = null,
    title: String,
    checked: Boolean,
    accentColor: Color,
    onCheckedChange: (Boolean) -> Unit
) {
    SettingsCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (iconRes != null) {
                Image(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp),
                    colorFilter = ColorFilter.tint(accentColor)
                )
            } else {
                Text(text = symbol.orEmpty(), color = accentColor, fontSize = 22.sp)
            }
            Spacer(Modifier.width(12.dp))
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.background,
                    checkedTrackColor = accentColor,
                    uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    uncheckedTrackColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    }
}

@Composable
private fun AccentCircle(
    option: AccentOption,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(38.dp)
            .then(
                if (selected) Modifier.border(3.dp, MaterialTheme.colorScheme.onSurface, CircleShape)
                else Modifier
            )
            .padding(if (selected) 4.dp else 0.dp)
            .background(option.color, CircleShape)
            .clickable(onClick = onClick)
    )
}

@Composable
private fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp), content = content)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F0D1F)
@Composable
private fun SettingsScreenPreview() {
    Voxel_ReviewTheme(darkTheme = true, dynamicColor = false) {
        var darkMode by rememberSaveable { mutableStateOf(true) }
        SettingsRoute(darkMode = darkMode, onDarkModeChange = { darkMode = it })
    }
}

