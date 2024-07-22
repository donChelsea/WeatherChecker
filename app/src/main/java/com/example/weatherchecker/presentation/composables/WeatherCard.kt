package com.example.weatherchecker.presentation.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
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
    backgroundColor: Color,
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
                .padding(16.dp)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 8 * density
                }
                .clickable {
                    rotated = !rotated
                },
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = backgroundColor)
        ) {
            if (!rotated) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = weatherInfo.locationName,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.End)
                            .graphicsLayer {
                                alpha = animateFront
                            }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = painterResource(id = data.weatherType.iconRes),
                        contentDescription = data.weatherType.weatherDesc,
                        modifier = Modifier
                            .width(200.dp)
                            .graphicsLayer {
                                alpha = animateFront
                            }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "${data.temperatureInFahrenheit}°F",
                        fontSize = 50.sp,
                        color = Color.White,
                        modifier = Modifier.graphicsLayer {
                            alpha = animateFront
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = data.weatherType.weatherDesc,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        WeatherDataDisplay(
                            value = data.pressure.roundToInt(),
                            unit = "hPa",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
                            iconTint = Color.White,
                            textStyle = TextStyle(color = Color.White)
                        )
                        WeatherDataDisplay(
                            value = data.humidity.roundToInt(),
                            unit = "%",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
                            iconTint = Color.White,
                            textStyle = TextStyle(color = Color.White)
                        )
                        WeatherDataDisplay(
                            value = data.windSpeed.roundToInt(),
                            unit = "mph",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                            iconTint = Color.White,
                            textStyle = TextStyle(color = Color.White)
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Rotated",
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.End)
                            .graphicsLayer {
                                alpha = animateBack
                                rotationY = rotation
                            }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = painterResource(id = data.weatherType.iconRes),
                        contentDescription = data.weatherType.weatherDesc,
                        modifier = Modifier
                            .width(200.dp)
                            .graphicsLayer {
                                alpha = animateBack
                            }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "${data.temperatureInFahrenheit}°F",
                        fontSize = 50.sp,
                        color = Color.White,
                        modifier = Modifier.graphicsLayer {
                            alpha = animateBack
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = data.weatherType.weatherDesc,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        WeatherDataDisplay(
                            value = data.pressure.roundToInt(),
                            unit = "hPa",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
                            iconTint = Color.White,
                            textStyle = TextStyle(color = Color.White)
                        )
                        WeatherDataDisplay(
                            value = data.humidity.roundToInt(),
                            unit = "%",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
                            iconTint = Color.White,
                            textStyle = TextStyle(color = Color.White)
                        )
                        WeatherDataDisplay(
                            value = data.windSpeed.roundToInt(),
                            unit = "mph",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                            iconTint = Color.White,
                            textStyle = TextStyle(color = Color.White)
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun PreviewWeatherCard() {
    WeatherCard(
        weatherInfo = WeatherInfo(
            mapOf(),
            WeatherData(
                time = LocalDateTime.now(),
                temperatureInFahrenheit = 89,
                weatherType = WeatherType.fromWMO(3),
                pressure = 9.0,
                windSpeed = 4.0,
                humidity = 6.0
            )
        ),
        backgroundColor = DeepBlue,
    )
}