package com.anshtya.weatherapp.data.repository

import com.anshtya.weatherapp.core.Resource
import com.anshtya.weatherapp.data.remote.WeatherApi
import com.anshtya.weatherapp.domain.repository.SearchLocationRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchLocationRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : SearchLocationRepository {
    override fun getLocations(searchQuery: String) = flow {
        emit(Resource.Loading)
        try {
            val locations = weatherApi.searchLocation(searchQuery).map { it.toSearchLocation() }
            emit(Resource.Success(locations))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
}