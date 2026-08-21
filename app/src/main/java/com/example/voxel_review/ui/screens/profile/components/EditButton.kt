package com.example.voxel_review.ui.screens.profile.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSurface

@Composable
fun EditButton(
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            containerColor = VoxelSurface
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .padding(vertical = 16.dp)
            .border(
                width = 2.dp,
                color = VoxelPrimary,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth(0.9f)
    ) {
        Text(
            text = "Editar Perfil",
            color = VoxelPrimary,
            fontWeight = FontWeight.Bold
        )
    }
}