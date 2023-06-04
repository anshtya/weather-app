package com.anshtya.weatherapp.domain.model

import androidx.annotation.DrawableRes
import com.anshtya.weatherapp.R

sealed class WeatherType(
    val weatherDescription: String,
    @DrawableRes val dayIconRes: Int,
    @DrawableRes val nightIconRes: Int,
) {

    object Clear : WeatherType(
        weatherDescription = "Clear",
        dayIconRes = R.drawable.day_113,
        nightIconRes = R.drawable.night_113
    )

    object PartlyCloudy : WeatherType(
        weatherDescription = "Partly Cloudy",
        dayIconRes = R.drawable.day_116,
        nightIconRes = R.drawable.night_116
    )

    object Cloudy : WeatherType(
        weatherDescription = "Cloudy",
        dayIconRes = R.drawable.day_119,
        nightIconRes = R.drawable.night_119
    )

    object Overcast : WeatherType(
        weatherDescription = "Overcast",
        dayIconRes = R.drawable.day_122,
        nightIconRes = R.drawable.night_122
    )

    object Mist : WeatherType(
        weatherDescription = "Mist",
        dayIconRes = R.drawable.day_143,
        nightIconRes = R.drawable.night_143
    )

    object PatchyRain : WeatherType(
        weatherDescription = "Patchy rain possible",
        dayIconRes = R.drawable.day_176,
        nightIconRes = R.drawable.night_176
    )

    object PatchySnow : WeatherType(
        weatherDescription = "Patchy snow possible",
        dayIconRes = R.drawable.day_179,
        nightIconRes = R.drawable.night_179
    )

    object PatchySleet : WeatherType(
        weatherDescription = "Patchy sleet possible",
        dayIconRes = R.drawable.day_182,
        nightIconRes = R.drawable.night_182
    )

    object PatchyFreezingDrizzle : WeatherType(
        weatherDescription = "Patchy freezing drizzle possible",
        dayIconRes = R.drawable.day_185,
        nightIconRes = R.drawable.night_185
    )

    object ThunderyOutbreaks : WeatherType(
        weatherDescription = "Thundery outbreaks possible",
        dayIconRes = R.drawable.day_200,
        nightIconRes = R.drawable.night_200
    )

    object BlowingSnow : WeatherType(
        weatherDescription = "Blowing snow",
        dayIconRes = R.drawable.day_227,
        nightIconRes = R.drawable.night_227
    )

    object Blizzard : WeatherType(
        weatherDescription = "Blizzard",
        dayIconRes = R.drawable.day_230,
        nightIconRes = R.drawable.night_230
    )

    object Fog : WeatherType(
        weatherDescription = "Fog",
        dayIconRes = R.drawable.day_248,
        nightIconRes = R.drawable.night_248
    )

    object FreezingFog : WeatherType(
        weatherDescription = "Freezing fog",
        dayIconRes = R.drawable.day_260,
        nightIconRes = R.drawable.night_260
    )

    object PatchyLightDrizzle : WeatherType(
        weatherDescription = "Patchy Light Drizzle",
        dayIconRes = R.drawable.day_263,
        nightIconRes = R.drawable.night_263
    )

    object LightDrizzle : WeatherType(
        weatherDescription = "Light Drizzle",
        dayIconRes = R.drawable.day_266,
        nightIconRes = R.drawable.night_266
    )

    object FreezingDrizzle : WeatherType(
        weatherDescription = "Freezing drizzle",
        dayIconRes = R.drawable.day_281,
        nightIconRes = R.drawable.night_281
    )

    object HeavyFreezingDrizzle : WeatherType(
        weatherDescription = "Heavy freezing drizzle",
        dayIconRes = R.drawable.day_284,
        nightIconRes = R.drawable.night_284
    )

    object PatchyLightRain : WeatherType(
        weatherDescription = "Patchy light rain",
        dayIconRes = R.drawable.day_293,
        nightIconRes = R.drawable.night_293
    )

    object LightRain : WeatherType(
        weatherDescription = "Light rain",
        dayIconRes = R.drawable.day_296,
        nightIconRes = R.drawable.night_296
    )


    object ModerateRainTimes : WeatherType(
        weatherDescription = "Moderate rain at times",
        dayIconRes = R.drawable.day_299,
        nightIconRes = R.drawable.night_299
    )

    object ModerateRain : WeatherType(
        weatherDescription = "Moderate rain",
        dayIconRes = R.drawable.day_302,
        nightIconRes = R.drawable.night_302
    )

    object HeavyRainTimes : WeatherType(
        weatherDescription = "Heavy rain at times",
        dayIconRes = R.drawable.day_305,
        nightIconRes = R.drawable.night_305
    )

    object HeavyRain : WeatherType(
        weatherDescription = "Heavy rain",
        dayIconRes = R.drawable.day_308,
        nightIconRes = R.drawable.night_308
    )

    object LightFreezingRain : WeatherType(
        weatherDescription = "Light freezing rain",
        dayIconRes = R.drawable.day_311,
        nightIconRes = R.drawable.night_311
    )

    object ModerateHeavyFreezingRain : WeatherType(
        weatherDescription = "Moderate or heavy freezing rain",
        dayIconRes = R.drawable.day_314,
        nightIconRes = R.drawable.night_314
    )

    object LightSleet : WeatherType(
        weatherDescription = "Light sleet",
        dayIconRes = R.drawable.day_317,
        nightIconRes = R.drawable.night_317
    )

    object ModerateHeavySleet : WeatherType(
        weatherDescription = "Moderate or heavy sleet",
        dayIconRes = R.drawable.day_320,
        nightIconRes = R.drawable.night_320
    )

    object PatchyLightSnow : WeatherType(
        weatherDescription = "Patchy light snow",
        dayIconRes = R.drawable.day_323,
        nightIconRes = R.drawable.night_323
    )

    object LightSnow : WeatherType(
        weatherDescription = "Light snow",
        dayIconRes = R.drawable.day_326,
        nightIconRes = R.drawable.night_326
    )

    object PatchyModerateSnow : WeatherType(
        weatherDescription = "Patchy moderate snow",
        dayIconRes = R.drawable.day_329,
        nightIconRes = R.drawable.night_329
    )

    object ModerateSnow : WeatherType(
        weatherDescription = "Moderate snow",
        dayIconRes = R.drawable.day_332,
        nightIconRes = R.drawable.night_332
    )

    object PatchyHeavySnow : WeatherType(
        weatherDescription = "Patchy heavy snow",
        dayIconRes = R.drawable.day_335,
        nightIconRes = R.drawable.night_335
    )

    object HeavySnow : WeatherType(
        weatherDescription = "Heavy snow",
        dayIconRes = R.drawable.day_338,
        nightIconRes = R.drawable.night_338
    )

    object IcePellets : WeatherType(
        weatherDescription = "Ice pellets",
        dayIconRes = R.drawable.day_350,
        nightIconRes = R.drawable.night_350
    )

    object LightRainShower : WeatherType(
        weatherDescription = "Light rain shower",
        dayIconRes = R.drawable.day_353,
        nightIconRes = R.drawable.night_353
    )

    object ModerateHeavyRainShower : WeatherType(
        weatherDescription = "Moderate or heavy rain shower",
        dayIconRes = R.drawable.day_356,
        nightIconRes = R.drawable.night_356
    )

    object TorrentialRainShower : WeatherType(
        weatherDescription = "Torrential rain shower",
        dayIconRes = R.drawable.day_359,
        nightIconRes = R.drawable.night_359
    )

    object LightSleetShowers : WeatherType(
        weatherDescription = "Light sleet showers",
        dayIconRes = R.drawable.day_362,
        nightIconRes = R.drawable.night_362
    )

    object ModerateHeavySleetShowers : WeatherType(
        weatherDescription = "Moderate or heavy sleet showers",
        dayIconRes = R.drawable.day_365,
        nightIconRes = R.drawable.night_365
    )

    object LightSnowShowers : WeatherType(
        weatherDescription = "Light snow showers",
        dayIconRes = R.drawable.day_368,
        nightIconRes = R.drawable.night_368
    )

    object ModerateHeavySnowShowers : WeatherType(
        weatherDescription = "Moderate or heavy snow showers",
        dayIconRes = R.drawable.day_371,
        nightIconRes = R.drawable.night_371
    )

    object LightShowersIcePellets : WeatherType(
        weatherDescription = "Light showers of ice pellets",
        dayIconRes = R.drawable.day_374,
        nightIconRes = R.drawable.night_374
    )

    object ModerateHeavyShowersIcePellets : WeatherType(
        weatherDescription = "Moderate or heavy showers of ice pellets",
        dayIconRes = R.drawable.day_377,
        nightIconRes = R.drawable.night_377
    )

    object PatchyLightRainThunder : WeatherType(
        weatherDescription = "Patchy light rain with thunder",
        dayIconRes = R.drawable.day_386,
        nightIconRes = R.drawable.night_386
    )

    object ModerateHeavyRainThunder : WeatherType(
        weatherDescription = "Moderate or heavy rain with thunder",
        dayIconRes = R.drawable.day_389,
        nightIconRes = R.drawable.night_389
    )

    object PatchyLightSnowThunder : WeatherType(
        weatherDescription = "Patchy light snow with thunder",
        dayIconRes = R.drawable.day_392,
        nightIconRes = R.drawable.night_392
    )

    object ModerateHeavySnowThunder : WeatherType(
        weatherDescription = "Moderate or heavy snow with thunder",
        dayIconRes = R.drawable.day_395,
        nightIconRes = R.drawable.night_395
    )

    companion object {
        fun fromWeatherCondition(code: Int): WeatherType {
            return when (code) {
                1000 -> Clear
                1003 -> PartlyCloudy
                1006 -> Cloudy
                1009 -> Overcast
                1030 -> Mist
                1063 -> PatchyRain
                1066 -> PatchySnow
                1069 -> PatchySleet
                1072 -> PatchyFreezingDrizzle
                1087 -> ThunderyOutbreaks
                1114 -> BlowingSnow
                1117 -> Blizzard
                1135 -> Fog
                1147 -> FreezingFog
                1150 -> PatchyLightDrizzle
                1153 -> LightDrizzle
                1168 -> FreezingDrizzle
                1171 -> HeavyFreezingDrizzle
                1180 -> PatchyLightRain
                1183 -> LightRain
                1186 -> ModerateRainTimes
                1189 -> ModerateRain
                1192 -> HeavyRainTimes
                1195 -> HeavyRain
                1198 -> LightFreezingRain
                1201 -> ModerateHeavyFreezingRain
                1204 -> LightSleet
                1207 -> ModerateHeavySleet
                1210 -> PatchyLightSnow
                1213 -> LightSnow
                1216 -> PatchyModerateSnow
                1219 -> ModerateSnow
                1222 -> PatchyHeavySnow
                1225 -> HeavySnow
                1237 -> IcePellets
                1240 -> LightRainShower
                1243 -> ModerateHeavyRainShower
                1246 -> TorrentialRainShower
                1249 -> LightSleetShowers
                1252 -> ModerateHeavySleetShowers
                1255 -> LightSnowShowers
                1258 -> ModerateHeavySnowShowers
                1261 -> LightShowersIcePellets
                1264 -> ModerateHeavyShowersIcePellets
                1273 -> PatchyLightRainThunder
                1276 -> ModerateHeavyRainThunder
                1279 -> PatchyLightSnowThunder
                1282 -> ModerateHeavySnowThunder
                else -> Clear
            }
        }
    }
}