package com.example.weatherchecker.presentation.screens.detail

import androidx.lifecycle.SavedStateHandle
import com.example.weatherchecker.common.WeatherCheckerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WeatherDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : WeatherCheckerViewModel<WeatherDetailState, WeatherDetailEvent, WeatherDetailAction>() {
    override val state: StateFlow<WeatherDetailState>
        get() = _state

    private val _state: MutableStateFlow<WeatherDetailState> = MutableStateFlow(WeatherDetailState())

    override fun handleAction(action: WeatherDetailAction) = when (action) {
        else -> {}
    }

    private fun updateState(screenData: ScreenData) =
        _state.update { it.copy(screenData = screenData) }
}