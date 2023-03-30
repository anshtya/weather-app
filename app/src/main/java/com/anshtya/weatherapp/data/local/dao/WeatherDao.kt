package com.anshtya.weatherapp.data.local.dao

import androidx.room.*
import com.anshtya.weatherapp.data.local.entity.WeatherEntity
import com.anshtya.weatherapp.data.local.model.SavedLocationModel
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

   @Query("SELECT name, region, country, last_updated, temp_c, condition FROM weather")
   fun getSavedWeatherLocations(): Flow<List<SavedLocationModel>>

   @Query("SELECT * FROM weather where id =:locationId")
   suspend fun getWeatherById(locationId: String): WeatherEntity?

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertCurrentWeather(currentWeather: WeatherEntity)

   @Update
   suspend fun updateCurrentWeather(currentWeather: WeatherEntity)
}