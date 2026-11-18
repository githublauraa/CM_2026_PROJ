package com.example.voxel_review.ui.screens.chat.conversation.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun ChatPhotoMessage(
    path: String,
    loadPhoto: suspend (String) -> Result<Bitmap>,
    modifier: Modifier = Modifier
) {
    var attempt by remember(path) { mutableIntStateOf(0) }
    val result by produceState<Result<Bitmap>?>(null, path, attempt) {
        value = null
        value = loadPhoto(path)
    }
    var expanded by remember(path) { mutableStateOf(false) }
    val image = result?.getOrNull()

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        when {
            image != null -> Image(
                bitmap = image.asImageBitmap(),
                contentDescription = "Abrir foto del chat",
                modifier = Modifier.widthIn(max = 240.dp).heightIn(max = 250.dp)
                    .clickable(onClickLabel = "Ampliar foto") { expanded = true }
            )
            result == null -> CircularProgressIndicator(
                modifier = Modifier.size(26.dp),
                color = Color(0xFF352B5D),
                strokeWidth = 2.dp
            )
            else -> TextButton(onClick = { attempt++ }) {
                Text("Reintentar cargar la foto", color = Color(0xFF352B5D))
            }
        }
    }

    if (expanded && image != null) {
        Dialog(onDismissRequest = { expanded = false }) {
            Image(image.asImageBitmap(), contentDescription = "Foto ampliada")
        }
    }
}
