package com.example.weatherchecker

import android.location.Geocoder
import app.cash.turbine.test
import com.example.weatherchecker.common.Resource
import com.example.weatherchecker.data.location.LocationTracker
import com.example.weatherchecker.domain.models.WeatherData
import com.example.weatherchecker.domain.repository.WeatherRepository
import com.example.weatherchecker.presentation.screens.weather.ScreenData
import com.example.weatherchecker.presentation.screens.weather.WeatherAction
import com.example.weatherchecker.presentation.screens.weather.WeatherEvent
import com.example.weatherchecker.presentation.weather.WeatherViewModel
import com.example.weatherchecker.util.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifyAll
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

class WeatherViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule(testDispatcher)

    @RelaxedMockK
    lateinit var repository: WeatherRepository

    @RelaxedMockK
    lateinit var locationTracker: LocationTracker

    @RelaxedMockK
    lateinit var geocoder: Geocoder

    private lateinit var testSubject: WeatherViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        testSubject = WeatherViewModel(
            repository,
            locationTracker,
            geocoder
        )
    }

    @Test
    fun `handleAction() sends OnWeatherItemClicked action to trigger OnWeatherItemClicked event`() {
        mainCoroutineRule.launch {
            testSubject.events.test {
                testSubject.handleAction(WeatherAction.OnWeatherItemClicked(mockk<List<WeatherData>>()))
                val item = awaitItem()
                assertTrue { item is WeatherEvent.OnWeatherItemClicked}
            }
        }
    }

    @Test
    fun `handleAction() sends OnRequestPermissions to start loading weather info when successful`() {
        mainCoroutineRule.launch {
            testSubject.events.test {
                testSubject.handleAction(WeatherAction.OnRequestPermissions)
                assertTrue { testSubject.state.value.screenData == ScreenData.Data() }
            }

            coVerifyAll {
                locationTracker.getCurrentLocation()
                repository.getWeatherData(mockk(), mockk())
            }
        }
    }

    @Test
    fun `handleAction() sends OnRequestPermissions and returns error when failed`() {
        coEvery {  repository.getWeatherData(mockk(), mockk()) } returns Resource.Error("Error occurred.")

        mainCoroutineRule.launch {
            testSubject.events.test {
                testSubject.handleAction(WeatherAction.OnRequestPermissions)
                assertTrue { testSubject.state.value.screenData == ScreenData.Error("Error occurred.") }
            }

            coVerifyAll {
                locationTracker.getCurrentLocation()
                repository.getWeatherData(mockk(), mockk())
            }
        }
    }
}