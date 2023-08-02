package com.anshtya.weatherapp.util

import android.content.Context
import android.content.IntentSender
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task

fun locationRequest(
    context: Context,
    onEnabled: () -> Unit,
    onDisabled: (IntentSenderRequest) -> Unit
) {
    val locationRequest = LocationRequest
        .Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, 0)
        .build()
    val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
    val client: SettingsClient = LocationServices.getSettingsClient(context)
    val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
    task.apply {
        addOnSuccessListener { onEnabled() }
        addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                try {
                    val intentSenderRequest = IntentSenderRequest
                        .Builder(exception.resolution)
                        .build()
                    onDisabled(intentSenderRequest)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }
}