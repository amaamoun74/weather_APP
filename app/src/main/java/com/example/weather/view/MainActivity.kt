package com.example.weather.view

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.modelView.ModelView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

   private lateinit var viewModel: ModelView
   private lateinit var getPreferences: SharedPreferences
   private lateinit var setPreferences: SharedPreferences.Editor
   private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        viewModel = ViewModelProviders.of(this).get(ModelView::class.java)
        getPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
        setPreferences = getPreferences.edit()

        val cityName = getPreferences.getString(packageName, "cairo")?.lowercase(Locale.getDefault())
        binding.address.text = cityName
        if (cityName != null) {
            viewModel.refreshData(cityName)
        }

        binding.layout.setOnRefreshListener {
            binding.errorText.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            binding.mainContainer.visibility = View.GONE

            viewModel.refreshData(cityName!!)
            binding.layout.isRefreshing = false
        }

        binding.search.setOnClickListener {
            val city = binding.city.text.toString()
            setPreferences.putString(packageName, city)
            setPreferences.apply()
            viewModel.refreshData(city)
            getData()
        }
        getData()
        dynamicBackground()
    }

    private fun dynamicBackground() {
        val animationDrawable: AnimationDrawable = binding.layout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2500)
        animationDrawable.setExitFadeDuration(5000)
        animationDrawable.start()
    }


    @SuppressLint("SetTextI18n")
    private fun getData(){
        viewModel.weatherData.observe(this) { data ->
            data?.let {
                binding.mainContainer.visibility = View.VISIBLE
                binding.address.text = data.name + " ," + data.sys.country
                // momkn error hena
                binding.updatedAt.text = "Updated at: " + SimpleDateFormat(
                    "dd/MM/yyyy hh:mm a",
                    Locale.getDefault()
                ).format(Date())

                binding.temp.text = (data.main.temp.toInt()).toString() + " °C"
                binding.humidity.text = data.main.humidity.toString() + "%"
                binding.wind.text = data.wind.speed.toString()

                // to change sunrise and sunset to time
                binding.sunrise.text = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date((data.sys.sunrise.toLong())*1000))
                binding.sunset.text = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date((data.sys.sunset.toLong())*1000))

                binding.tempMin.text = "Min Temp: " + data.main.tempMin.toString()
                binding.tempMax.text = "Max Temp: " + data.main.tempMax.toString()
                binding.status.text = data.weather[0].description
                binding.pressure.text = data.main.pressure.toString()
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.weather[0].icon + "@2x.png")
                    .into(binding.icon)

            }
        }
        viewModel.weatherError.observe(this) { error ->
            error?.let {
                if (error) {
                    binding.errorText.visibility = View.VISIBLE
                    binding.errorText.text = error.toString()
                    binding.progressBar.visibility = View.GONE
                    binding.mainContainer.visibility = View.GONE
                } else {
                    binding.errorText.visibility = View.GONE
                }
            }
        }
        viewModel.weatherLoading.observe(this,androidx.lifecycle.Observer { loading ->
            loading?.let {
                if (loading) {
                    binding.errorText.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    binding.mainContainer.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })

    }
}