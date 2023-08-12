package com.anshtya.weatherapp

import com.anshtya.weatherapp.data.remote.WeatherApi
import com.anshtya.weatherapp.data.remote.model.NetworkAstro
import com.anshtya.weatherapp.data.remote.model.NetworkCurrentWeather
import com.anshtya.weatherapp.data.remote.model.NetworkDay
import com.anshtya.weatherapp.data.remote.model.NetworkForecast
import com.anshtya.weatherapp.data.remote.model.NetworkForecastDay
import com.anshtya.weatherapp.data.remote.model.NetworkHour
import com.anshtya.weatherapp.data.remote.model.NetworkSearchLocation
import com.anshtya.weatherapp.data.remote.model.NetworkWeatherLocation
import com.anshtya.weatherapp.data.remote.model.NetworkWeatherResponse
import com.anshtya.weatherapp.domain.model.WeatherCondition

class FakeWeatherApi : WeatherApi {

    var shouldThrowError = false

    override suspend fun searchLocation(q: String, key: String): List<NetworkSearchLocation> {
        return if(shouldThrowError) {
            throw Exception("error")
        } else {
            listOf(
                NetworkSearchLocation(
                    name = q,
                    region = "",
                    country = "",
                    url = ""
                )
            )
        }
    }

    var updateWeather = false
    override suspend fun getWeatherForecast(
        q: String, days: Int, key: String
    ): NetworkWeatherResponse {
        return if (shouldThrowError) {
            throw Exception("error")
        } else {
            if (updateWeather) {
                testWeatherResponse(timeEpoch = 1)
            } else {
                testWeatherResponse()
            }
        }
    }
}

private fun testWeatherResponse(timeEpoch: Int = 0) = NetworkWeatherResponse(
    location = NetworkWeatherLocation(
        country = "country",
        timezoneId = "timezoneId",
        localtimeEpoch = timeEpoch,
        name = "name",
        region = "region"
    ),
    current = NetworkCurrentWeather(
        condition = WeatherCondition("", 0),
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
    ),
    forecast = NetworkForecast(
        listOf(
            NetworkForecastDay(
                dateEpoch = 0,
                astro = NetworkAstro("", ""),
                day = NetworkDay(0, 0, 0.0, 0.0, 0.0, 0.0),
                hour = listOf(
                    NetworkHour(
                        chanceOfRain = 0,
                        chanceOfSnow = 0,
                        condition = WeatherCondition("", 0) ,
                        isDay = 0,
                        tempC = 0.0,
                        tempF = 0.0,
                        time = "",
                        timeEpoch = timeEpoch
                    )
                )
            )
        )
    )
)