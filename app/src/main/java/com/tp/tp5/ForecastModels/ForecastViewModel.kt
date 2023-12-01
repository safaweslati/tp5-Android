package com.tp.tp5.ForecastModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tp.tp5.RetrofitHelper
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class ForecastViewModel : ViewModel(){
    private val forecastResponse = MutableLiveData<ForecastResponse>()
    var forecast : LiveData<ForecastResponse> = forecastResponse


    fun getForecast(city : String){
        RetrofitHelper.retrofitService.getForecast(city).enqueue(
            object : Callback<ForecastResponse> {
                override fun onResponse(
                    call: Call<ForecastResponse>,
                    response: Response<ForecastResponse>
                ) {
                    if(response.isSuccessful){
                        forecastResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                }
            }
        )
    }

}