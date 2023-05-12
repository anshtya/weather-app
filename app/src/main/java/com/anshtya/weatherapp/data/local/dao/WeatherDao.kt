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

    @Query("SELECT EXISTS(SELECT 1 FROM weather_location LIMIT 1)")
    fun checkIfTableEmpty(): Flow<Boolean>

    @Query(
        "SELECT name, region, country, tempC, tempF, maxTempC, maxTempF, minTempC, minTempF, " +
                "condition FROM weather_location " +
                "INNER JOIN current_weather ON current_weather.locationId = weather_location.id " +
                "INNER JOIN weather_forecast ON weather_forecast.locationId = weather_location.id "
    )
    fun getSavedWeatherLocations(): Flow<List<SavedLocationModel>>

    @Query("SELECT id FROM weather_location")
    fun getLocationIds(): Flow<List<String>>

    @Transaction
    @Query(
        "SELECT weather_location.id, name, current_weather.*, weather_forecast.* FROM weather_location " +
                "INNER JOIN current_weather ON weather_location.id = current_weather.locationId " +
                "INNER JOIN weather_forecast ON weather_location.id = weather_forecast.locationId"
    )
    fun getWeather(): Flow<List<WeatherModel>>

//    @Query("SELECT * FROM weather where id =:locationId")
//    suspend fun getWeatherById(locationId: String): WeatherEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM weather_location where id =:locationId)")
    suspend fun checkWeatherExist(locationId: String): Boolean

    @Insert
    suspend fun insertWeatherLocation(weatherLocation: WeatherLocationEntity)

    @Insert
    suspend fun insertCurrentWeather(currentWeather: CurrentWeatherEntity)

    @Insert
    suspend fun insertWeatherForecast(weatherForecast: WeatherForecastEntity)

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
        currentWeather: CurrentWeatherEntity,
        weatherForecast: WeatherForecastEntity
    ) {
        updateCurrentWeather(currentWeather)
        updateWeatherForecast(weatherForecast)
    }

    @Query("DELETE FROM weather_location where id =:locationId")
    suspend fun deleteWeatherLocation(locationId: String)
}