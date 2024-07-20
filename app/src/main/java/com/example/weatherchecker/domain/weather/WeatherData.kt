package com.example.weatherchecker.domain.weather

import java.time.LocalDateTime

data class WeatherData(
    val time: LocalDateTime,
    val temperatureInFahrenheit: Double,
    val weatherCode: Int,
    val weatherType: WeatherType,
)
