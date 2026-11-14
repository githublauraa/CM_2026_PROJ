package com.example.voxel_review.ui.screens.profile.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.theme.secondaryContainerLight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun pickImageButton(onImageSelected: (Uri) -> Unit, modifier: Modifier = Modifier) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let(onImageSelected)
    }
    OutlinedButton(
        onClick = { launcher.launch("image/*") },
        modifier = modifier.heightIn(min = 48.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, secondaryContainerLight.copy(alpha = 0.45f)),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = secondaryContainerLight)
    ) {
        Icon(Icons.Outlined.PhotoCamera, contentDescription = null, modifier = Modifier.size(16.dp))
        Spacer(Modifier.width(6.dp))
        Text("Cambiar foto")
    }
}
