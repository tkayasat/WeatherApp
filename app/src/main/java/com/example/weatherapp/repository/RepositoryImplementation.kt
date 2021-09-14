package com.example.weatherapp.repository

import com.example.weatherapp.domain.Weather
import com.example.weatherapp.domain.getRussianCities
import com.example.weatherapp.domain.getWorldCities

class RepositoryImplementation : Repository {
    override fun getWeatherFromRemoteSource() = Weather()

    override fun getWeatherFromLocalSource() = Weather()

    override fun getWeatherFromLocalStorageRussian() = getRussianCities()

    override fun getWeatherFromLocalStorageWorld() = getWorldCities()
}