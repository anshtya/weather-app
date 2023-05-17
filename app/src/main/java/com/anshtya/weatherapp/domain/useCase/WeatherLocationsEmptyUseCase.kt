package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherLocationsEmptyUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Flow<Boolean> = locationRepository.checkIfTableEmpty()
}