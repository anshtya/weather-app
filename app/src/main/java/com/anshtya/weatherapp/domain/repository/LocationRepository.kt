package com.anshtya.weatherapp.domain.repository

import com.anshtya.weatherapp.domain.model.SearchLocation

interface LocationRepository {
    suspend fun getLocations(searchQuery: String): List<SearchLocation>
//    fun savedLocations(): Flow<List<WeatherLocation>>
}