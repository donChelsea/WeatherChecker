package com.example.weatherchecker.data.remote.dtos.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherchecker.data.remote.dtos.WeatherDataDto
import com.example.weatherchecker.data.remote.dtos.WeatherResultDto
import com.example.weatherchecker.domain.models.WeatherData
import com.example.weatherchecker.domain.models.WeatherInfo
import com.example.weatherchecker.domain.models.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData,
)

@RequiresApi(Build.VERSION_CODES.O)
fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureInFahrenheit = temperature.roundToInt(),
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
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