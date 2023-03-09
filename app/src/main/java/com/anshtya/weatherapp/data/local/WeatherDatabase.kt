package com.anshtya.weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anshtya.weatherapp.data.model.Converters

@Database(
    entities = [WeatherDao::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun getWeatherDao()
}