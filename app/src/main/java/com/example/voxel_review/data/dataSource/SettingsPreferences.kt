package com.example.voxel_review.data.dataSource

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject

class SettingsPreferences @Inject constructor(@param:ApplicationContext context: Context) {
    private val preferences = context.getSharedPreferences("voxel_settings", Context.MODE_PRIVATE)

    fun getScreenColor(): String? = preferences.getString("settings_screen_color", null)

    fun saveScreenColor(color: String) {
        preferences.edit().putString("settings_screen_color", color).apply()
    }
}
