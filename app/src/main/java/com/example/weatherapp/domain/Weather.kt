package com.example.weatherapp.domain

data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = +14,
    val feelsLike: Int = +10
)

private fun getDefaultCity() = City("Псков", 57.0, 28.0)