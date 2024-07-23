package com.example.weatherchecker.presentation.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherchecker.domain.models.WeatherData
import com.example.weatherchecker.domain.models.WeatherType
import com.example.weatherchecker.presentation.ui.theme.DeepBlue
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HourlyWeatherReport(
    weatherDataList: List<WeatherData>,
    locationName: String,
    modifier: Modifier = Modifier,
) {
    val futureWeather = weatherDataList.filterNot { it.time < LocalDateTime.now() }
    val dayOfWeek = LocalDateTime.parse(weatherDataList[0].time.toString()).dayOfWeek.name

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(DeepBlue)
    ) {
        item {
            Text(
                text = locationName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = dayOfWeek,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            HorizontalDivider(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .height(2.dp)
            )
        }
        items(futureWeather) { data ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    text = data.time.format(DateTimeFormatter.ofPattern("h:mm a")),
                    fontSize = 16.sp,
                    color = Color.White,
                )
                Image(
                    painterResource(id = data.weatherType.iconRes),
                    modifier = Modifier.size(25.dp),
                    contentDescription = ""
                )
                Text(
                    text = "${data.temperatureInFahrenheit}°F",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun PreviewHourlyWeatherReport() {
    val weather = WeatherData(
        time = LocalDateTime.now(),
        temperatureInFahrenheit = 89,
        weatherType = WeatherType.fromWMO(3),
        pressure = 9.0,
        windSpeed = 4.0,
        humidity = 6.0,
        feelsLike = 92.9,
        chanceOfRain = 50,
        cloudCover = 10,
        windDirection = 33.3
    )
    HourlyWeatherReport(weatherDataList = listOf(weather, weather), locationName = "New York")
}
