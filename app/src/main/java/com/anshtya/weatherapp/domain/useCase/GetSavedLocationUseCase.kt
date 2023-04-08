package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.domain.model.SavedLocation
import com.anshtya.weatherapp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    fun getSavedLocations(): Flow<List<SavedLocation>> = locationRepository.getSavedLocations()

    fun checkIfTableEmpty(): Flow<Boolean> = locationRepository.checkIfTableEmpty()
}