package com.example.weatherapp.view

import com.example.weatherapp.domain.Weather

interface OnItemViewClickListener {
    fun onItemClick(weather: Weather)
}