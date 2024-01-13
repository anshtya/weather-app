package com.anshtya.weatherapp.data.repository

import com.anshtya.weatherapp.testdoubles.FakeWeatherApi
import com.anshtya.weatherapp.testdoubles.FakeWeatherDao
import com.anshtya.weatherapp.MainDispatcherRule
import com.anshtya.weatherapp.data.mapper.toExternalModel
import com.anshtya.weatherapp.data.remote.model.NetworkSearchLocation
import com.anshtya.weatherapp.util.Resource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherRepositoryImplTest {

    private lateinit var weatherRepository: WeatherRepositoryImpl
    private val weatherDao = FakeWeatherDao()
    private val weatherApi = FakeWeatherApi()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        weatherRepository = WeatherRepositoryImpl(
            weatherApi = weatherApi,
            weatherDao = weatherDao
        )
    }

    @Test
    fun testWeatherFlow() = runTest {
        val repositoryWeather = weatherRepository.weather.first()
        val daoWeather = weatherDao.getWeather().first()

        assertEquals(
            repositoryWeather,
            daoWeather.map { it.toExternalModel() }
        )
    }

    @Test
    fun testSavedLocationsFlow() = runTest {
        val repositorySavedLocations = weatherRepository.savedLocations.first()
        val daoSavedLocations = weatherDao.getSavedLocations().first()

        assertEquals(
            repositorySavedLocations,
            daoSavedLocations.map { it.toExternalModel() }
        )
    }

    @Test
    fun testSearchLocation() = runTest {
        val testLocation = listOf(
            NetworkSearchLocation(
                name = "q",
                region = "",
                country = "",
                url = ""
            )
        )

        val searchResult = weatherRepository.getSearchLocations("q")

        assertEquals(
            testLocation.map { it.toExternalModel() },
            (searchResult as Resource.Success).data
        )
    }

    @Test
    fun testSearchLocation_SearchFails() = runTest {
        weatherApi.shouldThrowError = true
        val result = weatherRepository.getSearchLocations("q")

        assertEquals(
            null,
            (result as Resource.Error).message
        )
    }

    @Test
    fun testAddLocation() = runTest {
        val result = weatherRepository.addWeather("id1")

        assertEquals(
            Resource.Success(Unit),
            result
        )

        assertEquals(true, weatherDao.checkWeatherExist("id1"))

        assertEquals(true, weatherRepository.isLocationTableNotEmpty.first())
    }

    @Test
    fun testAddLocation_AddFailed() = runTest {
        weatherApi.shouldThrowError = true
        val result = weatherRepository.addWeather("id1")

        assertEquals(
            "error",
            (result as Resource.Error).message
        )
    }

    @Test
    fun testLocationNotAdded_IfAlreadyExists() = runTest {
        weatherRepository.addWeather("id1")
        val result = weatherRepository.addWeather("id1")

        assertEquals(
            result as Resource.Error,
            result
        )
    }

    @Test
    fun testUpdateWeather_OlderForecastNotIncluded() = runTest {
        weatherRepository.addWeather("id1")

        weatherApi.updateWeather = true
        weatherRepository.updateWeather()

        assertEquals(
            1,
            weatherRepository.weather.first().count()
        )

        /*
        * Checking older forecast is replaced by new one
        */
        assertEquals(
            1,
            weatherRepository.weather.first().first().weatherForecast.first().hour.first().timeEpoch
        )

        assertEquals(
            1,
            weatherRepository.weather.first().first().weatherLocation.localtimeEpoch
        )
    }

    @Test
    fun testUpdateWeather_UpdateFails() = runTest {
        weatherRepository.addWeather("id1")

        weatherApi.updateWeather = true
        weatherApi.shouldThrowError = true
        val result = weatherRepository.updateWeather()

        assertEquals(
            "error",
            (result as Resource.Error).message
        )
    }
}