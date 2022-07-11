package com.example.weatherapp.repository

import com.example.weatherapp.utils.YANDEX_API_KEY_NAME
import com.example.weatherapp.utils.YANDEX_API_URL_END_POINT
import com.example.weatherapp.utils.YANDEX_API_URL_END_POINT_FACT
import com.example.weatherapp.utils.YANDEX_API_URL_END_POINT_IMG
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherApi {

    @GET(YANDEX_API_URL_END_POINT)
    fun getWeather(
        @Header(YANDEX_API_KEY_NAME) apikey: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
    ): Call<WeatherDTO>

    @GET(YANDEX_API_URL_END_POINT_FACT)
    fun getFact(): Call<FactDTO>

    @GET(YANDEX_API_URL_END_POINT_IMG)
    fun getImage(): Call<FactDTO>
}