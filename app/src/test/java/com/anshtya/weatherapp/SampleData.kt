package com.anshtya.weatherapp

import com.anshtya.weatherapp.domain.model.CurrentWeather
import com.anshtya.weatherapp.domain.model.SavedLocation
import com.anshtya.weatherapp.domain.model.Weather
import com.anshtya.weatherapp.domain.model.WeatherLocation
import com.anshtya.weatherapp.domain.model.WeatherType

val sampleWeatherList = listOf(
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

val sampleSavedLocations = sampleWeatherList.map { weather ->
    toSavedLocation(weather.weatherLocation.id)
}

fun toSavedLocation(id: String) = SavedLocation(
    id = id,
    country = "country",
    name = "name",
    region = "region",
    weatherType = WeatherType.Clear,
    isDay = 0,
    tempC = 0.0,
    tempF = 0.0,
    maxTempC = 0.0,
    maxTempF = 0.0,
    minTempC = 0.0,
    minTempF = 0.0
)