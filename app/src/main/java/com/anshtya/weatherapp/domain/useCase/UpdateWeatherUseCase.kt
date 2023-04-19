package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class UpdateWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke() = weatherRepository.updateWeather()
}