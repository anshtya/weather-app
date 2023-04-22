package com.anshtya.weatherapp.di

import android.content.Context
import androidx.work.WorkManager
import com.anshtya.weatherapp.util.NotificationUtil
import com.anshtya.weatherapp.worker.WeatherWorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideWeatherWorkManager(workManager: WorkManager): WeatherWorkManager {
        return WeatherWorkManager(workManager)
    }

    @Singleton
    @Provides
    fun provideNotificationUtil(@ApplicationContext context: Context): NotificationUtil {
        return NotificationUtil(context)
    }
}