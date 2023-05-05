package com.anshtya.weatherapp.di

import com.anshtya.weatherapp.domain.repository.LocationRepository
import com.anshtya.weatherapp.domain.repository.UserDataRepository
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import com.anshtya.weatherapp.domain.useCase.AddLocationUseCase
import com.anshtya.weatherapp.domain.useCase.GetSavedLocationUseCase
import com.anshtya.weatherapp.domain.useCase.GetSearchResultUseCase
import com.anshtya.weatherapp.domain.useCase.GetWeatherUseCase
import com.anshtya.weatherapp.domain.useCase.UpdateWeatherUseCase
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
    fun provideAddLocationUseCase(repository: LocationRepository): AddLocationUseCase {
        return AddLocationUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideWeatherUseCase(weatherRepository: WeatherRepository, userDataRepository: UserDataRepository): GetWeatherUseCase {
        return GetWeatherUseCase(weatherRepository, userDataRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateWeatherUseCase(repository: WeatherRepository): UpdateWeatherUseCase {
        return UpdateWeatherUseCase(repository)
    }
}