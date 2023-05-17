package com.anshtya.weatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.anshtya.weatherapp.data.local.model.SavedLocationModel
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherLocationDao {

    @Query("SELECT id FROM weather_location")
    fun getLocationIds(): Flow<List<String>>

    @Query("SELECT EXISTS(SELECT 1 FROM weather_location LIMIT 1)")
    fun checkIfTableEmpty(): Flow<Boolean>

    @Query(
        "SELECT name, region, country, tempC, tempF, maxTempC, maxTempF, minTempC, minTempF, " +
                "code, text FROM weather_location " +
                "INNER JOIN current_weather ON current_weather.locationId = weather_location.id " +
                "INNER JOIN weather_forecast ON weather_forecast.locationId = weather_location.id "
    )
    fun getSavedWeatherLocations(): Flow<List<SavedLocationModel>>

    @Query("DELETE FROM weather_location where id =:locationId")
    suspend fun deleteWeatherLocation(locationId: String)
}