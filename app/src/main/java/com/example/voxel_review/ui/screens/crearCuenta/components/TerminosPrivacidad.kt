package com.example.voxel_review.ui.screens.crearCuenta.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.VoxelBackgroundDark
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.onSurfaceVariantDark

@Composable
fun TerminosPrivacidad(
    aceptado: Boolean,
    onAceptadoChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 48.dp)
            .testTag("chkTerminos")
            .toggleable(value = aceptado, role = Role.Checkbox, onValueChange = onAceptadoChange),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = aceptado,
            onCheckedChange = null,
            colors = CheckboxDefaults.colors(
                checkedColor = VoxelPrimary,
                uncheckedColor = onSurfaceVariantDark,
                checkmarkColor = VoxelBackgroundDark
            )
        )
        Text(
            text = stringResource(R.string.register_accept_terms),
            style = MaterialTheme.typography.bodySmall,
            color = onSurfaceVariantDark
        )
    }
}
