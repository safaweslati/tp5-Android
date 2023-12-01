package com.tp.tp5.WeatherModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tp.tp5.RetrofitHelper
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class WeatherViewModel : ViewModel() {

    private val weatherReponse = MutableLiveData<WeatherResponse>()
    var weather : LiveData<WeatherResponse> = weatherReponse

    init {
        getWeather("tunis")
    }

    fun getWeather(city: String) {
        RetrofitHelper.retrofitService.getWeather(city).enqueue(
            object : Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        Log.d("WeatherViewModel", "Received response: $responseBody")
                        weatherReponse.postValue(response.body())
                    }
                    else {
                        Log.e("WeatherViewModel", "Unsuccessful response: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Log.e("WeatherViewModel", "Network request failed", t)
                }
            }
        )
    }
}
