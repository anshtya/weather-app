package com.anshtya.weatherapp.domain.model

sealed class TableState {
    object Empty: TableState()
    object NotEmpty: TableState()
    object Loading: TableState()
}