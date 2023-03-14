package com.anshtya.weatherapp.domain.repository

import com.anshtya.weatherapp.data.remote.dto.SearchLocationDto

interface SearchLocationRepository {
    suspend fun getLocations(searchQuery: String): List<SearchLocationDto>
}