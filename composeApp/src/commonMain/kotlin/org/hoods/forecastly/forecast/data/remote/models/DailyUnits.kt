package org.hoods.forecastly.forecast.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyUnits(
    @SerialName("sunrise")
    val sunrise: String = "",
    @SerialName("sunset")
    val sunset: String = "",
    @SerialName("temperature_2m_max")
    val temperature2mMax: String = "",
    @SerialName("temperature_2m_min")
    val temperature2mMin: String = "",
    @SerialName("time")
    val time: String = "",
    @SerialName("uv_index_max")
    val uvIndexMax: String = "",
    @SerialName("weather_code")
    val weatherCode: String = ""
)