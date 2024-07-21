package com.example.weatherchecker.presentation.screens

import androidx.lifecycle.viewModelScope
import com.example.weatherchecker.common.Resource
import com.example.weatherchecker.common.WeatherCheckerViewModel
import com.example.weatherchecker.domain.location.LocationTracker
import com.example.weatherchecker.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
) : WeatherCheckerViewModel<WeatherState, WeatherEvent, WeatherAction>() {
    override val state: StateFlow<WeatherState>
        get() = _state

    private val _state: MutableStateFlow<WeatherState> = MutableStateFlow(WeatherState())

    override fun handleAction(action: WeatherAction) = when (action) {
        is WeatherAction.OnWeatherItemClicked -> {}
    }

    init {
        loadWeatherInfo()
    }

    private fun loadWeatherInfo() {
        viewModelScope.launch {
            updateState(ScreenData.Loading)
            locationTracker.getCurrentLocation()?.let { location ->
                when (val result = repository.getWeatherData(location.latitude, location.longitude)) {
                    is Resource.Success -> updateState(ScreenData.Data(weatherInfo = result.data))
                    is Resource.Error -> updateState(ScreenData.Error(result.message ?: "An error occurred while getting location."))
                }
            } ?: kotlin.run {
                updateState(ScreenData.Error("Couldn't retrieve location. Make sure to grant permission and enable GPS."))
            }
        }
    }

    private fun updateState(screenData: ScreenData) =
        _state.update { it.copy(screenData = screenData) }
}