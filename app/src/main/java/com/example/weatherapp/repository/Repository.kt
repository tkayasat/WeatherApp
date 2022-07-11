package com.example.weatherapp.repository

import com.example.weatherapp.domain.Weather

interface Repository {
    fun getWeatherFromRemoteSource(): Weather
    fun getWeatherFromLocalSource(): Weather
    fun getWeatherFromLocalStorageRussian(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>
}