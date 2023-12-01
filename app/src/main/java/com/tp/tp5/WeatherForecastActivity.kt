package com.tp.tp5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tp.tp5.ForecastModels.ForecastViewModel
import com.tp.tp5.databinding.ActivityWeatherForecastBinding

class WeatherForecastActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWeatherForecastBinding
    var forecastViewModel : ForecastViewModel = ForecastViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val city=intent.getStringExtra("city")
        if(city != null){
            forecastViewModel.getForecast(city)
        }

        forecastViewModel.forecast.observe(this) {
            if (it != null){
                binding.forecastRecycler.apply {
                    layoutManager = LinearLayoutManager(this@WeatherForecastActivity)
                    adapter = ForecastAdapter(forecastViewModel.forecast.value)
                }
                binding.cityName.text = forecastViewModel.forecast.value!!.city.name
            }
        }

    }
}