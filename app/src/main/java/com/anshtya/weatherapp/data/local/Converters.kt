package com.anshtya.weatherapp.data.local

import androidx.room.TypeConverter
import com.anshtya.weatherapp.core.model.Astro
import com.anshtya.weatherapp.core.model.Hour
import com.anshtya.weatherapp.core.model.WeatherCondition
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Converters {
    private val moshi = Moshi.Builder().build()
    private val astroJsonAdapter: JsonAdapter<Astro> = moshi.adapter(Astro::class.java)
    private val listJsonAdapter: JsonAdapter<List<Hour>> = moshi.adapter(
        Types.newParameterizedType(List::class.java, Hour::class.java)
    )
    private val weatherConditionJsonAdapter: JsonAdapter<WeatherCondition> =
        moshi.adapter(WeatherCondition::class.java)

    @TypeConverter
    fun astroToString(astro: Astro): String {
        return astroJsonAdapter.toJson(astro)
    }

    @TypeConverter
    fun stringToAstro(json: String): Astro {
        return astroJsonAdapter.fromJson(json)!!
    }

    @TypeConverter
    fun hourListToString(hourList: List<Hour>): String {
        return listJsonAdapter.toJson(hourList)
    }

    @TypeConverter
    fun stringToHourList(json: String): List<Hour> {
        return listJsonAdapter.fromJson(json)!!
    }

    @TypeConverter
    fun weatherConditionToString(weatherCondition: WeatherCondition): String {
        return weatherConditionJsonAdapter.toJson(weatherCondition)
    }

    @TypeConverter
    fun stringToWeatherCondition(json: String): WeatherCondition {
        return weatherConditionJsonAdapter.fromJson(json)!!
    }
}