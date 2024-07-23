package com.example.weatherchecker.presentation.composables

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherchecker.R
import com.example.weatherchecker.domain.models.WeatherData
import com.example.weatherchecker.domain.models.WeatherInfo
import com.example.weatherchecker.domain.models.WeatherType
import com.example.weatherchecker.presentation.ui.theme.DeepBlue
import java.time.LocalDateTime
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherCard(
    weatherInfo: WeatherInfo,
    modifier: Modifier = Modifier,
) {
    var rotated by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(500)
    )

    val animateFront by animateFloatAsState(
        targetValue = if (!rotated) 1f else 0f,
        animationSpec = tween(500)
    )

    val animateBack by animateFloatAsState(
        targetValue = if (rotated) 1f else 0f,
        animationSpec = tween(500)
    )

    weatherInfo.currentWeatherData?.let { data ->
        Card(
            modifier = modifier
                .fillMaxWidth()
                .requiredHeight(470.dp)
                .padding(16.dp)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 8 * density
                }
                .clickable {
                    rotated = !rotated
                },
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = DeepBlue)
        ) {
            if (!rotated) {
                WeatherCardFront(
                    weatherData = weatherInfo.currentWeatherData,
                    locationName = weatherInfo.locationName,
                    animateFront = animateFront,
                    rotation = rotation
                )
            } else {
                WeatherCardBack(
                    weatherData = data,
                    animateBack = animateBack,
                    rotation = rotation
                )
            }
        }
    }
}

@Composable
fun WeatherCardFront(
    weatherData: WeatherData,
    locationName: String,
    animateFront: Float,
    rotation: Float,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .graphicsLayer {
                alpha = animateFront
                rotationY = rotation
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = locationName,
            color = Color.White,
            modifier = Modifier.align(Alignment.End)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = weatherData.weatherType.iconRes),
            contentDescription = weatherData.weatherType.weatherDesc,
            modifier = Modifier.width(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${weatherData.temperatureInFahrenheit}°F",
            fontSize = 50.sp,
            color = Color.White,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = weatherData.weatherType.weatherDesc,
            fontSize = 20.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            WeatherDataDisplay(
                value = weatherData.pressure.roundToInt(),
                unit = "hPa",
                icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
                iconTint = Color.White,
                textStyle = TextStyle(color = Color.White)
            )
            WeatherDataDisplay(
                value = weatherData.humidity.roundToInt(),
                unit = "%",
                icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
                iconTint = Color.White,
                textStyle = TextStyle(color = Color.White)
            )
            WeatherDataDisplay(
                value = weatherData.windSpeed.roundToInt(),
                unit = "mph",
                icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                iconTint = Color.White,
                textStyle = TextStyle(color = Color.White)
            )
        }
    }
}

@Composable
fun WeatherCardBack(
    weatherData: WeatherData,
    animateBack: Float,
    rotation: Float,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(DeepBlue),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WeatherCardDataRow(
            "${weatherData.pressure.roundToInt()} hPa",
            icon = R.drawable.ic_pressure,
            title = "Surface Pressure",
            modifier = Modifier.graphicsLayer {
                alpha = animateBack
                rotationY = rotation
            }
        )
        HorizontalDivider(
            modifier = Modifier
                .height(2.dp)
                .padding(horizontal = 16.dp)
        )
        WeatherCardDataRow(
            value = "${weatherData.windSpeed.roundToInt()} mph",
            icon = R.drawable.ic_wind,
            title = "Wind Speed",
            modifier = Modifier.graphicsLayer {
                alpha = animateBack
                rotationY = rotation
            }
        )
        HorizontalDivider(
            modifier = Modifier
                .height(2.dp)
                .padding(horizontal = 16.dp)
        )
        WeatherCardDataRow(
            value = "${weatherData.windDirection.roundToInt()}",
            icon = R.drawable.ic_windy,
            title = "Wind Direction",
            modifier = Modifier.graphicsLayer {
                alpha = animateBack
                rotationY = rotation
            }
        )
        HorizontalDivider(
            modifier = Modifier
                .height(2.dp)
                .padding(horizontal = 16.dp)
        )
        WeatherCardDataRow(
            value = "${weatherData.humidity.roundToInt()}%",
            icon = R.drawable.ic_drop,
            title = "Humidity",
            modifier = Modifier.graphicsLayer {
                alpha = animateBack
                rotationY = rotation
            }
        )
        HorizontalDivider(
            modifier = Modifier
                .height(2.dp)
                .padding(horizontal = 16.dp)
        )
        WeatherCardDataRow(
            value = "${weatherData.feelsLike.roundToInt()}°F",
            icon = R.drawable.ic_sunny,
            title = "Feels like",
            modifier = Modifier.graphicsLayer {
                alpha = animateBack
                rotationY = rotation
            }
        )
        HorizontalDivider(
            modifier = Modifier
                .height(2.dp)
                .padding(horizontal = 16.dp)
        )
        WeatherCardDataRow(
            value = "${weatherData.chanceOfRain}%",
            icon = R.drawable.ic_rainy,
            title = "Precipitation",
            modifier = Modifier.graphicsLayer {
                alpha = animateBack
                rotationY = rotation
            }
        )
        HorizontalDivider(
            modifier = Modifier
                .height(2.dp)
                .padding(horizontal = 16.dp)
        )
        WeatherCardDataRow(
            value = "${weatherData.cloudCover}%",
            icon = R.drawable.ic_cloudy,
            title = "Cloud cover",
            modifier = Modifier.graphicsLayer {
                alpha = animateBack
                rotationY = rotation
            }
        )
    }
}

@Composable
fun WeatherCardDataRow(
    value: String,
    @DrawableRes icon: Int,
    title: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(
                imageVector = ImageVector.vectorResource(id = icon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(25.dp)
                    .padding(end = 8.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
            )
        }
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun PreviewWeatherCardFront() {
    WeatherCard(
        weatherInfo = WeatherInfo(
            mapOf(),
            WeatherData(
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
        )
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun PreviewWeatherCardBack() {
    WeatherCardBack(
        weatherData =
        WeatherData(
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
        ),
        animateBack = 0f,
        rotation = 0f,
    )
}