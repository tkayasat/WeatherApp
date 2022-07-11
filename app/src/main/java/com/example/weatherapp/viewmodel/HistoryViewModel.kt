package com.example.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.MyApp
import com.example.weatherapp.repository.LocalRepositoryImpl

class HistoryViewModel(
    private val historyLiveDataToObserve: MutableLiveData<MainState> = MutableLiveData(),
    private val historyRepositoryImpl: LocalRepositoryImpl = LocalRepositoryImpl(MyApp.getHistoryDAO()),
) : ViewModel() {

    fun getAllHistory() {
        historyLiveDataToObserve.value = MainState.Loading
        Thread {
            historyLiveDataToObserve.postValue(MainState.Success(historyRepositoryImpl.getAllHistory()))
        }.start()
    }

    fun getLiveData() = historyLiveDataToObserve
}