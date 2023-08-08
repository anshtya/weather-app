package com.anshtya.weatherapp.presentation.screens.weather

import com.anshtya.weatherapp.FakeNetworkConnectionObserver
import com.anshtya.weatherapp.MainDispatcherRule
import com.anshtya.weatherapp.domain.model.CurrentWeather
import com.anshtya.weatherapp.domain.model.Weather
import com.anshtya.weatherapp.domain.model.WeatherLocation
import com.anshtya.weatherapp.domain.model.WeatherType
import com.anshtya.weatherapp.domain.model.WeatherWithPreferences
import com.anshtya.weatherapp.domain.useCase.GetWeatherWithPreferencesUseCase
import com.anshtya.weatherapp.repository.TestUserDataRepository
import com.anshtya.weatherapp.repository.TestWeatherRepository
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
    private val weatherRepository = TestWeatherRepository()
    private val userDataRepository = TestUserDataRepository()
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
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect {}
        }

        assertEquals(
            WeatherUiState(),
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
            WeatherUiState(
                errorMessage = null
            ),
            viewModel.uiState.value
        )

        collectJob.cancel()
    }

    @Test
    fun userWeatherUpdatedInUiState() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect {}
        }

        weatherRepository.setWeather(sampleWeatherList)

        assertEquals(
            WeatherUiState(
                userWeather = WeatherWithPreferences(
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
    fun ifNetworkUnavailable_UpdateFails() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect {}
        }

        connectionObserver.unavailableNetwork()
        viewModel.updateWeather()

        assertEquals(
            WeatherUiState(
                isLoading = false,
                errorMessage = "Update Failed, Network unavailable"
            ),
            viewModel.uiState.value
        )

        collectJob.cancel()
    }

    @Test
    fun ifUpdateFails_ErrorMessageUpdated() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect {}
        }

        weatherRepository.updateReturnsError = true
        viewModel.updateWeather()

        assertEquals(
            WeatherUiState(
                isLoading = false,
                errorMessage = "Update Failed"
            ),
            viewModel.uiState.value
        )

        collectJob.cancel()
    }

    @Test
    fun ifUpdateSuccess_SuccessResponseReceived() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect {}
        }

        weatherRepository.updateReturnsError = false
        viewModel.updateWeather()

        assertEquals(
            WeatherUiState(
                isLoading = false
            ),
            viewModel.uiState.value
        )

        collectJob.cancel()
    }
}

private val sampleWeatherList = listOf(
    Weather(
        weatherLocation = WeatherLocation(
            id = "id1",
            country = "country1",
            timezoneId = "timezoneId1",
            localtimeEpoch = 0,
            name = "name1",
            region = "region1"
        ),
        currentWeather = CurrentWeather(
            locationId = "locationId2",
            weatherType = WeatherType.Clear,
            feelsLikeC = 0.0,
            feelsLikeF = 0.0,
            humidity = 0,
            isDay = 0,
            tempC = 0.0,
            tempF = 0.0,
            uv = 0.0,
            visKm = 0.0,
            visMiles = 0.0,
            windDir = "windDir2",
            windKph = 0.0,
            windMph = 0.0
        ),
        weatherForecast = emptyList()
    ),
    Weather(
        weatherLocation = WeatherLocation(
            id = "id2",
            country = "country2",
            timezoneId = "timezoneId2",
            localtimeEpoch = 0,
            name = "name2",
            region = "region2"
        ),
        currentWeather = CurrentWeather(
            locationId = "locationId2",
            weatherType = WeatherType.Clear,
            feelsLikeC = 0.0,
            feelsLikeF = 0.0,
            humidity = 0,
            isDay = 0,
            tempC = 0.0,
            tempF = 0.0,
            uv = 0.0,
            visKm = 0.0,
            visMiles = 0.0,
            windDir = "windDir2",
            windKph = 0.0,
            windMph = 0.0
        ),
        weatherForecast = emptyList()
    )
)