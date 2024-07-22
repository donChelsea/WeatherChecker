package com.example.weatherchecker.presentation.screens.detail.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.weatherchecker.domain.models.WeatherData
import com.example.weatherchecker.presentation.composables.ShowError
import com.example.weatherchecker.presentation.composables.ShowLoading
import com.example.weatherchecker.presentation.screens.detail.ScreenData
import com.example.weatherchecker.presentation.screens.detail.WeatherDetailAction
import com.example.weatherchecker.presentation.screens.detail.WeatherDetailState
import com.example.weatherchecker.presentation.ui.theme.DarkBlue

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherDetailScreen(viewModel: NavHostController = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

//    LaunchedEffect(key1 = true) {
//        viewModel.events.collect { event ->
//            when (event) {
//                is WeatherEvent.OnWeatherItemClicked -> {
//                    println("${event.weatherData}, ${event.location}")
//                }
//            }
//        }
//    }

    WeatherDetailLayout(
        state = state,
        onAction = viewModel::handleAction
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun WeatherDetailLayout(
    state: WeatherDetailState,
    onAction: (WeatherDetailAction) -> Unit,
) {
    when (state.screenData) {
        ScreenData.Initial -> {}
        ScreenData.Loading -> ShowLoading()
        is ScreenData.Error -> ShowError()
        is ScreenData.Data -> state.screenData.let { screenData ->
            screenData.weatherData?.let { weatherData ->
                WeatherDetailContent(
                    weatherData = weatherData,
                    locationName = screenData.locationName,
                    onAction = onAction
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun WeatherDetailContent(
    weatherData: WeatherData,
    locationName: String,
    onAction: (WeatherDetailAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
    ) {
        println(weatherData.toString())
        println(locationName)
    }
}