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
    suspend fun getWeatherForecastId(locationId: String): Long

    @Transaction
    @Query("SELECT * FROM weather_location")
    fun getWeather(): Flow<List<WeatherModel>>

    @Query("SELECT EXISTS(SELECT 1 FROM weather_location where id =:locationId)")
    suspend fun checkWeatherExist(locationId: String): Boolean

    @Insert
    suspend fun insertWeatherLocation(weatherLocation: WeatherLocationEntity)

    @Insert
    suspend fun insertCurrentWeather(currentWeather: CurrentWeatherEntity)

    @Insert
    suspend fun insertWeatherForecast(weatherForecast: WeatherForecastEntity)

    @Update
    suspend fun updateWeatherLocation(weatherLocation: WeatherLocationEntity)

    @Update
    suspend fun updateCurrentWeather(currentWeather: CurrentWeatherEntity)

    @Update
    suspend fun updateWeatherForecast(weatherForecast: WeatherForecastEntity)

    @Transaction
    suspend fun insertWeather(
        weatherLocation: WeatherLocationEntity,
        currentWeather: CurrentWeatherEntity,
        weatherForecast: WeatherForecastEntity
    ) {
        insertWeatherLocation(weatherLocation)
        insertCurrentWeather(currentWeather)
        insertWeatherForecast(weatherForecast)
    }

    @Transaction
    suspend fun updateWeather(
        weatherLocation: WeatherLocationEntity,
        currentWeather: CurrentWeatherEntity,
        weatherForecast: WeatherForecastEntity
    ) {
        updateWeatherLocation(weatherLocation)
        updateCurrentWeather(currentWeather)
        updateWeatherForecast(weatherForecast)
    }
}