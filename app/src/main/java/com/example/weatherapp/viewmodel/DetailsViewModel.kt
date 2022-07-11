package com.example.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.MyApp.Companion.getHistoryDAO
import com.example.weatherapp.domain.Weather
import com.example.weatherapp.repository.DetailsRepositoryImpl
import com.example.weatherapp.repository.LocalRepositoryImpl
import com.example.weatherapp.repository.RemoteDataSource
import com.example.weatherapp.repository.WeatherDTO
import convertDtoToModel

class DetailsViewModel(
    private val detailsLiveDataToObserve: MutableLiveData<DetailsState> = MutableLiveData(),
    private val detailsRepositoryImpl: DetailsRepositoryImpl = DetailsRepositoryImpl(
        RemoteDataSource()
    ),
    private val historyRepositoryImpl: LocalRepositoryImpl = LocalRepositoryImpl(getHistoryDAO()),
) :
    ViewModel() {

    fun getLiveData() = detailsLiveDataToObserve

    fun getWeatherFromRemoteSource(lat: Double, lon: Double) {
        detailsLiveDataToObserve.value = DetailsState.Loading
        detailsRepositoryImpl.getWeatherDetailsFromServer(lat, lon, callback)
    }

    fun saveWeather(weather: Weather) {
        historyRepositoryImpl.saveEntity(weather)
    }

    private val callback = object : retrofit2.Callback<WeatherDTO> {

        override fun onResponse(
            call: retrofit2.Call<WeatherDTO>,
            response: retrofit2.Response<WeatherDTO>,
        ) {
            if (response.isSuccessful && response.body() != null) {
                val weatherDTO = response.body()
                weatherDTO?.let {
                    detailsLiveDataToObserve.postValue(
                        DetailsState.Success(
                            convertDtoToModel(
                                weatherDTO
                            )
                        )
                    )
                }
            } else {
                detailsLiveDataToObserve.postValue(DetailsState.Error("Error Loading Details"))
            }
        }

        override fun onFailure(call: retrofit2.Call<WeatherDTO>, t: Throwable) {
            detailsLiveDataToObserve.postValue(DetailsState.Error("Failure Loading Details"))
        }
    }
}