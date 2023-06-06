package com.anshtya.weatherapp.data.location

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.anshtya.weatherapp.domain.location.LocationTracker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationTrackerImpl @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application
) : LocationTracker {
    override suspend fun getCurrentLocation(): Location? {
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val priority = if(hasFineLocationPermission) {
            Priority.PRIORITY_HIGH_ACCURACY
        } else {
            Priority.PRIORITY_BALANCED_POWER_ACCURACY
        }

        return suspendCancellableCoroutine { continuation ->
            locationClient.getCurrentLocation(
                priority,
                CancellationTokenSource().token
            ).apply {
                addOnSuccessListener {
                    continuation.resume(it)
                }
                addOnFailureListener {
                    continuation.resume(null)
                }
                addOnCanceledListener {
                    continuation.cancel()
                }
            }
        }
    }
}