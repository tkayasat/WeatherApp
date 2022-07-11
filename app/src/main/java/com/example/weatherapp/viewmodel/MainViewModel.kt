package com.example.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.repository.RepositoryImplementation
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<MainState> = MutableLiveData(),
    private val repositoryImplementation: RepositoryImplementation = RepositoryImplementation(),
) : ViewModel() {

    fun getLiveDate() = liveDataToObserve

    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(false)

    fun getWeatherFromLocalSourceRussian() = getDataFromLocalSource(true)

    private fun getDataFromLocalSource(isRussian: Boolean) {
        with(liveDataToObserve) {

            postValue(MainState.Loading)
            Thread {
                sleep(500)
                if (isRussian)
                    postValue(MainState.Success(repositoryImplementation.getWeatherFromLocalStorageRussian()))
                else
                    postValue(MainState.Success(repositoryImplementation.getWeatherFromLocalStorageWorld()))
            }.start()
        }
    }
}