package com.example.weatherapp.repository

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FactDTO  (
    val temp : Int,
    val feels_like : Int,
    val condition : String,
    val icon : String
): Parcelable