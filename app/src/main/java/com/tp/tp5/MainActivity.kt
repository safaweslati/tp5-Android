package com.tp.tp5

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.R
import com.squareup.picasso.Picasso
import com.tp.tp5.WeatherModels.WeatherResponse
import com.tp.tp5.WeatherModels.WeatherViewModel
import com.tp.tp5.databinding.ActivityMainBinding
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {
        private lateinit var binding : ActivityMainBinding
        private val weatherViewModel by viewModels<WeatherViewModel>()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            weatherViewModel.weather.observe(this) {
                    setWeather(it)
            }

            val cities = listOf<String>("Tunis", "London", "Paris")
            val citiesAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, cities)
            val spinner = binding.citiesSpinner
            spinner.adapter = citiesAdapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    weatherViewModel.getWeather(cities[p2])
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}

            }

            binding.forecastButton.setOnClickListener{
                val intent = Intent(this, WeatherForecastActivity::class.java)
                intent.putExtra("city", spinner.selectedItem.toString())
                startActivity(intent)
            }
        }

        fun setWeather(weather : WeatherResponse){
            val iconUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}@2x.png"

            Picasso.get().load(iconUrl)
                .into(binding.weatherIcon)

            binding.cityTextView.text = weather.name
            val temperatureInCelsius = weather.main.temp - 273.15
            binding.temperatureTextView.text = "${temperatureInCelsius.roundToInt()}°C"

            // Capitalize the first letter of every word in the description
            val capitalizedDescription = weather.weather[0].description.split(" ")
                .joinToString(" ") { it.replaceFirstChar { char -> char.uppercaseChar() } }

            binding.weatherTextView.text = capitalizedDescription

            binding.humidityTextView.text = "Humidity  ${weather.main.humidity}"
            binding.pressureTextView.text = "Pressure  ${weather.main.pressure}"

        }
}
