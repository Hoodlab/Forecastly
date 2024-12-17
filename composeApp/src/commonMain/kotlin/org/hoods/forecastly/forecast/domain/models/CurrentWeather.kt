package org.hoods.forecastly.forecast.domain.models

import org.hoods.forecastly.utils.WeatherInfoItem


data class CurrentWeather(
    val temperature: Double,
    val time: String,
    val weatherStatus: WeatherInfoItem,
    val windDirection: String,
    val windSpeed: Double,
    val isDay: Boolean
)