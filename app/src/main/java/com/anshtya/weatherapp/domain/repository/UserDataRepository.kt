package com.anshtya.weatherapp.domain.repository

import com.anshtya.weatherapp.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val userData: Flow<UserData>
    suspend fun setWeatherUnit(useCelsius: Boolean)
    suspend fun setApiCallTime(time: Long)
    suspend fun setLocationId(locationId: String)
}