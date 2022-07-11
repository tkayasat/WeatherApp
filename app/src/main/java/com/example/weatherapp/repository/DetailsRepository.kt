package com.example.weatherapp.repository

interface DetailsRepository {
    fun getWeatherDetailsFromServer(
        lat: Double,
        log: Double,
        callback: retrofit2.Callback<WeatherDTO>,
    )
}