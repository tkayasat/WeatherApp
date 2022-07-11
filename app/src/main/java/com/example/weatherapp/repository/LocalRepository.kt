package com.example.weatherapp.repository

import com.example.weatherapp.domain.Weather

interface LocalRepository {
    fun getAllHistory(): List<Weather>
    fun saveEntity(weather: Weather)
}