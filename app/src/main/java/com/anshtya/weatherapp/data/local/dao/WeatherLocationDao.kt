package com.anshtya.weatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.anshtya.weatherapp.data.local.entity.WeatherLocationEntity

@Dao
interface WeatherLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherLocation(weatherLocationEntity: WeatherLocationEntity)
}