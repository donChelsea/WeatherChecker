package com.example.weatherchecker.presentation.screens

import androidx.compose.runtime.Immutable
import com.example.weatherchecker.domain.models.WeatherInfo

@Immutable
data class WeatherState(
    val screenData: ScreenData = ScreenData.Initial,
)

@Immutable
sealed class ScreenData {
    data object Initial : ScreenData()
    data object Loading : ScreenData()
    data object Offline : ScreenData()

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
    data class OnWeatherItemClicked(val coinId: String, val coinName: String): WeatherEvent()
}

@Immutable
sealed class WeatherAction {
    @Immutable
    data class OnWeatherItemClicked(val coinId: String, val coinName: String): WeatherAction()
    data object OnRequestPermissions: WeatherAction()
}