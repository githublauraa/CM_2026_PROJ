package com.example.voxel_review.ui.screens.profile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.theme.errorDark

@Composable
fun ButtonLogOut(buttonLogOutPressed: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = buttonLogOutPressed,
        modifier = modifier.defaultMinSize(minHeight = 48.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = errorDark),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, errorDark.copy(alpha = 0.45f))
    ) {
        Icon(Icons.AutoMirrored.Outlined.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(8.dp))
        Text("Cerrar sesión")
    }
}
