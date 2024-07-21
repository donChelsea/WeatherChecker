package com.example.weatherchecker.presentation.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherchecker.domain.models.WeatherData
import com.example.weatherchecker.domain.models.WeatherType
import com.example.weatherchecker.presentation.ui.theme.LightBlue
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DailyWeatherCard(
    modifier: Modifier = Modifier,
    weatherData: WeatherData,
) {
    val iconSize = 60.dp
    val offsetInPx = LocalDensity.current.run { (iconSize / 2).roundToPx() }
    val dayOfWeek = LocalDateTime.parse(weatherData.time.toString()).dayOfWeek.name

    Box(modifier = modifier.padding(horizontal = 8.dp)) {
        Card(
            modifier = Modifier.requiredSize(140.dp),
            colors = CardDefaults.cardColors(containerColor = LightBlue)
        ) {
            Box(
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Text(
                    text = "${weatherData.temperatureInFahrenheit}°F",
                    fontSize = 40.sp,
                    color = White,
                    modifier = Modifier.padding(top = 46.dp)
                )
            }
        }

        Image(
            painter = painterResource(id = weatherData.weatherType.iconRes),
            contentDescription = null,
            modifier = Modifier
                .offset {
                    IntOffset(x = offsetInPx / 40, y = -offsetInPx)
                }
                .clip(CircleShape)
                .background(Transparent)
                .size(iconSize)
                .align(Alignment.TopCenter)
        )

        Text(
            text = dayOfWeek,
            fontSize = 14.sp,
            color = White,
            modifier = Modifier
                .align(BottomCenter)
                .padding(bottom = 8.dp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun PreviewDailyWeatherCard() {
    DailyWeatherCard(
        weatherData = WeatherData(
            time = LocalDateTime.now(),
            temperatureInFahrenheit = 89,
            weatherType = WeatherType.fromWMO(3),
            pressure = 9.0,
            windSpeed = 4.0,
            humidity = 6.0
        )
    )
}