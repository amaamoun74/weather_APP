package com.example.weather.model


import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lat")
    val lat: Double, // 30.0626
    @SerializedName("lon")
    val lon: Double // 31.2497
)