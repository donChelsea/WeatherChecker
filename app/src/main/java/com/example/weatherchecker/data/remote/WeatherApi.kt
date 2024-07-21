package com.example.weatherchecker.data.remote

import com.example.weatherchecker.data.remote.dtos.WeatherResultDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("forecast?hourly=temperature_2m,relative_humidity_2m,weather_code,surface_pressure,wind_speed_10m&temperature_unit=fahrenheit&wind_speed_unit=mph&precipitation_unit=inch&timezone=America%2FNew_York")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") lng: Double,
    ): WeatherResultDto
}
