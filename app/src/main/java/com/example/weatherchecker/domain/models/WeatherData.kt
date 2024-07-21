package com.example.weatherchecker.domain.models

import java.time.LocalDateTime

data class WeatherData(
    val time: LocalDateTime,
    val temperatureInFahrenheit: Double,
    val weatherCode: Int,
    val weatherType: WeatherType,
)
