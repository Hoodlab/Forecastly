package org.hoods.forecastly.utils

import forecastly.composeapp.generated.resources.Res
import forecastly.composeapp.generated.resources.clear_sky
import forecastly.composeapp.generated.resources.drizzle
import forecastly.composeapp.generated.resources.fog
import forecastly.composeapp.generated.resources.freezing_drizzle
import forecastly.composeapp.generated.resources.freezing_rain
import forecastly.composeapp.generated.resources.mainly_clear
import forecastly.composeapp.generated.resources.over_cast
import forecastly.composeapp.generated.resources.rain_heavy
import forecastly.composeapp.generated.resources.rain_slight
import forecastly.composeapp.generated.resources.snow_fall
import forecastly.composeapp.generated.resources.snow_fall_slight
import forecastly.composeapp.generated.resources.thunder_storm
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.DrawableResource

object Util {
    private val DIRECTIONS = listOf(
        "North",
        "North East",
        "East",
        "South East",
        "South",
        "South West",
        "West",
        "North West"
    )

    @OptIn(FormatStringsInDatetimeFormats::class)
    fun formatNormalDate(pattern: String, time: Long): String {
        val instant = Instant.fromEpochSeconds(time)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        val formatter = DateTimeFormat.formatAsKotlinBuilderDsl(
            DateTimeComponents.Format {
                byUnicodePattern(pattern)
            }
        )
        return formatter

    }

    fun formatUnixToHour(unixTimestamp: Long, timeZone: String = TimeZone.currentSystemDefault().id): String {
        val instant = Instant.fromEpochSeconds(unixTimestamp) // Convert Unix timestamp to Instant
        val localDateTime = instant.toLocalDateTime(TimeZone.of(timeZone)) // Convert to LocalDateTime

        // Extract hour and minute, and format them
        val hour = localDateTime.hour.toString().padStart(2, '0') // Ensure two digits
        val minute = localDateTime.minute.toString().padStart(2, '0') // Ensure two digits
        return "$hour:$minute" // Combine into HH:mm format
    }


    fun formatUnixToDay(unixTimestamp: Long, timeZone: String = TimeZone.currentSystemDefault().id): String {
        val instant = Instant.fromEpochSeconds(unixTimestamp) // Convert Unix timestamp to Instant
        val localDateTime = instant.toLocalDateTime(TimeZone.of(timeZone)) // Convert to LocalDateTime
        return localDateTime.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() } // E.g., "Monday"
    }

    fun formatUnixToCustom(unixTimestamp: Long, timeZone: String = TimeZone.currentSystemDefault().id): String {
        val instant = Instant.fromEpochSeconds(unixTimestamp)
        val localDateTime = instant.toLocalDateTime(TimeZone.of(timeZone))

        // Example custom format: "MMM, d" (e.g., "Nov, 29")
        val month = localDateTime.month.name.lowercase().replaceFirstChar { it.uppercase() } // "November"
        val day = localDateTime.dayOfMonth
        return "$month, $day"
    }



    fun getWindDirection(windDirection: Double): String {
        return DIRECTIONS[(windDirection % 360 / 45 % 8).toInt()]
    }

    fun isTodayDate(day: String): Boolean {
        val todayDate = formatUnixToDay(Clock.System.now().toEpochMilliseconds())
        return todayDate.lowercase() == day.lowercase()
    }

    fun getWeatherInfo(code: Int): WeatherInfoItem {
        return when (code) {
            0 -> WeatherInfoItem("Clear sky", Res.drawable.clear_sky)
            1 -> WeatherInfoItem("Mainly clear", Res.drawable.mainly_clear)
            2 -> WeatherInfoItem("partly cloudy", Res.drawable.mainly_clear)
            3 -> WeatherInfoItem("overcast", Res.drawable.over_cast)
            45, 48 -> WeatherInfoItem("Fog", Res.drawable.fog)
            51, 53, 55,
            -> WeatherInfoItem("Drizzle", Res.drawable.drizzle)

            56, 57 -> WeatherInfoItem("Freezing Drizzle", Res.drawable.freezing_drizzle)
            61,
            -> WeatherInfoItem("Rain: Slight", Res.drawable.rain_slight)

            63 -> WeatherInfoItem("Rain: Moderate", Res.drawable.rain_heavy)
            65 -> WeatherInfoItem("Rain: Heavy", Res.drawable.rain_heavy)
            66, 67 -> WeatherInfoItem("Freezing Rain", Res.drawable.freezing_rain)
            71 -> WeatherInfoItem("Snow fall: Slight", Res.drawable.snow_fall_slight)
            73 -> WeatherInfoItem("Snow fall: moderate", Res.drawable.snow_fall_slight)
            75 -> WeatherInfoItem("Snow fall: Heavy", Res.drawable.snow_fall)
            77 -> WeatherInfoItem("Snow grains", Res.drawable.snow_fall)
            80, 81, 82 -> WeatherInfoItem("Rain showers: Slight", Res.drawable.rain_slight)
            85, 86 -> WeatherInfoItem("Snow showers slight", Res.drawable.snow_fall_slight)
            95, 96, 99 -> WeatherInfoItem("Thunderstorm: Slight", Res.drawable.thunder_storm)
            else -> WeatherInfoItem("Unknown", Res.drawable.clear_sky)
        }
    }



}

data class WeatherInfoItem(
    val info: String,
    val icon: DrawableResource
)