package com.anshtya.weatherapp.data.repository

import com.anshtya.weatherapp.domain.model.UserData
import com.anshtya.weatherapp.data.datastore.UserPreferencesDataSource
import com.anshtya.weatherapp.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val userPreferencesDataSource: UserPreferencesDataSource
) : UserDataRepository {
    override val userData: Flow<UserData> = userPreferencesDataSource.userData

    override suspend fun setWeatherUnit(useCelsius: Boolean) =
        userPreferencesDataSource.setWeatherUnit(useCelsius)
}