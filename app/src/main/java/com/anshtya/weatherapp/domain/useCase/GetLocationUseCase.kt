package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.core.common.Resource
import com.anshtya.weatherapp.domain.model.SavedLocation
import com.anshtya.weatherapp.domain.model.SearchLocationResponse
import com.anshtya.weatherapp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend fun getLocations(searchQuery: String): Resource<SearchLocationResponse> {
        return try {
            val list = locationRepository.getLocations(searchQuery)
            Resource.Success(SearchLocationResponse(list))
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    fun getSavedLocations(): Flow<List<SavedLocation>> = locationRepository.getSavedLocations()
}