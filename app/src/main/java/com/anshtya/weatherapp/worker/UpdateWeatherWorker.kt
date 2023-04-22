package com.anshtya.weatherapp.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.anshtya.weatherapp.domain.useCase.UpdateWeatherUseCase
import com.anshtya.weatherapp.util.NotificationUtil
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class UpdateWeatherWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val updateWeatherUseCase: UpdateWeatherUseCase,
    private val notificationUtil: NotificationUtil
): CoroutineWorker(context, params){

    override suspend fun doWork(): Result {
        return try {
            updateWeatherUseCase()
            notificationUtil.makeNotification("Weather Updated")
            Result.success()
        } catch(e: Error) {
            Result.failure()
        }
    }
}