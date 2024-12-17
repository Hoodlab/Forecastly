package org.hoods.forecastly.forecast.domain.models

import org.hoods.forecast.forecast.domain.models.Hourly

data class Weather(
    val currentWeather: CurrentWeather,
    val daily: Daily,
    val hourly: Hourly,
    val timezone:String,
)