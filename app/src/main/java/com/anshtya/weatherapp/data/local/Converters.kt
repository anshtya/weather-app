package com.anshtya.weatherapp.data.local

import androidx.room.TypeConverter
import com.anshtya.weatherapp.core.model.WeatherCondition

class Converters {

    @TypeConverter
    fun fromWeatherCondition(weatherCondition: WeatherCondition): String {
        return "${weatherCondition.icon.removePrefix("//")}, ${weatherCondition.text}"
    }

    @TypeConverter
    fun fromWeatherConditionText(weatherCondition: String): WeatherCondition {
        val properties = weatherCondition.split(",")
        return WeatherCondition(icon = properties[0], text = properties[1])
    }

}