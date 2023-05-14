package com.anshtya.weatherapp.data.local.model

import androidx.room.Relation
import com.anshtya.weatherapp.data.local.entity.CurrentWeatherEntity
import com.anshtya.weatherapp.data.local.entity.WeatherForecastEntity

data class WeatherModel(
    val id: String,
    val name: String,

    @Relation(
        parentColumn = "id",
        entityColumn = "locationId"
    )
    val currentWeather: CurrentWeatherEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "locationId"
    )
    val weatherForecast: WeatherForecastEntity
)
