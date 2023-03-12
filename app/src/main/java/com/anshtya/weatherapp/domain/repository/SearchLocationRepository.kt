package com.anshtya.weatherapp.domain.repository

import com.anshtya.weatherapp.domain.model.SearchLocation

interface SearchLocationRepository {
    suspend fun getLocations(searchQuery: String): List<SearchLocation>
}