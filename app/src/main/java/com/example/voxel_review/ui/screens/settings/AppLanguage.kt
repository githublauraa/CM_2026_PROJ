package com.example.voxel_review.ui.screens.settings

enum class AppLanguage(val tag: String, val label: String) {
    SPANISH("es", "Español"),
    ENGLISH("en", "English"),
    PORTUGUESE_BRAZIL("pt-BR", "Português (Brasil)");

    companion object {
        fun fromTag(tag: String?): AppLanguage =
            entries.firstOrNull { it.tag.equals(tag, ignoreCase = true) }
                ?: entries.firstOrNull { it.tag.substringBefore("-").equals(tag?.substringBefore("-"), ignoreCase = true) }
                ?: SPANISH
    }
}
