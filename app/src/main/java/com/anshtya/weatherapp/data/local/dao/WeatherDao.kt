package com.anshtya.weatherapp.data.local.dao

import androidx.room.*
import com.anshtya.weatherapp.data.local.entity.WeatherEntity
import com.anshtya.weatherapp.data.local.model.SavedLocationModel
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

   @Query("SELECT EXISTS(SELECT 1 FROM weather LIMIT 1)")
   fun checkIfTableEmpty(): Flow<Boolean>

   @Query("SELECT name, region, country, last_updated, temp_c, condition FROM weather")
   fun getSavedWeatherLocations(): Flow<List<SavedLocationModel>>

   @Query("SELECT * FROM weather")
   fun getWeather(): Flow<List<WeatherEntity>>

   @Query("SELECT * FROM weather where id =:locationId")
   suspend fun getWeatherById(locationId: String): WeatherEntity?

   @Query("SELECT EXISTS(SELECT 1 FROM weather where id =:locationId)")
   suspend fun checkWeatherExist(locationId: String): Boolean

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertCurrentWeather(currentWeather: WeatherEntity)

   @Update
   suspend fun updateCurrentWeather(currentWeather: List<WeatherEntity>)

   @Query("DELETE FROM weather where id =:locationId")
   suspend fun deleteWeatherLocation(locationId: String)
}