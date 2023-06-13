package com.anshtya.weatherapp.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.anshtya.weatherapp.domain.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val SHOW_CELSIUS = booleanPreferencesKey("show_celsius")
        val API_CALL_TIME = longPreferencesKey("api_call_time_difference")
    }

    val userData: Flow<UserData> = dataStore.data.map { preferences ->
        UserData(
            showCelsius = preferences[SHOW_CELSIUS] ?: true,
            apiCallTime = preferences[API_CALL_TIME] ?: 0
        )
    }

    suspend fun setWeatherUnit(useCelsius: Boolean) {
        dataStore.edit { preferences ->
            preferences[SHOW_CELSIUS] = useCelsius
        }
    }

    suspend fun setApiCallTime(apiCallTime: Long) {
        dataStore.edit { preferences ->
            preferences[API_CALL_TIME] = apiCallTime
        }
    }
}