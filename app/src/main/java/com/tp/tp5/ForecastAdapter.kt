package com.tp.tp5

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.tp.tp5.ForecastModels.ForecastResponse
import com.tp.tp5.databinding.ForecastItemBinding
import kotlin.math.roundToInt
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ForecastAdapter(private val forecast: ForecastResponse?) :
    RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ForecastItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val date = binding.date
        val weatherIcon = binding.weatherIcon
        val temperature = binding.temperature
        val weatherDescription = binding.weatherDescription
        val feelsLike = binding.feelsLike
        val humidity = binding.humidity
        val wind = binding.wind
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ForecastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecastItem = forecast?.list?.get(position)

        // Check if the forecastItem is not null
        forecastItem?.let {
            // Convert timestamp to Date
            val date = Date(it.dt * 1000L) // Multiply by 1000 to convert seconds to milliseconds

            // Format the date to display day and month
            val dateFormatDay = SimpleDateFormat("EEEE", Locale.getDefault())
            val dateFormatDate = SimpleDateFormat("d MMMM", Locale.getDefault())

            val day = dateFormatDay.format(date)
            val formattedDate = dateFormatDate.format(date)

            // Set date with day on one line and date on another line
            holder.date.text = "$day\n$formattedDate"


            // Set weather icon (replace "your_icon" with the actual property from your data model)
            val iconUrl = "https://openweathermap.org/img/wn/${it.weather[0].icon}@2x.png"
            Picasso.get().load(iconUrl).into(holder.weatherIcon)

            // Set temperature and weather description
            val temperatureInCelsius = it.temp.day - 273.15
            holder.temperature.text = "Temperature ${temperatureInCelsius.roundToInt()}°C"
            holder.weatherDescription.text = it.weather[0].description.split(" ")
                .joinToString(" ") { word -> word.replaceFirstChar { char -> char.uppercaseChar() } }

            // Set feels like, humidity, and wind
            holder.feelsLike.text = "Feels Like ${(it.feels_like.day - 273.15).toInt()}°C"
            holder.humidity.text = "Humidity ${it.humidity}"
            holder.wind.text = "Wind ${it.speed}"
        }
    }


    override fun getItemCount(): Int {
        return forecast?.list?.size ?: 0
    }
}
