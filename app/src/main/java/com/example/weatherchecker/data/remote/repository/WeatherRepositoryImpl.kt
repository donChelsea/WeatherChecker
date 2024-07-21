package com.example.weatherchecker.data.remote.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherchecker.common.Resource
import com.example.weatherchecker.data.remote.WeatherApi
import com.example.weatherchecker.data.remote.dtos.mappers.toWeatherInfo
import com.example.weatherchecker.domain.models.WeatherInfo
import com.example.weatherchecker.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
) : WeatherRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeatherData(lat: Double, lng: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    lng = lng
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}