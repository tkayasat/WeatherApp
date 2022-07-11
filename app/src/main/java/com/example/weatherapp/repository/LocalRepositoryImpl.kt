package com.example.weatherapp.repository

import com.example.weatherapp.domain.Weather
import com.example.weatherapp.room.HistoryDAO
import convertHistoryEntityToWeather
import convertWeatherToHistoryEntity

class LocalRepositoryImpl(
    private val localDataSource: HistoryDAO
) : LocalRepository {

    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun saveEntity(weather: Weather) {
        localDataSource.insert(convertWeatherToHistoryEntity(weather))
    }
}