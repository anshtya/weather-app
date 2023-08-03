package com.anshtya.weatherapp.data.local.dao

import androidx.room.*
import com.anshtya.weatherapp.data.local.entity.CurrentWeatherEntity
import com.anshtya.weatherapp.data.local.entity.WeatherForecastEntity
import com.anshtya.weatherapp.data.local.entity.WeatherLocationEntity
import com.anshtya.weatherapp.data.local.model.WeatherModel
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT id FROM current_weather WHERE locationId = :locationId")
    suspend fun getCurrentWeatherId(locationId: String): Long

    @Query("SELECT id FROM weather_forecast WHERE locationId = :locationId")
    suspend fun getWeatherForecastIds(locationId: String): List<Long>

    @Transaction
    @Query("SELECT * FROM weather_location")
    fun getWeather(): Flow<List<WeatherModel>>

    @Query("SELECT EXISTS(SELECT 1 FROM weather_location where id =:locationId)")
    suspend fun checkWeatherExist(locationId: String): Boolean

    @Upsert
    suspend fun upsertWeatherLocation(weatherLocation: WeatherLocationEntity)

    @Upsert
    suspend fun upsertCurrentWeather(currentWeather: CurrentWeatherEntity)

    @Upsert
    suspend fun upsertWeatherForecast(weatherForecast: List<WeatherForecastEntity>)

    @Transaction
    suspend fun upsertWeather(
        weatherLocation: WeatherLocationEntity,
        currentWeather: CurrentWeatherEntity,
        weatherForecast: List<WeatherForecastEntity>
    ) {
        upsertWeatherLocation(weatherLocation)
        upsertCurrentWeather(currentWeather)
        upsertWeatherForecast(weatherForecast)
    }
}