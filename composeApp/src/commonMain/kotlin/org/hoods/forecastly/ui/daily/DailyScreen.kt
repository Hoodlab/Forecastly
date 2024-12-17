package org.hoods.forecastly.ui.daily

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import forecastly.composeapp.generated.resources.Res
import forecastly.composeapp.generated.resources.wind_ic
import org.hoods.forecastly.forecast.domain.models.Daily
import org.hoods.forecastly.ui.components.LineGraph
import org.hoods.forecastly.ui.forecast.SunSetWeatherItem
import org.hoods.forecastly.ui.forecast.UvIndexWeatherItem
import org.hoods.forecastly.ui.forecast.degreeTxt
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DailyScreen(
    modifier: Modifier = Modifier,
    dailyViewModel: DailyViewModel = koinViewModel()
) {
    val dailyState by dailyViewModel.dailyState.collectAsStateWithLifecycle()
    var selectedWeatherIndex by remember { mutableStateOf(0) }
    val currentDailyWeatherItem = remember(key1 = selectedWeatherIndex, key2 = dailyState) {
        dailyState.daily?.weatherInfo?.get(selectedWeatherIndex)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when(dailyState.isLoading){
            true -> CircularProgressIndicator()
            else -> {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    item {
                        currentDailyWeatherItem?.let {
                            Text(
                                text = "Max ${it.temperatureMax} Min ${it.temperatureMin}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "7 days forecast",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(Modifier.height(8.dp))

                        }

                        LazyRow {
                            dailyState.daily?.let { daily ->
                                itemsIndexed(items = daily.weatherInfo){index,weather ->
                                    val selectedColor =
                                        if (selectedWeatherIndex == index) MaterialTheme.colorScheme.inverseSurface
                                        else CardDefaults.cardColors().containerColor
                                    DailyWeatherInfoItem(
                                        weatherInfo = weather,
                                        backgroundColor = selectedColor,
                                        onClick = {
                                            selectedWeatherIndex = index
                                        }
                                    )

                                }
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                        currentDailyWeatherItem?.let {
                            Card {
                                Column (
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ){
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            painter = painterResource(resource = Res.drawable.wind_ic),
                                            contentDescription = null
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(text = "WIND", style = MaterialTheme.typography.bodyMedium)
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = it.windSpeed.toString() + " Km/h " + it.windDirection,
                                        style = MaterialTheme.typography.headlineMedium,
                                        modifier = Modifier.basicMarquee()
                                    )
                                }
                            }
                            Spacer(Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillParentMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                SunSetWeatherItem(weatherInfo = it)
                                UvIndexWeatherItem(weatherInfo = it)
                            }
                            Spacer(Modifier.height(8.dp))

                        }
                    }
                    item {
                        dailyState.daily?.let { daily ->
                            LineGraph(
                                dataPoints = daily.weatherInfo,
                                xValueMapper = {it.time.take(2)},
                                yValueMapper = {it.temperatureMax.toFloat()},
                                modifier = Modifier.fillMaxWidth().height(300.dp),
                                graphTitle = "Temperature over days"
                            )
                        }
                    }
                }
            }
        }

    }


}

@Composable
fun DailyWeatherInfoItem(
    modifier: Modifier = Modifier,
    weatherInfo: Daily.WeatherInfo,
    backgroundColor:Color,
    onClick:() -> Unit
) {
    Card (
        onClick = onClick,
        modifier = modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ){
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${weatherInfo.temperatureMax} $degreeTxt",
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Icon(
                painter = painterResource(resource = weatherInfo.weatherStatus.icon),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = weatherInfo.time,
                style = MaterialTheme.typography.bodySmall,
            )

        }
    }

}





