package com.anshtya.weatherapp

import com.anshtya.weatherapp.domain.model.UserData
import com.anshtya.weatherapp.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeUserDataRepository: UserDataRepository {

    private val _data = MutableStateFlow(UserData(true))

    override val userData: Flow<UserData>
        get() = _data

    override suspend fun setWeatherUnit(useCelsius: Boolean) {
        _data.emit(UserData(useCelsius))
    }
}