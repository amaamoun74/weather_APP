package com.example.weather.model


import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("deg")
    val deg: Int, // 80
    @SerializedName("speed")
    val speed: Double // 2.57
)