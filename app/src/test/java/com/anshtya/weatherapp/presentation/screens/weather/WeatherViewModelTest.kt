package com.anshtya.weatherapp.presentation.screens.weather

import com.anshtya.weatherapp.testdoubles.FakeNetworkConnectionObserver
import com.anshtya.weatherapp.MainDispatcherRule
import com.anshtya.weatherapp.domain.model.WeatherWithPreferences
import com.anshtya.weatherapp.domain.useCase.GetWeatherWithPreferencesUseCase
import com.anshtya.weatherapp.testdoubles.repository.FakeUserDataRepository
import com.anshtya.weatherapp.testdoubles.repository.FakeWeatherRepository
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
class WeatherViewModelTest {

    private lateinit var viewModel: WeatherViewModel
    private val connectionObserver = FakeNetworkConnectionObserver()
    private val weatherRepository = FakeWeatherRepository()
    private val userDataRepository = FakeUserDataRepository()
    private val getWeatherWithPreferencesUseCase = GetWeatherWithPreferencesUseCase(
        weatherRepository = weatherRepository,
        userDataRepository = userDataRepository
    )

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        viewModel = WeatherViewModel(
            getWeatherWithPreferencesUseCase = getWeatherWithPreferencesUseCase,
            weatherRepository = weatherRepository,
            connectionObserver = connectionObserver
        )
    }

    @Test
    fun testInitialUiState() = runTest {
        assertEquals(
            WeatherUiState(),
            viewModel.uiState.value
        )
    }

    @Test
    fun afterErrorShown_MessageSetToNull() = runTest {
        viewModel.errorShown()

        assertEquals(
            WeatherUiState(
                errorMessage = null
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun userWeatherUpdatedInUiState() = runTest {
        weatherRepository.setWeather(sampleWeatherList)

        assertEquals(
            WeatherUiState(
                userWeather = WeatherWithPreferences(
                    weather = sampleWeatherList
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
    fun ifNetworkUnavailable_UpdateFails() = runTest {
        connectionObserver.unavailableNetwork()
        viewModel.updateWeather()

        assertEquals(
            WeatherUiState(
                isLoading = false,
                errorMessage = "Update Failed, Network unavailable"
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun ifUpdateFails_ErrorMessageUpdated() = runTest {
        weatherRepository.updateReturnsError = true
        viewModel.updateWeather()

        assertEquals(
            WeatherUiState(
                isLoading = false,
                errorMessage = "Update Failed"
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun ifUpdateSuccess_SuccessResponseReceived() = runTest {
        weatherRepository.updateReturnsError = false
        viewModel.updateWeather()

        assertEquals(
            WeatherUiState(
                isLoading = false
            ),
            viewModel.uiState.value
        )
    }
}