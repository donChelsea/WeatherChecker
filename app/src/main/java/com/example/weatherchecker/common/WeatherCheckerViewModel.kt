package com.example.weatherchecker.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class WeatherCheckerViewModel<UiState, UiEvent, UiAction> : ViewModel() {

    protected val _events = MutableSharedFlow<UiEvent>()

    abstract val state: StateFlow<UiState>

    val events: SharedFlow<UiEvent> = _events.asSharedFlow()

    abstract fun handleAction(action: UiAction)

    protected fun emitUiEvent(event: UiEvent) {
        viewModelScope.launch { _events.emit(event) }
    }
}