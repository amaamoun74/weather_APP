package com.example.weather.modelView

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.model.WeatherModel
import com.example.weather.webServices.WeatherAPIServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ModelView : ViewModel() {
    private val weatherAPIServices = WeatherAPIServices()
    private val disposable = CompositeDisposable()
    val weatherData = MutableLiveData<WeatherModel>()
    val weatherError = MutableLiveData<Boolean>()
    val weatherLoading = MutableLiveData<Boolean>()

    fun refreshData(cityName: String) {
        getDataFromAPI(cityName)
    }

    private fun getDataFromAPI(cityName: String) {
        weatherLoading.value = true
        disposable.add(
            weatherAPIServices.getWeatherData(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherModel>() {
                    override fun onSuccess(t: WeatherModel) {
                        if (!t.equals(null)) {
                            weatherError.value = false
                            weatherLoading.value = false
                            weatherData.value = t
                        }
                    }

                    override fun onError(e: Throwable) {
                        weatherError.value = true
                        weatherLoading.value = false
                        Log.e("Error", "Error: $e")
                    }
                })
        )
    }
}