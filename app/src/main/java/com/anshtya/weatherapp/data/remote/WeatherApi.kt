package com.anshtya.weatherapp.data.remote

import com.anshtya.weatherapp.BuildConfig
import com.anshtya.weatherapp.data.remote.model.NetworkSearchLocation
import com.anshtya.weatherapp.data.remote.model.NetworkWeatherResponse
import com.anshtya.weatherapp.util.Constants.Companion.FORECAST_DAYS
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/v1/search.json")
    suspend fun searchLocation(
        @Query("q")
        q: String,
        @Query("key")
        key: String = BuildConfig.API_KEY
    ): List<NetworkSearchLocation>

    @GET("/v1/forecast.json")
    suspend fun getWeatherForecast(
        @Query("q")
        q: String,
        @Query("days")
        days: Int = FORECAST_DAYS,
        @Query("key")
        key: String = BuildConfig.API_KEY
    ): NetworkWeatherResponse
}