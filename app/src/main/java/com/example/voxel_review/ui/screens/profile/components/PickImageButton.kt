package com.example.voxel_review.ui.screens.profile.components

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.theme.VoxelPrimary
import android.R.attr.shape
import androidx.compose.material3.CheckboxDefaults.colors
@Composable
fun pickImageButton(
    onImageSelected: (Uri) -> Unit,
    modifier: Modifier = Modifier
){
    val launcher = rememberLauncherForActivityResult (
        contract =
            ActivityResultContracts.GetContent()
    ){ uri: Uri? ->
        uri?.let {
            Log.d("ProfileScreen", uri.toString())
            onImageSelected(uri)
        }
    }
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = VoxelPrimary
        ),
        shape = RoundedCornerShape(16.dp),
        onClick = {
            launcher.launch("image/*")
        }
    ){
        Text(text = "Cambiar imagen")
    }
}