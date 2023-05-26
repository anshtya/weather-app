package com.anshtya.weatherapp.domain.useCase

import com.anshtya.weatherapp.domain.model.TableState
import com.anshtya.weatherapp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherLocationsEmptyUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Flow<TableState> {
        return locationRepository.checkIfTableEmpty().map { isNotEmpty ->
            if(isNotEmpty) {
                TableState.NotEmpty
            } else {
                TableState.Empty
            }
        }
    }
}