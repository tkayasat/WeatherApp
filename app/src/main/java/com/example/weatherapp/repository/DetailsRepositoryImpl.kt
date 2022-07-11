package com.example.weatherapp.repository

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DetailsRepository {

    override fun getWeatherDetailsFromServer(
        lat: Double,
        log: Double,
        callback: retrofit2.Callback<WeatherDTO>,
    ) {
        remoteDataSource.getWeatherDetails(lat, log, callback)
    }
}