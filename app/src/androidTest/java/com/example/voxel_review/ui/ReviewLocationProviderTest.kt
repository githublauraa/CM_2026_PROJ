package com.example.voxel_review.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.example.voxel_review.data.dataSource.services.ReviewLocationProvider
import com.example.voxel_review.data.dataSource.services.isRecentReviewLocation
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Assume.assumeTrue
import org.junit.Rule
import org.junit.Test

/** Run explicitly while the host supplies emulator GPS fixes. Never writes reviews. */
class ReviewLocationProviderTest {
    @get:Rule val compose = createAndroidComposeRule<ComponentActivity>()
    @Test fun receivesRecentEmulatorLocationWithCurrentUserPermissions() = runBlocking {
        assumeTrue(InstrumentationRegistry.getArguments().getString("locationCheck") == "true")
        compose.waitForIdle()
        val fix = ReviewLocationProvider(compose.activity).getLocation()
        assertNotNull("No location delivered by Android or Play services", fix)
        assertTrue(isRecentReviewLocation(fix!!))
        // Approximate permission deliberately allows Android to obfuscate the injected coordinates.
        assertEquals(4.60971, fix.latitude, 0.2)
        assertEquals(-74.08175, fix.longitude, 0.2)
    }
}
