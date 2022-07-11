package com.example.weatherapp.viewmodel

import com.example.weatherapp.domain.Weather

sealed class MainState {

    object Loading : MainState()
    data class Success(val weatherData: List<Weather>) : MainState()
    data class Error(val error: String) : MainState()
}