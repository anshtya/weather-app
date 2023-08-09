package com.anshtya.weatherapp.presentation.screens.manageLocation

import com.anshtya.weatherapp.MainDispatcherRule
import com.anshtya.weatherapp.domain.model.WeatherWithPreferences
import com.anshtya.weatherapp.domain.useCase.GetWeatherWithPreferencesUseCase
import com.anshtya.weatherapp.repository.TestUserDataRepository
import com.anshtya.weatherapp.repository.TestWeatherRepository
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
    private val weatherRepository = TestWeatherRepository()
    private val userDataRepository = TestUserDataRepository()
    private val getWeatherWithPreferencesUseCase = GetWeatherWithPreferencesUseCase(
        weatherRepository = weatherRepository,
        userDataRepository = userDataRepository
    )

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = ManageLocationViewModel(
            getWeatherWithPreferencesUseCase = getWeatherWithPreferencesUseCase,
            weatherRepository = weatherRepository
        )
    }

    @Test
    fun testInitialUiState() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect {}
        }

        assertEquals(
            ManageLocationUiState(),
            viewModel.uiState.value
        )

        collectJob.cancel()
    }

    @Test
    fun afterErrorShown_MessageSetToNull() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect {}
        }

        viewModel.errorShown()

        assertEquals(
            ManageLocationUiState(
                errorMessage = null
            ),
            viewModel.uiState.value
        )

        collectJob.cancel()
    }

    @Test
    fun savedLocationsUpdatedInUiState() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect {}
        }

        weatherRepository.setWeather(sampleWeatherList)

        assertEquals(
            ManageLocationUiState(
                savedLocations = WeatherWithPreferences(
                    weather = sampleWeatherList
                )
            ),
            viewModel.uiState.value
        )

        collectJob.cancel()
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
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.selectedLocations.collect {}
        }

        viewModel.selectLocation("id1")
        viewModel.selectLocation("id2")

        assertEquals(
            setOf("id1", "id2"),
            viewModel.selectedLocations.value
        )

        collectJob.cancel()
    }

    @Test
    fun ifLocationAlreadySelected_GetsRemoved() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.selectedLocations.collect {}
        }

        viewModel.selectLocation("id1")
        viewModel.selectLocation("id1")

        assertEquals(
            emptySet<String>(),
            viewModel.selectedLocations.value
        )

        collectJob.cancel()
    }

    @Test
    fun onDeleteLocation_GetsDeleted() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.selectedLocations.collect {}
        }

        viewModel.selectLocation("id1")
        viewModel.selectLocation("id2")
        viewModel.deleteLocation()

        assertEquals(
            emptySet<String>(),
            viewModel.selectedLocations.value
        )

        collectJob.cancel()
    }

    @Test
    fun ifNoLocationsSelected_DeleteGivesError() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect {}
        }

        viewModel.deleteLocation()

        assertEquals(
            ManageLocationUiState(
                errorMessage = "Select atleast one location"
            ),
            viewModel.uiState.value
        )

        collectJob.cancel()
    }
}