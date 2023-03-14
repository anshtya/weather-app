package com.anshtya.weatherapp.data.repository

import com.anshtya.weatherapp.data.remote.WeatherApi
import com.anshtya.weatherapp.data.remote.dto.SearchLocationDto
import com.anshtya.weatherapp.domain.repository.SearchLocationRepository
import javax.inject.Inject

class SearchLocationRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : SearchLocationRepository {
    override suspend fun getLocations(searchQuery: String): List<SearchLocationDto>{
        return weatherApi.searchLocation(searchQuery)
    }
}