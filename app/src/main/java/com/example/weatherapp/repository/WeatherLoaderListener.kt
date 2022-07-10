package com.example.weatherapp.repository

interface WeatherLoaderListener {
    fun onLoaded(weatherDTO: WeatherDTO)
    fun onFailed(throwable: Throwable)
}