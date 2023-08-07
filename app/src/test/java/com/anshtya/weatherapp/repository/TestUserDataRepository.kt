package com.anshtya.weatherapp.repository

import com.anshtya.weatherapp.domain.model.UserData
import com.anshtya.weatherapp.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class TestUserDataRepository: UserDataRepository {

    private val _data = MutableSharedFlow<UserData>()

    override val userData: Flow<UserData> = _data.asSharedFlow()

    override suspend fun setWeatherUnit(useCelsius: Boolean) {
        _data.emit(UserData(useCelsius))
    }
}