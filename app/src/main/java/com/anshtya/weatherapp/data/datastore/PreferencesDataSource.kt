package com.anshtya.weatherapp.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val HAS_SAVED_LOCATION = booleanPreferencesKey("has_saved_location")
    }

    val userPreferences: Flow<UserPreferences> = dataStore.data.map { preferences ->
        val hasSavedLocation = preferences[HAS_SAVED_LOCATION] ?: false
        UserPreferences(hasSavedLocation)
    }

    suspend fun updateUserPreferences(hasSavedLocation: Boolean) {
        dataStore.edit { preferences ->
            preferences[HAS_SAVED_LOCATION] = hasSavedLocation
        }
    }
}