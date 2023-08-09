package com.anshtya.weatherapp.data.repository

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.anshtya.weatherapp.MainDispatcherRule
import com.anshtya.weatherapp.data.datastore.UserPreferencesDataSource
import com.anshtya.weatherapp.domain.model.UserData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class UserDataRepositoryImplTest {

    private lateinit var repository: UserDataRepositoryImpl
    private lateinit var userPreferencesDataSource: UserPreferencesDataSource

    @get:Rule(order = 1)
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule(order = 2)
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @Before
    fun setup() {
        userPreferencesDataSource = UserPreferencesDataSource(
            PreferenceDataStoreFactory.create {
                tmpFolder.newFile("user_settings_test.preferences_pb")
            }
        )
        repository = UserDataRepositoryImpl(
            userPreferencesDataSource = userPreferencesDataSource
        )
    }

    @Test
    fun defaultDataIsCorrect() = runTest {
        assertEquals(
            UserData(showCelsius = true),
            repository.userData.first()
        )
    }

    @Test
    fun setShowCelsius() = runTest {
        repository.setWeatherUnit(useCelsius = false)
        assertEquals(
            UserData(showCelsius = false),
            repository.userData.first()
        )

        repository.setWeatherUnit(useCelsius = true)
        assertEquals(
            UserData(showCelsius = true),
            repository.userData.first()
        )
    }
}