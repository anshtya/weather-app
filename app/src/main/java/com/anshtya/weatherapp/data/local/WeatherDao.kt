package com.anshtya.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.anshtya.weatherapp.data.local.entity.CurrentWeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

   @Query("SELECT * FROM current_weather")
   fun getCurrentWeather(): Flow<CurrentWeatherEntity>

   @Insert
   suspend fun insertCurrentWeather(currentWeather: CurrentWeatherEntity)
}