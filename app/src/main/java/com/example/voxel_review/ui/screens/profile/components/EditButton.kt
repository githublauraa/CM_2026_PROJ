package com.example.voxel_review.ui.screens.profile.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.theme.secondaryContainerLight
import com.example.voxel_review.ui.theme.onSecondaryContainerLight

@Composable
fun EditButton(onEditProfileClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onEditProfileClick,
        modifier = modifier.heightIn(min = 48.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = secondaryContainerLight,
            contentColor = onSecondaryContainerLight
        )
    ) {
        Icon(Icons.Outlined.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
        Spacer(Modifier.width(6.dp))
        Text("Editar perfil", fontWeight = FontWeight.Bold)
    }
}
