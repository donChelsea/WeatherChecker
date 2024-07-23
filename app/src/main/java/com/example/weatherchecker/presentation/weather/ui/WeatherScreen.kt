@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.weatherchecker.presentation.weather.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherchecker.domain.models.WeatherInfo
import com.example.weatherchecker.presentation.composables.DailyWeatherCard
import com.example.weatherchecker.presentation.composables.HourlyWeatherReport
import com.example.weatherchecker.presentation.composables.ShowError
import com.example.weatherchecker.presentation.composables.ShowLoading
import com.example.weatherchecker.presentation.composables.WeatherCard
import com.example.weatherchecker.presentation.composables.WeatherForecast
import com.example.weatherchecker.presentation.screens.weather.ScreenData
import com.example.weatherchecker.presentation.screens.weather.WeatherAction
import com.example.weatherchecker.presentation.screens.weather.WeatherEvent
import com.example.weatherchecker.presentation.screens.weather.WeatherState
import com.example.weatherchecker.presentation.ui.theme.DarkBlue
import com.example.weatherchecker.presentation.ui.theme.DeepBlue
import com.example.weatherchecker.presentation.weather.WeatherViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.events.collect { event ->
            when (event) {
                is WeatherEvent.OnWeatherItemClicked -> {
                }
            }
        }
    }

    WeatherLayout(
        state = state,
        onAction = viewModel::handleAction
    )

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun WeatherLayout(
    state: WeatherState,
    onAction: (WeatherAction) -> Unit,
) {
    when (state.screenData) {
        ScreenData.Initial -> {}
        ScreenData.Loading -> ShowLoading()
        is ScreenData.Error -> ShowError()
        is ScreenData.Data -> state.screenData.weatherInfo?.let { data ->
            WeatherContent(
                weatherInfo = data,
                onAction = onAction,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun WeatherContent(
    weatherInfo: WeatherInfo,
    onAction: (WeatherAction) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberBottomSheetScaffoldState()
    var weatherDayIndex by remember { mutableIntStateOf(0) }

    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        sheetContent = {
            weatherInfo.weatherDataPerDay[weatherDayIndex]?.let { day ->
                HourlyWeatherReport(day, weatherInfo.locationName)
            }
        },
        sheetPeekHeight = 30.dp
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlue)
        ) {
            WeatherCard(weatherInfo = weatherInfo)
            Spacer(modifier = Modifier.height(12.dp))
            WeatherForecast(weatherInfo = weatherInfo)
            Spacer(modifier = Modifier.height(40.dp))
            LazyRow {
                itemsIndexed(weatherInfo.weatherDataPerDay.values.toList().drop(1)) { index, item ->
                    DailyWeatherCard(
                        weatherData = item[index],
                        onClick = {
                            weatherDayIndex = index
                            scope.launch {
                                if (bottomSheetState.bottomSheetState.isVisible) {
                                    bottomSheetState.bottomSheetState.expand()
                                }
                                onAction(WeatherAction.OnWeatherItemClicked(item))
                            }
                        }
                    )
                }
            }
        }
    }
}
