package com.anshtya.weatherapp.data.remote

import com.anshtya.weatherapp.BuildConfig
import com.anshtya.weatherapp.data.model.Weather
import com.anshtya.weatherapp.data.remote.dto.SearchLocationDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/v1/search.json")
    suspend fun searchLocation(
        @Query("q")
        q: String,
        @Query("key")
        key: String = BuildConfig.API_KEY
    ): List<SearchLocationDto>

    @GET("/v1/current.json")
    suspend fun getCurrentWeather(
        @Query("q")
        q: String,
        @Query("key")
        key: String = BuildConfig.API_KEY
    ): Weather

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