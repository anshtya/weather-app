package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.core.common.Resource
import com.anshtya.weatherapp.domain.model.Weather
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(locationId: String): Flow<Resource<Weather>> = flow {
        try {
            val weather = weatherRepository.getWeatherCondition(locationId)
            emit(Resource.Success(weather))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}