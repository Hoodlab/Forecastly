package org.hoods.forecastly.forecast.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyDto(
    @SerialName("temperature_2m")
    val temperature2m: List<Double> = listOf(),
    @SerialName("time")
    val time: List<Long> = listOf(),
    @SerialName("weather_code")
    val weatherCode: List<Int> = listOf()
)