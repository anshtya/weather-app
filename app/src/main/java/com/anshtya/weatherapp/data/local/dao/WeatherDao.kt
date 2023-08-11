package com.anshtya.weatherapp.data.local.dao

import androidx.room.*
import com.anshtya.weatherapp.data.local.entity.CurrentWeatherEntity
import com.anshtya.weatherapp.data.local.entity.WeatherForecastEntity
import com.anshtya.weatherapp.data.local.entity.WeatherLocationEntity
import com.anshtya.weatherapp.data.local.model.SavedLocationModel
import com.anshtya.weatherapp.data.local.model.WeatherModel
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT id FROM weather_location")
    suspend fun getLocationIds(): List<String>

    @Query("SELECT id FROM current_weather WHERE locationId = :locationId")
    suspend fun getCurrentWeatherId(locationId: String): Long

    @Query("SELECT id FROM weather_forecast WHERE locationId = :locationId")
    suspend fun getWeatherForecastIds(locationId: String): List<Long>

    @Transaction
    @Query("SELECT * FROM weather_location")
    fun getWeather(): Flow<List<WeatherModel>>

    @Query(
        value = """
            WITH current_forecast AS (
                SELECT * FROM weather_forecast WHERE id IN (
                    SELECT MIN(id) FROM weather_forecast GROUP BY locationId 
                )
            )
        
            SELECT weather_location.id, country, name, region,  current_weather.code, current_weather.isDay,
            current_weather.tempC,  current_weather.tempF,  current_forecast.maxTempC,  current_forecast.maxTempF, 
            current_forecast. minTempC,  current_forecast.minTempF FROM weather_location
            JOIN current_weather ON current_weather.locationId = weather_location.id
            JOIN current_forecast ON current_forecast.locationId = weather_location.id
    """
    )
    fun getSavedLocations(): Flow<List<SavedLocationModel>>

    @Query("SELECT EXISTS(SELECT 1 FROM weather_location where id =:locationId)")
    suspend fun checkWeatherExist(locationId: String): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM weather_location)")
    fun checkTableNotEmpty(): Flow<Boolean>

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

    @Query("DELETE FROM weather_location where id =:locationId")
    suspend fun deleteWeatherLocation(locationId: String)
}