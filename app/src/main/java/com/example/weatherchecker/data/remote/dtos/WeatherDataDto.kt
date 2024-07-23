package com.example.weatherchecker.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class WeatherDataDto(
    val time: List<String>,
    @SerializedName("temperature_2m")
    val temperatures: List<Double>,
    @SerializedName("weather_code")
    val weatherCodes: List<Int>,
    @SerializedName("surface_pressure")
    val pressures: List<Double>,
    @SerializedName("wind_speed_10m")
    val windSpeeds: List<Double>,
    @SerializedName("relative_humidity_2m")
    val humidities: List<Double>,
    @SerializedName("apparent_temperature")
    val feelsLike: List<Double>,
    @SerializedName("precipitation_probability")
    val chanceOfRain: List<Int>,
    @SerializedName("cloud_cover")
    val cloudCover: List<Int>,
    @SerializedName("wind_direction_10m")
    val windDirection: List<Double>
)
