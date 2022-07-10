package com.example.weatherapp.repository

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherDTO(val fact: FactDTO): Parcelable