package com.anshtya.weatherapp.di

import com.anshtya.weatherapp.domain.repository.LocationRepository
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import com.anshtya.weatherapp.domain.useCase.GetSavedLocationUseCase
import com.anshtya.weatherapp.domain.useCase.GetSearchResultUseCase
import com.anshtya.weatherapp.domain.useCase.GetWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideSearchResultUseCase(repository: LocationRepository): GetSearchResultUseCase {
        return GetSearchResultUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSavedLocationUseCase(repository: LocationRepository): GetSavedLocationUseCase {
        return GetSavedLocationUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideWeatherUseCase(repository: WeatherRepository): GetWeatherUseCase {
        return GetWeatherUseCase(repository)
    }
}