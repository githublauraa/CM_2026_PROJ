package com.example.voxel_review.ui

import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.voxel_review.ui.utils.SpoilerProtectedText
import org.junit.Rule
import org.junit.Test

class SpoilerProtectedTextTest {
    @get:Rule val compose = createAndroidComposeRule<ComponentActivity>()

    @Test fun spoilerHiddenUntilExplicitlyRevealedAndCanBeHiddenAgain() {
        compose.setContent { MaterialTheme { SpoilerProtectedText("one", "Secret ending", true) } }
        compose.onNodeWithText("Secret ending").assertDoesNotExist()
        compose.onNodeWithTag("toggleSpoiler_one").performClick()
        compose.onNodeWithText("Secret ending").assertIsDisplayed()
        compose.onNodeWithTag("toggleSpoiler_one").performClick()
        compose.onNodeWithText("Secret ending").assertDoesNotExist()
    }

    @Test fun changingReviewOrContentResetsRevealState() {
        val id = mutableStateOf("one")
        val text = mutableStateOf("First ending")
        compose.setContent { MaterialTheme { SpoilerProtectedText(id.value, text.value, true) } }
        compose.onNodeWithTag("toggleSpoiler_one").performClick()
        compose.onNodeWithText("First ending").assertIsDisplayed()
        compose.runOnIdle { id.value = "two" }
        compose.onNodeWithText("First ending").assertDoesNotExist()
        compose.onNodeWithTag("toggleSpoiler_two").performClick()
        compose.runOnIdle { text.value = "Updated ending" }
        compose.onNodeWithText("Updated ending").assertDoesNotExist()
    }

    @Test fun unmarkedReviewIsVisibleWithoutAnExtraAction() {
        compose.setContent { MaterialTheme { SpoilerProtectedText("legacy", "No spoilers", false) } }
        compose.onNodeWithText("No spoilers").assertIsDisplayed()
        compose.onNodeWithTag("toggleSpoiler_legacy").assertDoesNotExist()
    }
}
