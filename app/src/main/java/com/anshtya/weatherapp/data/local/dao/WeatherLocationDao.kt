package com.anshtya.weatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anshtya.weatherapp.data.local.entity.WeatherLocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherLocationDao {
    @Query("SELECT * FROM weather_location")
    fun getWeatherLocations(): Flow<List<WeatherLocationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherLocation(weatherLocationEntity: WeatherLocationEntity)
}