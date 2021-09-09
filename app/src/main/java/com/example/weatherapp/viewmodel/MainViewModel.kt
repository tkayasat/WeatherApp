package com.example.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.repository.RepositoryImplementation
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImplementation: RepositoryImplementation = RepositoryImplementation()
) :
    ViewModel() {

    fun getLiveDate() = liveDataToObserve

    fun getWeatherFromLocalSourceWorld() {
        getDataFromLocalSource(false)
    }

    fun getWeatherFromLocalSourceRussian() {
        getDataFromLocalSource(true)
    }

    private fun getDataFromLocalSource(isRussian: Boolean) {
        liveDataToObserve.postValue(AppState.Loading)
        Thread {
            sleep(500)
            if (isRussian)
                liveDataToObserve.postValue(AppState.Success(repositoryImplementation.getWeatherFromLocalStorageRussian()))
            else
                liveDataToObserve.postValue(AppState.Success(repositoryImplementation.getWeatherFromLocalStorageWorld()))
        }.start()
    }
}