package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.domain.model.WeatherWithPreferences
import com.anshtya.weatherapp.domain.repository.UserDataRepository
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val userDataRepository: UserDataRepository
) {
    operator fun invoke(): Flow<WeatherWithPreferences> {
        return combine(
            weatherRepository.getWeather(),
            userDataRepository.userData
        ) { weatherList, userData ->
            WeatherWithPreferences(
                weatherList = weatherList,
                showCelsius = userData.showCelsius,
                selectedLocationId = userData.selectedLocation
            )
        }
    }
}