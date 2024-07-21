package com.example.weatherchecker.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class WeatherResultDto(
    @SerializedName("hourly")
    val weatherData: WeatherDataDto,
)
