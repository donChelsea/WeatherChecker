package com.example.weatherchecker.presentation.screens.weather

import androidx.compose.runtime.Immutable
import com.example.weatherchecker.domain.models.WeatherData
import com.example.weatherchecker.domain.models.WeatherInfo

@Immutable
data class WeatherState(
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
        val weatherInfo: WeatherInfo? = null,
    ) : ScreenData()
}

@Immutable
sealed class WeatherEvent {
    @Immutable
    data class OnWeatherItemClicked(val weatherData: WeatherData, val location: String): WeatherEvent()
}

@Immutable
sealed class WeatherAction {
    @Immutable
    data class OnWeatherItemClicked(val weatherData: WeatherData, val location: String): WeatherAction()
    data object OnRequestPermissions: WeatherAction()
}