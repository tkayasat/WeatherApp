package com.example.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.Weather
import java.lang.Thread.sleep

class MainViewModel(private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()): ViewModel() {

    fun getLiveDate()= liveDataToObserve;
    fun getADtaFromRemoteSource(){
        liveDataToObserve.postValue(AppState.Loading)
        Thread{
            sleep(2000)
            liveDataToObserve.postValue(AppState.Success(Weather()))
        }.start()
    }
}