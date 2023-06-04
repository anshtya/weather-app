package com.anshtya.weatherapp.data.local

import androidx.room.TypeConverter
import com.anshtya.weatherapp.domain.model.Astro
import com.anshtya.weatherapp.data.local.model.HourModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Converters {
    private val moshi = Moshi.Builder().build()
    private val astroJsonAdapter: JsonAdapter<Astro> = moshi.adapter(Astro::class.java)
    private val listJsonAdapter: JsonAdapter<List<HourModel>> = moshi.adapter(
        Types.newParameterizedType(List::class.java, HourModel::class.java)
    )

    @TypeConverter
    fun astroToString(astro: Astro): String {
        return astroJsonAdapter.toJson(astro)
    }

    @TypeConverter
    fun stringToAstro(json: String): Astro? {
        return astroJsonAdapter.fromJson(json)
    }

    @TypeConverter
    fun hourListToString(hourList: List<HourModel>): String {
        return listJsonAdapter.toJson(hourList)
    }

    @TypeConverter
    fun stringToHourList(json: String): List<HourModel>? {
        return listJsonAdapter.fromJson(json)
    }
}