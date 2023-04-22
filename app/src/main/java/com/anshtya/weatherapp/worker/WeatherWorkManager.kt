package com.anshtya.weatherapp.worker

import androidx.lifecycle.asFlow
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkInfo.State
import androidx.work.WorkManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transformWhile
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WeatherWorkManager @Inject constructor(
    private val workManager: WorkManager
) {
    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    /*
    * Function to update weather periodically
    */
    fun updateWeather(): UUID {
        val updateWeatherWorkRequest =
            PeriodicWorkRequestBuilder<UpdateWeatherWorker>(1, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()
        workManager.enqueueUniquePeriodicWork(
            UPDATE_WORK,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            updateWeatherWorkRequest
        )
        return updateWeatherWorkRequest.id
    }

    /*
    * Function to refresh weather by user
    */
    fun refreshWeather(): UUID {
        val refreshWeatherWorkRequest = OneTimeWorkRequestBuilder<UpdateWeatherWorker>()
            .setConstraints(constraints)
            .build()
        workManager.enqueueUniqueWork(
            REFRESH_WORK,
            ExistingWorkPolicy.REPLACE,
            refreshWeatherWorkRequest
        )
        return refreshWeatherWorkRequest.id
    }

    /*
    * SUCCEEDED, FAILED and CANCELLED all represent a terminal state for this work
    */
    fun observeWork(workId: UUID): Flow<WorkInfo> {
        return workManager.getWorkInfoByIdLiveData(workId).asFlow()
            .transformWhile {
                emit(it)
                it.state !in listOf(State.SUCCEEDED, State.FAILED, State.CANCELLED)
            }
    }

    companion object {
        const val REFRESH_WORK = "refresh-weather"
        const val UPDATE_WORK = "update-weather"
    }
}