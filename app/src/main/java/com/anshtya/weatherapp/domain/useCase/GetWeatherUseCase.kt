package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.domain.model.Weather
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(): Flow<List<Weather>> = weatherRepository.getWeather()
}