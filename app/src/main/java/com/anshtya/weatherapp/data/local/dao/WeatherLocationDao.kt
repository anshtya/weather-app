package com.anshtya.weatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherLocationDao {

    @Query("SELECT id FROM weather_location")
    fun getLocationIds(): Flow<List<String>>

    @Query("SELECT EXISTS(SELECT 1 FROM weather_location)")
    fun checkIfTableEmpty(): Flow<Boolean>

    @Query("DELETE FROM weather_location where id =:locationId")
    suspend fun deleteWeatherLocation(locationId: String)
}