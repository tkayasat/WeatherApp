package com.example.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.repository.RepositoryImplementation
import java.lang.Thread.sleep
import java.util.*

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImplementation: RepositoryImplementation = RepositoryImplementation()
) :
    ViewModel() {

    fun getLiveDate() = liveDataToObserve;

    fun getDataFromRemoteSource() {
        liveDataToObserve.postValue(AppState.Loading)
        Thread {
            sleep(2000)
            val r = Random(10).nextInt()
            if (r > 5)
                liveDataToObserve.postValue(AppState.Success(repositoryImplementation.getWeatherFromRemoteSource()))
            else
                liveDataToObserve.postValue(AppState.Error(IllegalStateException()))
        }.start()
    }
}