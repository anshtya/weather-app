package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.domain.model.SavedLocation
import com.anshtya.weatherapp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedLocationsUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Flow<List<SavedLocation>> = locationRepository.getSavedLocations()
}