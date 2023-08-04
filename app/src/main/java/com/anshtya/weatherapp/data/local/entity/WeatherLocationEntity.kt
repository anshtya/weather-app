package com.anshtya.weatherapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_location")
data class WeatherLocationEntity(
    @PrimaryKey
    val id: String,
    val country: String,
    val timezoneId: String,
    val localtimeEpoch: Int,
    val name: String,
    val region: String,
)