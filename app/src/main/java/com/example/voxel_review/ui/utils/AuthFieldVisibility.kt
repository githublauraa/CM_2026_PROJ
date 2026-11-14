package com.example.voxel_review.ui.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalDensity
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun authFieldVisibility(): Modifier {
    val requester = remember { BringIntoViewRequester() }
    val ime = WindowInsets.ime
    val density = LocalDensity.current
    var focused by remember { mutableStateOf(false) }

    LaunchedEffect(focused, ime, density) {
        if (focused) {
            snapshotFlow { ime.getBottom(density) }.collectLatest {
                withFrameNanos { }
                requester.bringIntoView()
            }
        }
    }

    return Modifier
        .bringIntoViewRequester(requester)
        .onFocusChanged { focused = it.isFocused }
}
