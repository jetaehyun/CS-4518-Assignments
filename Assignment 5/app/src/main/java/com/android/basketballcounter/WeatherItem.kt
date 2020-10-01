package com.android.basketballcounter

import com.google.gson.annotations.SerializedName

data class WeatherItem(@SerializedName("id")var id: Int = 0,
                       @SerializedName("main")var main: String = "",
                       @SerializedName("description")var description: String = "",
                       @SerializedName("icon")var icon_n: String = "")