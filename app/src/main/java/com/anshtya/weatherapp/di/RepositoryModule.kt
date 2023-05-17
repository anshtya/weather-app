package com.anshtya.weatherapp.di

import com.anshtya.weatherapp.data.datastore.UserPreferencesDataSource
import com.anshtya.weatherapp.data.local.dao.WeatherDao
import com.anshtya.weatherapp.data.local.dao.WeatherLocationDao
import com.anshtya.weatherapp.data.remote.WeatherApi
import com.anshtya.weatherapp.data.repository.LocationRepositoryImpl
import com.anshtya.weatherapp.data.repository.UserDataRepositoryImpl
import com.anshtya.weatherapp.data.repository.WeatherRepositoryImpl
import com.anshtya.weatherapp.domain.repository.LocationRepository
import com.anshtya.weatherapp.domain.repository.UserDataRepository
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
    fun provideLocationRepository(
        api: WeatherApi,
        dao: WeatherDao,
        weatherLocationDao: WeatherLocationDao
    ): LocationRepository {
        return LocationRepositoryImpl(api, dao, weatherLocationDao)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        api: WeatherApi,
        currentWeatherDao: WeatherDao,
        weatherLocationDao: WeatherLocationDao
    ): WeatherRepository {
        return WeatherRepositoryImpl(api, currentWeatherDao, weatherLocationDao)
    }

    @Provides
    @Singleton
    fun provideUserDataRepository(userPreferencesDataSource: UserPreferencesDataSource): UserDataRepository {
        return UserDataRepositoryImpl(userPreferencesDataSource)
    }
}