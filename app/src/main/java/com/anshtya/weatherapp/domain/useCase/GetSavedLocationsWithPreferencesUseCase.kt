package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.domain.model.WeatherWithPreferences
import com.anshtya.weatherapp.domain.repository.UserDataRepository
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetSavedLocationsWithPreferencesUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val userDataRepository: UserDataRepository
) {
    operator fun invoke(): Flow<WeatherWithPreferences> {
        return combine(
            weatherRepository.savedLocations,
            userDataRepository.userData
        ) { savedLocations, userData ->
            WeatherWithPreferences(
                savedLocations = savedLocations,
                showCelsius = userData.showCelsius
            )
        }
    }
}