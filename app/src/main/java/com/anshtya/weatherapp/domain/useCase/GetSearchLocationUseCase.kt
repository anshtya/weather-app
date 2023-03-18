package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.common.Resource
import com.anshtya.weatherapp.domain.model.SearchLocationResponse
import com.anshtya.weatherapp.domain.repository.SearchLocationRepository
import javax.inject.Inject

class GetSearchLocationUseCase @Inject constructor(
    private val searchLocationRepository: SearchLocationRepository
) {
    suspend operator fun invoke(searchQuery: String): Resource<SearchLocationResponse> {
        return try {
            val list =
                searchLocationRepository.getLocations(searchQuery).map { it.toSearchLocation() }
            Resource.Success(SearchLocationResponse(list))
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }
}