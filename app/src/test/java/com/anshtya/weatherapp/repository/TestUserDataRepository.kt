package com.anshtya.weatherapp.repository

import com.anshtya.weatherapp.domain.model.UserData
import com.anshtya.weatherapp.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TestUserDataRepository: UserDataRepository {

    private val _data = MutableStateFlow(UserData(showCelsius = true))

    override val userData: Flow<UserData> = _data.asStateFlow()

    override suspend fun setWeatherUnit(useCelsius: Boolean) {
        _data.update { UserData(useCelsius) }
    }
}