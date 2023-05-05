package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.core.model.Result
import com.anshtya.weatherapp.domain.model.SearchLocationResponse
import com.anshtya.weatherapp.domain.repository.LocationRepository
import javax.inject.Inject

class GetSearchResultUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(searchQuery: String): Result<SearchLocationResponse> {
        return try {
            val list = locationRepository.getLocations(searchQuery)
            Result.Success(SearchLocationResponse(list))
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }
}