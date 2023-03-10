package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.core.Resource
import com.anshtya.weatherapp.domain.model.SearchLocation
import com.anshtya.weatherapp.domain.repository.SearchLocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchLocationUseCase @Inject constructor(
    private val searchLocationRepository: SearchLocationRepository
) {
    operator fun invoke(searchQuery: String): Flow<Resource<List<SearchLocation>>> {
        return searchLocationRepository.getLocations(searchQuery)
    }
}