package com.example.weatherchecker.domain.models

import java.time.LocalDateTime

data class WeatherData(
    val time: LocalDateTime,
    val temperatureInFahrenheit: Int,
    val weatherType: WeatherType,
    val pressure: Double,
    val windSpeed: Double,
    val humidity: Double,
    val feelsLike: Double,
    val chanceOfRain: Int,
    val cloudCover: Int,
    val windDirection: Double,
)