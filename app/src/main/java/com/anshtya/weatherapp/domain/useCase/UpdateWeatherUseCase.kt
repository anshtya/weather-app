package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.core.model.Resource
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class UpdateWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(): Resource<Unit> {
        return try {
            weatherRepository.updateWeather()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }
}