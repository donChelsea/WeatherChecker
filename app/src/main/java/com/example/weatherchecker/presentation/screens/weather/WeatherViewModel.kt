package com.example.weatherchecker.presentation.screens.weather

import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.viewModelScope
import com.example.weatherchecker.common.Resource
import com.example.weatherchecker.common.WeatherCheckerViewModel
import com.example.weatherchecker.data.location.LocationTracker
import com.example.weatherchecker.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val geocoder: Geocoder,
) : WeatherCheckerViewModel<WeatherState, WeatherEvent, WeatherAction>() {
    override val state: StateFlow<WeatherState>
        get() = _state

    private val _state: MutableStateFlow<WeatherState> = MutableStateFlow(WeatherState())

    override fun handleAction(action: WeatherAction) = when (action) {
        is WeatherAction.OnWeatherItemClicked -> emitUiEvent(
            WeatherEvent.OnWeatherItemClicked(
                action.weatherData,
                action.location
            )
        )

        WeatherAction.OnRequestPermissions -> loadWeatherInfo()
    }

    private fun loadWeatherInfo() {
        viewModelScope.launch {
            updateState(ScreenData.Loading)
            locationTracker.getCurrentLocation()?.let { location ->
                when (val result = repository.getWeatherData(location.latitude, location.longitude)) {
                    is Resource.Success -> {
                        val locationName = getLocationName(location.latitude, location.longitude)
                        result.data?.locationName = locationName
                        updateState(ScreenData.Data(weatherInfo = result.data))
                    }

                    is Resource.Error -> updateState(ScreenData.Error(result.message ?: "An error occurred while getting location."))
                }
            } ?: kotlin.run {
                updateState(ScreenData.Error("Couldn't retrieve location. Make sure to grant permission and enable GPS."))
            }
        }
    }

    private fun updateState(screenData: ScreenData) =
        _state.update { it.copy(screenData = screenData) }

    private fun getLocationName(lat: Double, lng: Double): String {
        var locality = ""
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(lat, lng, 1)
            val address: Address = addresses!![0]
            locality = address.locality.trim()
        } catch (e: IOException) {
            e.printStackTrace()
            println(e.message.toString())
        }
        return locality
    }
}