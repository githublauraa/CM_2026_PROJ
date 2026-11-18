package com.example.voxel_review.data.localization

import android.content.Context
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject

class AppStrings @Inject constructor(@param:ApplicationContext private val context: Context) {
    operator fun get(@StringRes id: Int): String =
        ContextCompat.getString(context, id)

    fun format(@StringRes id: Int, vararg arguments: Any): String =
        ContextCompat.getContextForLanguage(context).getString(id, *arguments)
}
