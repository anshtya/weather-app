package com.anshtya.weatherapp.data.local

import androidx.room.TypeConverter
import com.anshtya.weatherapp.data.remote.dto.WeatherConditionDto

class Converters {

    @TypeConverter
    fun fromWeatherCondition(weatherCondition: WeatherConditionDto): String {
        return "${weatherCondition.icon.removePrefix("//")}, ${weatherCondition.text}"
    }

    @TypeConverter
    fun fromWeatherConditionText(weatherCondition: String): WeatherConditionDto {
        val properties = weatherCondition.split(",")
        return WeatherConditionDto(icon = properties[0], text = properties[1])
    }

}