package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep

class MainViewModel(private val liveDataToObserve: MutableLiveData<Any> = MutableLiveData()): ViewModel() {

    fun getLiveDate()= liveDataToObserve;
    fun getADtaFromRemoteSource(){
        Thread{
            sleep(2000)
            liveDataToObserve.postValue(Any())
        }.start()
    }
}