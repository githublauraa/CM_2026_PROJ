package com.example.voxel_review.ui.screens.chat.conversation.components

import com.example.voxel_review.R
import androidx.compose.ui.res.stringResource
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File

@Composable
fun ChatCameraButton(
    enabled: Boolean,
    onPhotoCaptured: (Uri) -> Unit,
    onError: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val camera_cleanup_errorText = stringResource(R.string.camera_cleanup_error)
    val camera_open_errorText = stringResource(R.string.camera_open_error)
    val camera_permission_errorText = stringResource(R.string.camera_permission_error)
    var pendingCapture by rememberSaveable { mutableStateOf<String?>(null) }

    val camera = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { captured ->
        val uri = pendingCapture?.let(Uri::parse)
        pendingCapture = null
        if (uri != null) {
            if (captured) {
                onPhotoCaptured(uri)
            } else {
                try {
                    context.contentResolver.delete(uri, null, null)
                } catch (_: Exception) {
                    onError(camera_cleanup_errorText)
                }
            }
        }
    }

    val openCamera = {
        var file: File? = null
        try {
            val folder = File(context.cacheDir, "chat-camera")
            folder.mkdirs()
            file = File.createTempFile("foto-", ".jpg", folder)
            val uri = FileProvider.getUriForFile(context, "${context.packageName}.chat.camera", file)
            pendingCapture = uri.toString()
            camera.launch(uri)
        } catch (_: Exception) {
            pendingCapture = null
            file?.delete()
            onError(camera_open_errorText)
        }
    }

    val permission = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            openCamera()
        } else {
            onError(camera_permission_errorText)
        }
    }

    IconButton(
        modifier = modifier,
        enabled = enabled && pendingCapture == null,
        onClick = {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                permission.launch(Manifest.permission.CAMERA)
            }
        }
    ) {
        Icon(
            Icons.Outlined.PhotoCamera,
            contentDescription = stringResource(R.string.camera_take_photo),
            tint = if (enabled) Color(0xFF352B5D) else Color(0xFF6A617E)
        )
    }
}
