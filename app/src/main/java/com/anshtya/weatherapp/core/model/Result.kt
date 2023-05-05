package com.anshtya.weatherapp.core.model

sealed class Result<out T> {
//    object Loading : Result<Nothing>()
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val message: String?): Result<Nothing>()
}
