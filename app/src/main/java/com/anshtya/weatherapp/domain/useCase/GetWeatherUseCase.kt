package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.core.common.Resource
import com.anshtya.weatherapp.domain.model.CurrentWeather
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(): Flow<Resource<CurrentWeather>> {
        return flow {
            try {
                val weather = weatherRepository.getWeatherConditions()
                emit(Resource.Success(weather))
            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }
        }
    }
}