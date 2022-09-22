package com.example.weather.webServices

import com.example.weather.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface APIServices {
    //https://api.openweathermap.org/data/2.5/weather?q=cairo&units=metric&appid=25fd09f561c3dbc9470ee5ff51ce2fcf
    @GET("data/2.5/weather?&units=metric&appid=25fd09f561c3dbc9470ee5ff51ce2fcf")
    fun getWeather(@Query("q") cityName: String):Single<WeatherModel>
}