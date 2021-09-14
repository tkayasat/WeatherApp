package com.example.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.repository.RepositoryImplementation
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImplementation: RepositoryImplementation = RepositoryImplementation()
) : ViewModel() {

    fun getLiveDate() = liveDataToObserve

    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(false)

    fun getWeatherFromLocalSourceRussian() = getDataFromLocalSource(true)

    private fun getDataFromLocalSource(isRussian: Boolean) {
        with(liveDataToObserve) {

            postValue(AppState.Loading)
            Thread {
                sleep(500)
                if (isRussian)
                    postValue(AppState.Success(repositoryImplementation.getWeatherFromLocalStorageRussian()))
                else
                    postValue(AppState.Success(repositoryImplementation.getWeatherFromLocalStorageWorld()))
            }.start()
        }
    }
}