package com.example.voxel_review.ui.screens.profile.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.theme.VoxelSecondary
import com.example.voxel_review.ui.theme.VoxelBackgroundDark

@Composable
fun ButtonFollow(
    buttonFollowPressed: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
){
    Button(
        modifier = modifier.testTag("btnFollow"),
        onClick = {
            buttonFollowPressed()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = VoxelSecondary,
            contentColor = VoxelBackgroundDark
        ),
        shape = RoundedCornerShape(16.dp),

    ) {
        Text(
            text = text
        )
    }
}