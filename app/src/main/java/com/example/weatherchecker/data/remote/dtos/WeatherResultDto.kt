package com.example.weatherchecker.data.remote.dtos

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherchecker.domain.weather.WeatherData
import com.example.weatherchecker.domain.weather.WeatherInfo
import com.example.weatherchecker.domain.weather.WeatherType
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class WeatherResultDto(
    @SerializedName("hourly")
    val weatherData: WeatherDataDto,
)

data class WeatherDataDto(
    val time: List<String>,
    @SerializedName("temperature_2m")
    val temperatures: List<Double>,
    @SerializedName("weather_code")
    val weatherCode: List<Int>,
)

data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData,
)

@RequiresApi(Build.VERSION_CODES.O)
fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCode[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureInFahrenheit = temperature,
                weatherCode = weatherCode,
                weatherType = WeatherType.fromWMO(weatherCode)
            ),
        )
    }.groupBy { weatherData ->
        weatherData.index / 24
    }.mapValues { entry ->
        entry.value.map { it.data }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun WeatherResultDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}