package com.android.basketballcounter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.basketballcounter.api.OpenWeatherApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OpenWeatherFetcher {

    private val openWeatherApi: OpenWeatherApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        openWeatherApi = retrofit.create(OpenWeatherApi::class.java)
    }

    fun fetchWeather(): LiveData<WeatherItem> {
        val responseLiveData: MutableLiveData<WeatherItem> = MutableLiveData()
        val openWeatherRequest: Call<OpenWeatherResponse> = openWeatherApi.fetchWeather()

        openWeatherRequest.enqueue(object : Callback<OpenWeatherResponse> {

            override fun onResponse(call: Call<OpenWeatherResponse>, response: Response<OpenWeatherResponse>) {
                val openWeatherResponse: OpenWeatherResponse? = response.body()
                val weatherResponse = openWeatherResponse?.weather?.get(0)

                responseLiveData.value = weatherResponse
            }

            override fun onFailure(call: Call<OpenWeatherResponse>, t: Throwable) {
                Log.e(Debug, "Failed to fetch weather data", t)
            }
        })

        return responseLiveData
    }

    fun fetchTemp(): LiveData<TemperatureItem> {
        val responseLiveData: MutableLiveData<TemperatureItem> = MutableLiveData()
        val openWeatherRequest: Call<OpenWeatherResponse> = openWeatherApi.fetchWeather()

        openWeatherRequest.enqueue(object : Callback<OpenWeatherResponse> {

            override fun onResponse(call: Call<OpenWeatherResponse>, response: Response<OpenWeatherResponse>) {
                val openWeatherResponse: OpenWeatherResponse? = response.body()
                val weatherResponse = openWeatherResponse?.main

                responseLiveData.value = weatherResponse
            }

            override fun onFailure(call: Call<OpenWeatherResponse>, t: Throwable) {
                Log.e(Debug, "Failed to fetch weather data", t)
            }
        })

        return responseLiveData
    }

}