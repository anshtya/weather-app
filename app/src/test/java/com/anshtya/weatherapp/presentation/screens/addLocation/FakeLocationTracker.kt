package com.anshtya.weatherapp.presentation.screens.addLocation

import android.location.Location
import com.anshtya.weatherapp.domain.location.LocationTracker

class FakeLocationTracker: LocationTracker {

    override suspend fun getCurrentLocation(): Location {
        return Location("test")
    }
}