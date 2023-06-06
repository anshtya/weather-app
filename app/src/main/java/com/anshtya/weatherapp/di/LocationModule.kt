package com.anshtya.weatherapp.di

import com.anshtya.weatherapp.data.location.LocationTrackerImpl
import com.anshtya.weatherapp.domain.location.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {
    @Binds
    abstract fun bindLocationTracker(
        locationTrackerImpl: LocationTrackerImpl
    ): LocationTracker
}