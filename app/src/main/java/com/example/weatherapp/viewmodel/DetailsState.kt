package com.example.weatherapp.viewmodel

import com.example.weatherapp.domain.Weather

sealed class DetailsState {
    object Loading : DetailsState()
    data class Success(val weatherData: Weather) : DetailsState()
    data class Error(val error: String) : DetailsState()
}