package com.anshtya.weatherapp.domain.repository

import com.anshtya.weatherapp.core.Resource
import com.anshtya.weatherapp.domain.model.SearchLocation
import kotlinx.coroutines.flow.Flow

interface SearchLocationRepository {
    fun getLocations(searchQuery: String): Flow<Resource<List<SearchLocation>>>
}