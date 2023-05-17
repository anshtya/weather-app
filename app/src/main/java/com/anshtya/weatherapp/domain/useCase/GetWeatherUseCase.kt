package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.core.model.WeatherWithUnitPreference
import com.anshtya.weatherapp.domain.repository.UserDataRepository
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val userDataRepository: UserDataRepository
) {
    operator fun invoke(): Flow<WeatherWithUnitPreference> {
        return combine(
            weatherRepository.getWeather(),
            userDataRepository.userData
        ) { weatherList, userData ->
            WeatherWithUnitPreference(
                weatherList = weatherList,
                showCelsius = userData.showCelsius
            )
        }
    }
}