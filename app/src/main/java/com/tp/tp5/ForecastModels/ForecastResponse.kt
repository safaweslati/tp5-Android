package com.tp.tp5.ForecastModels

data class ForecastResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Infos>,
    val message: Double
)