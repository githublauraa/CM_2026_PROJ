package com.example.voxel_review.ui.screens.start.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.utils.AuthSecondaryButton
import com.example.voxel_review.ui.utils.AuthSubmitButton

@Composable
fun LoginButtons(
    logginButtonPressed: () -> Unit,
    createAccountButtonPressed: () -> Unit,
    loading: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        AuthSubmitButton(
            label = stringResource(R.string.login_sign_in),
            loading = loading,
            onClick = logginButtonPressed,
            modifier = Modifier.testTag("btnIniciarSesion")
        )
        AuthSecondaryButton(
            label = stringResource(R.string.login_create_account),
            onClick = createAccountButtonPressed,
            modifier = Modifier.testTag("btnCrearCuenta")
        )
    }
}
