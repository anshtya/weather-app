package com.anshtya.weatherapp.di

import com.anshtya.weatherapp.data.local.dao.WeatherDao
import com.anshtya.weatherapp.data.remote.WeatherApi
import com.anshtya.weatherapp.data.repository.LocationRepositoryImpl
import com.anshtya.weatherapp.data.repository.WeatherRepositoryImpl
import com.anshtya.weatherapp.domain.repository.LocationRepository
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideLocationRepository(api: WeatherApi, dao: WeatherDao): LocationRepository {
        return LocationRepositoryImpl(api, dao)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        api: WeatherApi,
        currentWeatherDao: WeatherDao
    ): WeatherRepository {
        return WeatherRepositoryImpl(api, currentWeatherDao)
    }
}