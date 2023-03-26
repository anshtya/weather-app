package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.core.common.Resource
import com.anshtya.weatherapp.domain.model.SearchLocationResponse
import com.anshtya.weatherapp.domain.repository.LocationRepository
import javax.inject.Inject

class GetSearchLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(searchQuery: String): Resource<SearchLocationResponse> {
        return try {
            val list = locationRepository.getLocations(searchQuery)
            Resource.Success(SearchLocationResponse(list))
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }
}