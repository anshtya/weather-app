package com.anshtya.weatherapp.data.local.model

import androidx.room.Embedded
import androidx.room.Relation
import com.anshtya.weatherapp.data.local.entity.CurrentWeatherEntity
import com.anshtya.weatherapp.data.local.entity.WeatherForecastEntity
import com.anshtya.weatherapp.data.local.entity.WeatherLocationEntity

data class WeatherModel(
    @Embedded val weatherLocation: WeatherLocationEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "locationId"
    )
    val currentWeather: CurrentWeatherEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "locationId"
    )
    val weatherForecast: List<WeatherForecastEntity>
)
