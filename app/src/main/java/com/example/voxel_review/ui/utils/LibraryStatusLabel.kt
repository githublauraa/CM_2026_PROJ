package com.example.voxel_review.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.voxel_review.R
import com.example.voxel_review.data.info.library.LibraryStatus

@Composable
fun libraryStatusLabel(status: LibraryStatus): String = stringResource(
    when (status) {
        LibraryStatus.WANT_TO_PLAY -> R.string.library_want_to_play
        LibraryStatus.PLAYING -> R.string.library_playing
        LibraryStatus.COMPLETED -> R.string.library_completed
    }
)
