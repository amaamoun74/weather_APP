package com.example.weather.model


import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("feels_like")
    val feelsLike: Double, // 25.61
    @SerializedName("humidity")
    val humidity: Int, // 61
    @SerializedName("pressure")
    val pressure: Int, // 1011
    @SerializedName("temp")
    val temp: Double, // 25.42
    @SerializedName("temp_max")
    val tempMax: Double, // 25.42
    @SerializedName("temp_min")
    val tempMin: Double // 25.23
)