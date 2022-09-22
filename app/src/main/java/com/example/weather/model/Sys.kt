package com.example.weather.model


import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("country")
    val country: String, // EG
    @SerializedName("id")
    val id: Int, // 2514
    @SerializedName("sunrise")
    val sunrise: Int, // 1663818189
    @SerializedName("sunset")
    val sunset: Int, // 1663861953
    @SerializedName("type")
    val type: Int // 1
)