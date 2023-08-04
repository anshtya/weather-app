package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.util.Resource
import com.anshtya.weatherapp.domain.model.SearchLocation
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetSearchResultUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(searchQuery: String): Resource<List<SearchLocation>> {
        return try {
            val list = weatherRepository.getSearchLocations(searchQuery)
            Resource.Success(list)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }
}