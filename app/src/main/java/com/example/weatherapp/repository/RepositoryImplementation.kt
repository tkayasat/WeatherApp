package com.example.weatherapp.repository

import com.example.weatherapp.domain.Weather

class RepositoryImplementation:Repository {
    override fun getWeatherFromRemoteSource(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalSource(): Weather {
       return Weather()
    }
}