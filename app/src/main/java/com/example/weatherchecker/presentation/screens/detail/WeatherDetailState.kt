package com.example.weatherchecker.presentation.screens.detail

import androidx.compose.runtime.Immutable
import com.example.weatherchecker.domain.models.WeatherData
import com.example.weatherchecker.domain.models.WeatherInfo

@Immutable
data class WeatherDetailState(
    val screenData: ScreenData = ScreenData.Initial,
)

@Immutable
sealed class ScreenData {
    data object Initial : ScreenData()
    data object Loading : ScreenData()

    @Immutable
    data class Error(
        val message: String,
    ) : ScreenData()

    @Immutable
    data class Data(
        val weatherData: WeatherData? = null,
        val locationName: String = ""
    ) : ScreenData()
}

@Immutable
sealed class WeatherDetailEvent {}

@Immutable
sealed class WeatherDetailAction {}