package com.example.voxel_review.ui.screens.writeReview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.theme.*

@Composable
fun ReviewText(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .height(145.dp)
                .background(
                    color = VoxelSurface,
                    shape = RoundedCornerShape(18.dp),
                ).border(
                    width = 1.dp,
                    color = VoxelSurfaceVariant,
                    shape = RoundedCornerShape(18.dp),
                ).padding(20.dp, 12.dp),
    ) {
        TextField(
            value = text,
            onValueChange = onTextChange,
            label = { Text("Escribe tu reseña aquí...") },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
            ),
            modifier = Modifier.fillMaxSize(),
        )
    }
}
