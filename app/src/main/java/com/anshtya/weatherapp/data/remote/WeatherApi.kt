package com.anshtya.weatherapp.data.remote

import com.anshtya.weatherapp.BuildConfig
import com.anshtya.weatherapp.data.remote.model.searchLocation.SearchLocation
import com.anshtya.weatherapp.data.remote.model.currentWeather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/v1/search.json")
    suspend fun searchLocation(
        @Query("q")
        q: String,
        @Query("key")
        key: String = BuildConfig.API_KEY
    ): List<SearchLocation>

    @GET("/v1/current.json")
    suspend fun getCurrentWeather(
        @Query("q")
        q: String,
        @Query("key")
        key: String = BuildConfig.API_KEY
    ): WeatherResponse

//    @GET("/v1/forecast.json")
//    suspend fun getWeatherForecast(
//        @Query("q")
//        q: String,
//        @Query("days")
//        days: Int = FORECAST_DAYS,
//        @Query("key")
//        key: String = BuildConfig.API_KEY
//    )
}