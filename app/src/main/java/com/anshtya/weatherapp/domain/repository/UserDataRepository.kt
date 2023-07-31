package com.anshtya.weatherapp.domain.repository

import com.anshtya.weatherapp.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val userData: Flow<UserData>
    suspend fun setWeatherUnit(useCelsius: Boolean)
}