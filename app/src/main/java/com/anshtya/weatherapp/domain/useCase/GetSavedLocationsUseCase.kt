package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.domain.repository.LocationRepository
import javax.inject.Inject

class GetSavedLocationsUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
//    operator fun invoke(): Flow<List<WeatherLocation>> = locationRepository.savedLocations()
}