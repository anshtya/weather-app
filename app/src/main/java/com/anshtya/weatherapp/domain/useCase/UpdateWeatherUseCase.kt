package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.core.model.Result
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class UpdateWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return try {
            weatherRepository.updateWeather()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }
}