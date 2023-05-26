package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.core.model.Resource
import com.anshtya.weatherapp.domain.repository.LocationRepository
import javax.inject.Inject

class AddLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(locationUrl: String): Resource<Unit> =
        locationRepository.addWeatherLocation(locationUrl)
}