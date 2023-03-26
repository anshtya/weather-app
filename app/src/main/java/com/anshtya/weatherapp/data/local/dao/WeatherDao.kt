package com.anshtya.weatherapp.data.local.dao


import androidx.room.*
import com.anshtya.weatherapp.data.local.entity.WeatherEntity

@Dao
interface WeatherDao {

   @Query("SELECT * FROM weather")
   suspend fun getCurrentWeather(): WeatherEntity

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertCurrentWeather(currentWeather: WeatherEntity)

   @Update
   suspend fun updateCurrentWeather(currentWeather: WeatherEntity)
}