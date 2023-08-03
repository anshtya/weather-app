package com.anshtya.weatherapp.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.anshtya.weatherapp.domain.model.Astro
import com.anshtya.weatherapp.domain.model.Day
import com.anshtya.weatherapp.data.local.model.HourModel

@Entity(
    tableName = "weather_forecast",
    indices = [Index(value = arrayOf("locationId"))],
    foreignKeys = [ForeignKey(
        entity = WeatherLocationEntity::class,
        parentColumns = ["id"],
        childColumns = ["locationId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class WeatherForecastEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val locationId: String,
    val dateEpoch: Int,
    val astro: Astro,
    @Embedded val day: Day,
    val hour: List<HourModel>
)
