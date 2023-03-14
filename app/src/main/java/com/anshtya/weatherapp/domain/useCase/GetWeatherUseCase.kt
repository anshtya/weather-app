package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.core.Resource
import com.anshtya.weatherapp.domain.model.WeatherResponse
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(): Flow<Resource<WeatherResponse>> {
        return flow {
            try {
                val weather = weatherRepository.getWeatherConditions().toWeatherResponse()
                emit(Resource.Success(weather))
            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }
        }
    }
}