package com.anshtya.weatherapp.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anshtya.weatherapp.data.local.WeatherDatabase
import com.anshtya.weatherapp.data.local.entity.CurrentWeatherEntity
import com.anshtya.weatherapp.data.local.entity.WeatherForecastEntity
import com.anshtya.weatherapp.data.local.entity.WeatherLocationEntity
import com.anshtya.weatherapp.data.local.model.SavedLocationModel
import com.anshtya.weatherapp.data.local.model.WeatherModel
import com.anshtya.weatherapp.domain.model.Astro
import com.anshtya.weatherapp.domain.model.Day
import com.anshtya.weatherapp.domain.model.WeatherCondition
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class WeatherDaoTest {

    private lateinit var weatherDao: WeatherDao
    private lateinit var db: WeatherDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, WeatherDatabase::class.java
        ).build()
        weatherDao = db.weatherDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetLocationIds() = runTest {
        val locations = listOf(
            testWeatherLocationEntity("id1"),
            testWeatherLocationEntity("id2")
        )

        locations.forEach {
            weatherDao.upsertWeatherLocation(it)
        }

        assertEquals(
            listOf("id1", "id2"),
            weatherDao.getLocationIds()
        )
    }

    @Test
    fun insertAndGetCurrentWeatherId() = runTest {
        val locationId = "id1"
        val location = testWeatherLocationEntity(locationId)
        val currentWeather = testCurrentWeatherEntity(1L, locationId)

        weatherDao.upsertWeatherLocation(location)
        weatherDao.upsertCurrentWeather(currentWeather)

        assertEquals(
            currentWeather.id,
            weatherDao.getCurrentWeatherId(currentWeather.locationId)
        )
    }

    @Test
    fun insertAndGetWeatherForecastIds() = runTest {
        val locationId = "id1"
        val location = testWeatherLocationEntity(locationId)
        val weatherForecasts = listOf(
            testWeatherForecastEntity(1, locationId),
            testWeatherForecastEntity(2, locationId),
            testWeatherForecastEntity(3, locationId)
        )

        weatherDao.upsertWeatherLocation(location)
        weatherDao.upsertWeatherForecast(weatherForecasts)

        assertEquals(
            listOf(1L, 2L, 3L),
            weatherDao.getWeatherForecastIds(locationId)
        )
    }

    @Test
    fun testWeatherLocationExist() = runTest {
        val weatherLocation = testWeatherLocationEntity("id1")

        weatherDao.upsertWeatherLocation(weatherLocation)

        assertEquals(
            true,
            weatherDao.checkWeatherExist(weatherLocation.id)
        )

        assertEquals(
            true,
            weatherDao.checkTableNotEmpty().first()
        )

        weatherDao.deleteWeatherLocation(weatherLocation.id)

        assertEquals(
            false,
            weatherDao.checkWeatherExist(weatherLocation.id)
        )

        assertEquals(
            false,
            weatherDao.checkTableNotEmpty().first()
        )
    }

    @Test
    fun insertAndGetWeatherAndSavedLocation() = runTest {
        val testWeather = listOf(
            WeatherModel(
                weatherLocation = testWeatherLocationEntity("id1"),
                currentWeather = testCurrentWeatherEntity(1, "id1"),
                weatherForecast = listOf(
                    testWeatherForecastEntity(1, "id1"),
                    testWeatherForecastEntity(2, "id1"),
                    testWeatherForecastEntity(3, "id1")
                )
            ),
            WeatherModel(
                weatherLocation = testWeatherLocationEntity("id2"),
                currentWeather = testCurrentWeatherEntity(2, "id2"),
                weatherForecast = listOf(
                    testWeatherForecastEntity(4, "id2"),
                    testWeatherForecastEntity(5, "id2"),
                    testWeatherForecastEntity(6, "id2")
                )
            )
        )
        val testSavedLocation = listOf(
            testSavedLocation("id1"),
            testSavedLocation("id2")
        )

        testWeather.forEach {
            weatherDao.upsertWeather(
                it.weatherLocation, it.currentWeather, it.weatherForecast
            )
        }

        assertEquals(
            testWeather,
            weatherDao.getWeather().first()
        )

        assertEquals(
            testSavedLocation,
            weatherDao.getSavedLocations().first()
        )

        weatherDao.deleteWeatherLocation("id1")
        weatherDao.deleteWeatherLocation("id2")

        assertEquals(
            listOf<WeatherModel>(),
            weatherDao.getWeather().first()
        )

        assertEquals(
            listOf<SavedLocationModel>(),
            weatherDao.getSavedLocations().first()
        )
    }
}

private fun testWeatherLocationEntity(
    id: String
) = WeatherLocationEntity(
    id = id,
    country = "",
    timezoneId = "",
    localtimeEpoch = 0,
    name = "",
    region = ""
)

private fun testCurrentWeatherEntity(
    id: Long,
    locationId: String
) = CurrentWeatherEntity(
    id = id,
    locationId = locationId,
    condition = WeatherCondition("test", 0),
    feelsLikeC = 0.0,
    feelsLikeF = 0.0,
    humidity = 0,
    isDay = 0,
    tempC = 0.0,
    tempF = 0.0,
    uv = 0.0,
    visKm = 0.0,
    visMiles = 0.0,
    windDir = "",
    windKph = 0.0,
    windMph = 0.0
)

private fun testWeatherForecastEntity(
    id: Long,
    locationId: String
) = WeatherForecastEntity(
    id = id,
    locationId = locationId,
    dateEpoch = 0,
    astro = Astro("", ""),
    day = Day(0, 0, 0.0, 0.0, 0.0, 0.0),
    hour = emptyList()
)

private fun testSavedLocation(
    id: String
) = SavedLocationModel(
    id = id,
    country = "",
    name = "",
    region = "",
    code = 0,
    isDay = 0,
    tempC = 0.0,
    tempF = 0.0,
    maxTempC = 0.0,
    maxTempF = 0.0,
    minTempC = 0.0,
    minTempF = 0.0

)