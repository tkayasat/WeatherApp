package com.example.weatherapp.repository

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class WeatherLoader(val listener: WeatherLoaderListener,val lat:Double,val lon:Double) {
    fun loadWeather(){
        val url = URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")

        Thread{
            val urlConnection = url.openConnection() as HttpsURLConnection
            urlConnection.requestMethod ="GET"
            urlConnection.addRequestProperty("X-Yandex-API-Key","94543579-ce09-462a-a376-f1b03443032d")
            urlConnection.readTimeout = 10000
            val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val weatherDTO = Gson().fromJson(reader,WeatherDTO::class.java)
            val handler = Handler(Looper.getMainLooper())
            handler.post { listener.onLoaded(weatherDTO) }
            urlConnection.disconnect()
        }.start()
    }
}