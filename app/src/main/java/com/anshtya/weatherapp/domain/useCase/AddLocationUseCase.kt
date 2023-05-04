package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.domain.repository.LocationRepository
import javax.inject.Inject

class AddLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(locationUrl: String) = locationRepository.addWeatherLocation(locationUrl)
}