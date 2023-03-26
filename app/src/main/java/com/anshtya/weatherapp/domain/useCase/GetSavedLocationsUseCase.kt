package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.domain.model.WeatherLocation
import com.anshtya.weatherapp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedLocationsUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Flow<List<WeatherLocation>> = locationRepository.savedLocations()
}