package com.anshtya.weatherapp.data.local.dao

import androidx.room.*
import com.anshtya.weatherapp.data.local.entity.CurrentWeatherEntity

@Dao
interface CurrentWeatherDao {

   @Query("SELECT * FROM current_weather")
   suspend fun getCurrentWeather(): CurrentWeatherEntity

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertCurrentWeather(currentWeather: CurrentWeatherEntity)

   @Update
   suspend fun updateCurrentWeather(currentWeather: CurrentWeatherEntity)
}