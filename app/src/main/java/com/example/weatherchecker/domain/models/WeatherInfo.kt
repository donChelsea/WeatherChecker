package com.example.weatherchecker.domain.models

data class WeatherInfo(
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData?,
) {
    var locationName: String = ""
        set(value) { field = value }
}
