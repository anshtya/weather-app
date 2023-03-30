package com.anshtya.weatherapp.domain.repository

import com.anshtya.weatherapp.domain.model.SavedLocation
import com.anshtya.weatherapp.domain.model.SearchLocation
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun getLocations(searchQuery: String): List<SearchLocation>
    fun getSavedLocations(): Flow<List<SavedLocation>>
}