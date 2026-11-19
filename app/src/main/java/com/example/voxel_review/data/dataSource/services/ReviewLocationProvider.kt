package com.example.voxel_review.data.dataSource.services

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.CancellationSignal
import android.os.SystemClock
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.math.round
import kotlin.coroutines.resume

/** A recent fix is sufficient; requiring a timestamp after the tap rejects valid cached fixes. */
fun isRecentReviewLocation(location: Location, nowNanos: Long = SystemClock.elapsedRealtimeNanos()): Boolean {
    val age = nowNanos - location.elapsedRealtimeNanos
    return location.latitude.isFinite() && location.longitude.isFinite() &&
        location.latitude in -90.0..90.0 && location.longitude in -180.0..180.0 &&
        location.elapsedRealtimeNanos > 0 && age in 0..60_000_000_000L
}

class ReviewLocationProvider(context: Context) {
    private val context = context.applicationContext

    @SuppressLint("MissingPermission")
    suspend fun getLocation(): Location? {
        val permitted = listOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
            .any { ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED }
        if (!permitted) return null
        val manager = context.getSystemService(LocationManager::class.java) ?: return null
        if (!LocationManagerCompat.isLocationEnabled(manager)) return null

        // Run Play services and the framework provider together. A failure in one must not skip the other.
        return withTimeoutOrNull(22_000L) {
            channelFlow {
                launch {
                    val cancellation = CancellationTokenSource()
                    try {
                        val request = CurrentLocationRequest.Builder()
                            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                            .setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
                            .setMaxUpdateAgeMillis(60_000L)
                            .setDurationMillis(20_000L)
                            .build()
                        val location = LocationServices.getFusedLocationProviderClient(context)
                            .getCurrentLocation(request, cancellation.token).await()
                        if (location != null && isRecentReviewLocation(location)) send(location)
                    } catch (cancelled: CancellationException) {
                        throw cancelled
                    } catch (_: Exception) {
                        // The independent Android provider can still supply the fix.
                    } finally {
                        cancellation.cancel()
                    }
                }
                listOf(LocationManager.GPS_PROVIDER, LocationManager.NETWORK_PROVIDER)
                    .filter { manager.isProviderEnabled(it) }
                    .forEach { provider ->
                        launch {
                            try {
                                val location = suspendCancellableCoroutine<Location?> { continuation ->
                                    val signal = CancellationSignal()
                                    continuation.invokeOnCancellation { signal.cancel() }
                                    LocationManagerCompat.getCurrentLocation(
                                        manager, provider, signal, ContextCompat.getMainExecutor(context)
                                    ) { fix ->
                                        if (continuation.isActive) continuation.resume(fix)
                                    }
                                }
                                if (location != null && isRecentReviewLocation(location)) send(location)
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (_: Exception) {
                                // Play services or another enabled provider may still complete.
                            }
                        }
                    }
            }.firstOrNull()?.let { fix ->
                Location(fix).apply {
                    latitude = round(fix.latitude * 100.0) / 100.0
                    longitude = round(fix.longitude * 100.0) / 100.0
                }
            }
        }
    }
}
