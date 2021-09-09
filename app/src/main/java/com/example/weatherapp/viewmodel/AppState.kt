package com.example.weatherapp.viewmodel

import com.example.weatherapp.domain.Weather

sealed class AppState {

    object Loading : AppState()
    data class Success(val weatherDate: List <Weather>) : AppState()
    data class Error(val error: Throwable) : AppState()

}
