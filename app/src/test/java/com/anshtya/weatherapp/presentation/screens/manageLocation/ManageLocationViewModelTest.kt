package com.anshtya.weatherapp.presentation.screens.manageLocation

import com.anshtya.weatherapp.MainDispatcherRule
import com.anshtya.weatherapp.domain.model.WeatherWithPreferences
import com.anshtya.weatherapp.domain.useCase.GetSavedLocationsWithPreferencesUseCase
import com.anshtya.weatherapp.testdoubles.repository.FakeUserDataRepository
import com.anshtya.weatherapp.testdoubles.repository.FakeWeatherRepository
import com.anshtya.weatherapp.sampleSavedLocations
import com.anshtya.weatherapp.sampleWeatherList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ManageLocationViewModelTest {

    private lateinit var viewModel: ManageLocationViewModel
    private val weatherRepository = FakeWeatherRepository()
    private val userDataRepository = FakeUserDataRepository()
    private val getSavedLocationsWithPreferencesUseCase = GetSavedLocationsWithPreferencesUseCase(
        weatherRepository = weatherRepository,
        userDataRepository = userDataRepository
    )

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = ManageLocationViewModel(
            getSavedLocationsWithPreferencesUseCase = getSavedLocationsWithPreferencesUseCase,
            weatherRepository = weatherRepository
        )
    }

    @Test
    fun testInitialUiState() = runTest {
        assertEquals(
            ManageLocationUiState(),
            viewModel.uiState.value
        )
    }

    @Test
    fun afterErrorShown_MessageSetToNull() = runTest {
        viewModel.errorShown()

        assertEquals(
            ManageLocationUiState(
                errorMessage = null
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun savedLocationsUpdatedInUiState() = runTest {
        weatherRepository.setWeather(sampleWeatherList)

        assertEquals(
            ManageLocationUiState(
                locationWithPreferences = WeatherWithPreferences(
                    savedLocations = sampleSavedLocations
                )
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun testIfNoWeatherLocations() = runTest {
        var hasLocations = true
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.hasLocations.collect {
                hasLocations = it
            }
        }

        assertEquals(false, hasLocations)

        collectJob.cancel()
    }

    @Test
    fun onSelectLocation_GetsAdded() = runTest {
        viewModel.selectLocation("id1")
        viewModel.selectLocation("id2")

        assertEquals(
            setOf("id1", "id2"),
            viewModel.selectedLocations.value
        )
    }

    @Test
    fun ifLocationAlreadySelected_GetsRemoved() = runTest {
        viewModel.selectLocation("id1")
        viewModel.selectLocation("id1")

        assertEquals(
            emptySet<String>(),
            viewModel.selectedLocations.value
        )
    }

    @Test
    fun onDeleteLocation_SelectedLocationsGetsDeleted() = runTest {
        val collectJob2 = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect {}
        }

        weatherRepository.setWeather(sampleWeatherList)
        assertEquals(
            sampleSavedLocations,
            viewModel.uiState.value.locationWithPreferences.savedLocations
        )

        viewModel.selectLocation("id1")
        viewModel.selectLocation("id2")
        viewModel.deleteLocation()

        assertEquals(
            emptySet<String>(),
            viewModel.selectedLocations.value
        )

        assertEquals(
            false,
            viewModel.uiState.value.locationWithPreferences.savedLocations.containsAll(sampleSavedLocations)
        )

        collectJob2.cancel()
    }

    @Test
    fun ifNoLocationsSelected_DeleteGivesError() = runTest {
        viewModel.deleteLocation()

        assertEquals(
            ManageLocationUiState(
                errorMessage = "Select atleast one location"
            ),
            viewModel.uiState.value
        )
    }
}