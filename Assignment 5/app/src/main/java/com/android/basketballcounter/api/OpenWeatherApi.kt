package com.android.basketballcounter.api

import com.android.basketballcounter.OpenWeatherResponse
import retrofit2.Call
import retrofit2.http.GET

interface OpenWeatherApi {
//    "data/2.5/forecast?id=4956184&appid=a697a406b377b5a3c6c25ac287a60bde"
//     "data/2.5/weather?id=4956184&appid=a697a406b377b5a3c6c25ac287a60bde"
    @GET(
        "data/2.5/weather?id=4956184&appid=a697a406b377b5a3c6c25ac287a60bde"
    )
    fun fetchWeather(): Call<OpenWeatherResponse>

}