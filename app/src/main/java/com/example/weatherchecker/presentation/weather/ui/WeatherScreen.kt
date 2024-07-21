package com.example.weatherchecker.presentation.weather.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherchecker.domain.models.WeatherInfo
import com.example.weatherchecker.presentation.composables.DailyWeatherCard
import com.example.weatherchecker.presentation.composables.ShowError
import com.example.weatherchecker.presentation.composables.ShowLoading
import com.example.weatherchecker.presentation.composables.WeatherCard
import com.example.weatherchecker.presentation.composables.WeatherForecast
import com.example.weatherchecker.presentation.ui.theme.DarkBlue
import com.example.weatherchecker.presentation.ui.theme.DeepBlue
import com.example.weatherchecker.presentation.weather.ScreenData
import com.example.weatherchecker.presentation.weather.WeatherState
import com.example.weatherchecker.presentation.weather.WeatherViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val state by viewModel.state.collectAsState()

    WeatherLayout(state = state)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun WeatherLayout(state: WeatherState) {
    when (state.screenData) {
        ScreenData.Initial -> {}
        ScreenData.Loading -> ShowLoading()
        is ScreenData.Error -> ShowError()
        is ScreenData.Data -> state.screenData.weatherInfo?.let { data ->
            WeatherContent(weatherInfo = data)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun WeatherContent(weatherInfo: WeatherInfo) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
    ) {
        WeatherCard(
            weatherInfo = weatherInfo,
            backgroundColor = DeepBlue
        )
        Spacer(modifier = Modifier.height(16.dp))
        WeatherForecast(weatherInfo = weatherInfo)
        Spacer(modifier = Modifier.height(40.dp))
        LazyRow {
            itemsIndexed(weatherInfo.weatherDataPerDay.values.toList().drop(1)) { index, item ->
                DailyWeatherCard(weatherData = item[index])
            }
        }
    }
}