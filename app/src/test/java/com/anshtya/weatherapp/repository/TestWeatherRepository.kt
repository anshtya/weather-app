package com.anshtya.weatherapp.repository

import com.anshtya.weatherapp.domain.model.CurrentWeather
import com.anshtya.weatherapp.domain.model.SearchLocation
import com.anshtya.weatherapp.domain.model.Weather
import com.anshtya.weatherapp.domain.model.WeatherLocation
import com.anshtya.weatherapp.domain.model.WeatherType
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import com.anshtya.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class TestWeatherRepository : WeatherRepository {

    private val weatherList = mutableListOf<Weather>()

    private val _weather = MutableStateFlow<List<Weather>>(emptyList())
    override val weather: Flow<List<Weather>> = _weather.asStateFlow()

    override val isLocationTableNotEmpty: Flow<Boolean> = weather
        .map {
            it.isNotEmpty()
        }

    override suspend fun getSearchLocations(searchQuery: String): Resource<List<SearchLocation>> {
        return if (searchQuery != "error") {
            val results = listOf(
                SearchLocation(
                    name = "name",
                    region = "region",
                    country = "country",
                    url = "url"
                )
            )
            Resource.Success(results)
        } else {
            Resource.Error("An Error Occurred")
        }
    }

    override suspend fun addWeather(locationUrl: String): Resource<Unit> {
    return if (locationUrl != "exist") {
            val weather = Weather(
                weatherLocation = WeatherLocation(
                    id = locationUrl,
                    country = "country",
                    timezoneId = "timezoneId",
                    localtimeEpoch = 0,
                    name = "name",
                    region = "region"
                ),
                currentWeather = CurrentWeather(
                    locationId = "locationId",
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
                    windDir = "windDir",
                    windKph = 0.0,
                    windMph = 0.0
                ),
                weatherForecast = emptyList()
            )
            weatherList.add(weather)
            _weather.update { weatherList }
            Resource.Success(Unit)
        } else {
            Resource.Error("Location already exists")
        }
    }

    var updateReturnsError = false
    override suspend fun updateWeather(): Resource<Unit> {
        return if (updateReturnsError) {
            Resource.Error("")
        } else {
            Resource.Success(Unit)
        }
    }

    override suspend fun deleteWeather(locationId: String) {
        weatherList.apply {
            remove(
                weatherList.find { it.weatherLocation.id == locationId }
            )
        }
        _weather.update { weatherList }
    }

    fun setWeather(list: List<Weather>) {
        _weather.update { list }
    }
}