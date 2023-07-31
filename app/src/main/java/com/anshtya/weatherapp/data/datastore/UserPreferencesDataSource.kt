package com.anshtya.weatherapp.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.anshtya.weatherapp.domain.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val SHOW_CELSIUS = booleanPreferencesKey("show_celsius")
    }

    val userData: Flow<UserData> = dataStore.data.map { preferences ->
        UserData(
            showCelsius = preferences[SHOW_CELSIUS] ?: true
        )
    }

    suspend fun setWeatherUnit(useCelsius: Boolean) {
        dataStore.edit { preferences ->
            preferences[SHOW_CELSIUS] = useCelsius
        }
    }
}