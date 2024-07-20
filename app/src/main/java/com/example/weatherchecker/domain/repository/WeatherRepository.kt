package com.example.weatherchecker.domain.repository

import com.example.weatherchecker.common.Resource
import com.example.weatherchecker.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, lng: Double): Resource<WeatherInfo>
}